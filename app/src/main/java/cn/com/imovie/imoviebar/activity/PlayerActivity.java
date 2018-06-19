package cn.com.imovie.imoviebar.activity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.widget.VideoView;
import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;

public class PlayerActivity extends BaseActivity implements OnPreparedListener, OnCompletionListener, OnErrorListener, OnInfoListener,MediaPlayerControl {
	
    public final static int HTTP_REQUEST_VOD_OPEN  = 90001;
    public final static int HTTP_REQUEST_VOD_CLOSE = 90002;
    public final static int HTTP_REQUEST_VOD_PAUSE = 90003;
    public final static int HTTP_REQUEST_VOD_START = 90004;
    public final static int HTTP_PUT_EWATCH_STATUS = 90009;
	
	public final static String TAG = "PlayerActivity";
	public int currentDuration=-1;
	public VideoView vvPlayer;
	
	public String curPlayMovieUrl;
	private MediaController mMediaController;
	
	public Integer playPosition;
	
	public long seekTimeMillis;
	public int seekToPos;
	
	@Override
	public void processMessage(Message message) {
		switch(message.what){	
		case HTTP_REQUEST_VOD_OPEN:
			String url = message.getData().getString("url");
			playPosition = message.getData().getInt("playPosition");
			try {   
				currentDuration = 1000 * playPosition;
				if(url.equalsIgnoreCase(curPlayMovieUrl)){
					seekTo(currentDuration);
				}else{
					curPlayMovieUrl=url;
					vvPlayer.setVideoURI(Uri.parse(curPlayMovieUrl));
				}
				mMediaController.setMediaPlayer(this);
				MyApplication.getInstance().playerStatus.setProcessReturn(0);
			} catch (Exception e) {
				e.printStackTrace();				
			}
			break;
		case HTTP_REQUEST_VOD_CLOSE:
			try {
				finish();
				MyApplication.getInstance().playerStatus.setProcessReturn(0);
			} catch (Exception e) {
				e.printStackTrace();
				
			}			
			break;
		case HTTP_REQUEST_VOD_PAUSE:
			try {
				if (canPause()) {
					pause();
					MyApplication.getInstance().playerStatus.setProcessReturn(0);
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}			
			break;
		case HTTP_REQUEST_VOD_START:
			try {
				if (!isPlaying()) {
					start();
					MyApplication.getInstance().playerStatus.setProcessReturn(0);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}					
			break;	
		}
		MyApplication.getInstance().playerStatus.process(0);
		super.processMessage(message);		
	}
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.setContentView(R.layout.activity_player);		
		super.onCreate(savedInstanceState);
		
		MyApplication.getInstance().playerActivity = this;   

		vvPlayer = (VideoView)findViewById(R.id.vvPlayer);
		vvPlayer.setKeepScreenOn(true);        
		vvPlayer.setOnPreparedListener(this);
        vvPlayer.setOnInfoListener(this);
		vvPlayer.setOnCompletionListener(this);
		vvPlayer.setOnErrorListener(this);

		mMediaController = new MediaController(this);
		vvPlayer.setMediaController(mMediaController);
		mMediaController.hide();
		mMediaController.setBackgroundColor(getResources().getColor(R.color.transparent));	
		
		seekToPos = 0;
		
        Message message = (Message)(getIntent().getExtras().get("message"));
        handler.sendMessage(message);		
      
	}
	
	@Override
	public void onPrepared(MediaPlayer mp) {
		try{  
			if(currentDuration!=-1){
				seekTo(currentDuration);
				currentDuration=-1;
			}
			start();
			//mp.setOnSeekCompleteListener(new OnSeekCompleteListener() {
			//	public void onSeekComplete(MediaPlayer m) {
			//	}
			//});			
			showLayoutMain();
		}catch(Exception e){
			e.printStackTrace();
			MyApplication.getInstance().handler.sendEmptyMessage(HTTP_PUT_EWATCH_STATUS);
		}
	}
	
	@Override
	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		// 锟斤拷一些锟截讹拷锟斤拷息锟斤拷锟街伙拷锟竭撅拷锟斤拷时锟斤拷锟斤拷
		switch (what) {
		case MediaPlayer.MEDIA_INFO_BAD_INTERLEAVING:
			Log.i(TAG, "MediaPlayer MEDIA_INFO_BAD_INTERLEAVING called="+what+":"+extra);
			break;
		case MediaPlayer.MEDIA_INFO_METADATA_UPDATE:
			Log.i(TAG, "MediaPlayer MEDIA_INFO_METADATA_UPDATE called="+what+":"+extra);
			break;
		case MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING:
			Log.i(TAG, "MediaPlayer MEDIA_INFO_VIDEO_TRACK_LAGGING called="+what+":"+extra);
			break;
		case MediaPlayer.MEDIA_INFO_NOT_SEEKABLE:
			Log.i(TAG, "MediaPlayer MEDIA_INFO_NOT_SEEKABLE called="+what+":"+extra);
			break;
		case MediaPlayer.MEDIA_INFO_BUFFERING_START:		
			Log.i(TAG, "MediaPlayer MEDIA_INFO_BUFFERING_START called="+what+":"+extra);
            showLayoutWaitLoading();
			break;
		case MediaPlayer.MEDIA_INFO_BUFFERING_END:
			Log.i(TAG, "MediaPlayer MEDIA_INFO_BUFFERING_END called="+what+":"+extra);
			showLayoutMain();
			break;
		default:
			Log.i(TAG, "MediaPlayer onInfo called="+what+":"+extra);	
		}
		return false;
	}
	
