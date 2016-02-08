package it.progess.core.service;

import it.progess.core.dao.MailDao;
import it.progess.core.hibernate.HibernateUtils;
import it.progess.core.vo.GECOObject;
import it.progess.core.vo.Head;
import it.progess.core.vo.MailConfig;
import it.progess.core.vo.User;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("email")
public class MailService {
	@Context
	ServletContext context;
	
	  @POST
	  @Path("test")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String testEmail(@FormParam("user") String user,@FormParam("mailconfig") String mailconfig){
		  Gson gson = new Gson();
		  User userobj = gson.fromJson(user, User.class);
		  MailConfig mailobj = gson.fromJson(mailconfig, MailConfig.class);
		  GECOObject obj = new MailDao().testEmail(userobj,mailobj);
		  return gson.toJson(obj);
	  }
	  @POST
	  @Path("document")
	  @Produces(MediaType.TEXT_HTML)
	  @Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	  public String documentSend(@FormParam("head") String head,@FormParam("user") String user,@FormParam("mailconfig") String mailconfig,@Context HttpServletRequest request) {
		  User loggeduser = HibernateUtils.getUserFromSession(request);
		  Gson gson = new Gson();
		  User userobj = gson.fromJson(user, User.class);
		  Head headobj = gson.fromJson(head, Head.class);
		  MailConfig mailobj = gson.fromJson(mailconfig, MailConfig.class);
		  GECOObject obj = new MailDao().documentEmail(context, loggeduser, headobj,mailobj);
		  return gson.toJson(obj);
	  }
}
