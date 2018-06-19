package cn.com.imovie.imoviebar.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.Status;
import cn.com.imovie.imoviebar.http.XMLDataGetter;
import cn.com.imovie.imoviebar.utils.StringHelper;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by yujinping on 2015/3/17.
 */
public class PlayerPopupActivity extends Activity {

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
    TextView left_time;
    @BindView(R.id.play_seek_bar)
    SeekBar seekBar;

    // End view
    static final String TAG = "PlayerPopupActivity";
    static final int REQUEST_DURATION = 60;
    static final int UPDATE_DURATION = 1;

    static final int COMMAND_RESUME = 1;
    static final int COMMAND_PAUSE = 2;
    static final int COMMAND_STOP = 3;
    //End constant
    ScheduledExecutorService scheduledRequestExecutorService;
    ScheduledExecutorService scheduledUpdateExecutorService;


    Status status;

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

    Handler commandHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.arg1 == BaseReturn.SUCCESS) {
                switch (msg.what) {
                    case COMMAND_RESUME:
                        break;
                }
                updateProgress();
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playmovie_console);
        ButterKnife.bind(this);
        play_info.setText("正在检查播放节目");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        startCheckService();
    }

    public void startCheckService() {
        checkStatus();
        scheduledUpdateExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledUpdateExecutorService.scheduleAtFixedRate(updateTask, 0, UPDATE_DURATION, TimeUnit.SECONDS);
        scheduledRequestExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledRequestExecutorService.scheduleAtFixedRate(checkTask, 0, REQUEST_DURATION, TimeUnit.SECONDS);
    }


    public void stopCheckService() {
        if (scheduledRequestExecutorService != null) {
            scheduledRequestExecutorService.shutdownNow();
            scheduledRequestExecutorService = null;
        }
        if (scheduledUpdateExecutorService != null) {
            scheduledUpdateExecutorService.shutdownNow();
            scheduledUpdateExecutorService = null;
        }
    }


    @OnClick(R.id.btn_prev)
    public void onPrevClick() {
        int pos = getPlayTime() - 30;
        setPlayTime(pos >= 0 ? pos : 0);
        sendCommand(commandHandler, COMMAND_RESUME, pos);
    }

    @OnClick(R.id.btn_play)
    public void onPlayClick() {
        Toast.makeText(this, "播放", Toast.LENGTH_SHORT).show();

        sendCommand(commandHandler, COMMAND_RESUME, 0);
    }

    @OnClick(R.id.btn_stop)
    public void onStopClick() {
        sendCommand(commandHandler, COMMAND_STOP, 0);
    }

    @OnClick(R.id.btn_next)
    public void onNextClick() {
        int pos = getPlayTime() + 30;
        setPlayTime(pos > seekBar.getMax() ? seekBar.getMax() : pos);
        sendCommand(commandHandler, COMMAND_RESUME, pos);
    }

    @OnClick(R.id.btn_volume_minus)
    public void onValumeMinClick() {

    }

    @OnClick(R.id.btn_volume_plus)
    public void onVolumePlusClick() {

    }

    private String getMin(int in) {
        String s;
        s = in / 60 + ":" + StringHelper.toString(in % 60, "00");
        return s;
    }

    private void setTime(String playTime, String leftTime) {
        play_time.setText(playTime);
        left_time.setText(leftTime);
        seekBar.setProgress(getPlayTime());
    }

    //checking player status
    public void checkStatus() {
        checking = true;
        Log.d(TAG, "checking remote in thread.");
        new AsyncTask<Void, Void, BaseReturn>() {
            @Override
            protected BaseReturn doInBackground(Void... params) {
                BaseReturn baseReturn = XMLDataGetter.doGetHttpRequest(MyApplication.getInstance().guide.getStatusUrl());
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
                            Log.d(TAG, "CheckPlayInfo success!");
                            currentMovieId = status.getMovieId().intValue();
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
            return;
        }
        //Log.d(TAG, "in update console!");
        Integer tag = (Integer) play_info.getTag();
        if (tag == null || tag.intValue() != status.getMovieId().intValue()) {
            //尚未获得影片信息或者获得的影片信息不一致,则更新文字UI
            play_info.setText(status.getMovieName());
            play_info.setTag(status.getMovieId());
            seekBar.setMax(status.getTimeLong());
        }
        setTime(getMin(getPlayTime()), "-" + getMin(seekBar.getMax() - getPlayTime()));
    }

    public void sendCommand(final Handler handler, final Integer command, final Integer position) {
        new AsyncTask<Void, Void, BaseReturn>() {

            @Override
            protected BaseReturn doInBackground(Void... voids) {
                StringBuilder data = new StringBuilder();
                data.append("xml=");
                data.append("<?xml version=\"1.0\" encoding=\"gbk\" ?>");
                data.append("<play_task>");
                data.append("  <id>").append(currentPlayTaskId).append("</id>");
                data.append("  <status>").append(command).append("</status>");
                data.append("  <play_position>");
                if (position != null && position.intValue() > 0)
                    data.append(position.intValue());
                data.append("</play_position>");
                data.append("</play_task>");
                BaseReturn baseReturn = XMLDataGetter.doHttpRequest(MyApplication.getInstance().guide.getPlayTaskUrl(), data.toString(), "PUT");
                return baseReturn;
            }

            @Override
            protected void onPostExecute(BaseReturn baseReturn) {

                Message message = new Message();
                message.what = command;
                message.arg1 = baseReturn.getCode();
                if (handler != null) {
                    handler.sendMessage(message);
                }
            }
        }.execute();
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
}