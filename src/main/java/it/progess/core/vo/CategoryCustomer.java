package it.progess.core.vo;

import it.progess.core.pojo.Itbl;
import it.progess.core.pojo.TblCategoryCustomer;
import it.progess.core.pojo.TblCompany;
import it.progess.core.properties.GECOParameter;


public class CategoryCustomer implements Ivo {
	private int idCategoryCustomer;
	private String code;
	private String name;
	private String description;
	private Company company;
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
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
	public void convertFromTable(Itbl obj){
		TblCategoryCustomer cc = (TblCategoryCustomer)obj;
		this.code = cc.getCode();
		this.description = cc.getDescription();
		this.idCategoryCustomer = cc.getIdCategoryCustomer();
		this.name = cc.getName();
		if (cc.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(cc.getCompany());
		}
	}
	public GECOObject control(User user){
		if (this.idCategoryCustomer == 0){
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
