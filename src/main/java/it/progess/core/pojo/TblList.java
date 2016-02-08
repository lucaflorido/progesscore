package it.progess.core.pojo;

import it.progess.core.hibernate.DataUtilConverter;
import it.progess.core.vo.Ivo;
import it.progess.core.vo.List;
import it.progess.core.vo.ListProduct;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name="tbllist")
public class TblList implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idList")
	private int idList;
	@Column(name="code")
	private String code;
	@Column(name="description")
	private String description;
	@Column(name="name")
	private String name;
	@OneToMany(fetch= FetchType.LAZY,mappedBy = "list",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<TblListProduct> listproduct;
	@Column(name="startdate")
	private Date startdate;
	@Column(name="active")
	private boolean active;
	@Column(name="isPercentage")
	private boolean isPercentage;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	private TblCompany company;
	@Column(name="increment")
	private float increment;
	@Column(name="publish")
	private boolean publish;
	@Column(name="key_code")
    private String key;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public boolean isPublish() {
		return publish;
	}
	public void setPublish(boolean publish) {
		this.publish = publish;
	}
	public float getIncrement() {
		return increment;
	}
	public void setIncrement(float increment) {
		this.increment = increment;
	}
	public boolean isPercentage() {
		return isPercentage;
	}
	public void setPercentage(boolean isPercentage) {
		this.isPercentage = isPercentage;
	}
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public int getIdList() {
		return idList;
	}
	public void setIdList(int idList) {
		this.idList = idList;
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
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Set<TblListProduct> getListproduct() {
		return listproduct;
	}
	public void setListproduct(Set<TblListProduct> listproduct) {
		this.listproduct = listproduct;
	}
	public void convertToTable(Ivo obj){
		List lt = (List)obj;
		this.code = lt.getCode();
		this.description = lt.getDescription();
		this.idList = lt.getIdList();
		this.name  = lt.getName();
		this.isPercentage = lt.isPercentage();
		this.increment = lt.getIncrement();
		this.active = lt.isActive();
		this.publish = lt.isPublish();
		this.key = lt.getKey();
		if (lt.getStartdate() != null){
			this.startdate = DataUtilConverter.convertDateFromString(lt.getStartdate());
		}else{
			this.startdate = new Date();
		}
		if (lt.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(lt.getCompany());
		}
	}
	public void convertToTableSingle(Ivo obj){
		List lt = (List)obj;
		this.code = lt.getCode();
		this.description = lt.getDescription();
		this.idList = lt.getIdList();
		this.name  = lt.getName();
		this.publish = lt.isPublish();
		if (lt.getStartdate() != null){
			this.startdate = DataUtilConverter.convertDateFromString(lt.getStartdate());
		}else{
			this.startdate = new Date();
		}
		
		this.active = lt.isActive();
		this.isPercentage = lt.isPercentage();
		this.increment = lt.getIncrement();
		this.key = lt.getKey();
		this.listproduct = new HashSet<TblListProduct>();
		if (lt.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(lt.getCompany());
		}
		if (lt.getListproduct() != null){
			for (Iterator<ListProduct> iterator = lt.getListproduct().iterator(); iterator.hasNext();){
				ListProduct listproduct = iterator.next();
				TblListProduct listp = new TblListProduct();
				listp.convertToTable(listproduct);
				this.listproduct.add(listp);
			}
		}
	}
	public void convertToTableForSaving(Ivo obj){
		List lt = (List)obj;
		this.code = lt.getCode();
		this.description = lt.getDescription();
		this.idList = lt.getIdList();
		this.name  = lt.getName();
		this.active = lt.isActive();
		this.publish = lt.isPublish();
		if (lt.getStartdate() != null){
			this.startdate = DataUtilConverter.convertDateFromString(lt.getStartdate());
		}else{
			this.startdate = new Date();
		}
		this.listproduct = new HashSet<TblListProduct>();
		this.isPercentage = lt.isPercentage();
		this.increment = lt.getIncrement();
		if (lt.getKey() == null || lt.getKey().isEmpty() ==  true){
			UUID ui = UUID.randomUUID();
			this.key = ui.toString();
		}else{
			this.key = lt.getKey();
		}
		
		if (lt.getListproduct() != null){
			for (Iterator<ListProduct> iterator = lt.getListproduct().iterator(); iterator.hasNext();){
				ListProduct listproduct = iterator.next();
				TblListProduct listp = new TblListProduct();
				listp.convertToTableForSaving(listproduct,this);
				/*if (listp.getStartdate() == null){
					listp.setStartdate(this.startdate);
					listp.setActive(true);
				}*/
				this.listproduct.add(listp);
			}
		}
		if (lt.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(lt.getCompany());
		}
	}
}
