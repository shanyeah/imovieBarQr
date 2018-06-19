package cn.com.imovie.imoviebar.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.activity.HtMainActivity;
import cn.com.imovie.imoviebar.adapter.EwatchAdapter;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.Ewatch;
import cn.com.imovie.imoviebar.bean.Page;
import cn.com.imovie.imoviebar.bean.PlayTaskLog;
import cn.com.imovie.imoviebar.config.ImageDisplayConfig;
import cn.com.imovie.imoviebar.event.Notify;
import cn.com.imovie.imoviebar.event.OnAutoLoad;
import cn.com.imovie.imoviebar.http.XMLDataGetter;
import cn.com.imovie.imoviebar.notify.ReloadNotify;
import cn.com.imovie.imoviebar.utils.DateHelper;
import cn.com.imovie.imoviebar.utils.StringHelper;

/**
 * Created by yujinping on 2015/2/2.
 */
public class InfoFragment extends Fragment implements ReloadNotify, Notify {

    public final static String TAG = "InfoFragment";

    GridView listView;

    ListView playListView;

    HtMainActivity mActivity;
    OnAutoLoad mAutoLoadListener = null;
    int pageNo =1;

    List<Ewatch> list = new ArrayList<Ewatch>();
    List<PlayTaskLog> playTaskLogList = new ArrayList<PlayTaskLog>();

