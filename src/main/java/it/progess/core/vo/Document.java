package it.progess.core.vo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.progess.core.pojo.Itbl;
import it.progess.core.pojo.TblDocument;
import it.progess.core.pojo.TblDocumentFlow;
import it.progess.core.properties.GECOParameter;

public class Document  implements Ivo{
	private int idDocument;
	private String code;
	private String description;
	private boolean customer;
	private boolean supplier;
	private boolean internal;
	private StoreMovement storemovement;
	private Counter counter; 
	private boolean credit;
	private boolean debit;
	private boolean order;
	private boolean invoice;
	private boolean transport;
	private Company company;
	private int expireday;
	private boolean online;
	private Set<DocumentFlow> flows;
	public boolean isOnline() {
		return online;
	}
	public void setOnline(boolean online) {
		this.online = online;
	}
	public int getExpireday() {
		return expireday;
	}
	public void setExpireday(int expireday) {
		this.expireday = expireday;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public boolean isOrder() {
		return order;
	}
	public void setOrder(boolean order) {
		this.order = order;
	}
	public boolean isInvoice() {
		return invoice;
	}
	public void setInvoice(boolean invoice) {
		this.invoice = invoice;
	}
	public boolean isTransport() {
		return transport;
	}
	public void setTransport(boolean transport) {
		this.transport = transport;
	}
	
	public boolean isCredit() {
		return credit;
	}
	public void setCredit(boolean credit) {
		this.credit = credit;
	}
	public boolean isDebit() {
		return debit;
	}
	public void setDebit(boolean debit) {
		this.debit = debit;
	}
	public Counter getCounter() {
		return counter;
	}
	public void setCounter(Counter counter) {
		this.counter = counter;
	}
	public StoreMovement getStoremovement() {
		return storemovement;
	}
	public void setStoremovement(StoreMovement storemovement) {
		this.storemovement = storemovement;
	}
	public int getIdDocument() {
		return idDocument;
	}
	public void setIdDocument(int idDocument) {
		this.idDocument = idDocument;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean getCustomer() {
		return customer;
	}
	public void setCustomer(boolean customer) {
		this.customer = customer;
	}
	public boolean getSupplier() {
		return supplier;
	}
	public void setSupplier(boolean supplier) {
		this.supplier = supplier;
	}
	public boolean getInternal() {
		return internal;
	}
	public void setInternal(boolean internal) {
		this.internal = internal;
	}
	
	public Set<DocumentFlow> getFlows() {
		return flows;
	}
	public void setFlows(Set<DocumentFlow> flows) {
		this.flows = flows;
	}
	public void convertFromTable(Itbl obj){
		TblDocument dc = (TblDocument)obj;
		this.code = dc.getCode();
		this.customer = dc.getCustomer();
		this.description = dc.getDescription();
		this.idDocument = dc.getIdDocument();
		this.internal = dc.getInternal();
		this.supplier = dc.getSupplier();
		this.credit = dc.isCredit();
		this.debit = dc.isDebit();
		this.order = dc.isOrder();
		this.invoice = dc.isInvoice();
		this.transport = dc.isTransport();
		this.expireday = dc.getExpireday();
		this.online = dc.isOnline();
		if (dc.getStoremovement() != null){
			this.storemovement = new StoreMovement();
			this.storemovement.convertFromTable(dc.getStoremovement());
		}
		if (dc.getCounter() != null){
			this.counter = new Counter();
			this.counter.convertFromTable(dc.getCounter());
		}
		if (dc.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(dc.getCompany());
		}
	}
	public void convertFromTableWithSources(Itbl obj){
		TblDocument dc = (TblDocument)obj;
		this.convertFromTable(obj);
		if (dc.getFlows() != null){
			this.flows = new HashSet<DocumentFlow>();
			for (Iterator<TblDocumentFlow> iterator = dc.getFlows().iterator(); iterator.hasNext();){
				TblDocumentFlow df = iterator.next();
				DocumentFlow tdf = new DocumentFlow();
				tdf.convertFromTable(df);
				this.flows.add(tdf);
			}
		}
	}
	public GECOObject control(){
		if (this.code == null || this.code.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Mancante");
		}
		if (this.description == null || this.description.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Nome Mancante");
		}
		return null;
	}
}
