package it.progess.core.dao;

import it.progess.core.hibernate.HibernateUtils;
import it.progess.core.pojo.TblBrand;
import it.progess.core.pojo.TblCategoryCustomer;
import it.progess.core.pojo.TblCategoryProduct;
import it.progess.core.pojo.TblCategorySupplier;
import it.progess.core.pojo.TblComposition;
import it.progess.core.pojo.TblCounter;
import it.progess.core.pojo.TblCurrency;
import it.progess.core.pojo.TblDocument;
import it.progess.core.pojo.TblGroupCustomer;
import it.progess.core.pojo.TblGroupProduct;
import it.progess.core.pojo.TblGroupSupplier;
import it.progess.core.pojo.TblPayment;
import it.progess.core.pojo.TblPaymentSolution;
import it.progess.core.pojo.TblRegion;
import it.progess.core.pojo.TblStoreMovement;
import it.progess.core.pojo.TblSubCategoryProduct;
import it.progess.core.pojo.TblTaxrate;
import it.progess.core.pojo.TblUnitMeasure;
import it.progess.core.vo.Brand;
import it.progess.core.vo.CategoryCustomer;
import it.progess.core.vo.CategoryProduct;
import it.progess.core.vo.CategorySupplier;
import it.progess.core.vo.Composition;
import it.progess.core.vo.Counter;
import it.progess.core.vo.Currency;
import it.progess.core.vo.Document;
import it.progess.core.vo.GECOError;
import it.progess.core.vo.GECOObject;
import it.progess.core.vo.GECOSuccess;
import it.progess.core.vo.GroupCustomer;
import it.progess.core.vo.GroupProduct;
import it.progess.core.vo.GroupSupplier;
import it.progess.core.vo.Payment;
import it.progess.core.vo.PaymentSolution;
import it.progess.core.vo.Region;
import it.progess.core.vo.StoreMovement;
import it.progess.core.vo.SubCategoryProduct;
import it.progess.core.vo.TaxRate;
import it.progess.core.vo.UnitMeasure;
import it.progess.core.vo.User;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.ConstraintViolationException;

