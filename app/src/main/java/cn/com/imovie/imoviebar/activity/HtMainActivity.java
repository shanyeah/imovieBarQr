package cn.com.imovie.imoviebar.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.p2p.Lspi;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

import java.util.concurrent.ScheduledExecutorService;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.adapter.FooterAdapter;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.dialog.LoginDialog;
import cn.com.imovie.imoviebar.event.Notify;
import cn.com.imovie.imoviebar.fragment.FooterFragment;
import cn.com.imovie.imoviebar.fragment.InfoFragment;
import cn.com.imovie.imoviebar.fragment.MovieListFragment;
import cn.com.imovie.imoviebar.http.XMLDataGetter;
import cn.com.imovie.imoviebar.http.XMLDataParser;
import cn.com.imovie.imoviebar.notify.OnFooterItemClick;
import cn.com.imovie.imoviebar.notify.ReloadNotify;
import cn.com.imovie.imoviebar.widget.PlayConsole;

public class HtMainActivity extends BaseActivity implements OnFooterItemClick, Notify  {

    public final static String TAG = "HtMainActivity";
    static final int REQUEST_DURATION = 1;
    public XMLDataParser xmlParser;

    public static HtMainActivity htMainActivity = null;

    private FragmentManager fragmentManager;
    private MovieListFragment movieListFragment;


    private InfoFragment infoFragment;
    public FooterFragment footerFragment;

    private ImageView bt_setting;

    public TextView tv_title;

    public RelativeLayout layoutTop;



    ScheduledExecutorService scheduledRequestExecutorService;
    SearchView mSearchView;
    String mSearch;
    public PlayConsole console;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivityIntoTask(this);



        htMainActivity = this;


        MyApplication.getInstance().mainActivity = this;
        xmlParser = XMLDataParser.getXmlParser();

        console = new PlayConsole(this);
        console.setOutsideTouchable(true);
        console.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                console.stopCheckService();
            }
        });

        //设置图标
        ImageView btnMenu = (ImageView) findViewById(R.id.btnMenu);
        tv_title = (TextView) findViewById(R.id.tv_title);


        tv_title.setText(MyApplication.getInstance().stginfo.getSimpleName());
        //搜索框
        mSearchView = (SearchView) findViewById(R.id.search);

        bt_setting = (ImageView) findViewById(R.id.btn_setting);


        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = (TextView) mSearchView.findViewById(id);
        textView.setTextColor(Color.WHITE);

        mSearchView.setOnQueryTextListener(searchListener);
        mSearchView.setOnCloseListener(searchCloseListener);
        mSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mSearchView.setIconified(false);
            }
        });


        bt_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginDialog d = new LoginDialog(HtMainActivity.this,view,HtMainActivity.this);
                d.show();

//
//                PopupMenu popupMenu = new PopupMenu(HtMainActivity.this, view);
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//
//                        switch (menuItem.getItemId()){
//                            case R.id.item_setserver:
//                                ServerSettingDialog serverSettingDialog = ServerSettingDialog.newInstance();
//                                serverSettingDialog.show(getFragmentManager(),"find Server");
//
//                               break;
//                            case R.id.item_setmode:
//                                BoxModeDialog dialog = new BoxModeDialog(HtMainActivity.this,HtMainActivity.this);
//                                dialog.show();
//
//                                break;
//
//
//                        }
//
//
//                        return false;
//                    }
//                });
//                popupMenu.inflate(R.menu.popup_menu);
//
//                popupMenu.show();


            }
        });


        fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        //MovieListFragment
        movieListFragment = new MovieListFragment();
        ft.add(R.id.layoutMain, movieListFragment, "0");
        ft.show(movieListFragment);

        //InfoFragment
//        infoFragment = new InfoFragment();
//        ft.add(R.id.layoutMain, infoFragment, "1");
//        ft.hide(infoFragment);


        //FooterFragment
        footerFragment = FooterFragment.createInstance(0);
        ft.add(R.id.layoutFooter, footerFragment);
        ft.show(footerFragment);
        ft.commitAllowingStateLoss();

        Log.d(TAG, "主Activity创建完毕！");
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);




        if (movieListFragment != null){

                movieListFragment.reload();

        }

