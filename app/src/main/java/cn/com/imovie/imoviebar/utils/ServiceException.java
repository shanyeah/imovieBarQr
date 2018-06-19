package cn.com.imovie.imoviebar.utils;


/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * <p>CreateTime: 2006-6-4 15:56:52
 * @author 21锟斤拷锟酵的匡拷锟斤拷 topilee@gmail.com
 * @version 1.0
 */
public class ServiceException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 锟届常锟斤拷锟斤拷
	 */
	private String errorCode = null;
	public String getErrorCode(){
		return this.errorCode;
	}
	//
	private Object[] args = null;
	public Object[] getArgs(){
		return this.args;
	}
	public ServiceException(final String errorCode){
		this.errorCode = errorCode;
	}
	//
	public ServiceException(final String errorCode,final String arg1){
		this.errorCode = errorCode;
		this.args = new Object[]{arg1};
	}
	//
	public ServiceException(final String errorCode,final String arg1,final String arg2){
		this.errorCode = errorCode;
		this.args = new Object[]{arg1,arg2};
	}
	//
	public ServiceException(final String errorCode,final Object[] args){
		this.errorCode = errorCode;
		this.args = args;
	}
	public String getErrorMsg(){
		return (String)args[0];
	}
}
