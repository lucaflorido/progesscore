package it.progess.core.pojo;

import it.progess.core.vo.GroupProduct;
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
@Table(name="tblgroup_product")
public class TblGroupProduct implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idGroupProduct")
	private int idGroupProduct;
	@Column(name="code")
	private String code;
	@Column(name="description")
	private String description;
	@Column(name="name")
	private String name;
	@Column(name="note")
	private String note;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public int getIdGroupProduct() {
		return idGroupProduct;
	}
	public void setIdGroupProduct(int idGroupProduct) {
		this.idGroupProduct = idGroupProduct;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void convertToTable(Ivo obj){
		GroupProduct gp = (GroupProduct)obj; 
		this.code = gp.getCode();
		this.description = gp.getDescription();
		this.idGroupProduct = gp.getIdGroupProduct();
		this.name = gp.getName();
		this.note = gp.getNote();
		if (gp.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(gp.getCompany());
		}
	}
}