//        if (infoFragment != null) {
//            infoFragment.reload();
//        }


    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        footerFragment.reload();
        if(MyApplication.getInstance().mPref.getInt("BoxMode", 0)==2){
            tv_title.setText(MyApplication.getInstance().stginfo.getSimpleName() + "("+MyApplication.getInstance().mPref.getString("EwatchName", "")+")");
        }else {
            tv_title.setText(MyApplication.getInstance().stginfo.getSimpleName());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    void checkPlayStatus(){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                checkTask.run();
                return null;
            }
        }.execute();
    }

    Runnable checkTask = new Runnable() {
        public void run() {
            new AsyncTask<Void, Void, BaseReturn>() {
                @Override
                protected BaseReturn doInBackground(Void... params) {

//                    if(!htMainActivity.connectServer()) return new BaseReturn(BaseReturn.SUCCESS);
                    String serverIP = MyApplication.getInstance().mPref.getString("serverIp","0.0.0.0");
                    StringBuilder s = new StringBuilder("http://" + serverIP +"/ewatch/status.php?");
                    s.append("ewatch_status_id=").append(MyApplication.getInstance().mPref.getInt("EwatchId", 0));
                    BaseReturn baseReturn = XMLDataGetter.doGetHttpRequest(s.toString());
                    if (baseReturn.getCode() == BaseReturn.SUCCESS) {
                        MyApplication.getInstance().xmlParser.parserStatus(baseReturn);
                    }
                    return baseReturn;
                }

                @Override
                protected void onPostExecute(BaseReturn baseReturn) {
                    Bundle bundle = new Bundle();
                    if (baseReturn.getCode() == BaseReturn.SUCCESS) {
                        bundle.putBoolean("running", true);
                        cn.com.imovie.imoviebar.bean.Status status = (cn.com.imovie.imoviebar.bean.Status) baseReturn.getOtherObject();
                        if (status != null && status.getPlayStatus() != null) {
                            boolean idle = status.getPlayStatus().intValue()==0;
                            boolean playing = status.getPlayStatus().intValue() == 1;
                            boolean pausing = status.getPlayStatus().intValue() == 2;
                            bundle.putBoolean("idle",idle);
                            bundle.putBoolean("playing", playing);
                            bundle.putBoolean("pausing", pausing);
                            bundle.putSerializable("status",status);
                            if(status.getPlayPosition()!=null)
                                bundle.putInt("position",status.getPlayPosition());
                        }
                    } else {
                        bundle.putBoolean("running", false);
                    }
                    console.onNotify("HtMainActivity",bundle);
                }
            }.execute();
        }
    };

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 0x100 && resultCode==0x100) {
//
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("enable",true);
//            onNotify("HtMainActivity", bundle);
//
//            return;
//        }
//        if (resultCode >= 0 && resultCode < 4) {
//            footerFragment.select(resultCode);
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "requestCoderequestCode="+requestCode);

        if (requestCode == 0x100 && resultCode==0x100) {

            checkPlayStatus();
            console = new PlayConsole(HtMainActivity.this);
            console.showAtLocation(HtMainActivity.this.getCurrentFocus(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

            Bundle bundle = new Bundle();
            bundle.putBoolean("enable",true);
            onNotify("HtMainActivity", bundle);
            return;
        }
        if (resultCode >= 0 && resultCode < 4) {
            footerFragment.reload();
            footerFragment.select(resultCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().scheduledThreadPoolExecutor.shutdownNow();
        Lspi.fini();

        System.exit(0);
    }

    @Override
    public void onFooterItemClick(int position) {
        int  moviePos = 0;
//        int  infoPos = 1;
        int  playPos = 2;
        //当底部导航点击切换fragment 的时候调用
//        if(position== playPos ){
//                checkPlayStatus();
//                console = new PlayConsole(HtMainActivity.this);
//                console.showAtLocation(HtMainActivity.this.getCurrentFocus(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//                return;
//        }

        //当底部导航点击切换fragment 的时候调用

        if (position==1){
            if(MyApplication.getInstance().mPref.getInt("BoxMode", 0)==2){
                checkPlayStatus();
                console = new PlayConsole(HtMainActivity.this);
                console.showAtLocation(HtMainActivity.this.getCurrentFocus(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                return;
           }else{
                if (MyApplication.getInstance().selectedMovList.size() ==0){
                    showToast("您还没有挑选影片",0);
                    return;
                }
                Intent  intent = new Intent(this,SelectedMovieActivity.class);
                startActivity(intent);
        }
        }
        else {

 if (position >= 0 && position < FooterAdapter.MAX_SIZE) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            for (int i = 0; i < FooterAdapter.MAX_SIZE; i++) {
                Fragment fragment = fragmentManager.findFragmentByTag(String.valueOf(i));
                if (fragment != null) {
                    if (i == position && fragment instanceof ReloadNotify) {


                      if (position==moviePos)  ft.show(fragment);

//                        if(i==infoPos){//刷新收藏夹页面
//                            ReloadNotify notify = (ReloadNotify) fragment;
//                            notify.setPageNo(1);
//                            notify.reload();
//
//
//                        }

                        if(i==moviePos){
                            mSearchView.setVisibility(View.VISIBLE);
                        }else{
                            mSearchView.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        ft.hide(fragment);
                    }
                }
            }
            ft.commitAllowingStateLoss();
        }



        }



    }

    @Override
    public void onNotify(String tag, Bundle bundle) {
    }


    OnQueryTextListener searchListener = new OnQueryTextListener(){
        @Override
        public boolean onQueryTextChange(String queryText) {

            return true;
        }

        @Override
        public boolean onQueryTextSubmit(String queryText) {
            if (mSearchView != null) {

                movieListFragment.setMovieConditionSearch(queryText);
                movieListFragment.reload();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(
                            mSearchView.getWindowToken(), 0);
                }
                mSearchView.clearFocus();
            }
            return true;
        }
    };

    OnCloseListener searchCloseListener = new OnCloseListener(){
        @Override
        public boolean onClose() {
            movieListFragment.setMovieConditionSearch("");
//            movieListFragment.reload();
            return false;
        }
    };

}
