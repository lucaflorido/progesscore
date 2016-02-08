package it.progess.core.vo;

import it.progess.core.pojo.Itbl;
import it.progess.core.pojo.TblCountry;

import javax.persistence.Column;

public class Country implements Ivo {
	private int idCountry;
	private String name;
	public int getIdCountry() {
		return idCountry;
	}
	public void setIdCountry(int idCountry) {
		this.idCountry = idCountry;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void convertFromTable(Itbl tbl){
		TblCountry c = (TblCountry)tbl;
		this.idCountry = c.getIdCountry();
		this.name = c.getName();
	}
	public GECOObject control(){
		return null;
	}
}
