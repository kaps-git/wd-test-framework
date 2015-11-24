package com.korg.common.util;

import org.apache.log4j.Logger;

/**
 * 
 * @author akapil
 *
 */
public class DataUtil {

	private static final Logger logger=Logger.getLogger(DataUtil.class.getName());
	
	public static boolean isInteger(Object object){
        Boolean isInt=false;
        if(object instanceof  Double){
            Double dObject=(Double)object;
            if ((dObject == Math.floor(dObject)) && !Double.isInfinite(dObject)) {
                isInt=true;
            }
        }
        return isInt;
    }//end method
	

    public static Double getDoubleValue(Object object){
        Double doubleValue=null;
        Number number=(Number)object;
        doubleValue=number.doubleValue();
        return doubleValue;
    }//end method
}
