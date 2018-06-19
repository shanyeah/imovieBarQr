package cn.com.imovie.imoviebar.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.activity.HtMainActivity;
import cn.com.imovie.imoviebar.activity.SplashActivity;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.dialog.ServerSettingDialog;
import cn.com.imovie.imoviebar.utils.StringHelper;

public class HtSplashFragment extends Fragment {

    public final static String TAG = "SplashFragment";

    public static final int INIT_LSPI_START = 1000;

    public static final int INIT_LSPI_FAILURE = 1001;

    public static final int SEARCH_SERVER_START = 1002;

    public static final int SEARCH_SERVER_FAILURE = 1003;

    public static final int CHECK_RELEASE_START = 1004;

    public static final int CHECK_RELEASE_FAILURE = 1005;

    public static final int INIT_FINISH = 1100;

    public static final int INSTALL_NEW_VERSION = 1101;

    public static final int NETWORK_AVAILABLE = 1301;

    public static final int NETWORK_INAVAILABLE = 1303;

    public static final int SHOW_UPDATE_NOTICE = 1501;

    private SplashActivity splashActivity;
    private static  int serarchServerTimes = 0;
    String apkFileName;

    AlertDialog noticeDialog;

    AlertDialog processDialog;

    ProgressBar progressBar;

    boolean cancelDownFlag=false;

    TextView tvInfo;

    public Handler handler = new Handler(){

        public void handleMessage(Message message){
            processMessage(message);
            super.handleMessage(message);
        }

    };

    protected void processMessage(Message message) {
        switch(message.what){
            case (NETWORK_INAVAILABLE):
                tvInfo.setText("当前网络未链接");
                break;
            case (INIT_LSPI_START):
                tvInfo.setText(message.getData().getString("message"));
                break;
            case (INIT_LSPI_FAILURE):
                tvInfo.setText(message.getData().getString("message"));
                break;
            case (SEARCH_SERVER_START):
                new Thread() {
                    public void run(){
                        Message message = new Message();


                        BaseReturn baseReturn;
                /*  if ("".equals(MyApplication.getInstance().serverIp))
                  {
                     baseReturn = MyApplication.getInstance().searchServer();


                  }
*/

                        baseReturn = MyApplication.getInstance().getStgInfo();
                        Log.d(TAG,"baseReturn=="+baseReturn.getCode());
                        if (baseReturn.getCode()!=BaseReturn.SUCCESS) {

                            if (serarchServerTimes > 1) {
                                message.what = SEARCH_SERVER_FAILURE;
                                if (baseReturn.getCode() != BaseReturn.SUCCESS) {
                                    if (baseReturn.getCode() == BaseReturn.ERROR3) {
                                        baseReturn.setMessage("无法链接网络，请检查网络连接是否正常");
                                    } else {//BaseReturn.ERROR
                                        baseReturn.setMessage("无法搜索到主机，请检查网络连接是否正常以及主机是否开机。");
                                    }
                                }
                                message.getData().putString("message", baseReturn.getMessage());

                            }

                            else
                            {


                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        serarchServerTimes++;
                                        try {
                                            ServerSettingDialog serverSettingDialog = ServerSettingDialog.newInstance();
                                            serverSettingDialog.show(getActivity().getFragmentManager(), "find Server");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });






                            }


                        } else {
                            Log.d(TAG,"Check_release_start");
                            message.what = CHECK_RELEASE_START;
                        }
                        handler.sendMessage(message);
                    }
                }.start();
                break;
            case (SEARCH_SERVER_FAILURE):
                tvInfo.setText(message.getData().getString("message"));
                break;
            case (CHECK_RELEASE_START):

                tvInfo.setText("正在检查版本更新，请稍后...");
                new Thread() {
                    public void run(){
                        Message message = new Message();
                        message.what = INIT_FINISH;

                         BaseReturn baseReturn = MyApplication.getInstance().queryRelease();
                         if (baseReturn.getCode() == BaseReturn.SUCCESS) {

                         if ("true".equals(MyApplication.getInstance().getResources().getString(R.string.allowUpdate)) && !StringHelper.isEmpty(MyApplication.getInstance().release.getNewVersion())) {
                         message.what = SHOW_UPDATE_NOTICE;
                         }
                         } else {
                         //请求更新接口出错直接进入程序
                         //message.what = CHECK_RELEASE_FAILURE;
                         //message.getData().putString("message", "检查新版本失败");
                         }
                        handler.sendMessage(message);
                    }

                }.start();

                break;
            case (SHOW_UPDATE_NOTICE):
                showNoticeDialog("检测到新版本，确认马上更新吗？",btnYesOnClickListener,btnNoOnClickListener);
                break;
            case (INSTALL_NEW_VERSION):
                Log.d(TAG,"installNewVersion="+apkFileName);
                if(!cancelDownFlag){
                    new Thread() {
                        public void run(){

                            BaseReturn baseReturn = MyApplication.getInstance().installNewVersion(splashActivity,apkFileName);
                            if (baseReturn.getCode() != BaseReturn.SUCCESS) {
                                Message message = new Message();
                                message.what = INIT_FINISH;
                                handler.sendMessage(message);

                            }
                        }
                    }.start();
                }
                break;
            case (INIT_FINISH):
                splashActivity.finish();
                Intent intent = new Intent(splashActivity, HtMainActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        splashActivity = (SplashActivity)activity;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       /* new Thread() {
            public void run(){
                Message message = new Message();
                BaseReturn baseReturn = MyApplication.getInstance().initLspi();
                if (baseReturn.getCode()!=BaseReturn.SUCCESS) {
                    message.what = INIT_LSPI_FAILURE;
                    message.getData().putString("message", baseReturn.getMessage());
                } else {

                    message.what = SEARCH_SERVER_START;
                }
                handler.sendMessage(message);
            }
        }.start();*/

        Message message = new Message();
        BaseReturn baseReturn = MyApplication.getInstance().initLspi();
        if (baseReturn.getCode()!=BaseReturn.SUCCESS) {
            message.what = INIT_LSPI_FAILURE;
            message.getData().putString("message", baseReturn.getMessage());
            handler.sendMessage(message);
        } else {

            searchServer();
        }

    }

    public void searchServer() {

        handler.sendEmptyMessage(SEARCH_SERVER_START);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ht_splash, container, false);


        tvInfo = (TextView)view.findViewById(R.id.tvInfo);
        tvInfo.setText(getResources().getString(R.string.init_waiting));



        return view;
    }


