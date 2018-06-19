package cn.com.imovie.imoviebar.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.Status;
import cn.com.imovie.imoviebar.event.Notify;
import cn.com.imovie.imoviebar.http.XMLDataGetter;
import cn.com.imovie.imoviebar.utils.StringHelper;

/**
 * Created by yujinping on 2015/3/17.
 */
@SuppressLint({"SimpleDateFormat", "InflateParams", "HandlerLeak"})
public class PlayConsole extends PopupWindow implements Notify {
    @BindView(R.id.btn_prev)
    ImageView btn_prev;
    @BindView(R.id.btn_play)
    ImageView btn_play;
    @BindView(R.id.btn_stop)
    ImageView btn_stop;
    @BindView(R.id.btn_next)
    ImageView btn_next;
    @BindView(R.id.btn_volume_minus)
    ImageView btn_volume_minus;
    @BindView(R.id.btn_volume_plus)
    ImageView btn_volume_plus;

    @BindView(R.id.play_info)
    TextView play_info;
    @BindView(R.id.play_time)
    TextView play_time;
    @BindView(R.id.left_time)
    TextView total_time;
    @BindView(R.id.play_seek_bar)
    SeekBar seekBar;

    // End view
    static final String TAG = "PlayConsole";
    static final int UPDATE_DURATION = 1;
    static final int COMMAND_PLAY = 1;
    static final int COMMAND_PAUSE = 2;
    static final int COMMAND_STOP = 3;

    //End constant
    ScheduledExecutorService scheduledUpdateExecutorService;

    Status status;
    boolean serviceStarted=false;
    boolean checking = false;//checking the player
    boolean running = false;// the player is running.

    boolean playing = false;
    boolean pausing = false;

    int currentPlayTaskId = 0;

    int currentMovieId = 0;

    //play time sync
    int playTime = 0;

    synchronized void setPlayTime(int time) {
        this.playTime = time;
    }

    synchronized int getPlayTime() {
        return this.playTime;
    }

    Notify notify = null;
    //End var
    Runnable checkTask = new Runnable() {
        public void run() {
            checkStatus();
        }
    };

