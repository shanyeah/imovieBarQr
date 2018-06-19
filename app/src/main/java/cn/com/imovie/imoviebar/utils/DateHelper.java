package cn.com.imovie.imoviebar.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>Title:时锟斤拷锟斤拷锟节革拷锟斤拷锟斤拷</p>
 * <p>Description: 锟斤拷锟竭帮拷</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: 21cn</p> 
 * @author topilee topilee@gmail.com
 * @version 2.0
 */
public class DateHelper {
	private static final String TAG = "DateHelper";
	/**
	 * 默锟较碉拷锟斤拷锟斤拷锟斤拷式锟斤拷yyyy-MM-dd
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	public static final int YEAR = 1;
	public static final int MONTH = 2;
	public static final int DAY = 3;
	public static final int HOUR = 4;
	public static final int MINUTE = 5;
	public static final int SECOND = 6;
	public DateHelper(){
	}
	/**
	 * 锟叫讹拷锟角凤拷为锟斤拷确锟斤拷时锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷
	 * @param in String : 锟斤拷锟叫断碉拷锟街凤拷锟斤拷锟斤拷锟界：1980-01-01 10:10:10
	 * @param format String : 时锟斤拷锟斤拷锟节革拷式锟斤拷锟界：yyyy-MM-dd HH:mm:ss
	 * @return boolean : 锟斤拷锟斤拷锟饺凤拷蚍祷锟絫rue锟斤拷锟斤拷锟津返伙拷false
	 */
	public static boolean isDateFormat(final String in,final String format){
		if(in == null) return false;
		try{
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Date date = new Date(formatter.parse(in).getTime());
			//锟角凤拷时锟斤拷锟绞�
			if(toString(date, format).equals(in) == false) return false;
			return true;
		}catch(ParseException pe){
			VV8Utils.printLog(TAG,"isDateFormat("+in+","+format+"):ParseException-->" + pe.getMessage());
			return false;
		}		
	}
	/**
	 * 锟叫讹拷锟角凤拷为锟斤拷确锟斤拷时锟斤拷锟斤拷锟斤拷锟街凤拷锟斤拷
	 * @param in String : 锟斤拷锟叫断碉拷锟街凤拷锟斤拷锟斤拷锟界：1980-01-01锟斤拷锟斤拷锟斤拷yyyy-MM-dd锟斤拷式锟斤拷
	 * @return boolean : 锟斤拷锟斤拷锟饺凤拷蚍祷锟絫rue锟斤拷锟斤拷锟津返伙拷false
	 */	
	public static boolean isDateFormat(final String in){
		return isDateFormat(in,DEFAULT_DATE_FORMAT);
	}	
	/**
	 * 锟斤拷锟街凤拷锟斤拷转锟斤拷为java.sql.Date
	 * @param in String : 锟斤拷转锟斤拷锟斤拷锟街凤拷锟斤拷锟斤拷锟界：1980-01-01 10:10:10
	 * @param format String : 锟斤拷锟节革拷式锟斤拷锟界：yyyy-MM-dd HH:mm:ss
	 * @return Date : 转锟斤拷锟斤拷锟斤拷锟斤拷冢锟斤拷锟斤拷址锟斤拷锟斤拷锟绞撅拷锟斤拷欠欠锟绞憋拷锟斤拷式锟斤拷锟津返伙拷null
	 */
	public static Date toDate(final String in, final String format){
		if(isDateFormat(in,format) == false) return null;
		if(in == null) return null;
		try{
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.parse(in);
		}catch(ParseException pe){
			VV8Utils.printLog(TAG,"toDate("+in+","+format+"):ParseException-->" + pe.getMessage());
			return null;
		}
	}
	/**
	 * 锟斤拷Timestamp转锟斤拷为Date
	 * @param in java.sql.Timestamp : 时锟斤拷
	 * @return java.util.Date :
	 */
	public static Date fromTimestamp(final Timestamp in){
		if(in == null) return null;
		return new Date(in.getTime());
	}
	/**
	 * 锟斤拷Date锟斤拷式锟斤拷锟斤拷锟�
	 * @param in Date : 要转锟斤拷锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param format : 锟斤拷式锟斤拷锟斤拷锟斤拷锟絡ava.text.SimpleDateFormat锟侥革拷式
	 * @return String : 转锟斤拷锟斤拷锟斤拷址锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 */
	public static String toString(final Date in, final String format){
		if(in == null) return null;
		try{
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			return formatter.format(in);
		}catch(Exception e){
			SimpleDateFormat formatter = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
			return formatter.format(in);
		}
	}
	/**
	 * 取锟斤拷某锟斤拷某锟铰碉拷锟斤拷锟斤拷
	 * @param year int : 锟斤拷荩锟斤拷锟斤拷锟�0锟斤拷
	 * @param month int : 锟铰凤拷
	 * @return int : 锟斤拷锟斤拷锟斤拷锟斤拷应锟斤拷锟斤拷锟斤拷
	 */
	public static int getDayCounts(final int year, final int month){
		switch(month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				return 31;
			case 2:
				if(isLeapYear(year)){
					return 29;
				}else{
					return 28;
				}
			case 4:
			case 6:
			case 9:
			case 11:
				return 30;
			default:
				return 0;
		}
	}
	/**
	 * 锟角凤拷为锟斤拷锟斤拷锟斤拷
	 * @param year int : 锟斤拷锟�
	 * @param month int : 锟铰凤拷
	 * @param day int : 锟斤拷
	 * @return boolean : 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷旆碉拷锟絫rue,锟斤拷锟津返伙拷false
	 */
	public static boolean isSunday(final int year,final int month, final int day){
		return (getDayofWeek(year,month,day) == 1);
	}
	/**
	 * 锟角凤拷为锟斤拷锟斤拷锟斤拷
	 * @param year int : 锟斤拷锟�
	 * @param month int : 锟铰凤拷
	 * @param day int : 锟斤拷
	 * @return boolean : 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟絫rue,锟斤拷锟津返伙拷false
	 */
	public static boolean isSaturday(final int year, final int month, final int day){
		return (getDayofWeek(year,month,day) == 7);
	}
	/**
	 * 锟角凤拷为锟斤拷末(锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷)
	 * @param year int : 锟斤拷锟�
	 * @param month int : 锟铰凤拷
	 * @param day int : 锟斤拷
	 * @return boolean : 锟斤拷锟斤拷锟斤拷锟侥╋拷蚍祷锟絫rue,锟斤拷锟津返伙拷false
	 */
	public static boolean isWeekend(final int year, final int month, final int day){
		return (isSaturday(year,month,day) || isSunday(year,month,day));
	}
	private static int getDayofWeek(int year,int month,int day){
		Calendar c = Calendar.getInstance();
		c.set(year,month-1,day);
		return c.get(Calendar.DAY_OF_WEEK);		
	}
	
