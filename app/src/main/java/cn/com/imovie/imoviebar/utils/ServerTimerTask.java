package cn.com.imovie.imoviebar.utils;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 锟斤拷锟斤拷锟斤拷时锟斤拷锟斤拷锟斤拷锟�
 * @author 锟斤拷锟斤拷锟斤拷
 * 2012-3-15锟斤拷锟斤拷11:56:31
 */
public class ServerTimerTask {
	private static final Timer timer = new Timer();
	private static long servertime=System.currentTimeMillis()/1000;
	private static long tempTime=0;
	private static TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			if(tempTime>0){
				servertime=tempTime;
				tempTime=0;
			}else{
				servertime+=1;
			}
			//System.out.println("servertime="+DateHelper.toString(new Date(servertime*1000), "yyyy-MM-dd HH:mm:ss")+"|"+servertime);
		}
	};
	static{
		//System.out.println("system-time="+DateHelper.toString(new Date(servertime*1000), "yyyy-MM-dd HH:mm:ss")+"|"+servertime);
		timer.schedule(task, 0, 1000);//每锟斤拷锟斤拷锟斤拷执锟斤拷一锟斤拷
	}
	
	
	/**
	 * 锟斤拷锟斤拷锟斤拷时锟斤拷
	 * @param time
	 * @author 锟斤拷锟斤拷锟斤拷
	 * @return void
	 * 2012-3-15锟斤拷锟斤拷1:14:32
	 */
	public static void startTimerTask(long time) {
		tempTime=time;
		
	}
	public static Date getDate(){
		if(task==null){
			return new Date();
		}
		Date date=new Date(servertime*1000);//锟斤拷锟斤拷锟角猴拷锟诫，锟斤拷锟斤拷锟斤拷要X1000
		return date;
	}
	
	public static long getDateInt(){
		if(task==null){
			return System.currentTimeMillis()/1000;
		}
		return servertime;
	}
}