    Runnable updateTask = new Runnable() {
        @Override
        public void run() {
            new AsyncTask<Void, Void, Void>() {

                @Override
                protected Void doInBackground(Void... voids) {
                    if (running) {
                        if (isPlaying() && seekBar.getProgress() < seekBar.getMax())
                            setPlayTime(getPlayTime() + 1);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    if (running)
                        updateProgress();

                }
            }.execute();

        }
    };
Resources resources;
    public PlayConsole(Context context) {
        super(context);
        if (context instanceof Notify) {
            notify = (Notify) context;
        }
        resources = context.getResources();
        View view = LayoutInflater.from(context).inflate(R.layout.playmovie_console, null);
        setContentView(view);
        ButterKnife.bind(this, view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setAnimationStyle(R.style.AppBaseTheme);
        ColorDrawable dw = new ColorDrawable(resources.getColor(R.color.default_background));
        this.setBackgroundDrawable(dw);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                /**
                if (fromUser) {
                    setPlayTime(progress);
                }
                 */
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int progress =seekBar.getProgress();
                setPlayTime(progress);
                sendCommand(COMMAND_PLAY, progress,true);
            }
        });
    }

    public void startCheckService() {
        checkStatus();
        scheduledUpdateExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledUpdateExecutorService.scheduleAtFixedRate(updateTask, 0, UPDATE_DURATION, TimeUnit.SECONDS);
        serviceStarted = true;
    }

    public void stopCheckService() {

        if (scheduledUpdateExecutorService != null) {
            scheduledUpdateExecutorService.shutdownNow();
            scheduledUpdateExecutorService = null;
        }
        serviceStarted = false;
    }

    @OnClick(R.id.btn_prev)
    public void onPrevClick() {
        int pos = getPlayTime() - 30;
        int p = pos >= 0 ? pos : 0 ;
        setPlayTime(p);
        sendCommand(COMMAND_PLAY, p,true);
    }

    @OnClick(R.id.btn_play)
    public void onPlayClick() {

        if (playing) {
//            Log.d("---time1",":"+getPlayTime());
            sendCommand(COMMAND_PAUSE, getPlayTime(),true);
        } else {
            sendCommand(COMMAND_PLAY, getPlayTime(),true);
        }
    }

    @OnClick(R.id.btn_stop)
    public void onStopClick() {
        Log.d(TAG,"call on stop click!");
        sendCommand(COMMAND_STOP, 0,true);
    }

    @OnClick(R.id.btn_next)
    public void onNextClick() {
        int pos = getPlayTime() + 30;

        int p = pos > seekBar.getMax() ? seekBar.getMax() : pos ;
        setPlayTime(p);

        sendCommand(COMMAND_PLAY, p,true);
    }

    @OnClick(R.id.btn_volume_minus)
    public void onValumeMinClick() {

    }

    @OnClick(R.id.btn_volume_plus)
    public void onVolumePlusClick() {

    }

    private String getMin(int in) {
        String s;
        int hour = in/3600;
        int min = in % 3600;

        s = (hour>0?hour+ ":" : "") +  StringHelper.toString(min / 60, "00") + ":" + StringHelper.toString(min % 60, "00");
//        SimpleDateFormat sf = new SimpleDateFormat("hh:mm:ss");
//        Date d = new Date(in*1000);
//        Log.d(TAG, "time="+sf.format(d));
        return s;
    }

    private void setTime(String playTime, String leftTime) {
        play_time.setText(playTime);
        total_time.setText(leftTime);
        seekBar.setProgress(getPlayTime());
    }

    //checking player status
    public void checkStatus() {
        checking = true;
        Log.d(TAG, "checking remote in thread.");
        new AsyncTask<Void, Void, BaseReturn>() {
            @Override
            protected BaseReturn doInBackground(Void... params) {

//                if(!MyApplication.getInstance().getConnectFlag()) return new BaseReturn(BaseReturn.SUCCESS);

                String serverIP = MyApplication.getInstance().mPref.getString("serverIp","0.0.0.0");
                StringBuilder s = new StringBuilder("http://" + serverIP +"/ewatch/status.php?");
                s.append("ewatch_status_id=").append(MyApplication.getInstance().mPref.getInt("EwatchId", 0));
                Log.d("---url", s.toString());
                BaseReturn baseReturn = XMLDataGetter.doGetHttpRequest(s.toString());
                if (baseReturn.getCode() == BaseReturn.SUCCESS) {
                    MyApplication.getInstance().xmlParser.parserStatus(baseReturn);
                }
                return baseReturn;
            }

            @Override
            protected void onPostExecute(BaseReturn baseReturn) {
                //super.onPostExecute(baseReturn);
                checking = false;
                if (baseReturn.getCode() == BaseReturn.SUCCESS) {
                    running = true;
                    status = (cn.com.imovie.imoviebar.bean.Status) baseReturn.getOtherObject();
                    if (status != null) {
                        if (status.getPlayStatus() != null) {
                            playing = status.getPlayStatus().intValue() == 1;
                            pausing = status.getPlayStatus().intValue() == 2;
                        }
                        if (status.getMovieId() != null && status.getMovieId().intValue() > 0) {
                            setPlayTime(status.getPlayPosition());//play time
//                            Log.d(TAG, "CheckPlayInfo success!");
                            currentMovieId = status.getMovieId().intValue();
                            if (status.getPlayTaskId() != null)
                                currentPlayTaskId = status.getPlayTaskId();
                            updateProgress();
                        }
                    }
                } else {
                    running = false;
                }
            }
        }.execute();
    }

    public void updateProgress() {
        if (status == null) {
            Log.e(TAG, "Status is null");
            play_info.setText(resources.getString(R.string.loading_play_status));
            return;
        }

        if (!isShowing())
            return;
        //Log.d(TAG, "in update console!");
        //Integer tag = (Integer) play_info.getTag();
        //if (tag == null || tag.intValue() != status.getMovieId().intValue()) {
        play_info.setText(status.getMovieName());
        play_info.setTag(status.getMovieId());
        seekBar.setMax(status.getTimeLong());
        //}
//        Log.d("---time", ":" + getPlayTime());
        setTime(getMin(getPlayTime()), getMin(seekBar.getMax()));
        if (playing)
            btn_play.setImageResource(R.drawable.pause);
        else
            btn_play.setImageResource(R.drawable.play);
    }

    public void sendCommand(final Integer command, final Integer position,final boolean type) {
        try {
            new AsyncTask<Void, Void, BaseReturn>() {
                @Override
                protected BaseReturn doInBackground(Void... voids) {

                    Integer ewatchStatusId = MyApplication.getInstance().mPref.getInt("EwatchId", 0);
                    String serverIP = MyApplication.getInstance().mPref.getString("serverIp","0.0.0.0");
                    StringBuilder s = new StringBuilder("http://" + serverIP +"/ewatch/status.php?");
                    s.append("ewatch_status_id=").append(ewatchStatusId);
                    BaseReturn baseReturn = XMLDataGetter.doGetHttpRequest(s.toString());
                    if (baseReturn.getCode() == BaseReturn.SUCCESS) {
                        MyApplication.getInstance().xmlParser.parserStatus(baseReturn);
                        status = (cn.com.imovie.imoviebar.bean.Status) baseReturn.getOtherObject();
                        if(status.getPlayTaskId()==null) return baseReturn;
                        currentPlayTaskId = status.getPlayTaskId();
                    }
                    StringBuilder data = new StringBuilder();
                    data.append("xml=");
                    data.append("<?xml version=\"1.0\" encoding=\"gbk\" ?>");
                    data.append("<play_task>");
                    data.append("<id>").append(currentPlayTaskId).append("</id>");
                    data.append("<status>").append(command).append("</status>");
                    data.append("<play_position>");
                    data.append(position.intValue());
                    data.append("</play_position>");
                    data.append("</play_task>");
//                    Log.d("---data", data.toString());
                    baseReturn = XMLDataGetter.doHttpRequest("http://" + serverIP +"/movie/playtask.php?ewatch_status_id="+ewatchStatusId, data.toString(), "PUT");
                    return baseReturn;
                }

                @Override
                protected void onPostExecute(BaseReturn baseReturn) {

                    if (baseReturn.getCode() == BaseReturn.SUCCESS && type) {
                        afterCommand(command);
                    }
                }
            }.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void afterCommand(int cmd){
        switch (cmd) {
            case COMMAND_PLAY:
                playing=true;
                running=true;
                playing=true;
                checkStatus();
                //updateProgress();
                sendPlayStatus(true);
                break;
            case COMMAND_PAUSE:
                playing=false;
                running=true;
                pausing=true;
                //updateProgress();
                checkStatus();
                break;
            case COMMAND_STOP:
                setPlayTime(0);
                running = false;
                playing = false;
                pausing = false;
                updateProgress();
                stopCheckService();
                sendPlayStatus(false);
                dismiss();
                break;
        }
        updateProgress();
    }

    void sendPlayStatus(boolean enable){
        if (notify != null) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("enable", enable);
            notify.onNotify("PlayConsole", bundle);
        }
    }
    public boolean isPlaying() {
        if (status == null)
            return false;
        else
            return playing;
    }

    public boolean isPausing() {
        if (status == null)
            return false;
        else
            return pausing;
    }

    @Override
    public void onNotify(String tag, Bundle bundle) {
        if(bundle.getBoolean("idle",false)) {
            play_info.setText(resources.getString(R.string.play_is_free));
            try {
                Thread.sleep(3000);
                //dismiss();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return;
        }
        //if (isShowing())
        {
            if(serviceStarted==false)
                startCheckService();
            if (bundle.getBoolean("running", false)) {
                playing = bundle.getBoolean("playing", false);
                pausing = bundle.getBoolean("pausing", false);
            }else{
                stopCheckService();
            }
            updateProgress();
        }
    }
}
