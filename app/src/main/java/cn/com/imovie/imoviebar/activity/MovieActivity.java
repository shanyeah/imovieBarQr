package cn.com.imovie.imoviebar.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.Ewatch;
import cn.com.imovie.imoviebar.bean.Movie;
import cn.com.imovie.imoviebar.bean.PlayTask;
import cn.com.imovie.imoviebar.fragment.EwatchSelectFragment;
import cn.com.imovie.imoviebar.fragment.MovieDetailFragment;
import cn.com.imovie.imoviebar.fragment.PlayPositionFragment;
import cn.com.imovie.imoviebar.http.ImageDataGet;
import cn.com.imovie.imoviebar.http.XMLDataGetter;
import cn.com.imovie.imoviebar.utils.StringHelper;

public class MovieActivity extends BaseActivity {

    public final static String TAG = "MovieActivity";

    private static final int GET_MOVIE_SUCCESS = 3000;

    private static final int GET_MOVIE_FAILURE = 3001;

    private static final int PLAY_MOVIE_SUCCESS = 3002;

    private static final int PLAY_MOVIE_FAILURE = 3003;

    private FragmentManager fragmentManager;
    private MovieDetailFragment movieDetailFragment;
    private PlayPositionFragment playPositionFragment;
    private EwatchSelectFragment ewatchSelectFragment;
    private Fragment currentFragment;

    public static MovieActivity mActivity = null;

    public Integer movieId;
    public Movie movie;
    public Ewatch ewatch;
    public Bitmap bigPosterBitmap;
    public PlayTask playTask;
    public Integer playPosition;
    public PlayTask movieLastPlayTask;

