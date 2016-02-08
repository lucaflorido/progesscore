package it.progess.core.vo;

import it.progess.core.pojo.Itbl;
import it.progess.core.pojo.TblGroupCustomer;
import it.progess.core.properties.GECOParameter;

public class GroupCustomer implements Ivo{
	private int idGroupCustomer;
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
	public void convertFromTable(Itbl obj){
		TblGroupCustomer gc = (TblGroupCustomer) obj;
		this.code = gc.getCode();
		this.description = gc.getDescription();
		this.idGroupCustomer = gc.getIdGroupCustomer();
		this.name = gc.getName();
		if (gc.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(gc.getCompany());
		}
	}
	public GECOObject control(User user){
		if (this.idGroupCustomer == 0){
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
