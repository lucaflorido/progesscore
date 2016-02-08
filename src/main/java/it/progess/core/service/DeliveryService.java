package it.progess.core.service;


import it.progess.core.dao.DeliveryDao;

import it.progess.core.vo.Address;
import it.progess.core.vo.Company;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
@Path("delivery")
public class DeliveryService {
	@POST
	@Path("ecdelivery")
	@Produces(MediaType.APPLICATION_JSON)
	public String saveEcDelivery(String data){
		Gson gson = new Gson();
		Company c = (Company)gson.fromJson(data,Company.class);
		return gson.toJson(new DeliveryDao().saveUpdatesEcDeliveryCompany(c));
	}
	@POST
	@Path("costs/{key}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDeliveryCosts(String data,@PathParam("key") String key){
		Gson gson = new Gson();
		Address a = (Address)gson.fromJson(data,Address.class);
		return gson.toJson(new DeliveryDao().getDeliveryPrice(a, key));
	}
}
