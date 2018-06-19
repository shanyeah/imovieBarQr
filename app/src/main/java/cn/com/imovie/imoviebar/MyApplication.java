package cn.com.imovie.imoviebar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.p2p.Lspi;
import android.preference.PreferenceManager;
import android.util.Log;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import cn.com.imovie.imoviebar.activity.BaseActivity;
import cn.com.imovie.imoviebar.activity.PlayerActivity;
import cn.com.imovie.imoviebar.activity.SplashActivity;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.Ewatch;
import cn.com.imovie.imoviebar.bean.Guide;
import cn.com.imovie.imoviebar.bean.LspiStatus;
import cn.com.imovie.imoviebar.bean.PlayerStatus;
import cn.com.imovie.imoviebar.bean.QRData;
import cn.com.imovie.imoviebar.bean.Release;
import cn.com.imovie.imoviebar.bean.Roomcategory;
import cn.com.imovie.imoviebar.bean.SelectedMov;
import cn.com.imovie.imoviebar.bean.Stginfo;
import cn.com.imovie.imoviebar.http.XMLDataGetter;
import cn.com.imovie.imoviebar.http.XMLDataParser;
import cn.com.imovie.imoviebar.utils.MD5Helper;
import cn.com.imovie.imoviebar.utils.NetWorkTypeUtils;

