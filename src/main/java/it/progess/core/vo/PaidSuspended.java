package it.progess.core.vo;

import it.progess.core.pojo.Itbl;
import it.progess.core.pojo.TblPaid;
import it.progess.core.pojo.TblPaidSuspended;
import it.progess.core.pojo.TblSuspended;
import it.progess.core.properties.GECOParameter;

public class PaidSuspended implements Ivo {
	private int idPaidSuspended;
	private Paid paid;
	private Suspended suspended;
	private double amount;
	public int getIdPaidSuspended() {
		return idPaidSuspended;
	}
	public void setIdPaidSuspended(int idPaidSuspended) {
		this.idPaidSuspended = idPaidSuspended;
	}
	public Paid getPaid() {
		return paid;
	}
	public void setPaid(Paid paid) {
		this.paid = paid;
	}
	public Suspended getSuspended() {
		return suspended;
	}
	public void setSuspended(Suspended suspended) {
		this.suspended = suspended;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void convertFromTable(Itbl obj){
		TblPaidSuspended ps = (TblPaidSuspended)obj;
		this.amount = ps.getAmount();
		this.idPaidSuspended = ps.getIdPaidSuspended();
		if (ps.getPaid() != null){
			this.paid = new Paid();
			this.paid.convertFromTable(ps.getPaid());
		}
		if (ps.getSuspended() != null){
			this.suspended = new Suspended();
			this.suspended.convertFromTable(ps.getSuspended());
		}
	}
	public GECOObject control(){
		return null;
	}
}
