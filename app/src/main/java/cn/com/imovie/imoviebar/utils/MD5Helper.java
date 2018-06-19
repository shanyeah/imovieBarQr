package cn.com.imovie.imoviebar.utils;

import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * MD5锟斤拷锟斤拷锟姐法专锟矫癸拷锟斤拷锟斤拷
 * @author 锟斤拷锟斤拷锟斤拷
 * 2012-2-3锟斤拷锟斤拷10:59:28
 */
public class MD5Helper {
	private static final String TAG = "MD5Helper";
	/**
	 * 锟斤拷MD5锟姐法锟斤拷锟斤拷
	 * @param in String : 锟斤拷锟斤拷锟杰碉拷原锟斤拷
	 * @return String : 锟斤拷锟杰猴拷锟斤拷锟斤拷模锟斤拷锟斤拷原锟斤拷为锟秸ｏ拷锟津返伙拷null;
	 */
	public static String encode(final String in){
		return encode(in, "");
	}
	/**
	 * 锟斤拷MD5锟姐法锟斤拷锟斤拷
	 * @param in String : 锟斤拷锟斤拷锟杰碉拷原锟斤拷
	 * @param charset String : 锟斤拷锟斤拷锟姐法锟街凤拷锟斤拷
	 * @return String : 锟斤拷锟杰猴拷锟斤拷锟斤拷模锟斤拷锟斤拷锟斤拷锟斤拷原锟斤拷为null锟斤拷锟津返伙拷null
	 */
	public static String encode(final String in, final String charset){
		if(in == null) return null;
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			if(StringHelper.isEmpty(charset)){
				md.update(in.getBytes());
			}else{
				try{
					md.update(in.getBytes(charset));
				}catch(Exception e){
					md.update(in.getBytes());
				}
			}
			byte[] digesta = md.digest();
			return StringHelper.bytes2Hex(digesta);
		}catch(java.security.NoSuchAlgorithmException ex){
			//锟斤拷锟斤拷
			Log.e(TAG, "encode("+in+","+charset+"):NoSuchAlgorithmException -->"+ex.getMessage());
			return null;
		}
	}
	public static byte[] md5(byte[] bytes){
		try{
			MessageDigest digest = MessageDigest.getInstance("MD5");
			digest.update(bytes);
			byte[] ret = digest.digest();
			return ret;
		} catch (NoSuchAlgorithmException ex){
			Log.e(TAG, "encode(byte[]):NoSuchAlgorithmException -->"+ex.getMessage());
			return null;
		}		
	}
}