public class BasicDao {
	/*******
	 * TAXRATES
	 */
	/***
	 * Get the list of taxrates
	 * @return
	 */
	public ArrayList<TaxRate> getTaxRateList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<TaxRate> list = new ArrayList<TaxRate>();
		try{
			session.clear();
			Criteria cr = session.createCriteria(TblTaxrate.class,"taxrate");
			checkORCompany("taxrate",user,cr);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblTaxrate> taxrates = cr.list();
			if (taxrates.size() > 0){
				for (Iterator<TblTaxrate> iterator = taxrates.iterator(); iterator.hasNext();){
					TblTaxrate tbltaxrate = iterator.next();
					TaxRate taxrate = new TaxRate();
					taxrate.convertFromTable(tbltaxrate);
					list.add(taxrate);
				}
			}
			session.clear();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			
			session.close();
		}
		return list;
	}
	private void checkORCompany(String obj,User user,Criteria cr){
		if (user.getCompany() != null){
			cr.add(Restrictions.disjunction().add(Restrictions.eq(obj+".company.idCompany", user.getCompany().getIdCompany()))
					.add(Restrictions.isNull(obj+".company.idCompany")));
		}
			
	}
	/***
	 * SAVE UPDATE taxrates
	 * @param taxrates
	 * @return
	 */
	public GECOObject saveUpdatesTaxrate(TaxRate[] taxrates,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null; 
		try{ 
			tx = session.beginTransaction();
			for(int i =0; i< taxrates.length;i++){
				TaxRate taxrate = taxrates[i];
				if (taxrate.control(user) == null){
					TblTaxrate tbltaxrate = new TblTaxrate();
					tbltaxrate.convertToTable(taxrate);
					session.saveOrUpdate(tbltaxrate);
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return taxrate.control();
				}
			}
			tx.commit();
		}catch(HibernateException e){
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			session.close();
			return new GECOError("", "Errore nel Database");
		}finally{
			if (session.isOpen() == true){
				session.close();
			}
		}
		return new GECOSuccess();
	}
	
	/***
	 * DELETE A SINGLE TAXRATE
	 * **/
	public GECOObject deleteTaxRate(TaxRate taxrate){
		TblTaxrate tbltaxrate = new TblTaxrate();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tbltaxrate.convertToTable(taxrate);
			tx = session.beginTransaction();
			session.delete(tbltaxrate);
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
		
	}
	/*****
	 * Get List of unit measure 
	 */
	public ArrayList<UnitMeasure> getUnitMeasureList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<UnitMeasure> list = new ArrayList<UnitMeasure>();
		try{
			Criteria cr = session.createCriteria(TblUnitMeasure.class,"um");
			checkORCompany("um",user,cr);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblUnitMeasure> unitmeasures = cr.list();
			if (unitmeasures.size() > 0){
				for (Iterator<TblUnitMeasure> iterator = unitmeasures.iterator(); iterator.hasNext();){
					TblUnitMeasure tblunitmeasure = iterator.next();
					UnitMeasure unitmeasure = new UnitMeasure();
					unitmeasure.convertFromTable(tblunitmeasure);
					list.add(unitmeasure);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	/***
	 * SAVE UPDATE UM
	 * @param taxrates
	 * @return
	 */
	public GECOObject saveUpdatesUnitMeasure(UnitMeasure[] ums,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< ums.length;i++){
				UnitMeasure um = ums[i];
				if (um.control(user) == null){
					if (um.getName() != "" && um.getName() != null && um.getCode() != ""  && um.getCode() != ""){
						TblUnitMeasure tblum = new TblUnitMeasure();
						tblum.convertToTable(um);
						session.saveOrUpdate(tblum);
					}
				}else{
					if (tx!= null) tx.rollback();
					return um.control();
				}
			}
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
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE UM
	 * **/
	public GECOObject deleteUM(UnitMeasure um){
		TblUnitMeasure tblum = new TblUnitMeasure();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tblum.convertToTable(um);
			tx = session.beginTransaction();
			session.delete(tblum);
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
		
	}
	/*****
	 * Get List of Store Movement 
	 */
	public ArrayList<StoreMovement> getStoreMovementList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<StoreMovement> list = new ArrayList<StoreMovement>();
		try{
			Criteria cr = session.createCriteria(TblStoreMovement.class);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblStoreMovement> storemovements = cr.list();
			if (storemovements.size() > 0){
				for (Iterator<TblStoreMovement> iterator = storemovements.iterator(); iterator.hasNext();){
					TblStoreMovement tblstoremovement = iterator.next();
					StoreMovement storemovement = new StoreMovement();
					storemovement.convertFromTable(tblstoremovement);
					list.add(storemovement);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	/***
	 * SAVE UPDATE SM
	 * @param storemovcment
	 * @return
	 */
	public GECOObject saveUpdatesStoreMovement(StoreMovement[] sms){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				StoreMovement sm = sms[i];
				if (sm.control() == null){
					if (sm.getName() != "" && sm.getName() != null ){
						TblStoreMovement tblsm = new TblStoreMovement();
						tblsm.convertToTable(sm);
						session.saveOrUpdate(tblsm);
					}
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
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
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE ms
	 * **/
	public Boolean deleteSM(StoreMovement sm){
		TblStoreMovement tblsm = new TblStoreMovement();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
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
		return true;
		
	}
	/*****
	 * Get List of Counter 
	 */
	public ArrayList<Counter> getCounterList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Counter> list = new ArrayList<Counter>();
		try{
			Criteria cr = session.createCriteria(TblCounter.class,"counter");
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCounter> counters = cr.list();
			if (counters.size() > 0){
				for (Iterator<TblCounter> iterator = counters.iterator(); iterator.hasNext();){
					TblCounter tblcounter = iterator.next();
					Counter counter = new Counter();
					counter.convertFromTable(tblcounter);
					list.add(counter);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	public ArrayList<Counter> getCounterList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Counter> list = new ArrayList<Counter>();
		try{
			Criteria cr = session.createCriteria(TblCounter.class,"counter");
			cr.add(Restrictions.eq("counter.company.idCompany", user.getCompany().getIdCompany()));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCounter> counters = cr.list();
			if (counters.size() > 0){
				for (Iterator<TblCounter> iterator = counters.iterator(); iterator.hasNext();){
					TblCounter tblcounter = iterator.next();
					Counter counter = new Counter();
					counter.convertFromTable(tblcounter);
					list.add(counter);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	/***
	 * Save update Counters
	 * **/
	public GECOObject saveUpdatesCounter(Counter[] sms){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				Counter sm = sms[i];
				if(sm.control() == null){
					if (sm.getName() != "" && sm.getName() != null ){
						TblCounter tblsm = new TblCounter();
						tblsm.convertToTableForSaving(sm);
						session.saveOrUpdate(tblsm);
					}
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
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
		return new GECOSuccess();
	}
	public GECOObject saveUpdatesCounter(Counter[] sms,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				Counter sm = sms[i];
				sm.setCompany(user.getCompany());
				if(sm.control() == null){
					if (sm.getName() != "" && sm.getName() != null ){
						TblCounter tblsm = new TblCounter();
						tblsm.convertToTableForSaving(sm);
						session.saveOrUpdate(tblsm);
					}
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
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
		return new GECOSuccess();
	}
	public Counter getCounter(int idCounter){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Counter> list = new ArrayList<Counter>();
		Counter counter = null;
		try{
			Criteria cr = session.createCriteria(TblCounter.class,"counter");
			cr.add(Restrictions.eq("counter.idCounter",idCounter));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCounter> counters = cr.list();
			if (counters.size() > 0){
				for (Iterator<TblCounter> iterator = counters.iterator(); iterator.hasNext();){
					TblCounter tblcounter = iterator.next();
					counter = new Counter();
					counter.convertFromTable(tblcounter);
					
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return counter;
	}
	/***
	 * Save update Counters
	 * **/
	public GECOObject saveUpdatesSingleCounter(Counter sms){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			if (sms.control() == null){
				if (sms.getName() != "" && sms.getName() != null ){
						TblCounter tblsm = new TblCounter();
						tblsm.convertToTableForSaving(sms);
						session.saveOrUpdate(tblsm);
				}
			}else{
				if (tx!= null) tx.rollback();
				//session.close();
				return sms.control();
			}
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
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE counter
	 * **/
	public GECOObject deleteCounter(Counter sm){
		TblCounter tblsm = new TblCounter();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
		
	}
	/*****
	 * Get List of Payment 
	 */
	public ArrayList<Payment> getPaymentList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Payment> list = new ArrayList<Payment>();
		try{
			Criteria cr = session.createCriteria(TblPayment.class);
			List<TblPayment> payments = cr.list();
			if (payments.size() > 0){
				for (Iterator<TblPayment> iterator = payments.iterator(); iterator.hasNext();){
					TblPayment tblpayment = iterator.next();
					Payment payment = new Payment();
					payment.convertFromTable(tblpayment);
					list.add(payment);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	/***
	 * Save update Payments
	 * **/
	public GECOObject saveUpdatesPayment(Payment[] sms){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				Payment sm = sms[i];
				if (sm.control() == null){
					if (sm.getCode() != "" && sm.getCode() != null ){
						TblPayment tblsm = new TblPayment();
						tblsm.convertToTableForSaving(sm);
						session.saveOrUpdate(tblsm);
					}
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
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
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE payment
	 * **/
	public Boolean deletePayment(Payment sm){
		TblPayment tblsm = new TblPayment();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
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
		return true;
		
	}
	/*****
	 * Get List of Document 
	 */
	public ArrayList<Document> getDocumentList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Document> list = new ArrayList<Document>();
		try{
			Criteria cr = session.createCriteria(TblDocument.class);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblDocument> documents = cr.list();
			if (documents.size() > 0){
				for (Iterator<TblDocument> iterator = documents.iterator(); iterator.hasNext();){
					TblDocument tbldocument = iterator.next();
					Document document = new Document();
					document.convertFromTable(tbldocument);
					list.add(document);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	public ArrayList<Document> getDocumentList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Document> list = new ArrayList<Document>();
		try{
			Criteria cr = session.createCriteria(TblDocument.class,"document");
			cr.add(Restrictions.eq("document.company.idCompany", user.getCompany().getIdCompany()));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblDocument> documents = cr.list();
			if (documents.size() > 0){
				for (Iterator<TblDocument> iterator = documents.iterator(); iterator.hasNext();){
					TblDocument tbldocument = iterator.next();
					Document document = new Document();
					document.convertFromTable(tbldocument);
					list.add(document);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	public ArrayList<Document> getDocumentOrderList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Document> list = new ArrayList<Document>();
		try{
			Criteria cr = session.createCriteria(TblDocument.class,"document");
			cr.add(Restrictions.eq("document.company.idCompany", user.getCompany().getIdCompany()));
			cr.add(Restrictions.eq("document.order",true));
			cr.add(Restrictions.eq("document.expireday",0));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblDocument> documents = cr.list();
			if (documents.size() > 0){
				for (Iterator<TblDocument> iterator = documents.iterator(); iterator.hasNext();){
					TblDocument tbldocument = iterator.next();
					Document document = new Document();
					document.convertFromTable(tbldocument);
					list.add(document);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	public ArrayList<Document> getDocumentOrderTimeList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Document> list = new ArrayList<Document>();
		try{
			Criteria cr = session.createCriteria(TblDocument.class,"document");
			cr.add(Restrictions.eq("document.company.idCompany", user.getCompany().getIdCompany()));
			cr.add(Restrictions.eq("document.order",true));
			cr.add(Restrictions.gt("document.expireday",0));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblDocument> documents = cr.list();
			if (documents.size() > 0){
				for (Iterator<TblDocument> iterator = documents.iterator(); iterator.hasNext();){
					TblDocument tbldocument = iterator.next();
					Document document = new Document();
					document.convertFromTable(tbldocument);
					list.add(document);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	public Document getDocumentOrderOnline(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Document> list = new ArrayList<Document>();
		try{
			Criteria cr = session.createCriteria(TblDocument.class,"document");
			cr.add(Restrictions.eq("document.company.idCompany", user.getCompany().getIdCompany()));
			cr.add(Restrictions.eq("document.order",true));
			cr.add(Restrictions.eq("document.online",true));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblDocument> documents = cr.list();
			if (documents.size() > 0){
				for (Iterator<TblDocument> iterator = documents.iterator(); iterator.hasNext();){
					TblDocument tbldocument = iterator.next();
					Document document = new Document();
					document.convertFromTable(tbldocument);
					list.add(document);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list.get(0);
	}
	public Document getDocumentInvoiceOnline(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Document> list = new ArrayList<Document>();
		try{
			Criteria cr = session.createCriteria(TblDocument.class,"document");
			cr.add(Restrictions.eq("document.company.idCompany", user.getCompany().getIdCompany()));
			cr.add(Restrictions.eq("document.invoice",true));
			cr.add(Restrictions.eq("document.online",true));
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblDocument> documents = cr.list();
			if (documents.size() > 0){
				for (Iterator<TblDocument> iterator = documents.iterator(); iterator.hasNext();){
					TblDocument tbldocument = iterator.next();
					Document document = new Document();
					document.convertFromTable(tbldocument);
					list.add(document);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list.get(0);
	}
	/***
	 * Save update Documents
	 * **/
	public GECOObject saveUpdatesDocument(Document[] sms,User user){
		for (int i =0;i<sms.length;i++){
			sms[i].setCompany(user.getCompany());
		}
		return saveUpdatesDocument(sms);
	}
	public GECOObject saveUpdatesDocument(Document[] sms){
		
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				Document sm = sms[i];
				
				if (sm.control() == null ){
					TblDocument tblsm = new TblDocument();
					tblsm.convertToTable(sm);
					session.saveOrUpdate(tblsm);
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
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
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE Tbldocument
	 * **/
	public GECOObject deleteDocument(Document sm){
		TblDocument tblsm = new TblDocument();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
		
	}
	
	
	/*****
	 * Get List of GroupProduct 
	 */
	public ArrayList<GroupProduct> getGroupProductList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<GroupProduct> list = new ArrayList<GroupProduct>();
		try{
			Criteria cr = session.createCriteria(TblGroupProduct.class,"group");
			checkORCompany("group",user,cr);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblGroupProduct> groupproducts = cr.list();
			if (groupproducts.size() > 0){
				for (Iterator<TblGroupProduct> iterator = groupproducts.iterator(); iterator.hasNext();){
					TblGroupProduct tblgroupproduct = iterator.next();
					GroupProduct groupproduct = new GroupProduct();
					groupproduct.convertFromTable(tblgroupproduct);
					list.add(groupproduct);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	public ArrayList<GroupProduct> getGroupProductList(boolean defaultValue,User user){
		ArrayList<GroupProduct> list = getGroupProductList(user);
		GroupProduct gp = new GroupProduct();
		gp.setCode(" ");
		gp.setName(" ");
		list.add(0, gp);
		return list;
	}
	/***
	 * Save update GroupProducts
	 * **/
	public GECOObject saveUpdatesGroupProduct(GroupProduct[] sms,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				GroupProduct sm = sms[i];
				if (sm.control(user) == null ){
					TblGroupProduct tblsm = new TblGroupProduct();
					tblsm.convertToTable(sm);
					session.saveOrUpdate(tblsm);
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
	}
	/***
	 * DELETE A SINGLE Tblgroupproduct
	 * **/
	public GECOObject deleteGroupProduct(GroupProduct sm){
		TblGroupProduct tblsm = new TblGroupProduct();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
		
	}
	
	
	/*****
	 * Get List of CategoryProduct 
	 */
	public ArrayList<CategoryProduct> getCategoryProductList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<CategoryProduct> list = new ArrayList<CategoryProduct>();
		try{
			Criteria cr = session.createCriteria(TblCategoryProduct.class,"category");
			checkORCompany("category",user,cr);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCategoryProduct> categoryproducts = cr.list();
			if (categoryproducts.size() > 0){
				for (Iterator<TblCategoryProduct> iterator = categoryproducts.iterator(); iterator.hasNext();){
					TblCategoryProduct tblcategoryproduct = iterator.next();
					CategoryProduct categoryproduct = new CategoryProduct();
					categoryproduct.convertFromTable(tblcategoryproduct);
					list.add(categoryproduct);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	/***
	 * Save update CategoryProducts
	 * **/
	public GECOObject saveUpdatesCategoryProduct(CategoryProduct[] sms,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				CategoryProduct sm = sms[i];
				if (sm.control(user) == null ){
					TblCategoryProduct tblsm = new TblCategoryProduct();
					tblsm.convertToTableForSaving(sm);
					session.saveOrUpdate(tblsm);
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
			tx.commit();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			
		}finally{
			session.close();
		}
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE Tblcategoryproduct
	 * **/
	public GECOObject deleteCategoryProduct(CategoryProduct sm){
		TblCategoryProduct tblsm = new TblCategoryProduct();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
		
	}
	public GECOObject deleteSubCategoryProduct(SubCategoryProduct sm){
		TblSubCategoryProduct tblsm = new TblSubCategoryProduct();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
		
	}
	/*****
	 * Get List of CategoryProduct 
	 */
	public ArrayList<CategoryCustomer> getCategoryCustomerList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<CategoryCustomer> list = new ArrayList<CategoryCustomer>();
		try{
			Criteria cr = session.createCriteria(TblCategoryCustomer.class,"category");
			checkORCompany("category",user,cr);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCategoryCustomer> categorycustomers = cr.list();
			if (categorycustomers.size() > 0){
				for (Iterator<TblCategoryCustomer> iterator = categorycustomers.iterator(); iterator.hasNext();){
					TblCategoryCustomer tblcategorycustomer = iterator.next();
					CategoryCustomer categorycustomer = new CategoryCustomer();
					categorycustomer.convertFromTable(tblcategorycustomer);
					list.add(categorycustomer);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	/***
	 * Save update CategoryCustomers
	 * **/
	public GECOObject saveUpdatesCategoryCustomer(CategoryCustomer[] sms,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				CategoryCustomer sm = sms[i];
				if (sm.control(user) == null ){
					TblCategoryCustomer tblsm = new TblCategoryCustomer();
					tblsm.convertToTable(sm);
					session.saveOrUpdate(tblsm);
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
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
		return new GECOSuccess();
	}
	/***
	 * Save update Composition
	 * **/
	public GECOObject saveUpdatesComposition(Composition[] sms,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				Composition sm = sms[i];
				sm.setCompany(user.getCompany());
				if (sm.control() == null ){
					TblComposition tblsm = new TblComposition();
					tblsm.convertToTable(sm);
					session.saveOrUpdate(tblsm);
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
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
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE Tblcategorycustomer
	 * **/
	public GECOObject deleteCategoryCustomer(CategoryCustomer sm){
		TblCategoryCustomer tblsm = new TblCategoryCustomer();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
		
	}
	
	/*****
	 * Get List of CategoryProduct 
	 */
	public ArrayList<GroupCustomer> getGroupCustomerList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<GroupCustomer> list = new ArrayList<GroupCustomer>();
		try{
			Criteria cr = session.createCriteria(TblGroupCustomer.class,"group");
			checkORCompany("group",user,cr);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblGroupCustomer> groupcustomers = cr.list();
			if (groupcustomers.size() > 0){
				for (Iterator<TblGroupCustomer> iterator = groupcustomers.iterator(); iterator.hasNext();){
					TblGroupCustomer tblgroupcustomer = iterator.next();
					GroupCustomer groupcustomer = new GroupCustomer();
					groupcustomer.convertFromTable(tblgroupcustomer);
					list.add(groupcustomer);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	/*****
	 * Get List of Composition
	 */
	public ArrayList<Composition> getComposition(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Composition> list = new ArrayList<Composition>();
		try{
			Criteria cr = session.createCriteria(TblComposition.class,"composition");
			checkORCompany("composition",user,cr);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblComposition> compositions = cr.list();
			if (compositions.size() > 0){
				for (Iterator<TblComposition> iterator = compositions.iterator(); iterator.hasNext();){
					TblComposition tblcomposition = iterator.next();
					Composition composition = new Composition();
					composition.convertFromTable(tblcomposition);
					list.add(composition);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	/***
	 * Save update GroupCustomers
	 * **/
	public GECOObject saveUpdatesGroupCustomer(GroupCustomer[] sms,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				GroupCustomer sm = sms[i];
				if (sm.control(user) == null ){
					TblGroupCustomer tblsm = new TblGroupCustomer();
					tblsm.convertToTable(sm);
					session.saveOrUpdate(tblsm);
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
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
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE Tblgroupcustomer
	 * **/
	public GECOObject deleteGroupCustomer(GroupCustomer sm){
		TblGroupCustomer tblsm = new TblGroupCustomer();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
		
	}
	
	/***
	 * DELETE A SINGLE TblComposition
	 * **/
	public GECOObject deleteComposition(Composition sm){
		TblComposition tblsm = new TblComposition();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
		
	}
	
	
	/*****
	 * Get List of CategoryProduct 
	 */
	public ArrayList<CategorySupplier> getCategorySupplierList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<CategorySupplier> list = new ArrayList<CategorySupplier>();
		try{
			Criteria cr = session.createCriteria(TblCategorySupplier.class);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblCategorySupplier> categorysuppliers = cr.list();
			if (categorysuppliers.size() > 0){
				for (Iterator<TblCategorySupplier> iterator = categorysuppliers.iterator(); iterator.hasNext();){
					TblCategorySupplier tblcategorysupplier = iterator.next();
					CategorySupplier categorysupplier = new CategorySupplier();
					categorysupplier.convertFromTable(tblcategorysupplier);
					list.add(categorysupplier);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	/***
	 * Save update CategorySuppliers
	 * **/
	public GECOObject saveUpdatesCategorySupplier(CategorySupplier[] sms){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				CategorySupplier sm = sms[i];
				if (sm.control() == null ){
					TblCategorySupplier tblsm = new TblCategorySupplier();
					tblsm.convertToTable(sm);
					session.saveOrUpdate(tblsm);
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
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
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE Tblcategorysupplier
	 * **/
	public Boolean deleteCategorySupplier(CategorySupplier sm){
		TblCategorySupplier tblsm = new TblCategorySupplier();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
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
		return true;
		
	}
	
	/*****
	 * Get List of CategoryProduct 
	 */
	public ArrayList<GroupSupplier> getGroupSupplierList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<GroupSupplier> list = new ArrayList<GroupSupplier>();
		try{
			Criteria cr = session.createCriteria(TblGroupSupplier.class);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblGroupSupplier> groupsuppliers = cr.list();
			if (groupsuppliers.size() > 0){
				for (Iterator<TblGroupSupplier> iterator = groupsuppliers.iterator(); iterator.hasNext();){
					TblGroupSupplier tblgroupsupplier = iterator.next();
					GroupSupplier groupsupplier = new GroupSupplier();
					groupsupplier.convertFromTable(tblgroupsupplier);
					list.add(groupsupplier);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	/***
	 * Save update GroupSuppliers
	 * **/
	public GECOObject saveUpdatesGroupSupplier(GroupSupplier[] sms){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				GroupSupplier sm = sms[i];
				if (sm.control() == null ){
					TblGroupSupplier tblsm = new TblGroupSupplier();
					tblsm.convertToTable(sm);
					session.saveOrUpdate(tblsm);
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
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
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE Tblgroupsupplier
	 * **/
	public Boolean deleteGroupSupplier(GroupSupplier sm){
		TblGroupSupplier tblsm = new TblGroupSupplier();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
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
		return true;
		
	}
	
	
	
	/*****
	 * Get List of CategoryProduct 
	 */
	public ArrayList<Brand> getBrandList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Brand> list = new ArrayList<Brand>();
		try{
			Criteria cr = session.createCriteria(TblBrand.class,"brand");
			checkORCompany("brand",user,cr);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblBrand> brands = cr.list();
			if (brands.size() > 0){
				for (Iterator<TblBrand> iterator = brands.iterator(); iterator.hasNext();){
					TblBrand tblgroupsupplier = iterator.next();
					Brand groupsupplier = new Brand();
					groupsupplier.convertFromTable(tblgroupsupplier);
					list.add(groupsupplier);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	/***
	 * Save update GroupSuppliers
	 * **/
	public GECOObject saveUpdatesBrand(Brand[] sms,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< sms.length;i++){
				Brand sm = sms[i];
				if (sm.control(user) == null ){
					TblBrand tblsm = new TblBrand();
					tblsm.convertToTable(sm);
					session.saveOrUpdate(tblsm);
				}else{
					if (tx!= null) tx.rollback();
					//session.close();
					return sm.control();
				}
			}
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
		return new GECOSuccess();
	}
	/***
	 * DELETE A SINGLE Tblgroupsupplier
	 * **/
	public GECOObject deleteBrand(Brand sm){
		TblBrand tblsm = new TblBrand();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tblsm.convertToTable(sm);
			tx = session.beginTransaction();
			session.delete(tblsm);
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
		
	}
	
	/***
	 * DELETE A SINGLE REGION
	 * **/
	public GECOObject deleteRegion(Region re){
		TblRegion tblre = new TblRegion();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		GECOObject obj = null;
		try{
			tblre.convertToTable(re);
			tx = session.beginTransaction();
			session.delete(tblre);
			tx.commit();
			obj = new GECOSuccess();
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			if (tx!= null) tx.rollback();
			e.printStackTrace();
			if (e instanceof ConstraintViolationException){
				obj =  new GECOError("ERR","Non � possibile cancellare l'elemento selezionato");
			}else{
				obj =  new GECOError("ERR","Errore nella cancellazione dell'elemento");
			}
		}finally{
			session.close();
		}
		return obj;
		
	}
	public ArrayList<Region> getRegionList(User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Region> list = new ArrayList<Region>();
		try{
			Criteria cr = session.createCriteria(TblRegion.class,"region");
			checkORCompany("region",user,cr);
			cr.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<TblRegion> regions = cr.list();
			if (regions.size() > 0){
				for (Iterator<TblRegion> iterator = regions.iterator(); iterator.hasNext();){
					TblRegion tblregion = iterator.next();
					Region region = new Region();
					region.convertFromTable(tblregion);
					list.add(region);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	public GECOObject saveUpdatesRegion(Region[] regions,User user){
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();
			for(int i =0; i< regions.length;i++){
				Region region = regions[i];
				if (region.control(user) == null){
					TblRegion tblregion = new TblRegion();
					tblregion.convertToTable(region);
					session.saveOrUpdate(tblregion);
					
				}else{
					if (tx!= null) tx.rollback();
					return region.control();
				}
			}
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
		return new GECOSuccess();
	}
	public ArrayList<PaymentSolution> getPaymentSolutionList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<PaymentSolution> list = new ArrayList<PaymentSolution>();
		try{
			Criteria cr = session.createCriteria(TblPaymentSolution.class);
			List<TblPaymentSolution> payments = cr.list();
			if (payments.size() > 0){
				for (Iterator<TblPaymentSolution> iterator = payments.iterator(); iterator.hasNext();){
					TblPaymentSolution tblpayment = iterator.next();
					PaymentSolution payment = new PaymentSolution();
					payment.convertFromTable(tblpayment);
					list.add(payment);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
	public ArrayList<Currency> getCurrencyList(){
		Session session = HibernateUtils.getSessionFactory().openSession();
		ArrayList<Currency> list = new ArrayList<Currency>();
		try{
			Criteria cr = session.createCriteria(TblCurrency.class);
			List<TblCurrency> currencies = cr.list();
			if (currencies.size() > 0){
				for (Iterator<TblCurrency> iterator = currencies.iterator(); iterator.hasNext();){
					TblCurrency tblcurrency = iterator.next();
					Currency currency = new Currency();
					currency.convertFromTable(tblcurrency);
					list.add(currency);
				}
			}
		}catch(HibernateException e){
			System.err.println("ERROR IN LIST!!!!!!");
			e.printStackTrace();
			throw new ExceptionInInitializerError(e);
		}finally{
			session.close();
		}
		return list;
	}
}
