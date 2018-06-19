package cn.com.imovie.imoviebar.bean;

public class BaseReturn {
	
	public BaseReturn(){
		
	}
	public BaseReturn(int code,String message){
		this.code=code;
		this.message=message;
	}
	public BaseReturn(int code){
		this.code=code;
	}
	public static int SUCCESS = 0;	
	public static int ERROR = 1;

    public static int ERROR3 = 3;//fwh
    public static int ERROR5 = 5;//fwh

	private int code = SUCCESS;
	
	
	private String message = "";
	
	private String otherText = "";
	
	private Object otherObject = null;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOtherText() {
		return otherText;
	}

	public void setOtherText(String otherText) {
		this.otherText = otherText;
	}

	public Object getOtherObject() {
		return otherObject;
	}

	public void setOtherObject(Object otherObject) {
		this.otherObject = otherObject;
	}

}
