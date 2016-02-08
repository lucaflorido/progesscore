package it.progess.core.vo;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import it.progess.core.pojo.Itbl;
import it.progess.core.pojo.TblCategoryProduct;
import it.progess.core.pojo.TblSubCategoryProduct;
import it.progess.core.properties.GECOParameter;

public class CategoryProduct implements Ivo{
	private int idCategoryProduct;
	private String code;
	private String name;
	private String description;
	private String note;
	private Set<SubCategoryProduct> subcategories;
	private Company company;
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Set<SubCategoryProduct> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(Set<SubCategoryProduct> subcategories) {
		this.subcategories = subcategories;
	}
	public int getIdCategoryProduct() {
		return idCategoryProduct;
	}
	public void setIdCategoryProduct(int idCategoryProduct) {
		this.idCategoryProduct = idCategoryProduct;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void convertFromTable(Itbl obj){
		TblCategoryProduct cp = (TblCategoryProduct)obj;
		this.code = cp.getCode();
		this.description = cp.getDescription();
		this.idCategoryProduct = cp.getIdCategoryProduct();
		this.name = cp.getName();
		this.note = cp.getNote();
		if (cp.getSubcategories() != null){
			this.subcategories = new HashSet<SubCategoryProduct>();
			for (Iterator<TblSubCategoryProduct> iterator = cp.getSubcategories().iterator(); iterator.hasNext();){
				TblSubCategoryProduct subcategoryproduct = iterator.next();
				SubCategoryProduct subc = new SubCategoryProduct();
				subc.convertFromTable(subcategoryproduct);
				this.subcategories.add(subc);
			}
		}
		if (cp.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(cp.getCompany());
		}
	}
	public GECOObject control(User user){
		if (this.idCategoryProduct == 0){
			this.company = user.getCompany();
		}
		return control();
	}
	public GECOObject control(){
		if (this.code == null || this.code.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Mancante");
		}
		if (this.name == null || this.name.equals("") == true){
			return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Nome Mancante");
		}
		return null;
	}
}
