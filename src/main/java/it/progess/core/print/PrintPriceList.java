package it.progess.core.print;

import it.progess.core.vo.Company;
import it.progess.core.vo.List;
import it.progess.core.vo.ListProduct;
import it.progess.core.vo.Product;

public class PrintPriceList extends PrintProductList {
	public PrintPriceList(){
		super();
	}
	public String listino = "";
	public String data = "";
	
	public String getListino() {
		return listino;
	}

	public void setListino(String listino) {
		this.listino = listino;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setFromObject(Company comp,Product prod,List list,ListProduct listp){
		super.setFromObject(comp, prod);
		this.prodotto_prezzo = String.valueOf(listp.getPrice());
		this.listino = list.getName();
		this.data = list.getStartdate();
	}
}
