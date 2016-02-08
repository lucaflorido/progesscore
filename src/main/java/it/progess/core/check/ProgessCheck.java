package it.progess.core.check;

import it.progess.core.properties.GECOParameter;
import it.progess.core.vo.GECOObject;


public class ProgessCheck {
	public static boolean basicCheck(GECOObject obj){
		if (obj.type.equals(GECOParameter.SUCCESS_TYPE)){
			return true;
		}
		return false;
	}
}
