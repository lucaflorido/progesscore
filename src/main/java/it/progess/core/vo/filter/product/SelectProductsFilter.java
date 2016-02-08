package it.progess.core.vo.filter.product;

import it.progess.core.pojo.TblBrand;
import it.progess.core.pojo.TblCategoryProduct;
import it.progess.core.pojo.TblComposition;
import it.progess.core.pojo.TblGroupProduct;
import it.progess.core.pojo.TblRegion;
import it.progess.core.pojo.TblSubCategoryProduct;
import it.progess.core.pojo.TblSupplier;
import it.progess.core.vo.Head;
import it.progess.core.vo.filter.PagesFilter;
import it.progess.core.vo.helpobject.ProductBasicPricesCalculation;

public class SelectProductsFilter {
	private TblBrand brand;
	private TblCategoryProduct category;
	private TblGroupProduct group;
	private TblSupplier supplier;
	private TblSubCategoryProduct subcategory;
	private TblRegion region;
	private float increment;
	private boolean isPercentage;
	private boolean isUmPref;
	private boolean isnotUmPref;
	private String searchstring;
	private TblComposition composition;
	private float minprice;
	private float maxprice;
	private String orderBy;
	private String orderdirection;
	private Head h;
	
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public String getOrderdirection() {
		return orderdirection;
	}
	public void setOrderdirection(String orderdirection) {
		this.orderdirection = orderdirection;
	}
	public float getMinprice() {
		return minprice;
	}
	public void setMinprice(float minprice) {
		this.minprice = minprice;
	}
	public float getMaxprice() {
		return maxprice;
	}
	public void setMaxprice(float maxprice) {
		this.maxprice = maxprice;
	}
	public TblComposition getComposition() {
		return composition;
	}
	public void setComposition(TblComposition composition) {
		this.composition = composition;
	}
	public Head getH() {
		return h;
	}
	public void setH(Head h) {
		this.h = h;
	}
	public TblRegion getRegion() {
		return region;
	}
	public void setRegion(TblRegion region) {
		this.region = region;
	}
	public String getSearchstring() {
		return searchstring;
	}
	public void setSearchstring(String searchstring) {
		this.searchstring = searchstring;
	}
	private PagesFilter pagefilter;
	
	public PagesFilter getPagefilter() {
		return pagefilter;
	}
	public void setPagefilter(PagesFilter pagefilter) {
		this.pagefilter = pagefilter;
	}
	public TblSupplier getSupplier() {
		return supplier;
	}
	public void setSupplier(TblSupplier supplier) {
		this.supplier = supplier;
	}
	public TblBrand getBrand() {
		return brand;
	}
	public void setBrand(TblBrand brand) {
		this.brand = brand;
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
	public boolean isUmPref() {
		return isUmPref;
	}
	public void setUmPref(boolean isUmPref) {
		this.isUmPref = isUmPref;
	}
	public boolean isIsnotUmPref() {
		return isnotUmPref;
	}
	public void setIsnotUmPref(boolean isnotUmPref) {
		this.isnotUmPref = isnotUmPref;
	}
	
}
