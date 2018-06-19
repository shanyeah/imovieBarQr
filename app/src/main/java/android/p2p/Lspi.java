package android.p2p;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.activity.PlayerActivity;


public class Lspi {
	public static String TAG="Lspi";

	/**
	 * 1.锟斤拷始锟斤拷锟斤拷,锟斤拷锟较讹拷,(path,锟斤拷锟斤拷) ,path 锟斤拷锟斤拷锟斤拷url锟斤拷锟竭憋拷锟斤拷路锟斤拷
	 * 锟斤拷锟斤拷:0 锟缴癸拷,-2busy,锟斤拷锟斤拷失锟斤拷
	 * @param url
	 * @param playtime
	 * @return
	 */
	public static int vod_open(String url, int playPosition) {		
		Log.d(TAG, "call java vod_open("+url+", playPosition="+playPosition+")");		
		Message msg = Message.obtain();
		Bundle data=new Bundle();
		data.putString("url", url);
		data.putInt("playPosition", playPosition);
		msg.setData(data);
		msg.what = PlayerActivity.HTTP_REQUEST_VOD_OPEN;
		return MyApplication.getInstance().processPlayer(msg);
	} 
	  
	public static int vod_close(){
		Log.d(TAG, "call java vod_close()"); 
		Bundle data=new Bundle();
		Message msg=Message.obtain();
		msg.what=PlayerActivity.HTTP_REQUEST_VOD_CLOSE;
		msg.setData(data);
		return MyApplication.getInstance().processPlayer(msg);
		
	}
	
	public static int vod_3d_open(String path, int playPosition){
		return -1; //锟捷诧拷支锟斤拷
	}

	public static int vod_pause(){
		Log.d(TAG, "call java vod_pause()");
		Bundle data=new Bundle();
		Message msg=Message.obtain();
		msg.what=PlayerActivity.HTTP_REQUEST_VOD_PAUSE;
		msg.setData(data);		
		return MyApplication.getInstance().processPlayer(msg);
	}

	public static int vod_start(){
		Log.d(TAG, "call java vod_start()");
		Bundle data=new Bundle();
		Message msg=Message.obtain();
		msg.what=PlayerActivity.HTTP_REQUEST_VOD_START;
		msg.setData(data);		
		return MyApplication.getInstance().processPlayer(msg);
	}
	//5.锟斤拷取锟斤拷锟斤拷状态,锟斤拷锟街凤拷锟斤拷锟斤拷式锟斤拷锟斤拷
	public static String vod_state(){
		Log.d(TAG, "call java vod_state()");
		return MyApplication.getInstance().vod_state();
	}
	//6.锟斤拷取锟芥本锟斤拷
	public static String get_version(){
		return MyApplication.getInstance().versionName;
	}
	
	//*********

	public native static int init(String confpath, int port, int maxMemory);
	public native static int fini();
	
	public native static String search_server(String name, int port);
	public native static String get_sn();
	public native static String get_sign_key();	
	/*
		<?xml version="1.0" encoding="gbk" ?>
		<status>
			<hash>1</hash>
			<speed>1</speed>
			<file_size>1</file_size>
		</status>
	 */
	public native static String get_status(String url);	
	
	static{
    	System.loadLibrary("lspclient_jni");
    }
}
