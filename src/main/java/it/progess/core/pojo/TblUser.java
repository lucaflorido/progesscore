package it.progess.core.pojo;


import java.util.Date;

import it.progess.core.hibernate.DataUtilConverter;
import it.progess.core.vo.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbluser")
public class TblUser {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="idUser")
	private int iduser;
	@Column(name="username")
	private String username;
	@Column(name="password")
	private String password;
	@Column(name="name")
	private String name;
	@Column(name="surname")
	private String surname;
	@Column(name="phone")
	private String phone;
	@Column(name="mobile")
	private String mobile;
	@Column(name="email")
	private String email;
	@ManyToOne
	@JoinColumn(name="idRole")
	private TblRole role;
	@Column(name="ACTIVE")
	private Boolean active;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="idCompany")
	private TblCompany company;
	@OneToOne(mappedBy="user",cascade = CascadeType.ALL)
	private TblContact contact;
	@Column(name="code")
	private String code;
	@Column(name="birthday")
	private Date birthday;
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public TblContact getContact() {
		return contact;
	}
	public void setContact(TblContact contact) {
		this.contact = contact;
	}
	public TblCompany getCompany() {
		return company;
	}
	public void setCompany(TblCompany company) {
		this.company = company;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public TblRole getRole() {
		return role;
	}
	public void setRole(TblRole role) {
		this.role = role;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public int getIduser() {
		return iduser;
	}
	public void setIduser(int idtbluser) {
		this.iduser = idtbluser;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String tblusercol) {
		this.username = tblusercol;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void convertToTable(User user){
		this.setIduser(user.get_iduser());
		this.setUsername(user.getUsername());
		this.setPassword(user.getPassword());
		this.setName(user.getName());
		this.setSurname(user.getSurname());
		this.setPhone(user.getPhone());
		this.setMobile(user.getMobile());
		this.setEmail(user.getEmail());
		this.setActive(user.getActive());
		this.setCode(user.getCode());
		if (user.getBirthday() != null && user.getBirthday() != ""){
			this.birthday = DataUtilConverter.convertDateFromString(user.getBirthday());
		}else{
			this.birthday = null;
		}
		
		TblRole roleTo = new TblRole();
		roleTo.convertToTable(user.getRole());
		this.setRole(roleTo);
		
		if (user.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTable(user.getCompany());
		}
	}
	public void convertToTableSave(User user){
		convertToTable(user);
		if (user.getCompany() != null){
			this.company = new TblCompany();
			this.company.convertToTableSave(user.getCompany());
		}
		if (user.getContact() != null){
			this.contact = new TblContact();
			this.contact.convertToTable(user.getContact());
			this.contact.setUser(this);
		}
	}
}
