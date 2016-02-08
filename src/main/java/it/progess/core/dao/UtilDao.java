package it.progess.core.dao;

import java.util.Iterator;

import it.progess.core.hibernate.HibernateUtils;
import it.progess.core.properties.GECOParameter;
import it.progess.core.vo.GECOError;
import it.progess.core.vo.GECOObject;
import it.progess.core.vo.GECOSuccess;
import it.progess.core.vo.ListProduct;
import it.progess.core.vo.Product;
import it.progess.core.vo.helpobject.ProductBasicPricesCalculation;
import it.progess.core.vo.helpobject.ProductListIncrementVo;
import it.progess.core.vo.helpobject.ProductListPricesCalculation;

public class UtilDao {
	public GECOObject calculateBasicPricesProduct(ProductBasicPricesCalculation basic,boolean isSellPrice){
		try{
			if (isSellPrice == false){
				basic.calculatePercentage();
			}else{
				basic.calculateSellPrice();
			}
		}catch(Exception e){
			new GECOError(GECOParameter.ERROR_CALCULATION, e.getMessage());
		}
		return new GECOSuccess(basic);
	}
	public GECOObject calculateEndPriceProduct(ProductBasicPricesCalculation basic){
		try{
			basic.calculateFromTotalPrice();
		}catch(Exception e){
			new GECOError(GECOParameter.ERROR_CALCULATION, e.getMessage());
		}

		return new GECOSuccess(basic);
	}
	public GECOObject calculateProductListPrices(Product p){
		float tr =(float) p.getTaxrate().getValue();
		for (Iterator<ListProduct> it = p.getListproduct().iterator();it.hasNext();){
			ListProduct lp = it.next();
			lp.getProduct().setPurchaseprice(p.getPurchaseprice());
			lp.calculatePrices(tr);
		}
		double increment = HibernateUtils.calculatePercentage(p.getPurchaseprice(),p.getPercentage());
		p.setSellprice((float)increment+(float)p.getPurchaseprice());
		return new GECOSuccess(p);
	}
	public GECOObject calculateIncrementProductListPrice(ProductListIncrementVo plivo){
		for (Iterator<ListProduct> it = plivo.getListproducts().iterator();it.hasNext();){
			ListProduct lp = it.next();
			new ProductListPricesCalculation().calculateIncrement(plivo.isEndPrice(),plivo.isPercentage(), plivo.getIncrement(), lp);
		}
			
		return new GECOSuccess(plivo.getListproducts());
	}
}
