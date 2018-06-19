package cn.com.imovie.imoviebar.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 一些锟斤拷锟矫凤拷锟斤拷锟斤拷锟斤拷
 * 
 * @author 锟斤拷锟斤拷锟斤拷 2012-1-4锟斤拷锟斤拷11:02:00
 */
public class VV8Utils {
	private static boolean DEBUG = true;

	public static void printLog(String s, String s1) {
		if (DEBUG)
			Log.d(s, s1);
	}

	/**
	 * MD5 锟斤拷锟杰诧拷锟斤拷
	 * 
	 * @param str
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String 2012-1-4锟斤拷锟斤拷11:02:36
	 */
	public static String MD5Helper(String str) {
		MessageDigest messageDigest = null;
		StringBuffer md5StrBuff = new StringBuffer();
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
			byte[] byteArray = messageDigest.digest();
			for (int i = 0; i < byteArray.length; i++) {
				if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
					md5StrBuff.append("0").append(
							Integer.toHexString(0xFF & byteArray[i]));
				else
					md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return md5StrBuff.toString();
	}

	/**
	 * 锟斤拷锟斤拷锟狡讹拷锟借备锟斤拷锟斤拷锟�
	 * 
	 * @param context
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String 2012-1-4锟斤拷锟斤拷2:21:39
	 */
	public static String getIEMI(Context context) {
		if (context == null)
			return null;
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}

	/**
	 * 取Android OS锟芥本锟斤拷息
	 * 
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String 2012-1-4锟斤拷锟斤拷2:22:01
	 */
	public static String getOSVersionInfo() {
		String sdk = String.valueOf("Android" + Build.VERSION.SDK);
		StringBuilder sb = new StringBuilder(sdk).append("-");
		sb.append(Build.MANUFACTURER).append("-");
		sb.append(Build.PRODUCT);
		return sb.toString();

	}

	/**
	 * 锟斤拷取锟酵伙拷锟剿版本versionName锟斤拷息
	 * 
	 * @param context
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String 2012-1-4锟斤拷锟斤拷2:23:14
	 */
	public static String getClientVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			String packageName = context.getPackageName();
			return packageManager.getPackageInfo(packageName, 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();

		}
		return null;
	}

	/**
	 * 锟斤拷取锟酵伙拷锟剿版本versionCode锟斤拷息
	 * 
	 * @param context
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return int 2012-1-4锟斤拷锟斤拷2:23:57
	 */
	public static int getClientVersionCode(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			String packageName = context.getPackageName();
			return packageManager.getPackageInfo(packageName, 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();

		}
		return 0;
	}

	/**
	 * 锟斤拷取锟斤拷锟斤拷锟狡讹拷锟矫伙拷识锟斤拷锟斤拷
	 * 
	 * @param context
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String 2012-1-4锟斤拷锟斤拷2:24:24
	 */
	public static String getIMSI(Context context) {
		return ((TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
	}

	/**
	 * 锟斤拷取锟街伙拷锟酵猴拷锟斤拷息
	 * 
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String 2012-1-4锟斤拷锟斤拷2:24:50
	 */
	public static String getMobileModel() {
		return Build.MODEL;
	}

	/**
	 * 锟斤拷取锟街伙拷锟街憋拷锟斤拷
	 * 
	 * @param context
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String 2012-1-4锟斤拷锟斤拷2:25:23
	 */
	public static String getMobileResolution(Context context) {
		DisplayMetrics displayMetrics = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(displayMetrics);
		StringBuilder sb = new StringBuilder(
				String.valueOf(displayMetrics.widthPixels));
		sb.append("x").append(String.valueOf(displayMetrics.heightPixels));
		return sb.toString();
	}

	/**
	 * 锟斤拷取锟街伙拷锟斤拷锟斤拷 锟斤拷锟斤拷锟狡讹拷锟斤拷营锟斤拷原锟津，诧拷锟斤拷锟街伙拷锟斤拷取锟斤拷锟斤拷
	 * 
	 * @param context
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String 2012-1-4锟斤拷锟斤拷2:26:10
	 */
	public static String getTelNumber(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String tel = tm.getLine1Number();
		if (tel == null || "".equals(tel))
			tel = "13000000000";
		return tel;
	}

	/**
	 * dip转px
	 * 
	 * @param context
	 * @param dip
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return int 2012-1-4锟斤拷锟斤拷2:28:45
	 */
	public static int dipToPx(Context context, int dip) {
		float f = context.getResources().getDisplayMetrics().density;
		return (int) (dip * f + 0.5F);
	}
	
	public static boolean getExternalMount(){
		String state=Environment.getExternalStorageState();
		if(Environment.MEDIA_MOUNTED.equalsIgnoreCase(state))return true;
		return false;
	}

}
