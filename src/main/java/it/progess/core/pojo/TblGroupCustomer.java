package it.progess.core.pojo;

import it.progess.core.vo.GroupCustomer;
import it.progess.core.vo.Ivo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tblgroup_customer")
public class TblGroupCustomer  implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idGroupCustomer")
	private int idGroupCustomer;
	@Column(name="code")
	private String code;
	@Column(name="name")
	private String name;
	@Column(name="description")
	private String description;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public int getIdGroupCustomer() {
		return idGroupCustomer;
	}
	public void setIdGroupCustomer(int idGroupCustomer) {
		this.idGroupCustomer = idGroupCustomer;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void convertToTable(Ivo obj){
		GroupCustomer gc = (GroupCustomer) obj;
		this.code = gc.getCode();
		this.description = gc.getDescription();
		this.idGroupCustomer = gc.getIdGroupCustomer();
		this.name = gc.getName();
		if (gc.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(gc.getCompany());
		}
	}
}
