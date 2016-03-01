package it.progess.core.service;

import it.progess.core.dao.DocumentDao;
import it.progess.core.dao.UserDao;
import it.progess.core.exception.GecoException;
import it.progess.core.vo.AddRowToHead;
import it.progess.core.vo.GECOObject;
import it.progess.core.vo.GenerateDocsObject;
import it.progess.core.vo.GenerateObject;
import it.progess.core.vo.Head;
import it.progess.core.vo.Row;
import it.progess.core.vo.User;
import it.progess.core.vo.filter.GenerateDocsFilter;
import it.progess.core.vo.filter.HeadFilter;

import java.util.Arrays;
import java.util.HashSet;















import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
@Path("head")
public class DocumentService {
	/*****
	   * 
	   * Product
	   * @return
	   */
	  @POST
	  @Path("pages/{size}")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON)
	  public String pages(@HeaderParam("UUID") String uuid,@PathParam("size") int size, String data) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		HeadFilter filterDoc = gson.fromJson(data,HeadFilter.class);
		User loggeduser = UserDao.getSingleUserVOByCode(uuid);
		return gson.toJson(dao.getPagesNumber(size,filterDoc,loggeduser));
	  }
	  @POST
	  @Path("head")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String getHeadList(@HeaderParam("UUID") String uuid, String data){
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		HeadFilter filterDoc = gson.fromJson(data,HeadFilter.class);
		User loggeduser = UserDao.getSingleUserVOByCode(uuid);
		return gson.toJson(dao.getHeadList(filterDoc,loggeduser));
	  }
	  /***
		Get Single user
  */
	  @GET
	  @Path("head/{idhead}")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String singleHead(@PathParam("idhead") int id) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		Head head = new Head();
		head = dao.getSingleHead(id);
		return gson.toJson(head);
	  }
	  @PUT
	  @Path("head")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String saveHead(@HeaderParam("UUID") String uuid,String data){
		  Gson gson = new Gson();
		  Head sms = gson.fromJson(data,Head.class);
		  DocumentDao dao = new DocumentDao();
		  GECOObject ge = null;
		  User loggeduser = UserDao.getSingleUserVOByCode(uuid);
		  try{
			   ge = dao.saveUpdatesHead(sms,loggeduser);
		  }catch(GecoException e){
			  return gson.toJson(e);
		  }
		  return gson.toJson(ge);
	  }
		/***
		Delete user 
	   */
	  @DELETE
	  @Path("head")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String deleteHead(String data){
		  Gson gson = new Gson();
		  try{
			  Head sm = gson.fromJson(data,Head.class);
			  DocumentDao dao = new DocumentDao();
			  dao.deleteHead(sm);
			  return gson.toJson(true);
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  @POST
	  @Path("generationdocs/filterdocs")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String singleHead(String data) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		GenerateDocsFilter filterDoc = gson.fromJson(data,GenerateDocsFilter.class);
		return gson.toJson(dao.getHeadRowsGenerateList(filterDoc));
	  }
	  @POST
	  @Path("generationdocs/objectdocs")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String generateHeads(@HeaderParam("UUID") String uuid,String data) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		GenerateDocsObject generateDoc = gson.fromJson(data,GenerateDocsObject.class);
		User loggeduser = UserDao.getSingleUserVOByCode(uuid);
		return gson.toJson(dao.createHeadRowsGenerateList(generateDoc,loggeduser));
	  }
	  @POST
	  @Path("copyrows/objectdocs")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String copyRows(@HeaderParam("UUID") String uuid,String data) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		GenerateDocsObject generateDoc = gson.fromJson(data,GenerateDocsObject.class);
		User loggeduser = UserDao.getSingleUserVOByCode(uuid);
		return gson.toJson(dao.copyHeadRows(generateDoc,loggeduser));
	  }
	  @POST
	  @Path("createorders")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String createDailyOrders(@HeaderParam("UUID") String uuid,String data) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		GenerateObject generateDoc = gson.fromJson(data,GenerateObject.class);
		User loggeduser = UserDao.getSingleUserVOByCode(uuid);
		return gson.toJson(dao.createDailyOrder(generateDoc,loggeduser));
	  }
	  @POST
	  @Path("fillserialnumber/{idhead}")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String fillSerialNumber(@HeaderParam("UUID") String uuid,@PathParam("idhead") int idhead) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		User loggeduser = UserDao.getSingleUserVOByCode(uuid);
		return gson.toJson(dao.fillSerialNumbers(idhead,loggeduser));
	  }
	  @POST
	  @Path("serialnumberList/{idhead}")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String getSerialNumberRows(@PathParam("idhead") int idhead) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		return gson.toJson(dao.getListOfSerialNumbers(idhead));
	  }
	  @POST
	  @Path("copyrowserialnumber/")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String copyRow( String data) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		Row r = gson.fromJson(data, Row.class);
		return gson.toJson(dao.copyRowSerialNumber(r));
	  }
	  @PUT
	  @Path("saverowsserialnumber/{idhead}")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String saveRowsSerialNumber(@HeaderParam("UUID") String uuid,String data,@PathParam("idhead") int idhead) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		Row[] r = gson.fromJson(data, Row[].class);
		HashSet<Row> rs = new HashSet<Row>();
		rs.addAll(Arrays.asList(r));
		User loggeduser = UserDao.getSingleUserVOByCode(uuid);
		return gson.toJson(dao.saveSerialNumbersRows(idhead, rs,loggeduser));
	  }
	  @POST
	  @Path("createreport")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String createReport(String data) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		GenerateObject r = gson.fromJson(data, GenerateObject.class);
		return gson.toJson(dao.getOrderReport(r));
	  }
	  @POST
	  @Path("checkhead")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String checkHead(String data) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		Head h = gson.fromJson(data, Head.class);
		return gson.toJson(dao.headValidation(h));
	  }
	  @DELETE
	  @Path("removerow/{idrow}")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String deleteRow(@PathParam("idrow") int id){
		  Gson gson = new Gson();
		  try{
			  DocumentDao dao = new DocumentDao();
			  return gson.toJson(dao.removeRow(id));
		  }catch(Exception e){
			  return gson.toJson("");
		  }
	  }
	  @POST
	  @Path("addrow")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String addRow(String data) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		AddRowToHead addr = gson.fromJson(data,AddRowToHead.class);
		return gson.toJson(dao.addRow(addr.getUm(), addr.getH()));
	  }
	  @PUT
	  @Path("addrow/{type}")
	  @Produces(MediaType.APPLICATION_JSON)
	  @Consumes(MediaType.APPLICATION_JSON) 
	  public String saveWizardHead(@HeaderParam("UUID") String uuid,String data,@PathParam("type") String type) {
		Gson gson = new Gson();
		DocumentDao dao = new DocumentDao();
		Head h = gson.fromJson(data,Head.class);
		User loggeduser = UserDao.getSingleUserVOByCode(uuid);
		return gson.toJson(dao.saveWizardHead(loggeduser, h,type));
	  }
}
