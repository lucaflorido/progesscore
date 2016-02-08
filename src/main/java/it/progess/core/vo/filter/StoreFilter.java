package it.progess.core.vo.filter;

import it.progess.core.vo.Brand;
import it.progess.core.vo.CategoryProduct;
import it.progess.core.vo.Customer;
import it.progess.core.vo.Destination;
import it.progess.core.vo.Document;
import it.progess.core.vo.GroupProduct;
import it.progess.core.vo.SubCategoryProduct;
import it.progess.core.vo.Supplier;
import it.progess.core.vo.Transporter;

public class StoreFilter {
	private Brand brand;
	private GroupProduct group;
	private CategoryProduct category;
	private SubCategoryProduct subcategory;
	private String searchString;
	private String fromDate;
	private String toDate;
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public Brand getBrand() {
		return brand;
	}
	public void setBrand(Brand brand) {
		this.brand = brand;
	}
	public GroupProduct getGroup() {
		return group;
	}
	public void setGroup(GroupProduct group) {
		this.group = group;
	}
	public CategoryProduct getCategory() {
		return category;
	}
	public void setCategory(CategoryProduct category) {
		this.category = category;
	}
	public SubCategoryProduct getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(SubCategoryProduct subcategory) {
		this.subcategory = subcategory;
	}
	public String getSearchString() {
		return searchString;
	}
	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}
	
}
