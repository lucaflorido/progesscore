package it.progess.core.vo;

import it.progess.core.pojo.Itbl;
import it.progess.core.pojo.TblEcPayment;



public class EcPayment implements Ivo{
	private int idEcPayment;
	private boolean loggeduser;
	private boolean nologgeduser;
	
	private String accountID;
	private String token;
	private PaymentSolution paysolution;
    private float commission;
	
	public float getCommission() {
		return commission;
	}
	public void setCommission(float commission) {
		this.commission = commission;
	}
	public int getIdEcPayment() {
		return idEcPayment;
	}
	public void setIdEcPayment(int idEcPayment) {
		this.idEcPayment = idEcPayment;
	}
	public boolean isLoggeduser() {
		return loggeduser;
	}
	public void setLoggeduser(boolean loggeduser) {
		this.loggeduser = loggeduser;
	}
	public boolean isNologgeduser() {
		return nologgeduser;
	}
	public void setNologgeduser(boolean nologgeduser) {
		this.nologgeduser = nologgeduser;
	}
	
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public PaymentSolution getPaysolution() {
		return paysolution;
	}
	public void setPaysolution(PaymentSolution paysolution) {
		this.paysolution = paysolution;
	}
	public void convertFromTable(Itbl t){
		TblEcPayment tep  =(TblEcPayment)t;
		this.idEcPayment = tep.getIdEcPayment();
		this.accountID = tep.getAccountID();
		this.loggeduser = tep.isLoggeduser();
		this.nologgeduser = tep.isNologgeduser();
		this.token = tep.getToken();
		this.commission = tep.getCommission();
		if (tep.getPaysolution() != null){
			this.paysolution = new PaymentSolution();
			this.paysolution.convertFromTable(tep.getPaysolution());
		}
	}
	public GECOObject control(){
		return null;
	}
}