    EwatchAdapter adapter;
    PlayTaskLogAdapter playTaskLogAdapter;
    private ProgressBar mProgressBar;
    private boolean mClear=true;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAutoLoadListener = new OnAutoLoad(new OnAutoLoad.OnAutoLoadCallBack() {

            @Override
            public void execute(String url) {
                pageNo++;
                mClear = false;

//                loadPlayLogList();//鍙槸鍒锋柊鎾斁璁板綍
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info,container,false);

        mProgressBar = (ProgressBar)view.findViewById(R.id.progress);
        mProgressBar.setVisibility(View.INVISIBLE);


        int numColumn = 4;
        GridView.LayoutParams itemLayoutParams = mActivity.getGridLayoutParams(numColumn);

        listView = (GridView) view.findViewById(R.id.listView);
        playListView = (ListView) view.findViewById(R.id.playListView);


        adapter = new EwatchAdapter(inflater,list,itemLayoutParams);




        listView.setNumColumns(numColumn);
        listView.setAdapter(adapter);

        playTaskLogAdapter = new PlayTaskLogAdapter(inflater);

        boolean pauseOnScroll = ImageDisplayConfig.pauseOnScroll(); // or true
        boolean pauseOnFling = ImageDisplayConfig.pauseOnFling(); // or false
        PauseOnScrollListener listener = new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll,
                pauseOnFling, mAutoLoadListener);



        playListView.setAdapter(playTaskLogAdapter);
        playListView.setFocusableInTouchMode(true);
        playListView.setFocusable(true);
        playListView.setOnScrollListener(listener);
//        playListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                PlayTaskLog item = playTaskLogList.get(position);
//                if (item != null) {
//                    Intent intent = new Intent(getActivity(), MovieActivity.class);
//                    intent.putExtra("movieId", item.getMovieId());
//                    getActivity().startActivity(intent);
//                }
//            }
//        });

        reload();
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof HtMainActivity)
            this.mActivity = (HtMainActivity)activity;

    }

    @Override
    public void reload() {

//        loadEwatchList();

//        loadPlayLogList();
    }

    @Override
    public void setPageNo(int page){

    }

    @Override
    public void onNotify(String tag, Bundle bundle) {
        reload();
    }

    public void loadEwatchList(){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                String url = MyApplication.getInstance().guide.getEwatchListUrl();
                BaseReturn baseReturn = XMLDataGetter.doHttpRequest(url, null, XMLDataGetter.GET_REQUEST);
                if(baseReturn.getCode()==BaseReturn.SUCCESS){
                    MyApplication.getInstance().xmlParser.parserEwatchList(baseReturn);
                    List<Ewatch> ewatches = (List<Ewatch>)baseReturn.getOtherObject();
                    /**
                    List<Ewatch> ewatches = new ArrayList<Ewatch>();
                    for(int i = 0;i<10;i++){
                        Ewatch e = new Ewatch();
                        e.setId(i+1);
                        e.setPrice("25.00");
                        e.setName(i+"==e");
                        if(i%2==1){//0 绌洪棽 1 鎾斁 2 鏆傚仠
                            e.setPlayStatus(1);
                            e.setMovieName("娴嬭瘯绉湪鑺傜洰绉湪鑺傜洰");
                            e.setBigPoster("http://192.168.5.197/poster/454/9454_big.jpg");
                        }else{
                            e.setPlayStatus(0);
                            e.setMovieName("");
                            e.setBigPoster("");
                        }

                        ewatches.add(e);
                    }
                    */

                    if(ewatches!=null){
                        list.clear();
                        list.addAll(ewatches);
                    }

                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                adapter.notifyDataSetChanged();
            }
        }.execute();
    }

    public void loadPlayLogList(){
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... params) {

                StringBuilder s = new StringBuilder();
                s.append(MyApplication.getInstance().guide.getPlayTaskLogListUrl());
                s.append("&page_size=8&page_no="+pageNo);

                BaseReturn baseReturn = XMLDataGetter.doHttpRequest(s.toString(), null, XMLDataGetter.GET_REQUEST);

                if(baseReturn.getCode()==BaseReturn.SUCCESS){

                    MyApplication.getInstance().xmlParser.parserPage(baseReturn);
                    Page page = (Page) baseReturn.getOtherObject();

                    MyApplication.getInstance().xmlParser.parserPlayTaskLogList(baseReturn);
                    List<PlayTaskLog> list = (List<PlayTaskLog>)baseReturn.getOtherObject();

                    if(mClear || pageNo==1)
                        playTaskLogList.clear();

                    if(list!=null && list.size()>0 && pageNo<=page.getTotalPage()) {

                        Map<Integer,PlayTaskLog> map = new HashMap<Integer,PlayTaskLog>();
                        for(PlayTaskLog pl : playTaskLogList){
                            map.put(pl.getMovieId(),pl);
                        }
                        for(PlayTaskLog pl : list){
                            if(map.get(pl.getMovieId())==null) playTaskLogList.add(pl);
                        }

                    }
                    if(pageNo>page.getTotalPage()) pageNo=page.getTotalPage();

                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mProgressBar.setVisibility(View.INVISIBLE);
                playTaskLogAdapter.notifyDataSetChanged();
            }



        }.execute();
    }

    class PlayTaskLogAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        DisplayImageOptions options = null;

        public PlayTaskLogAdapter(LayoutInflater layoutInflater){
            this.mInflater = layoutInflater;
            options = new DisplayImageOptions.Builder()
                    .resetViewBeforeLoading()
                    .cacheInMemory()
                    .cacheOnDisc()
                    .bitmapConfig(Bitmap.Config.RGB_565).build();

        }
        @Override
        public int getCount() {
            return playTaskLogList ==null?0: playTaskLogList.size();
        }

        @Override
        public PlayTaskLog getItem(int position) {
            return playTaskLogList ==null?null: playTaskLogList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return playTaskLogList ==null?0:getItem(position).getId();
        }

        @SuppressWarnings("unchecked")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null)
                convertView = mInflater.inflate(R.layout.fragment_play_task_log_item,null);


            ImageView poster = (ImageView)convertView.findViewById(R.id.ivPoster);
            TextView movie_name = (TextView) convertView.findViewById(R.id.txt_movie_name);
            TextView description=(TextView)convertView.findViewById(R.id.txt_description);
            TextView times = (TextView)convertView.findViewById(R.id.tvTimes);
            TextView actor = (TextView)convertView.findViewById(R.id.tvActors);
            TextView director = (TextView)convertView.findViewById(R.id.tvDirectors);
            TextView play_info = (TextView)convertView.findViewById(R.id.tvPlayInfo);

            TextView play_log_time = (TextView)convertView.findViewById(R.id.play_log_time);

            PlayTaskLog playTaskLog = getItem(position);

            movie_name.setText(""+playTaskLog.getName());
            times.setText("骞翠唤锛�" + playTaskLog.getTimes());
            times.setText("骞翠唤锛�" + playTaskLog.getTimes());
            description.setText(""+playTaskLog.getShortDesc());
            actor.setText("婕斿憳锛�"+playTaskLog.getActors());
            director.setText("瀵兼紨锛�" + playTaskLog.getDirectors());
            play_info.setText("鍖呮埧锛�"+(StringHelper.isEmpty(playTaskLog.getEwatchName())?"---":playTaskLog.getEwatchName())+" 浠锋牸锛�"+(StringHelper.isEmpty(playTaskLog.getPrice())?"---":playTaskLog.getPrice()));
            play_log_time.setText("鎾斁鏃堕棿锛�"+ DateHelper.toString(playTaskLog.getCreateTime(), "MM-dd HH:mm"));
            ImageLoader.getInstance().displayImage(playTaskLog.getBigPoster(), poster,options);

            return convertView;
        }
    }
}
