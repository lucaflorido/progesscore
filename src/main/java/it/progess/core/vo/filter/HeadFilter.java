package it.progess.core.vo.filter;

import it.progess.core.vo.Customer;
import it.progess.core.vo.Document;
import it.progess.core.vo.Supplier;

public class HeadFilter {
	public int pageSize;
	public int startelement;
	public String fromDate;
	public String toDate;
	public boolean isCustomer;
	public boolean isSupplier;
	public boolean isOrder;
	public boolean isInvoice;
	public boolean isTransport;
	public Customer customer;
	public Supplier supplier;
	public Document doc;
	
}