    private void showNoticeDialog(String message, View.OnClickListener btnYesOnClickListener,View.OnClickListener btnNoOnClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(splashActivity);
        builder.setTitle("版本更新").setIcon(R.drawable.confirm);
        View vDialog = null;

        vDialog = splashActivity.getLayoutInflater().inflate(getResources().getLayout(R.layout.dialog_confirm1), null);
        builder.setView(vDialog);
        noticeDialog = builder.create();
        noticeDialog.setCanceledOnTouchOutside(false);

        Button btnYes = (Button)vDialog.findViewById(R.id.btnYes);
        Button btnNo = (Button)vDialog.findViewById(R.id.btnNo);
        TextView tvInfo = (TextView)vDialog.findViewById(R.id.tvInfo);

        btnYes.setText("马上更新");
        btnNo.setText("下次更新");

        tvInfo.setText(message);

        btnNo.setOnClickListener(btnNoOnClickListener);
        btnYes.setOnClickListener(btnYesOnClickListener);
        builder.setView(vDialog);
        noticeDialog.show();
    }


    private void showProcessDialog(String message,View.OnClickListener btnNoOnClickListener) {

        AlertDialog.Builder builder = new AlertDialog.Builder(splashActivity);
        builder.setTitle("正在更新").setIcon(R.drawable.info);
        View vDialog = splashActivity.getLayoutInflater().inflate(getResources().getLayout(R.layout.dialog_progress), null);
        builder.setView(vDialog);
        processDialog = builder.create();
        processDialog.setCanceledOnTouchOutside(false);


        progressBar = (ProgressBar) vDialog.findViewById(R.id.progressBar);
        Button btnNo = (Button)vDialog.findViewById(R.id.btnNo);

        btnNo.setOnClickListener(btnNoOnClickListener);
        builder.setView(vDialog);

        processDialog.show();

    }

    View.OnClickListener btnYesOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View arg0) {
            Log.d(TAG,"DOWNLOAD_NEW_VERSION=");
            //显示进度对话框
            noticeDialog.dismiss();
            showProcessDialog("正在下载新版本", btnNoOnClickListener);
            new Thread() {
                public void run(){
                    Message message = new Message();
                    BaseReturn baseReturn =MyApplication.getInstance().apkFileDownload(MyApplication.getInstance().release.getDownloadUrl(),"Player1.apk");
                    if (baseReturn.getCode() == BaseReturn.SUCCESS) {
                        apkFileName = baseReturn.getOtherText();
                        message.what = INSTALL_NEW_VERSION;
                    } else {//更新失败也进入
                        message.what = INIT_FINISH;
                        splashActivity.showToast("新版本更新失败！下次再更新", 0);
                    }
                    handler.sendMessage(message);
                }
            }.start();
        }
    };

    View.OnClickListener btnNoOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View arg0) {//不更新 直接进入
            Message message = new Message();
            cancelDownFlag = true;

            if(noticeDialog!=null)noticeDialog.dismiss();
            if(processDialog!=null)processDialog.dismiss();


            message.what = INIT_FINISH;
            handler.sendMessage(message);

        }
    };
}
