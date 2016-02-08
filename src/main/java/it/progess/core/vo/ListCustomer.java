package it.progess.core.vo;

import it.progess.core.pojo.Itbl;
import it.progess.core.pojo.TblListCustomer;
import it.progess.core.properties.GECOParameter;


public class ListCustomer implements Ivo {
	private int idListCustomer;
	private List list;
	private Customer customer;
	public int getIdListCustomer() {
		return idListCustomer;
	}
	public void setIdListCustomer(int idListCustomer) {
		this.idListCustomer = idListCustomer;
	}
	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public void convertFromTable(Itbl obj){
		TblListCustomer lc = (TblListCustomer)obj;
		this.idListCustomer = lc.getIdListCustomer();
		if (lc.getCustomer() != null){
			this.customer = new Customer();
			this.customer.convertFromTable(lc.getCustomer());
		}
		if (lc.getList() != null){
			this.list = new List();
			this.list.convertFromTable(lc.getList());
		}
	}
	public GECOObject control(){
		
		return null;
	}
}
