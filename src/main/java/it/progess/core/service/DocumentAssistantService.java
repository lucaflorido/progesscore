package it.progess.core.service;

import java.util.Iterator;

import it.progess.core.vo.Head;
import it.progess.core.vo.HeadTotalCalculation;
import it.progess.core.vo.Row;
import it.progess.core.vo.RowTotalCalculator;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("documenthelp")
public class DocumentAssistantService {
	  @POST
	  @Path("rowtotal")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String calculateRowTotal( String data){
		  Gson gson = new Gson();
		  RowTotalCalculator sms = gson.fromJson(data,RowTotalCalculator.class);
		  sms.calculation();
		  return gson.toJson(sms);
	  }
	  @POST
	  @Path("headtotal")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String calculateHead(String data){

		  Gson gson = new Gson();
		  HeadTotalCalculation sms = gson.fromJson(data,HeadTotalCalculation.class);
		  sms.calculation();
		  return gson.toJson(sms);
	  }
	  @POST
	  @Path("headalltotal")
	  @Produces(MediaType.APPLICATION_JSON)
	  public String calculateHeadComplete(String data){
		  Gson gson = new Gson();
		  Head h = gson.fromJson(data,Head.class);
		  for(Iterator<Row> it = h.getRows().iterator();it.hasNext();){
			  Row r = it.next();
			  RowTotalCalculator rc = new RowTotalCalculator();
			  rc.rowCalculation(r);
		  }
		  HeadTotalCalculation htc = new HeadTotalCalculation();
		  htc.calculation(h);
		  return gson.toJson(h);
	  }
}
