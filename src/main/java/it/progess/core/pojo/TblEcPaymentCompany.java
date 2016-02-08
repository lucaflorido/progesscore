package it.progess.core.pojo;

import it.progess.core.vo.Company;
import it.progess.core.vo.EcPayment;
import it.progess.core.vo.EcPaymentCompany;
import it.progess.core.vo.Ivo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name="tblecpayment_company")
public class TblEcPaymentCompany implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idecpayment_company")
	private int idEcPaymentCompany;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idecpayment")
	private TblEcPayment ecpayment;
	public int getIdEcPaymentCompany() {
		return idEcPaymentCompany;
	}
	public void setIdEcPaymentCompany(int idEcPaymentCompany) {
		this.idEcPaymentCompany = idEcPaymentCompany;
	}
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public TblEcPayment getEcpayment() {
		return ecpayment;
	}
	public void setEcpayment(TblEcPayment ecpayment) {
		this.ecpayment = ecpayment;
	}
	public void convertToTable(Ivo obj){
		EcPaymentCompany ecp = (EcPaymentCompany)obj;
		this.idEcPaymentCompany = ecp.getIdEcPaymentCompany();
		if (ecp.getEcpayment() != null){
			this.ecpayment = new TblEcPayment();
			this.ecpayment.convertToTable(ecp.getEcpayment());
		}
	}
	public void convertToTableForSaving(Ivo obj,Itbl itbl){
		EcPaymentCompany ecp = (EcPaymentCompany)obj;
		convertToTable(obj);
		this.company = (TblCompany)itbl;
	}
}
