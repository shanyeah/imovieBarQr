package cn.com.imovie.imoviebar.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;


public class BaseActivity extends Activity {

    private static final String TAG = "BaseActivity";

	public RelativeLayout layoutWaitLoading;	
	public RelativeLayout layoutError;	
	public ViewGroup layoutMain;	
	
	public TextView tvError;
	public Button btnTryAgain;	
	public Button btnErrorReturn;	
	
	public AlertDialog confirmDialog;
    public AlertDialog m_confirmDialog;
	
	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler(){
		public void handleMessage(Message message){
			processMessage(message);
			super.handleMessage(message);
		}	
		
	};	

	protected void processMessage(Message message){
		//
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);

		layoutWaitLoading = (RelativeLayout)findViewById(R.id.layoutWaitLoading);
		layoutError = (RelativeLayout)findViewById(R.id.layoutError);
		layoutMain = (ViewGroup)findViewById(R.id.layoutMain);
		
		tvError = (TextView)findViewById(R.id.tvError);
		btnTryAgain = (Button)findViewById(R.id.btnTryAgain);	
		btnTryAgain.setVisibility(View.GONE); 
		btnErrorReturn = (Button)findViewById(R.id.btnErrorReturn);
		btnErrorReturn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {	
				finish();
			}    		
    	});			
		
		showLayoutWaitLoading();	
	}
	
	@Override
	public void onStart() {
		super.onStart();
		MyApplication.getInstance().currActivity = this;

	}

	public void showConfirmDialog(String message, OnClickListener btnYesOnClickListener) {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setTitle("确认选择").setIcon(R.drawable.confirm);
		View vDialog = getLayoutInflater().inflate(getResources().getLayout(R.layout.dialog_confirm1), null);
		builder.setView(vDialog); 
		confirmDialog = builder.create();		
		Button btnYes = (Button)vDialog.findViewById(R.id.btnYes);
		Button btnNo = (Button)vDialog.findViewById(R.id.btnNo);
		TextView tvInfo = (TextView)vDialog.findViewById(R.id.tvInfo);
		btnNo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {	
				confirmDialog.dismiss();
			}    		
    	}); 
		tvInfo.setText(message);
		btnYes.setOnClickListener(btnYesOnClickListener);
		builder.setView(vDialog); 
		confirmDialog = builder.create();
		confirmDialog.show();
	}


	 @SuppressLint("InflateParams")
	public void showToast(String text, int type){
		 View view=getLayoutInflater().inflate(R.layout.toast ,null);
		 Toast toast=new Toast(this);
		 toast.setView(view);
		 TextView tv=(TextView)view.findViewById(R.id.tvText);
		 tv.setText(text);
		 ImageView ivImage=(ImageView)view.findViewById(R.id.ivImage);
		 if (type == 0) { 
			 tv.setTextColor(getResources().getColor(R.color.white));
			 ivImage.setImageDrawable(getResources().getDrawable(R.drawable.info));
		 } else {
			 tv.setTextColor(getResources().getColor(R.color.error_text_color));
			 ivImage.setImageDrawable(getResources().getDrawable(R.drawable.error));
		 }
		 toast.setDuration(Toast.LENGTH_LONG);
		 toast.show();
	 }
	 
	public void showLayoutWaitLoading() {
		layoutWaitLoading.setVisibility(View.VISIBLE);
		layoutError.setVisibility(View.GONE); 
		if (layoutMain != null) {
			layoutMain.setVisibility(View.GONE);		
		}		
	}
	
	public void showLayoutError() {
		layoutWaitLoading.setVisibility(View.GONE);
		layoutError.setVisibility(View.VISIBLE); 
		if (layoutMain != null) {
			layoutMain.setVisibility(View.GONE);		
		}			
	}
	
	public void showLayoutMain() {
		layoutWaitLoading.setVisibility(View.GONE);
		layoutError.setVisibility(View.GONE); 
		if (layoutMain != null) {
			layoutMain.setVisibility(View.VISIBLE);		
		}		
	}

    public GridView.LayoutParams getGridLayoutParams(int numColumn){
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int diff = 135;
        //int numColumn = 4;//
        int itemWidth = (screenWidth - diff) / numColumn;
        int itemHeight = (itemWidth * 7) / 5;//
        return new GridView.LayoutParams(itemWidth, itemHeight);
    }

}