    @Override
    protected void processMessage(Message message) {
        switch(message.what){
            case (GET_MOVIE_SUCCESS):
                movieDetailFragment.displayMovieDetail();
                layoutWaitLoading.setVisibility(View.GONE);
                layoutError.setVisibility(View.GONE);
                layoutMain.setVisibility(View.VISIBLE);
                movieDetailFragment.btnPlay.requestFocus();
                break;
            case (GET_MOVIE_FAILURE):
                layoutWaitLoading.setVisibility(View.GONE);
                layoutError.setVisibility(View.VISIBLE);
                layoutMain.setVisibility(View.GONE);
                break;
            case (PLAY_MOVIE_SUCCESS):
                showToast("播放电影成功!", 0);
                afterPlayMovieSuccess(message);
                break;
            case (PLAY_MOVIE_FAILURE):
                tvError.setText(message.obj.toString());
                layoutWaitLoading.setVisibility(View.GONE);
                layoutError.setVisibility(View.VISIBLE);
                layoutMain.setVisibility(View.GONE);
                btnErrorReturn.requestFocus();
                break;
        }
        super.processMessage(message);
    }
    cn.com.imovie.imoviebar.bean.Status status;
    void afterPlayMovieSuccess(final Message message){
            new AsyncTask<Void, Void, BaseReturn>() {
                @Override
                protected BaseReturn doInBackground(Void... params) {
                    BaseReturn baseReturn=null;
                    for(int i=0;i<3;i++) {
                        String serverIP = MyApplication.getInstance().mPref.getString("serverIp","0.0.0.0");
                        StringBuilder s = new StringBuilder("http://" + serverIP +"/ewatch/status.php?");
                        s.append("ewatch_status_id=").append(MyApplication.getInstance().mPref.getInt("EwatchId", 0));
                        baseReturn = XMLDataGetter.doGetHttpRequest(s.toString());
                        if (baseReturn.getCode() == BaseReturn.SUCCESS) {
                            MyApplication.getInstance().xmlParser.parserStatus(baseReturn);
                            cn.com.imovie.imoviebar.bean.Status status = (cn.com.imovie.imoviebar.bean.Status) baseReturn.getOtherObject();
                            if(status.getPlayStatus()!=null && status.getPlayStatus().intValue()==1)
                                break;
                            else
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                        }
                    }
                    return baseReturn;
                }

                @Override
                protected void onPostExecute(BaseReturn baseReturn) {
                    final Intent intent = new Intent();
                    intent.putExtra("play_task_id",message.arg1);
                    if(status!=null) {
                        intent.putExtra("enable",true);
                        intent.putExtra("status", status);
                    }
                    setResult(0x100,intent);
                    finish();
                }
            }.execute();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setContentView(R.layout.activity_movie);
        super.onCreate(savedInstanceState);

        mActivity = this;
        MyApplication.getInstance().addActivityIntoTask(this);

        btnTryAgain.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (movie == null) {
                    getMovie();
                } else {
                    playMovie();
                }
            }
        });

        fragmentManager = getFragmentManager();
        movieDetailFragment = new MovieDetailFragment();
        playPositionFragment = new PlayPositionFragment();
        ewatchSelectFragment = new EwatchSelectFragment();

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.layoutMain, playPositionFragment);
        ft.add(R.id.layoutMain, ewatchSelectFragment);
        ft.hide(playPositionFragment);
        ft.hide(ewatchSelectFragment);
        ft.add(R.id.layoutMain, movieDetailFragment);
        currentFragment = movieDetailFragment;
        ft.commitAllowingStateLoss();

        movieId = (Integer)(getIntent().getExtras().get("movieId"));
        playPosition = 0;

        getMovie();
    }

    public void getMovie() {
        layoutWaitLoading.setVisibility(View.VISIBLE);
        layoutError.setVisibility(View.GONE);
        layoutMain.setVisibility(View.GONE);
        new Thread() {
            public void run(){
                StringBuffer urlSB = new StringBuffer();

                urlSB = new StringBuffer("http://"+MyApplication.getInstance().serverIp+"/stg/moviedetail.php");

                if(urlSB.indexOf("?")==-1){
                    urlSB.append("?");
                } else {
                    urlSB.append("&");
                }

                urlSB.append("movie_id="+movieId);

                BaseReturn baseReturn = XMLDataGetter.doGetHttpRequest(urlSB.toString());
                if (baseReturn.getCode() == BaseReturn.SUCCESS) {
                    MyApplication.getInstance().xmlParser.parserMovie(baseReturn);
                    movie = (Movie)baseReturn.getOtherObject();

                    if(!StringHelper.isEmpty(movie.getBigPosterHash())){
                        bigPosterBitmap = ImageDataGet.returnBitMap(movie.getBigPoster(), movie.getBigPosterHash());
                    }

                    handler.sendEmptyMessage(GET_MOVIE_SUCCESS);
                } else {
                    Message message = new Message();
                    message.what = GET_MOVIE_FAILURE;
                    message.getData().putString("message", baseReturn.getMessage());
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    public void showPlayPosition() {
//        playPositionFragment.displayPlayPosition();
        layoutWaitLoading.setVisibility(View.GONE);
        layoutError.setVisibility(View.GONE);
        layoutMain.setVisibility(View.VISIBLE);
        playPositionFragment.updateView(bigPosterBitmap,movie);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(currentFragment);
        ft.show(playPositionFragment);
        currentFragment = playPositionFragment;
        ft.commitAllowingStateLoss();
    }

    public void playMovie() {
        new Thread() {
            public void run(){
                Message message = new Message();
                StringBuilder s = new StringBuilder();
                s.append("xml=<?xml version=\"1.0\" encoding=\"gbk\" ?>");
                s.append("<play_task>");
                s.append("<movie_id>").append(movieId).append("</movie_id>");
                s.append("<file_id>0</file_id>");
                s.append("<play_position>").append(playPosition).append("</play_position>");
                s.append("</play_task>");
                String serverIP = MyApplication.getInstance().mPref.getString("serverIp","0.0.0.0");
                StringBuilder url = new StringBuilder("http://" + serverIP + "/movie/playtask.php?");
                url.append("ewatch_status_id=").append(MyApplication.getInstance().mPref.getInt("EwatchId", 0));
                BaseReturn baseReturn=XMLDataGetter.doHttpRequest(url.toString(), s.toString(), "POST");
                if(baseReturn.getCode() == BaseReturn.SUCCESS){
                    MyApplication.getInstance().xmlParser.parserPlayTask(baseReturn);
                    playTask = (PlayTask)baseReturn.getOtherObject();
                    message.what = PLAY_MOVIE_SUCCESS;
                    message.arg1=playTask.getId();
                } else {
                    message.what = PLAY_MOVIE_FAILURE;
                    message.arg1 =0;
                    message.obj = baseReturn.getMessage();
                    message.getData().putString("message", baseReturn.getMessage());
                }
                handler.sendMessage(message);
            }
        }.start();
    }

    public void selectEwatch() {
        layoutWaitLoading.setVisibility(View.GONE);
        layoutError.setVisibility(View.GONE);
        layoutMain.setVisibility(View.VISIBLE);

        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.hide(currentFragment);
        ft.show(ewatchSelectFragment);

        ewatchSelectFragment.loadEwatchList();

        currentFragment = ewatchSelectFragment;


        ft.commitAllowingStateLoss();
    }

//    public void playMovie() {
//        new Thread() {
//            public void run(){
//                Message message = new Message();
//                StringBuilder s = new StringBuilder();
//                s.append("xml=<?xml version=\"1.0\" encoding=\"gbk\" ?>");
//                s.append("<play_task>");
//                s.append("<movie_id>").append(movieId).append("</movie_id>");
//                s.append("<file_id>0</file_id>");
//                s.append("<play_position>0</play_position>");
//                s.append("</play_task>");
//
//
//                Log.d(TAG,"============"+ewatch.getId());
//
//                StringBuilder url = new StringBuilder(MyApplication.getInstance().guide.getPlayTaskUrl());
//                url.append("&ewatch_status_id=").append(ewatch.getId());
//                url.append("&price=").append(ewatch.getPrice());
//                BaseReturn baseReturn=XMLDataGetter.doHttpRequest(url.toString(), s.toString(), "POST");
//                if(baseReturn.getCode() == BaseReturn.SUCCESS){
//                    MyApplication.getInstance().xmlParser.parserPlayTask(baseReturn);
//                    playTask = (PlayTask)baseReturn.getOtherObject();
//                    message.what = PLAY_MOVIE_SUCCESS;
//                    message.arg1=playTask.getId();
//                } else {
//                    message.what = PLAY_MOVIE_FAILURE;
//                    message.arg1 =0;
//                    message.getData().putString("message", baseReturn.getMessage());
//                }
//                handler.sendMessage(message);
//            }
//        }.start();
//    }




}
