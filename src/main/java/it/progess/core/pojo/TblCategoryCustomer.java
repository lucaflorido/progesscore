package it.progess.core.pojo;

import it.progess.core.vo.CategoryCustomer;
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
@Table(name="tblcategory_customer")
public class TblCategoryCustomer implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idCategoryCustomer")
	private int idCategoryCustomer;
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
	public int getIdCategoryCustomer() {
		return idCategoryCustomer;
	}
	public void setIdCategoryCustomer(int idCategoryCustomer) {
		this.idCategoryCustomer = idCategoryCustomer;
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
		CategoryCustomer cc = (CategoryCustomer)obj;
		this.code = cc.getCode();
		this.description = cc.getDescription();
		this.idCategoryCustomer = cc.getIdCategoryCustomer();
		this.name = cc.getName();
		if (cc.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(cc.getCompany());
		}
	}
}
