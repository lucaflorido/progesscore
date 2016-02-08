package it.progess.core.validator;

import it.progess.core.properties.GECOParameter;
import it.progess.core.vo.GECOError;
import it.progess.core.vo.GECOObject;
import it.progess.core.vo.GECOSuccess;



public class CFPIValidator {
	public static GECOObject checkCFPI(String cf, String pi,boolean cfMandatory,boolean piMandatory,boolean disjointMandatory){
		if (disjointMandatory == true){
			if (isEmpty(pi) == true && isEmpty(cf)== true){
				return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Fiscale o Partita Iva mancante");
			}
		}
		//CF MANDATORY
		if (cfMandatory == true){
			if (isEmpty(cf)){
				return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Fiscale mancante");
			}else{
				if (cf.length() != 11){
					if(cf.length() != 16){
						return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Fiscale non conforme");
					}
				}
			}
		}else{
		//CF NO MANDATORY	
			if (isEmpty(cf) == false){
				if (cf.length() != 11){
					if(cf.length() != 16){
						return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Codice Fiscale non conforme");
					}
				}
			}
		}
		//PI MANDATORY
		if (piMandatory == true){
			if (isEmpty(pi)){
				return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Partita Iva mancante");
			}else{
				if (pi.length() != 11){
					return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Partita Iva non conforme");
				}
			}
		}else{
		//PI NO MANDATORY	
			if (isEmpty(pi) == false){
				if (pi.length() != 11){
						return new GECOError(GECOParameter.ERROR_VALUE_MISSING,"Partita Iva non conforme");
					
				}
			}
		}
		return new GECOSuccess();
	}
	public static GECOObject checkCFPI(String cf, String pi,boolean cfMandatory,boolean piMandatory){
		return checkCFPI( cf,pi,cfMandatory, piMandatory,false);
	}
	public static GECOObject checkCFPI(String cf, String pi){
		return checkCFPI(cf,pi,false, false,false);
	}
	public static GECOObject checkCFPI(String cf, String pi,boolean disjointMandatory){
		return checkCFPI(cf,pi,false, false,disjointMandatory);
	}
	private static boolean isEmpty(String value){
		if (value == null || value.equals("")){
			return true;
		}else{
			return false;
		}
	}
}
