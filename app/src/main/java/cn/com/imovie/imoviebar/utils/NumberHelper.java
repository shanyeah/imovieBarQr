package cn.com.imovie.imoviebar.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;

public class NumberHelper {
	public static int getIntValue(Integer in,int defaultValue){
		return (in == null)?defaultValue:in.intValue();
	}	
	public static int getIntValue(Object in,int defaultValue){
		if(in == null) return defaultValue;
		if(in instanceof BigDecimal){
			return ((BigDecimal)in).intValue();
		}else if(in instanceof Long){
			return ((Long)in).intValue();
		}else if(in instanceof Integer){
			return ((Integer)in).intValue();
		}else if(in instanceof String){
			return (int)StringHelper.parseDouble((String)in, defaultValue);
		}else if(in instanceof Double){
			return ((Double)in).intValue();
		}else if(in instanceof Float){
			return ((Float)in).intValue();
		}else if(in instanceof BigInteger){
			return ((BigInteger)in).intValue();
		}else{
			return defaultValue;
		}
		
		
	}
	public static long getIntValue(Long in,long defaultValue){
		return (in == null)?defaultValue:in.longValue();
	}	
	public static long getLongValue(Object in,long defaultValue){
		if(in == null) return defaultValue;
		if(in instanceof BigDecimal){
			return ((BigDecimal)in).longValue();
		}else if(in instanceof Long){
			return ((Long)in).longValue();
		}else if(in instanceof Integer){
			return ((Integer)in).longValue();
		}else if(in instanceof String){
			return (long)StringHelper.parseDouble((String)in, defaultValue);
		}else if(in instanceof Double){
			return ((Double)in).longValue();
		}else if(in instanceof Float){
			return ((Float)in).longValue();
		}else if(in instanceof BigInteger){
			return ((BigInteger)in).longValue();
		}else{
			return defaultValue;
		}		
	}
	
	public static double getDoubleValue(Object in,double defaultValue){
		if(in == null) return defaultValue;
		if(in instanceof BigDecimal){
			return ((BigDecimal)in).doubleValue();
		}else if(in instanceof Long){
			return ((Long)in).doubleValue();
		}else if(in instanceof Integer){
			return ((Integer)in).doubleValue();
		}else if(in instanceof String){
			return (double)StringHelper.parseDouble((String)in, defaultValue);
		}else if(in instanceof Double){
			return ((Double)in).doubleValue();
		}else if(in instanceof Float){
			return ((Float)in).doubleValue();
		}else if(in instanceof BigInteger){
			return ((BigInteger)in).doubleValue();
		}else{
			return defaultValue;
		}		
	}
	
	public static Float getFloatValue(Object in,float defaultValue){
		if(in == null) return defaultValue;
		if(in instanceof BigDecimal){
			return ((BigDecimal)in).floatValue();
		}else if(in instanceof Long){
			return ((Long)in).floatValue();
		}else if(in instanceof Integer){
			return ((Integer)in).floatValue();
		}else if(in instanceof String){
			return (float)StringHelper.parseFloat((String)in, defaultValue);
		}else if(in instanceof Double){
			return ((Double)in).floatValue();
		}else if(in instanceof Float){
			return ((Float)in).floatValue();
		}else if(in instanceof BigInteger){
			return ((BigInteger)in).floatValue();
		}else{
			return defaultValue;
		}		
	}
	/**
	 * 锟斤拷式锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param num
	 * @param format
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String
	 * 2010-7-5锟斤拷锟斤拷02:43:24
	 */
	public static String getFormatNumber(Object num,String format){
		if(num==null)return format;
		DecimalFormat df=(DecimalFormat) DecimalFormat.getInstance();
		df.applyPattern(format);
		return df.format(num);
	}
	/**
	 * 锟斤拷式锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param num
	 * @param format
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String
	 * 2010-7-5锟斤拷锟斤拷02:43:24
	 */
	public static String getFormatNumber(Object num,String format,String defaultValue){
		if(num==null)return defaultValue;
		return getFormatNumber(num,format);
	}
	/**
	 * 默锟较憋拷锟斤拷锟斤拷位小锟斤拷
	 * @param num
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String
	 * 2010-7-5锟斤拷锟斤拷02:43:41
	 */
	public static String getDefFormatNumber(Object num){
		if(num==null)return "0.00";
		return getFormatNumber(num,"0.00");
	}
}
