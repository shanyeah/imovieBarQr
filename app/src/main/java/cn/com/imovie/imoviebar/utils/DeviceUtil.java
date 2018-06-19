package cn.com.imovie.imoviebar.utils;

import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

/**
 * Created by fwh on 15-8-12.
 */
public class DeviceUtil {

    /**
     *取得imei码
     * @param context
     * @return
     */
    public static String getImei(Context context){
        TelephonyManager telephonyManager =  (TelephonyManager)context.getSystemService( Context.TELEPHONY_SERVICE );
        String imei = telephonyManager.getDeviceId();
        return imei;
    }

    public static String getImsi(Context context){
        TelephonyManager telephonyManager =  (TelephonyManager)context.getSystemService( Context.TELEPHONY_SERVICE );
        String imsi = telephonyManager.getSubscriberId();
        return imsi;
    }

    public static String getAndroidId(Context context){
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }

    public static String getSerialnum(){
        String serialnum = null;
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class, String.class );
            serialnum = (String)(get.invoke(c, "ro.serialno", "unknown" )  );
        }
        catch (Exception ignored){}

        return serialnum;
    }

}
