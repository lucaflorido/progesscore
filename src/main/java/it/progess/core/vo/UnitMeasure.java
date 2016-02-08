package it.progess.core.vo;

import it.progess.core.pojo.Itbl;
import it.progess.core.pojo.TblUnitMeasure;
import it.progess.core.properties.GECOParameter;

public class UnitMeasure implements Ivo {
	private int idUnitMeasure;
	private String description;
	private String code;
	private String name;
	private Company company;
	
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
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
	public void convertFromTable(Itbl obj){
		TblUnitMeasure um = (TblUnitMeasure)obj;
		this.idUnitMeasure = um.getIdUnitMeasure();
		this.description = um.getDescription();
		this.code = um.getCode();
		this.name = um.getName();
		if (um.getCompany() != null){
			this.company = new Company();
			this.company.convertFromTable(um.getCompany());
		}
	}
	public GECOObject control(User user){
		if (this.idUnitMeasure == 0){
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
