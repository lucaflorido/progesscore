package it.progess.core.pojo;

import it.progess.core.vo.Ivo;
import it.progess.core.vo.UnitMeasure;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.NONE)
@Table(name="tblunitmeasure")
public class TblUnitMeasure implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idUnitMeasure")
	private int idUnitMeasure;
	@Column(name="description")
	private String description;
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public int getIdUnitMeasure() {
		return idUnitMeasure;
	}
	public void setIdUnitMeasure(int idUnitMeasure) {
		this.idUnitMeasure = idUnitMeasure;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public void convertToTable(Ivo obj) {
		UnitMeasure um = (UnitMeasure)obj;
		this.idUnitMeasure = um.getIdUnitMeasure();
		this.description = um.getDescription();
		this.code = um.getCode();
		this.name = um.getName();
		if (um.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(um.getCompany());
		}
	}
}
