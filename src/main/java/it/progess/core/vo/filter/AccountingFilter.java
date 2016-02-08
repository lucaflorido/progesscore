package it.progess.core.vo.filter;

import it.progess.core.vo.Customer;
import it.progess.core.vo.Supplier;

public class AccountingFilter {
	public String dateFrom;
	public String dateTo;
	public Customer customer;
	public Supplier supplier;
	public boolean paid;
	public boolean expired;
	public boolean isCustomer;
	public boolean isSupplier;
}
