package cn.com.imovie.imoviebar.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * 锟斤拷取锟斤拷锟斤拷锟斤拷锟斤拷锟较�
 * @author 锟斤拷锟斤拷锟斤拷
 * 2011-12-14锟斤拷锟斤拷1:53:11
 */
public class NetWorkTypeUtils {
	
	/**
	 * 锟斤拷取锟斤拷效锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param context
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return NetworkInfo
	 * 2011-12-14锟斤拷锟斤拷2:01:37
	 */
	public static NetworkInfo getAvailableNetWorkInfo(Context context){
		ConnectivityManager manager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo=manager.getActiveNetworkInfo();
		if(networkInfo!=null && networkInfo.isAvailable()){
			return networkInfo;
		}
		return null;
	}
	
	/**
	 * 锟介看锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷
	 * @param context
	 * @return 0-2G/3G锟斤拷锟斤拷锟斤拷锟斤拷 1-wifi锟斤拷锟斤拷
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String
	 * 2011-12-14锟斤拷锟斤拷2:08:36
	 */
	public static String getNetWorkType(Context context){
		String type="NONE";
		if(context==null)return type;
		NetworkInfo networkInfo=getAvailableNetWorkInfo(context);
		if(networkInfo==null)return type;
		if(networkInfo.getType()==ConnectivityManager.TYPE_WIFI){
			return ConnectivityManager.TYPE_WIFI+"";
		}else if(networkInfo.getType()==ConnectivityManager.TYPE_MOBILE){
			return ConnectivityManager.TYPE_MOBILE+"";
		}
		return type;
	}
	
	/**
	 * 锟叫讹拷锟角凤拷锟斤拷3G锟斤拷锟斤拷
	 * @param context
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return boolean
	 * 2011-12-14锟斤拷锟斤拷3:11:49
	 */
	public static boolean isThirdGeneration(Context context) {
		TelephonyManager telManager=(TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		int type=telManager.getNetworkType();
		if(type==TelephonyManager.NETWORK_TYPE_UMTS)return true;
		return false;
	}
}
