package it.progess.core.service;

import it.progess.core.dao.UtilDao;
import it.progess.core.vo.Product;
import it.progess.core.vo.helpobject.ProductBasicPricesCalculation;
import it.progess.core.vo.helpobject.ProductListIncrementVo;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

@Path("util")
public class UtilService {
	@POST
	@Path("prodbasicprice/percentage")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String productBasicPrice( String data){
		Gson g = new Gson();
		ProductBasicPricesCalculation p = g.fromJson(data,ProductBasicPricesCalculation.class);
		return g.toJson(new UtilDao().calculateBasicPricesProduct(p,false));
	}
	@POST
	@Path("prodbasicprice/sellprice")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String productBasicSellPrice( String data){
		Gson g = new Gson();
		ProductBasicPricesCalculation p = g.fromJson(data,ProductBasicPricesCalculation.class);
		return g.toJson(new UtilDao().calculateBasicPricesProduct(p,true));
	}
	@POST
	@Path("prodbasicprice/endprice")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String productBasicEndPrice( String data){
		Gson g = new Gson();
		ProductBasicPricesCalculation p = g.fromJson(data,ProductBasicPricesCalculation.class);
		return g.toJson(new UtilDao().calculateEndPriceProduct(p));
	}
	@POST
	@Path("prodbasicprice/list")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String productBasicListPrice(String data){
		Gson g = new Gson();
		Product p = g.fromJson(data,Product.class);
		return g.toJson(new UtilDao().calculateProductListPrices(p));
	}
	@POST
	@Path("incrementlist")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public String productListPriceIncrement(String data){
		Gson g = new Gson();
		ProductListIncrementVo p = g.fromJson(data,ProductListIncrementVo.class);
		return g.toJson(new UtilDao().calculateIncrementProductListPrice(p));
	}
}
