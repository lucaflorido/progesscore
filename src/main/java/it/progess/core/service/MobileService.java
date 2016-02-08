package it.progess.core.service;

import it.progess.core.dao.RegistryDao;
import it.progess.core.dao.UserDao;
import it.progess.core.hibernate.HibernateUtils;
import it.progess.core.vo.User;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
@Path("mobile")
public class MobileService {
	/***
	   * Search Product
	   */
	  @GET
	  @Path("product/search/{userkey}/{value}")
	  @Produces(MediaType.TEXT_HTML)
	  public String searchProducts(@Context HttpServletRequest request,@PathParam("value") String value,@PathParam("userkey") String userkey) {
		Gson gson = new Gson();
		RegistryDao dao = new RegistryDao();
		User loggeduser = new UserDao().getSingleUserVO(Integer.parseInt(userkey)); 
		return gson.toJson(dao.searchProducts(value,loggeduser));
	  }
}
