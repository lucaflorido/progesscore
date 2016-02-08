package it.progess.core.service;

import it.progess.core.dao.DocumentDao;
import it.progess.core.dao.StoreDao;
import it.progess.core.exception.GecoException;
import it.progess.core.hibernate.HibernateUtils;
import it.progess.core.vo.Company;
import it.progess.core.vo.GenerateDocsObject;
import it.progess.core.vo.Head;
import it.progess.core.vo.NeededObject;
import it.progess.core.vo.Product;
import it.progess.core.vo.User;
import it.progess.core.vo.filter.GenerateDocsFilter;
import it.progess.core.vo.filter.NeededFilter;
import it.progess.core.vo.filter.StoreFilter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
@Path("store")
public class StoreService {
	/*****
	   * 
	   * Product
	   * @return
	   */
	  @GET
	  @Path("list")
	  @Produces(MediaType.TEXT_PLAIN)
	  public String getHeadList(){
		Gson gson = new Gson();
		StoreDao dao = new StoreDao();
		return gson.toJson(dao.getList());
	  }
	  @POST
	  @Path("neededlist")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String getNeededList(@FormParam("filter") String filter){
		Gson gson = new Gson();
		NeededFilter neededFilter = gson.fromJson(filter,NeededFilter.class);
		DocumentDao dao = new DocumentDao();
		return gson.toJson(dao.getHeadRowsNeededList(neededFilter));
	  }
	  @POST
	  @Path("neededdoc")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String createNeededDoc(@Context HttpServletRequest request,@FormParam("needed") String needed){
		Gson gson = new Gson();
		NeededObject neededFilter = gson.fromJson(needed,NeededObject.class);
		DocumentDao dao = new DocumentDao();
		User loggeduser = HibernateUtils.getUserFromSession(request);
		return gson.toJson(dao.createHeadRowsNeeded(neededFilter,loggeduser));
	  }
	  @POST
	  @Path("filteredlist")
	  @Produces(MediaType.TEXT_PLAIN)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String getFilteredList(@FormParam("filter") String filter){
		Gson gson = new Gson();
		StoreFilter neededFilter = gson.fromJson(filter,StoreFilter.class);
		StoreDao dao = new StoreDao();
		return gson.toJson(dao.getListStorage(neededFilter));
	  }
}
