package it.progess.core.pojo;


import it.progess.core.vo.CompositionProduct;
import it.progess.core.vo.Ivo;
import it.progess.core.vo.ListProduct;
import it.progess.core.vo.Product;
import it.progess.core.vo.ProductEcConfig;
import it.progess.core.vo.UnitMeasureProduct;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;



import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
@Entity
@Table(name="tblproduct")
public class TblProduct implements Itbl{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idProduct")
	private int idProduct;
	@Column(name="code")
	private String code;
	@Column(name="description")
	private String description;
	@Column(name="barcode")
	private String barcode;
	@Column(name="weightbarcode")
	private int weightbarcode;
	@Column(name="sellprice")
	private float sellprice;
	@Column(name="purchaseprice")
	private float purchaseprice;
	@Column(name="manageserialnumber")
	private boolean manageserialnumber;//catalog
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<TblUnitMeasureProduct> ums;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idCategory")
	@Fetch(FetchMode.SELECT)
	private TblCategoryProduct category;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idSubcategory")
	@Fetch(FetchMode.SELECT)
	private TblSubCategoryProduct subcategory;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idGroup")
	@Fetch(FetchMode.SELECT)
	private TblGroupProduct group;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTaxRate")
	@Fetch(FetchMode.SELECT)
	private TblTaxrate taxrate;
	@ManyToOne
	@JoinColumn(name = "idSupplier")
	@Fetch(FetchMode.SELECT)
	private TblSupplier supplier;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idBrand")
	@Fetch(FetchMode.SELECT)
	private TblBrand brand;
	@ManyToOne
	@JoinColumn(name = "idCompany")
	@Fetch(FetchMode.SELECT)
	private TblCompany company;
	@Column(name="isProduct")
	private boolean isProduct;
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<TblListProduct> listproduct;
	@ManyToOne
	@JoinColumn(name = "idRegion")
	@Fetch(FetchMode.SELECT)
	private TblRegion region;
	@Column(name="publish")
	private boolean publish;
	@Column(name="photo")
	private byte[] photo;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "idproduct_ec_config")
	@Fetch(FetchMode.SELECT)
	private TblProductEcConfig ecconfig;
	@Column(name="shortdescription")
	private String shortdescription;
	@Column(name="longdescription")
	private String longdescription;
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	@Fetch(FetchMode.SELECT)
	private Set<TblCompositionProduct> compositions;
	
	public Set<TblCompositionProduct> getCompositions() {
		return compositions;
	}
	public void setCompositions(Set<TblCompositionProduct> compositions) {
		this.compositions = compositions;
	}
	public String getShortdescription() {
		return shortdescription;
	}
	public void setShortdescription(String shortdescription) {
		this.shortdescription = shortdescription;
	}
	public String getLongdescription() {
		return longdescription;
	}
	public void setLongdescription(String longdescription) {
		this.longdescription = longdescription;
	}
	public TblProductEcConfig getEcconfig() {
		return ecconfig;
	}
	public void setEcconfig(TblProductEcConfig ecconfig) {
		this.ecconfig = ecconfig;
	}
	public boolean isPublish() {
		return publish;
	}
	public void setPublish(boolean publish) {
		this.publish = publish;
	}
	public byte[] getPhoto() {
		return photo;
	}
	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	public TblRegion getRegion() {
		return region;
	}
	public void setRegion(TblRegion region) {
		this.region = region;
	}
	public Set<TblListProduct> getListproduct() {
		return listproduct;
	}
	public void setListproduct(Set<TblListProduct> listproduct) {
		this.listproduct = listproduct;
	}
	public boolean isProduct() {
		return isProduct;
	}
	public void setProduct(boolean isProduct) {
		this.isProduct = isProduct;
	}
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public TblBrand getBrand() {
		return brand;
	}
	public void setBrand(TblBrand brand) {
		this.brand = brand;
	}
	public TblSupplier getSupplier() {
		return supplier;
	}
	public void setSupplier(TblSupplier supplier) {
		this.supplier = supplier;
	}
	public TblTaxrate getTaxrate() {
		return taxrate;
	}
	public void setTaxrate(TblTaxrate taxrate) {
		this.taxrate = taxrate;
	}
	public int getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
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
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public int getWeightbarcode() {
		return weightbarcode;
	}
	public void setWeightbarcode(int weightbarcode) {
		this.weightbarcode = weightbarcode;
	}
	public float getSellprice() {
		return sellprice;
	}
	public void setSellprice(float sellprice) {
		this.sellprice = sellprice;
	}
	public float getPurchaseprice() {
		return purchaseprice;
	}
	public void setPurchaseprice(float purchaseprice) {
		this.purchaseprice = purchaseprice;
	}
	public boolean getManageserialnumber() {
		return manageserialnumber;
	}
	public void setManageserialnumber(boolean manageserialnumber) {
		this.manageserialnumber = manageserialnumber;
	}
	public Set<TblUnitMeasureProduct> getUms() {
		return ums;
	}
	public void setUms(Set<TblUnitMeasureProduct> ums) {
		this.ums = ums;
	}
	
	public TblCategoryProduct getCategory() {
		return category;
	}
	public void setCategory(TblCategoryProduct category) {
		this.category = category;
	}
	public TblSubCategoryProduct getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(TblSubCategoryProduct subcategory) {
		this.subcategory = subcategory;
	}
	public TblGroupProduct getGroup() {
		return group;
	}
	public void setGroup(TblGroupProduct group) {
		this.group = group;
	}
	public void convertToTable(Ivo obj){
		Product pd = (Product)obj;
		this.barcode = pd.getBarcode();
		this.code = pd.getCode();
		this.description = pd.getDescription();
		this.idProduct = pd.getIdProduct();
		this.manageserialnumber = pd.getManageserialnumber();
		this.purchaseprice = pd.getPurchaseprice();
		this.sellprice = pd.getSellprice();
		this.weightbarcode = pd.getWeightbarcode();
		this.isProduct = pd.isProduct();
		this.publish = pd.isPublish();
		this.shortdescription = pd.getShortdescription();
		this.longdescription = pd.getLongdescription();
		if(pd.getPhoto() != null){
			try{
				//this.photo = (new String(pd.getPhoto().getBytes(),"UTF-8")).getBytes();
				this.photo = (pd.getPhoto()).getBytes();
			}catch(Exception e){
			
			}
			
		}
		if(pd.getGroup()!= null){
			this.group = new TblGroupProduct();
			this.group.convertToTable(pd.getGroup());
		}
		if (pd.getTaxrate()!= null){
			this.taxrate = new TblTaxrate();
			this.taxrate.convertToTable(pd.getTaxrate());
		}
		if (pd.getCategory() != null){
			this.category = new TblCategoryProduct();
			this.category.convertToTable(pd.getCategory());
		}
		if (pd.getSubcategory() != null){
			this.subcategory = new TblSubCategoryProduct();
			this.subcategory.convertToTable(pd.getSubcategory());
		}
		if (pd.getBrand() != null){
			this.brand = new TblBrand();
			this.brand.convertToTable(pd.getBrand());
		}
		if (pd.getSupplier() != null){
			this.supplier = new TblSupplier();
			this.supplier.convertToTable(pd.getSupplier());
		}
		if (pd.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(pd.getCompany());
		}
		if (pd.getRegion() != null){
			this.region = new TblRegion();
			this.region.convertToTable(pd.getRegion());
		}
		if(pd.getEcconfig()!= null){
			this.ecconfig = new TblProductEcConfig();
			this.ecconfig.convertToTable(pd.getEcconfig());
		}
		if (pd.getUms() != null ){
			this.ums = new HashSet<TblUnitMeasureProduct>();
			for (Iterator<UnitMeasureProduct> iterator = pd.getUms().iterator(); iterator.hasNext();){
				UnitMeasureProduct ump = iterator.next();
				TblUnitMeasureProduct umpt = new TblUnitMeasureProduct();
				umpt.convertToTableForSaving(ump,this,this.isProduct);
				this.ums.add(umpt);
			}
		}else{
			this.ums = null;
		}
		if (pd.getCompositions() != null){
			this.compositions = new HashSet<TblCompositionProduct>();
			for(Iterator<CompositionProduct> itcomp = pd.getCompositions().iterator(); itcomp.hasNext();){
				CompositionProduct cp = itcomp.next();
				TblCompositionProduct tcp = new TblCompositionProduct();
				tcp.convertToTable(cp);
				tcp.setProduct(this);
				this.compositions.add(tcp);
			}
		}else{
			this.compositions = null;
		}
			
		if (pd.getListproduct() != null){
			this.listproduct = new HashSet<TblListProduct>();
			for (Iterator<ListProduct> iterator = pd.getListproduct().iterator(); iterator.hasNext();){
				ListProduct listproduct = iterator.next();
				//if (listproduct.isActive() == true){
					TblListProduct listp = new TblListProduct();
					listp.convertToTable(listproduct);
					listp.setProduct(this);
					listp.setActive(true);
					this.listproduct.add(listp);
				//}
			}
		}
	}
}
