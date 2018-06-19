package cn.com.imovie.imoviebar.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * DES锟斤拷锟斤拷锟斤拷锟斤拷锟�?
 * 
 * @author 锟斤拷锟斤拷锟斤拷 2012-3-14锟斤拷锟斤拷1:43:58
 */
public class DES {
	public static final String DES_KEY="G25AG1X7";
	private static final String TAG ="DES";
	private static final String ALGORITHM = "DES";
	/**
	 * 使锟斤拷DES锟姐法锟斤拷锟斤拷
	 * @param in String : 锟斤拷锟斤拷锟杰碉拷锟街凤拷锟斤拷
	 * @param key javax.crypto.SecretKey : 锟杰筹拷
	 * @return String : 锟斤拷锟杰猴拷锟斤拷锟斤拷模锟斤拷锟斤拷锟斤拷芄锟斤拷坛锟斤拷锟斤拷锟斤拷锟絥ull
	 */
	public static String encode(final String in, final SecretKey key){
		if(in == null || key == null) return null; 			
		try{
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] bytes = cipher.doFinal(in.getBytes());
			return StringHelper.bytes2Hex(bytes);
		}catch(java.security.NoSuchAlgorithmException nsae){
			VV8Utils.printLog(TAG,"encode("+in+","+new String(key.getEncoded())+"):NoSuchAlgorithmException -->"+nsae.getMessage());
			return null;
		}catch(javax.crypto.NoSuchPaddingException nspe){
			VV8Utils.printLog(TAG,"encode("+in+","+new String(key.getEncoded())+"):NoSuchPaddingException -->"+nspe.getMessage());
			return null;
		}catch(java.lang.Exception e){
			VV8Utils.printLog(TAG,"encode("+in+","+new String(key.getEncoded())+"):Exception -->"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 使锟斤拷默锟较碉拷锟斤拷钥锟斤拷锟斤拷
	 * @param in
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String
	 * 2012-3-15锟斤拷锟斤拷1:32:30
	 */
	public static String encode(final String in){
		SecretKey key = DES.createSecretKey(DES_KEY);
		return encode(in,key);
	}
	/**
	 * 锟斤拷锟斤拷
	 * @param in String : 锟斤拷锟斤拷锟杰碉拷锟斤拷锟斤拷
	 * @param key javax.crypto.SecretKey : 锟杰匙ｏ拷锟斤拷锟斤拷锟绞憋拷锟斤拷艹锟接σ伙拷锟�?
	 * @return String : 锟斤拷锟杰猴拷锟斤拷锟斤拷模锟斤拷锟斤拷锟斤拷锟杰筹拷锟斤拷锟斤拷null
	 */
	public static String decode(final String in, final SecretKey key){
		if(in == null || key == null){ return null; }
		try{
			Cipher cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] bytes = cipher.doFinal(StringHelper.hex2Bytes(in));
			String decode=new String(new String(bytes,"GBK"));
			return decode.replaceAll("", "");
		}catch(java.security.NoSuchAlgorithmException nsae){
			VV8Utils.printLog(TAG,"decode("+in+","+new String(key.getEncoded())+"):NoSuchAlgorithmException -->"+nsae.getMessage());
			return null;
		}catch(javax.crypto.NoSuchPaddingException nspe){
			VV8Utils.printLog(TAG,"decode("+in+","+new String(key.getEncoded())+"):NoSuchPaddingException -->"+nspe.getMessage());
			return null;
		}catch(java.lang.Exception e){
			VV8Utils.printLog(TAG,"decode("+in+","+new String(key.getEncoded())+"):Exception -->"+e.getMessage());
			return null;
		}
	}
	
	/**
	 * 使锟斤拷默锟较碉拷锟斤拷钥锟斤拷锟斤拷
	 * @param in
	 * @return
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return String
	 * 2012-3-15锟斤拷锟斤拷1:31:27
	 */
	public static String decode(final String in){
		SecretKey key = createSecretKey(DES_KEY);
		return decode(in,key);
	}
	private static SecretKey createSecretKey(final byte[] bytes){
		try{	
			return new SecretKeySpec(bytes, "DES");
		}catch(Exception e){
			VV8Utils.printLog(TAG,"createSecretKey("+new String(bytes)+"):Exception -->"+e.getMessage());
			return null;
		}
	}
	/**
	 * 锟斤拷锟斤拷锟街凤拷锟斤拷锟斤拷锟斤拷锟杰筹拷
	 * @param key String : 锟杰筹拷锟街凤拷锟斤拷锟斤拷示,16锟斤拷锟街斤拷
	 * @return javax.crypto.SecretKey : 锟斤拷应锟斤拷锟杰匙ｏ拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷null
	 */
	public static SecretKey createSecretKey(final String key){
		return createSecretKey(key.getBytes());
	}
}