/**
 *
 * @author 刘斌
 * 2012-1-12下午4:44:01
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    public SharedPreferences mPref;
    public XMLDataParser xmlParser ;
    public String serverIp;
    public int isMainEwatch;

    public String versionName;
    public int lspiPort;
    public Date startupTime;

    public Guide guide;
    public Release release;
    public Stginfo stginfo;

    public QRData qrData;


    public String homeDir;

    public String imageDir;
    public String lspiDir;

    public BaseActivity currActivity;
    public BaseActivity mainActivity;
    public PlayerActivity playerActivity;

    public SplashActivity splashActivity;

    public PlayerStatus playerStatus;
    public ScheduledThreadPoolExecutor scheduledThreadPoolExecutor;

    public int getSelectedFooterMenu() {
        return selectedFooterMenu;
    }

    public void setSelectedFooterMenu(int selectedFooterMenu) {
        this.selectedFooterMenu = selectedFooterMenu;
    }

    public int selectedFooterMenu =0;

    public    List<SelectedMov> selectedMovList = null;

    public   int selectedRoomID = -1;

    public    List<Roomcategory>  roomList = null;


    public final static String TAG="MyApplication";
    private List<Activity> activityList;


    /*
     * (non-Javadoc)
     *
     * @see android.app.Application#onCreate()
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //init task list
        activityList = new ArrayList<Activity>();

        mPref = PreferenceManager.getDefaultSharedPreferences(this);
        xmlParser = XMLDataParser.getXmlParser();
        serverIp = mPref.getString("serverIp", "");
//        serverIp = "121.33.254.22:8231";
        isMainEwatch = mPref.getInt("isMainEwatch", 0);

        versionName = getClientVersionName(this);
        lspiPort = 8080;
        startupTime = new Date();

        homeDir = getApplicationContext().getFilesDir().getAbsolutePath();
        imageDir = homeDir + "/image";
        lspiDir = homeDir + "/lspi";
        File dir = new File(homeDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        dir = new File(imageDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        dir = new File(lspiDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        playerStatus = new PlayerStatus();

        scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1);
        configImageLoader();
        selectedMovList = new ArrayList<SelectedMov>();
        roomList = new ArrayList<Roomcategory>();



    }

    public static MyApplication getInstance() {
        return instance;
    }

    public void finishToActivity(Class cls) {

        try {
            for (int i = activityList.size() - 1; i >= 0; i--) {

                Activity activity = activityList.get(i);
                if (activity.getClass().equals(cls)) {
                    return;
                }
                activity.finish();
            }

        } catch (Exception ex) {
        }
    }

    public void addActivityIntoTask(Activity activity) {

        if (activity != null) {
            activityList.add(activity);
        }
    }









    public String getClientVersionName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            String packageName = context.getPackageName();
            return packageManager.getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        }
        return null;
    }

    public long getUptime() {
        long time1 = startupTime.getTime();
        long time2 = (new Date()).getTime();
        long diff = time2 - time1;
        return diff / 1000;
    }

    public BaseReturn initLspi() {
        BaseReturn baseReturn = new BaseReturn();

        int initResult = Lspi.init(lspiDir, lspiPort, 32);

        if (initResult != 0 && initResult != 1) {
            baseReturn.setCode(1);
            baseReturn.setMessage("数据通信模块初始化失败");
        }
        return baseReturn;
    }

    public BaseReturn queryGuide(String serverIp) {
        StringBuffer url=new StringBuffer("http://"+serverIp);
        url.append("hotel/padguide.php");
        BaseReturn baseReturn = XMLDataGetter.doGetHttpRequest(url.toString(), 3000, 10000);//限制时间

        if (baseReturn.getCode() == BaseReturn.SUCCESS) {
            xmlParser.parserGuide(baseReturn);
            guide = (Guide)baseReturn.getOtherObject();
            /*if(guide.getDefaultEwatchStatusId()==null)
                guide.setDefaultEwatchStatusId(0);


            Editor stgIdEditor = mPref.edit();
            stgIdEditor.putString("guide_stg_id", guide.getStgId());
            stgIdEditor.commit();*/

        }
        return baseReturn;
    }



    public BaseReturn getStgInfo() {

        BaseReturn baseReturn = new BaseReturn();
        baseReturn.setCode(BaseReturn.ERROR);
        Log.d(TAG, "serverIp " + serverIp);
        if ("".equals(serverIp)) return baseReturn;



        StringBuffer url = new StringBuffer("http://"+serverIp+"/stg/stg.php");
        url.append("?"+getTimeSignSuf());
        baseReturn = XMLDataGetter.doGetHttpRequest(url.toString());
        if (baseReturn.getCode() == BaseReturn.SUCCESS) {
            xmlParser.parserStginfo(baseReturn);
            stginfo = (Stginfo) baseReturn.getOtherObject();

        }




        return baseReturn;
    }









    public BaseReturn getRoomInfo() {

        BaseReturn baseReturn = new BaseReturn();
        baseReturn.setCode(BaseReturn.ERROR);

        if ("".equals(serverIp)) return baseReturn;









        StringBuffer url = new StringBuffer("http://"+serverIp+"/stg/roomcategorylist.php");

        roomList.clear();

        baseReturn = XMLDataGetter.doGetHttpRequest(url.toString());
        if (baseReturn.getCode() == BaseReturn.SUCCESS) {
            List<Roomcategory>  rmList =      xmlParser.parserRoomList(baseReturn);


            if (rmList ==null) return baseReturn;
            for (Roomcategory lm : rmList){
                if (lm==null) return baseReturn;

                if (lm.getType() ==0) {
                    roomList.add(new Roomcategory(lm.getId(),lm.getName(),lm.getType()));
                }

            }

        }




        return baseReturn;
    }

    public  String getTimeSignSuf(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        long secs = calendar.getTimeInMillis() /1000 ;

        return  "time="+secs+"&sign=" + MD5Helper.encode(secs +"stgNewV3");

    }




    public BaseReturn searchServer() {

        BaseReturn baseReturn = new BaseReturn();
        baseReturn.setCode(BaseReturn.ERROR);
        Log.d(TAG,"serverIp "+serverIp);

        if (!"".equals(serverIp)) {

           // baseReturn = queryGuide(serverIp);
        }

        if (baseReturn.getCode()!=BaseReturn.SUCCESS) {

            //这里先加网络判断
            NetworkInfo networkInfo = NetWorkTypeUtils.getAvailableNetWorkInfo(this);

            if(networkInfo==null){//无网络
                baseReturn.setCode(BaseReturn.ERROR3);
                return baseReturn;
            }



            String ipText = Lspi.search_server("htplayer", 9090);

            if (!"".equals(ipText)) {
                String[] ipArray = ipText.split(",");
                for (String ip: ipArray) {
                    baseReturn = queryGuide(ip);
                    if (baseReturn.getCode()==BaseReturn.SUCCESS) {
                        serverIp = ip;
                        break;
                    }
                }
            }else{

                baseReturn.setCode(BaseReturn.ERROR);
                return baseReturn;
            }
        }

        if (baseReturn.getCode() == BaseReturn.SUCCESS) {

            if (!mPref.getString("serverIp", "").equals(serverIp)) {
                SharedPreferences.Editor editor = mPref.edit();
                editor.putString("serverIp", serverIp);
                editor.commit();
            }
        }


        return baseReturn;
    }

    public BaseReturn queryRelease() {
        String str = "http://"+ MyApplication.getInstance().mPref.getString("serverIp","0.0.0.0")+"/release?item_name=androidtouch";
        StringBuffer url=new StringBuffer(str);
        String currVersion = getClientVersionName(this);
        if (currVersion!=null) {
            url.append("&curr_version="+versionName);
        }
        BaseReturn baseReturn = XMLDataGetter.doGetHttpRequest(url.toString());
        if (baseReturn.getCode() == BaseReturn.SUCCESS) {
            xmlParser.parserRelease(baseReturn);
            release = (Release)baseReturn.getOtherObject();
        }
        return baseReturn;
    }


    public List<Ewatch> queryEwatchList(){
        StringBuffer url=new StringBuffer(guide.getReleaseUrl());
        String currVersion = getClientVersionName(this);
        List<Ewatch> list = new ArrayList<Ewatch>();
        if (currVersion!=null) {
            url.append("&curr_version="+versionName);
        }
        BaseReturn baseReturn = XMLDataGetter.doGetHttpRequest(url.toString());
        if (baseReturn.getCode() == BaseReturn.SUCCESS) {
            xmlParser.parserEwatchList(baseReturn);
            list = (List<Ewatch>)baseReturn.getOtherObject();
        }
        return list;
    }

    public void putStatus() {
        new Thread() {
            public void run(){
                StringBuffer data=new StringBuffer();
                data.append("xml=<?xml version=\"1.0\" encoding=\"gbk\" ?>");
                data.append("<status>");
                data.append("<id>"+playerStatus.getId()+"</id>");
                data.append("<status>"+playerStatus.getStatus()+"</status>");
                data.append("<play_status>"+playerStatus.getPlayStatus()+"</play_status>");
                data.append("<hash><![CDATA["+playerStatus.getHash()+"]]></hash>");
                data.append("<time_long>"+playerStatus.getTimeLong()+"</time_long>");
                data.append("<play_position>"+playerStatus.getPlayPosition()+"</play_position>");
                data.append("<speed>"+playerStatus.getSpeed()+"</speed>");
                data.append("<file_size>"+playerStatus.getFileSize()+"</file_size>");
                data.append("<uptime>"+String.valueOf(getUptime())+"</uptime>");
                data.append("</status>");
                BaseReturn baseReturn=XMLDataGetter.doHttpRequest(guide.getStatusUrl(), data.toString(), XMLDataGetter.PUT_REQUEST);
                if (baseReturn.getCode()!=BaseReturn.SUCCESS){
                    Log.d(TAG, "上报状态失败：" + baseReturn.getMessage());
                }
                playerStatus.putStatusTimeMillis = System.currentTimeMillis();
            }
        }.start();

    }


    public BaseReturn apkFileDownload(String url, String apkFileName) {
        BaseReturn retvo = new BaseReturn(BaseReturn.SUCCESS);
        InputStream inputstream = null;
        FileOutputStream fileoutputstream = null;
        try {
            DefaultHttpClient httpclient = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(url);
            HttpEntity httpentity = httpclient.execute(httpget).getEntity();
            inputstream = httpentity.getContent();
            byte abyte[] = new byte[1024];
            if (inputstream != null) {
                long contentLength = httpentity.getContentLength();

                int j = 0;

                if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
                    retvo.setCode(BaseReturn.ERROR);
                    return retvo;
                }

                File storageDirectory= Environment.getExternalStorageDirectory();

                String fileName = storageDirectory + "/" + apkFileName;

                File file = new File(fileName);

                if(file.exists()) {
                    file.delete();
                }
                fileoutputstream = new FileOutputStream(file);
                while ((j = inputstream.read(abyte)) != -1) {
                    fileoutputstream.write(abyte, 0, j);
                }
                fileoutputstream.flush();
                retvo.setOtherText(fileName);
            }
        } catch (Exception e) {
            retvo.setCode(BaseReturn.ERROR);
            retvo.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (inputstream != null) {
                    inputstream.close();
                }
                if (fileoutputstream != null) {
                    fileoutputstream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return retvo;
    }

    public BaseReturn installNewVersion(Context context, String apkFilePath) {
        BaseReturn retvo = new BaseReturn(BaseReturn.SUCCESS);
        try {

            Log.d(TAG,"installNewVersion1=["+apkFilePath+"]");

            //Runtime.getRuntime().exec("pm install -r "+apkFilePath);//+";am start -n cn.com.imovie.player/cn.com.imovie.player.activity.SplashActivity;");

            File apkfile = new File(apkFilePath);
            if (apkfile.exists()){
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse("file://" + apkFilePath),"application/vnd.android.package-archive");
                context.startActivity(i);

                System.exit(0);
            }
        } catch (Exception e) {
            retvo.setCode(BaseReturn.ERROR);
            retvo.setMessage(e.getMessage());
            e.printStackTrace();
        }
        return retvo;
    }

    public synchronized int processPlayer(Message msg) {
        playerStatus.process(1);
        handler.sendMessage(msg);
        playerStatus.process(2);
        return playerStatus.getProcessReturn();
    }

    public synchronized String vod_state() {
        StringBuffer sb=new StringBuffer();
        sb.append("code=").append(playerStatus.getPlayStatus());
        sb.append("&path=").append(playerStatus.getPath());
        sb.append("&playtime=").append(playerStatus.getPlayPosition());
        sb.append("&duration=").append(playerStatus.getTimeLong());
        sb.append("&cachetimes=").append(0);
        sb.append("&seektimes=").append(0);
        String state=sb.toString();
        return state;
    }

    @SuppressLint("HandlerLeak")
    public Handler handler = new Handler(){

        public void handleMessage(Message message){
            switch(message.what){
                case (PlayerActivity.HTTP_REQUEST_VOD_OPEN):
                    if (mainActivity == null || currActivity == null) {
                        playerStatus.process(0);
                        return;
                    }
                    Message msg = Message.obtain();
                    Bundle data=new Bundle();
                    data.putString("url", message.getData().getString("url"));
                    data.putInt("playPosition", message.getData().getInt("playPosition"));
                    msg.setData(data);
                    msg.what = PlayerActivity.HTTP_REQUEST_VOD_OPEN;
                    if (currActivity != playerActivity) {
                        Intent intent = new Intent(currActivity, PlayerActivity.class);
                        intent.putExtra("message", msg);
                        currActivity.startActivity(intent);
                    } else {
                        playerActivity.handler.sendMessage(msg);
                    }
                    break;
                case (PlayerActivity.HTTP_REQUEST_VOD_CLOSE):
                    if (mainActivity == null || currActivity == null) {
                        playerStatus.process(0);
                        return;
                    }
                    if (currActivity != playerActivity) {
                        playerStatus.setProcessReturn(0);
                        playerStatus.process(0);
                    } else {
                        playerActivity.handler.sendEmptyMessage(PlayerActivity.HTTP_REQUEST_VOD_CLOSE);
                    }
                    break;
                case (PlayerActivity.HTTP_REQUEST_VOD_PAUSE):
                    if (mainActivity == null || currActivity == null) {
                        playerStatus.process(0);
                        return;
                    }
                    if (currActivity != playerActivity) {
                        playerStatus.process(0);
                    } else {
                        playerActivity.handler.sendEmptyMessage(PlayerActivity.HTTP_REQUEST_VOD_PAUSE);
                    }
                    break;
                case (PlayerActivity.HTTP_REQUEST_VOD_START):
                    if (mainActivity == null || currActivity == null) {
                        playerStatus.process(0);
                        return;
                    }
                    if (currActivity != playerActivity) {
                        playerStatus.process(0);
                    } else {
                        playerActivity.handler.sendEmptyMessage(PlayerActivity.HTTP_REQUEST_VOD_START);
                    }
                    break;
                case (PlayerActivity.HTTP_PUT_EWATCH_STATUS):
                    playerStatus.setStatus(1);//状态：0 未连接 1 已连接 2 有故障
                    if (playerActivity == null ) {
                        playerStatus.setPlayStatus(0);//播放状态：0 空闲 1 播放 2 暂停
                        playerStatus.setHash("");
                        playerStatus.setPath("");
                        playerStatus.setTimeLong(0);
                        playerStatus.setPlayPosition(0);
                        playerStatus.setSpeed((long) 0);
                        playerStatus.setFileSize((long) 0);
                    } else {
                        if (playerActivity.isPlaying()) {
                            playerStatus.setPlayStatus(1);
                        } else {
                            playerStatus.setPlayStatus(2);
                        }
                        LspiStatus lspiStatus = xmlParser.parserLspiGetStatus(Lspi.get_status(playerActivity.curPlayMovieUrl));
                        playerStatus.setHash(lspiStatus.getHash());
                        playerStatus.setPath(playerActivity.curPlayMovieUrl);
                        playerStatus.setTimeLong(playerActivity.getDuration() / 1000);
                        playerStatus.setPlayPosition(playerActivity.getCurrentPosition() / 1000);
                        playerStatus.setSpeed(lspiStatus.getSpeed());
                        playerStatus.setFileSize(lspiStatus.getFileSize());
                    }
                    putStatus();
                    break;
            }

        }

    };
    public void configImageLoader(){
        int threadCount;
        ImageSize size;
        ImageLoaderConfiguration config = null;
        //config = ImageLoaderConfiguration.createDefault(getApplicationContext());
            size= new ImageSize(200,240);
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading()
                .cacheInMemory()
                .cacheOnDisc()
                .bitmapConfig(Bitmap.Config.RGB_565).build();
        File cacheDir = StorageUtils.getCacheDirectory(this);
        config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(size.getWidth(), size.getHeight()) // default = device screen dimensions
                .discCacheExtraOptions(size.getWidth(), size.getHeight(), Bitmap.CompressFormat.JPEG, 65)
                .taskExecutor(AsyncTask.THREAD_POOL_EXECUTOR)
                .taskExecutorForCachedImages(AsyncTask.THREAD_POOL_EXECUTOR)
                .threadPoolSize(1) // default
                .threadPriority(Thread.NORM_PRIORITY - 1) // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(50 * 1024 * 1024)
                .discCache(new UnlimitedDiscCache(cacheDir)) // default
                .discCacheSize(200 * 1024 * 1024)
                        //.discCacheFileCount(100)
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                        //.imageDownloader(new HttpClientImageDownloader(this,Rest.getDefaultHttpClient())) // default
                .imageDecoder(new BaseImageDecoder()) // default

                .defaultDisplayImageOptions(options) // default
                        //.enableLogging()
                .build();

        config =  ImageLoaderConfiguration.createDefault(getApplicationContext());
        ImageLoader.getInstance().init(config);
    }


    public String getGuideStgId(){
        String stgId  = mPref.getString("guide_stg_id","");
        return stgId;
    }

}
