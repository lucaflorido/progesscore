package it.progess.core.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import it.progess.core.vo.Address;
import it.progess.core.vo.Ivo;
@Entity
@Table(name="tbladdress")
public class TblAddress implements Itbl {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idAddress")
	private int idaddress;
	@Column(name="address")
	private String address;
	@Column(name="city")
	private String city;
	@Column(name="zone")
	private String zone;
	@Column(name="zipcode")
	private String zipcode;
	@Column(name="number")
	private String number;
	@Column(name="country")
	private String country;
	@ManyToOne
	@JoinColumn(name = "idcountry")
	private TblCountry countryObj;
	@ManyToOne
	@JoinColumn(name = "idzone")
	private TblZone zoneObj;
	@ManyToOne
	@JoinColumn(name = "idcity")
	private TblCity cityObj;
	@Column(name="name")
	private String name;
	@Column(name="code")
	private String code;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getIdaddress() {
		return idaddress;
	}
	public void setIdaddress(int idaddress) {
		this.idaddress = idaddress;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
	public TblCountry getCountryObj() {
		return countryObj;
	}
	public void setCountryObj(TblCountry countryObj) {
		this.countryObj = countryObj;
	}
	public TblZone getZoneObj() {
		return zoneObj;
	}
	public void setZoneObj(TblZone zoneObj) {
		this.zoneObj = zoneObj;
	}
	public TblCity getCityObj() {
		return cityObj;
	}
	public void setCityObj(TblCity cityObj) {
		this.cityObj = cityObj;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void convertToTable(Ivo obj){
		Address ad = (Address)obj;
		this.address = ad.getAddress();
		this.city = ad.getCity();
		this.idaddress = ad.getIdaddress();
		this.zipcode = ad.getZipcode();
		this.zone = ad.getZone();
		this.number = ad.getNumber();
		this.country = ad.getCountry();
		this.code = ad.getCode();
		this.name = ad.getName();
		if (ad.getCountryObj() != null && ad.getCountryObj().getIdCountry() > 0){
			this.countryObj = new TblCountry();
			this.countryObj.convertToTable(ad.getCountryObj());
		}
		if (ad.getZoneObj() != null && ad.getZoneObj().getIdZone() > 0){
			this.zoneObj = new TblZone();
			this.zoneObj.convertToTable(ad.getZoneObj());
		}
		if (ad.getCityObj() != null && ad.getCityObj().getIdCity()>0){
			this.cityObj = new TblCity();
			this.cityObj.convertToTable(ad.getCityObj());
		}
	}
}
