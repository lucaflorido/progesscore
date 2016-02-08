package it.progess.core.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.google.gson.Gson;

import it.progess.core.hibernate.HibernateUtils;
import it.progess.core.pojo.TblCompany;
import it.progess.core.pojo.TblDraft;
import it.progess.core.pojo.TblRole;
import it.progess.core.pojo.TblUser;
import it.progess.core.properties.MailParameter;
import it.progess.core.vo.Company;
import it.progess.core.vo.Contact;
import it.progess.core.vo.Customer;
import it.progess.core.vo.Draft;
import it.progess.core.vo.DraftElement;
import it.progess.core.vo.GECOError;
import it.progess.core.vo.GECOObject;
import it.progess.core.vo.GECOSuccess;
import it.progess.core.vo.Head;
import it.progess.core.vo.Product;
import it.progess.core.vo.ProductEcConfig;
import it.progess.core.vo.Role;
import it.progess.core.vo.User;

public class DraftDao {
	/*gestisce id draft ed un oggetto draft in sessione*/
	public GECOObject setupDraft(String draftid,String key,HttpSession session){
		Draft draft = null;
		if (draftid != null && draftid != ""){
			draft = (Draft)session.getAttribute(draftid);
			if (draft == null){
				UUID ui = UUID.randomUUID();
				draftid = ui.toString();
				draft = new Draft();
				draft.setKey(key);
				draft.setId(draftid);
				draft.setProducts(new ArrayList<DraftElement>());
				session.setAttribute(draftid,draft);
			}
		}else{
			UUID ui = UUID.randomUUID();
			draftid = ui.toString();
			draft = new Draft();
			draft.setKey(key);
			draft.setId(draftid);
			draft.setProducts(new ArrayList<DraftElement>());
			session.setAttribute(draftid,draft);
		}
		return new GECOSuccess(draft);
	}
	/*aggiunge un oggetto al draft*/
	public GECOObject addToDraft(String draftid,HttpSession session,DraftElement product,String key){
		Draft draft = (Draft)session.getAttribute(draftid);
		StoreDao sd = new StoreDao();
		ProductEcConfig ecp = product.getProduct().getEcconfig();
		int qta = ecp.getQuantitymax() -  product.getQuantity();
		ecp.setQuantitymax(qta);
		sd.saveEcCommerce(ecp);
		if (draft == null){
			draft = new Draft();
			draft.setId(draftid);
			draft.setKey(key);
			session.setAttribute(draftid,draft);
		}
		draft.getProducts().add(product);
		draft.calculateTotal();
		persistenceDraft(draft);
		return new GECOSuccess(draft);
	}
	/*rimuove un oggetto dal draft*/
	public GECOObject removeFromDraft(String draftid,HttpSession session,DraftElement product,String key){
		Draft draft = (Draft)session.getAttribute(draftid);
		ProductEcConfig ecp = product.getProduct().getEcconfig();
		StoreDao sd = new StoreDao();
		int qta = ecp.getQuantitymax() +  product.getQuantity();
		ecp.setQuantitymax(qta);
		sd.saveEcCommerce(ecp);
		if (draft == null){
			draft = new Draft();
			draft.setId(draftid);
			draft.setKey(key);
			session.setAttribute(draftid,draft);
		}
		for (int j = draft.getProducts().size() - 1; j >= 0; j--) {
		   if (draft.getProducts().get(j).getProduct().getIdProduct() == product.getProduct().getIdProduct()){
			   draft.getProducts().remove(j);
		   }
		}
		draft.calculateTotal();
		persistenceDraft(draft);
		return new GECOSuccess(draft);
	}
	/*modifica oggetto dal draft*/
	public GECOObject updateDraft(String draftid,HttpSession session,DraftElement product){
		Draft draft = (Draft)session.getAttribute(draftid);
		for (int j = draft.getProducts().size() - 1; j >= 0; j--) {
		   if (draft.getProducts().get(j).getProduct().getIdProduct() == product.getProduct().getIdProduct()){
			   draft.getProducts().set(j, product);
		   }
		}
		draft.calculateTotal();
		persistenceDraft(draft);
		return new GECOSuccess(draft);
	}
	/*refresh draft*/
	public GECOObject refreshDraft(HttpSession session,Draft draft){
		draft.calculateTotal();
		session.setAttribute(draft.getId(),draft);
		persistenceDraft(draft);
		return new GECOSuccess(draft);
	}
	public void persistenceDraft(Draft draft){
		TblDraft td = getDraft(draft.getId());
		if (td == null){
			td = new TblDraft();
			storeDraft(draft);
		}else{
			storeDraft(td,draft);
		}
	}
	/*calcola totale*/
	/*chiude il carrello*/
	/*Paga il carrello*/
	public GECOObject confirmPayment(HttpSession session,ServletContext context,HttpServletRequest request,User user,String draftid,String paymentType,String companyKey){
		Draft draft = (Draft)session.getAttribute(draftid);
		draft.setUser(user);
		//TODO controllo password
		if (user.get_iduser() <= 0){
			if (user.getPassword().equals(user.getConfirmpassword()) == false){
				return new GECOError("USR","La password non � stata confermata correttamente");
			}
		}
		//TODO controllo esistenza cf
		if (user.get_iduser() <= 0){
			if (new RegistryDao().getSingleCustomerByTaxcode(user.getTaxcode(),companyKey).getIdCustomer() > 0){
				return new GECOError("USR","Codice Fiscale gi� inserito");
			}
		}
		//TODO controllo data di nascita
		//TODO 
		switch(paymentType){
			case "03":
				boolean newuser = createEcUser(user,draft.getKey());
				DocumentDao dd = new DocumentDao();
				dd.createOrderFromDraft(context,request,user, draft,newuser);
				//dd.createInvoiceFromDraft(user, draft);
				return new GECOSuccess("confirmed");
			case "01":
				TblDraft td = getDraft(draft.getId());
				if (storeDraft(td,draft) == true){
					return new GECOSuccess("paypal");
				}else{
					return new GECOError("ERP","Errore nel pagamento");
				}
		}
		/*Utente Registrato*/
		/*Utente non Registrato*/
		return new GECOSuccess();
	}
	public boolean storeDraft(Draft d){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		Boolean result = false;
		try{
			tx = session.beginTransaction();
			TblDraft draft = new TblDraft();
			draft.setCode(d.getId());
			draft.setCreationdate(new Date());
			
			Gson gson = new Gson();
			draft.setValue(gson.toJson(d));
			session.save(draft);
		    tx.commit();
		    result = true;
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			session.close();
			return false;
		}finally{
			session.close();
		}
		
		return true;
	}
	public boolean storeDraft(TblDraft draft,Draft d){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		Boolean result = false;
		try{
			tx = session.beginTransaction();
			
			draft.setCode(d.getId());
			//draft.setCreationdate(new Date());
			
			Gson gson = new Gson();
			draft.setValue(gson.toJson(d));
			session.saveOrUpdate(draft);
		    tx.commit();
		    result = true;
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			session.close();
			return false;
		}finally{
			session.close();
		}
		
		return true;
	}
	public TblDraft getDraft(String code){
		Session session = HibernateUtils.getSessionFactory().openSession();
		try{
			Criteria cr = session.createCriteria(TblDraft.class,"draft");
			cr.add(Restrictions.eq("draft.code", code));
			List<TblDraft> companys = cr.list();
			if (companys.size() > 0){
				return companys.get(0);
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return null;
	}
	public GECOObject confirmPayment(ServletContext context,HttpServletRequest request,String draftid){
		TblDraft td = getDraft(draftid);
		if (td == null){
			return new GECOError();
		}
		Draft draft = new Gson().fromJson(td.getValue(), Draft.class);
		User user = draft.getUser();
		boolean newuser = createEcUser(user,draft.getKey());
		DocumentDao dd = new DocumentDao();
		dd.createOrderFromDraft(context,request,user, draft,newuser);
		//dd.createInvoiceFromDraft(user, draft);
		removeDraft(td);		
		/*Utente Registrato*/
		/*Utente non Registrato*/
		return new GECOSuccess();
	}
	private boolean createEcUser(User user,String key){
		RegistryDao rd = new RegistryDao();
		if (user.getIduser() == 0){
			user.set_iduser(new UserDao().createEcUser(user,key));
			GECOObject obj  = rd.createCustomerFromUser(user);
			if(obj instanceof GECOSuccess ){
				int idc = (Integer)((GECOSuccess)obj).success;
				Customer c = rd.getSingleCustomer(idc);
				Contact co = c.getContact();
				//co.setUser(user);
				//rd.updateContact(co);
				user.setContact(co);
				new UserDao().saveUpdate(user);
				return true;
			}
		}else{
			rd.createDestinationFromUser(user);
			
		}
		return false;
	}
	public GECOObject createEcUserForm(User user,String key,HttpServletRequest request){
		if (user.getIduser() == 0){
			RegistryDao rd = new RegistryDao();
			user.set_iduser(new UserDao().createEcUser(user,key));
			GECOObject obj  = rd.createCustomerFromUser(user);
			UserDao daou = new UserDao(); 
			if(obj instanceof GECOSuccess ){
				int idc = (Integer)((GECOSuccess)obj).success;
				Customer c = rd.getSingleCustomer(idc);
				Contact co = c.getContact();
				user.setContact(co);
				int iduser =  daou.saveUpdate(user);
				user = daou.getSingleUserVO(iduser);
				return new MailDao().sendNewCustomerUser(key,user,MailParameter.NEW_USER_CUSTOMER_MAIL,request);
			}else{
				return obj;
			}
				
		}else{
			return new GECOError("USR", "Utente gi� registrato");
		}
	}
	public void removeDraft(TblDraft td){
			Session session = HibernateUtils.getSessionFactory().openSession();
			Transaction tx = null;
			try{
				
				tx = session.beginTransaction();
				session.delete(td);
				tx.commit();
			}catch(HibernateException e){
				System.err.println("ERROR IN LIST!!!!!!");
				if (tx!= null) tx.rollback();
				e.printStackTrace();
				session.close();
				throw new ExceptionInInitializerError(e);
			}finally{
				session.close();
			}
			
			
		
	}
	public List<TblDraft> getAllDraft(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		List<TblDraft> companys = null;
		try{
			Criteria cr = session.createCriteria(TblDraft.class,"draft");
			companys = cr.list();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return companys;
	}
	public void deleteOldDraft(){
		Date today = new Date();
		List<TblDraft> l = getAllDraft();
		StoreDao sd = new StoreDao();
		Gson gson = new Gson();
		if (l != null){
			for(Iterator<TblDraft> it = l.iterator();it.hasNext();){
				TblDraft td = it.next();
				long diff = today.getTime() - td.getCreationdate().getTime();
				diff = diff/60000;
				if (diff > 15){
					Draft draft = gson.fromJson(td.getValue(), Draft.class);
					//RIPRISTINA TUTTE LE QUANTITA'
					for (Iterator<DraftElement> ide = draft.getProducts().iterator();ide.hasNext();){
						DraftElement de = ide.next();
						Product product = de.getProduct();
						ProductEcConfig pec = product.getEcconfig();
						int qta = pec.getQuantitymax() +  de.getQuantity();
						pec.setQuantitymax(qta);
						sd.saveEcCommerce(pec);
					}
					removeDraft(td);
				}
			}
		}
	}
}