	/**
	 * 锟矫碉拷锟斤拷锟斤拷锟斤拷一锟杰碉拷锟斤拷锟斤拷
	 * @param date Date : 锟斤拷锟斤拷锟斤拷锟斤拷
	 * @return int : 锟杰硷拷
	 */
	public static int getDayofWeek(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK)-1;
	}
	
	/**
	 * 锟矫碉拷锟斤拷锟节硷拷(锟斤拷锟斤拷)
	 * @param date Date : 锟斤拷锟斤拷锟斤拷锟斤拷
	 * @return int : 锟杰硷拷
	 */
	public static String getDate(Date date){
		int dayofWeek = getDayofWeek(date);
		switch(dayofWeek) {
		case 1:
			return "一";
		case 2:
			return "锟斤拷";
		case 3:
			return "锟斤拷";
		case 4:
			return "锟斤拷";
		case 5:
			return "锟斤拷";
		case 6:
			return "锟斤拷";
		case 0:
			return "锟斤拷";
			
		default:
			return null;
		}
	}
	
	/**
	 * 锟叫讹拷某锟斤拷锟斤拷欠锟轿拷锟斤拷锟�
	 * @param year int : 锟斤拷荩锟斤拷锟斤拷锟�0锟斤拷
	 * @return boolean : 锟斤拷锟斤拷欠锟斤拷锟絫rue锟斤拷锟斤拷锟津返伙拷false
	 */
	public static boolean isLeapYear(final int year){
		if(year <= 0) return false;
		if(year % 4 != 0) return false;
		if(year % 100 == 0 && year % 400 != 0) return false;
		return true;
	}
	/**
	 * 锟矫碉拷锟斤拷前时锟斤拷锟斤拷锟斤拷
	 * @return int : 锟斤拷前锟斤拷锟�
	 */
	public static int getNowYear(){
		Date date = new Date(getSystemTime());
		return get(date,YEAR);	
	}
	/**
	 * 锟矫碉拷锟斤拷前时锟斤拷锟斤拷路锟�
	 * @return int : 锟斤拷前锟铰份ｏ拷[1,12]
	 */
	public static int getNowMonth(){
		Date date = new Date(getSystemTime());
		return get(date,MONTH);	
	}
	/**
	 * 锟矫碉拷锟斤拷前时锟斤拷锟斤拷锟斤拷锟�
	 * @return int : 锟斤拷前锟斤拷锟节ｏ拷[1,31]
	 */	
	public static int getNowDay(){
		Date date = new Date(getSystemTime());
		return get(date,DAY);	
	}
	/**
	 * 锟矫碉拷锟斤拷前时锟斤拷锟叫∈�
	 * @return int : 锟斤拷前小时锟斤拷[0,23]
	 */	
	public static int getNowHour(){
		Date date = new Date(getSystemTime());
		return get(date,HOUR);
	}
	/**
	 * 锟矫碉拷锟斤拷前时锟斤拷姆锟斤拷锟斤拷锟�
	 * @return int : 锟斤拷前锟斤拷锟接ｏ拷[0,59]
	 */
	public static int getNowMinute(){
		Date date = new Date(getSystemTime());
		return get(date,MINUTE);
	}
	/**
	 * 锟矫碉拷锟斤拷前时锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 * @return int : 锟斤拷前锟斤拷锟街ｏ拷[0,59]
	 */
	public static int getNowSecond(){
		Date date = new Date(getSystemTime());
		return get(date,SECOND);
	}
	/**
	 * 锟斤拷时锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param date Date : 锟斤拷要锟斤拷锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷
	 * @param field int : 锟斤拷要锟斤拷锟斤拷锟斤拷锟筋，
	 * @param amount int : 锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷为锟斤拷锟斤拷锟斤拷
	 * @return Date : 锟斤拷锟斤拷锟斤拷锟绞憋拷锟�
	 * @see net.jemboo.helper.DateHelper 锟斤拷锟斤拷锟斤拷
	 */
	public static Date add(final Date date, final int field, final int amount){
		if(date == null) return null;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		switch(field) {
			case YEAR:
				c.add(Calendar.YEAR, amount);
				break;
			case MONTH: //1 - 12
				c.add(Calendar.MONTH, amount);
				break;
			case DAY:
				c.add(Calendar.DATE, amount);
				break;
			case HOUR:
				c.add(Calendar.HOUR, amount);
				break;
			case MINUTE:
				c.add(Calendar.MINUTE, amount);
				break;
			case SECOND:
				c.add(Calendar.SECOND, amount);
				break;
			default:
				return null;
		}
		return new Date(c.getTimeInMillis());
	}
	/**
	 * 取锟斤拷某锟斤拷锟节革拷锟斤拷位锟矫碉拷值
	 * @param date Date : 锟斤拷锟斤拷
	 * @param field int : 位锟矫ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟侥撅拷态锟斤拷锟斤拷
	 * @return int : 锟斤拷应位锟矫碉拷值
	 */
	public static int get(final Date date, final int field){
		if(date == null){ return 0; }
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(date.getTime());
		switch(field) {
			case YEAR:
				return c.get(Calendar.YEAR);
			case MONTH: //1 - 12
				return c.get(Calendar.MONTH) + 1;
			case DAY:
				return c.get(Calendar.DAY_OF_MONTH);
			case HOUR:
				return c.get(Calendar.HOUR_OF_DAY);
			case MINUTE:
				return c.get(Calendar.MINUTE);
			case SECOND:
				return c.get(Calendar.SECOND);
			default:
				return 0;
		}
	}	
	public static Timestamp toTimestamp(Date in){
		if(in == null) return null;
		return new Timestamp(in.getTime());		
	}
	public static Date toDate(Timestamp in){
		if(in == null) return null;
		return new Date(in.getTime());		
	}
	/**
	 * 锟矫碉拷指锟斤拷锟疥，锟铰ｏ拷锟秸ｏ拷时锟斤拷锟街ｏ拷锟斤拷锟绞憋拷锟侥猴拷锟斤拷锟斤拷
	 * @param year int : 锟斤拷[0..)
	 * @param month int : 锟斤拷[0..11]
	 * @param day int : 锟斤拷[1..31]
	 * @param hour int : 时[0..23]
	 * @param min int : 锟斤拷[0..59]
	 * @param second int : 锟斤拷[0..59]
	 * @return long : 锟斤拷锟斤拷锟斤拷
	 */
	public static long getTime(final int year, final int month,final int day,final int hour,final int min,final int second){
		Calendar c = Calendar.getInstance();
		c.set(year, month, day, hour, min, second);
		return c.getTimeInMillis();
	}
	/**
	 * 取锟矫碉拷前系统时锟戒（WEB锟斤拷锟斤拷锟斤拷锟斤拷
	 * @return long : 系统锟斤拷前时锟斤拷暮锟斤拷锟斤拷锟�
	 */
	public static long getSystemTime(){
		return System.currentTimeMillis();
	}
	/**
	 * 取锟矫碉拷前系统时锟戒（WEB锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param format String : 时锟斤拷锟斤拷示锟侥革拷式
	 * @return String : 锟斤拷锟街凤拷锟斤拷锟斤拷示锟斤拷时锟戒串
	 */
	public static String getSystemTime(final String format){
		return toString(new Date(getSystemTime()), format);
	}
	/**
	 * 锟矫碉拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
	 * @return Date : 锟斤拷锟斤拷
	 */
	public static Date getToday(){
		return DateHelper.toDate(DateHelper.getSystemTime(DEFAULT_DATE_FORMAT), DEFAULT_DATE_FORMAT);
	}
	/**
	 * 锟叫讹拷锟角凤拷锟节碉拷前锟斤拷锟斤拷之前
	 * @param date String : 锟斤拷式锟斤拷yyyy-MM-dd
	 * @return boolean : 
	 */
	public static boolean isBeforeToday(final String date){
		Date d = DateHelper.toDate(date, DEFAULT_DATE_FORMAT);
		if(d == null) return false;
		return d.before(getToday());
	}
	/**
	 * 锟叫讹拷锟角凤拷锟节碉拷前锟斤拷锟斤拷之锟斤拷
	 * @param date String : 锟斤拷式锟斤拷yyyy-MM-dd
	 * @return boolean : 
	 */
	public static boolean isAfterToday(final String date){
		Date d = DateHelper.toDate(date, DEFAULT_DATE_FORMAT);
		if(d == null) return false;
		return d.after(getToday());
	}	
	/**
	 * 锟叫讹拷锟角凤拷为锟斤拷锟斤拷
	 * @param date String : 锟斤拷式锟斤拷yyyy-MM-dd
	 * @return boolean : 
	 */
	public static boolean isToday(final String date){
		Date d = DateHelper.toDate(date, DEFAULT_DATE_FORMAT);
		if(d == null) return false;
		return (d.getTime() == getToday().getTime());
	}
	public static void main(String[] args){
//		System.out.println(DateHelper.isDateFormat("2004-12-20 17:39:05","yyyy-MM-dd HH:mm:ss"));
//		System.out.println(DateHelper.toString(DateHelper.toDate("2004-12-20 17:39:05", "yyyy-MM-dd HH:mm:ss"),
//				"yyyy-MM-dd HH:mm"));
		//System.out.println(DateHelper.isSaturday(2005,12,3));
//		System.out.println(DateHelper.toDate("2006-7-22", "yyyy-M-d"));
//		System.out.println(DateHelper.getSystemTime("yyMMdd HH时mm锟斤拷ss锟斤拷"));
		
		String dd = "2007-10-10";
		Date d = toDate(dd,"yyyy-MM-dd");
		System.out.println("--"+(getDayofWeek(d)));
	}
}