	@Override
	public void onCompletion(MediaPlayer mp) {
		this.finish();
	}
	
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		MyApplication.getInstance().handler.sendEmptyMessage(HTTP_PUT_EWATCH_STATUS);
		Log.i(TAG, "MediaPlayer onError called="+what+":"+extra);
		return true;
	}	
	
	@Override
	public void onPause() {
		Log.i(TAG, "onPause");	
		super.onPause();
		vvPlayer.stopPlayback();
		Intent intent = new Intent(this, MyApplication.getInstance().mainActivity.getClass());
		startActivity(intent);	
		MyApplication.getInstance().playerActivity = null;  
		MyApplication.getInstance().handler.sendEmptyMessage(HTTP_PUT_EWATCH_STATUS);
	}

	@Override
	public void start() {		
		vvPlayer.start();
		MyApplication.getInstance().handler.sendEmptyMessage(HTTP_PUT_EWATCH_STATUS);
	}

	@Override
	public void pause() {
		vvPlayer.pause();		
		MyApplication.getInstance().handler.sendEmptyMessage(HTTP_PUT_EWATCH_STATUS);		
	}

	@Override
	public int getDuration() {
		return vvPlayer.getDuration();
	}

	@Override
	public int getCurrentPosition() {
		if (seekToPos == 0) {
			return vvPlayer.getCurrentPosition();			
		} else {
			return seekToPos;		
		}
	}

	@Override
	public void seekTo(int pos) {
		if (vvPlayer.getCurrentPosition() != pos) {
			seekTimeMillis = System.currentTimeMillis();
			vvPlayerSeekTo(pos, seekTimeMillis);			
		}
	}

	@Override
	public boolean isPlaying() {
		return vvPlayer.isPlaying();
	}

	@Override
	public int getBufferPercentage() {
		return vvPlayer.getBufferPercentage();
	}

	@Override
	public boolean canPause() {
		return vvPlayer.canPause();
	}

	@Override
	public boolean canSeekBackward() {
		return vvPlayer.canSeekBackward();
	}

	@Override
	public boolean canSeekForward() {
		return vvPlayer.canSeekForward();
	}

	@Override
	public int getAudioSessionId() {
		return vvPlayer.getAudioSessionId();
	}
	
	public void vvPlayerSeekTo(final int pos, final long currTimeMillis) {	
		seekToPos = pos; 
        new Thread() {        	
        	public void run(){         		          		
        		try {
        			Thread.sleep(150);
        		} catch (InterruptedException e) {
        			e.printStackTrace();
        		}	
        		if (currTimeMillis < seekTimeMillis) {
        			return;
        		} 
        		vvPlayer.seekTo(pos);	
        		seekToPos = 0;
    			MyApplication.getInstance().handler.sendEmptyMessage(HTTP_PUT_EWATCH_STATUS);
        		Log.i(TAG, "vvPlayerSeekTo="+pos+":"+currTimeMillis);	
        	}
    	}.start();		
	}
	
}
