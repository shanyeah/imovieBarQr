package cn.com.imovie.imoviebar.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.PauseOnScrollListener;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.activity.HtMainActivity;
import cn.com.imovie.imoviebar.activity.MovieActivity;
import cn.com.imovie.imoviebar.bean.Area;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.MovieCondition;
import cn.com.imovie.imoviebar.bean.MovieQueryCondition;
import cn.com.imovie.imoviebar.bean.Moviecat;
import cn.com.imovie.imoviebar.bean.Page;
import cn.com.imovie.imoviebar.bean.SimpleMovie;
import cn.com.imovie.imoviebar.bean.Times;
import cn.com.imovie.imoviebar.config.ImageDisplayConfig;
import cn.com.imovie.imoviebar.event.OnAutoLoad;
import cn.com.imovie.imoviebar.http.XMLDataGetter;
import cn.com.imovie.imoviebar.notify.ReloadNotify;
import cn.com.imovie.imoviebar.utils.AsyncImageLoader;
import cn.com.imovie.imoviebar.utils.StringHelper;

public class MovieListFragment extends Fragment implements ReloadNotify {
    public final static String TAG = "MovieListFragment";

    private int mPageNum;
    private HtMainActivity mActivity;

    private MovieCondition movieCondition;
    private Page moviePage;
    private List<SimpleMovie> movieList=new ArrayList<SimpleMovie>();
    private MovieQueryCondition movieQueryCondition;

    private AsyncImageLoader mAsyncImageLoader;

    private ProgressBar mProgressBar;

    GridView.LayoutParams itemLayoutParams;


    public static final int DEFAULT_FOOTER_SELECTION = 0;
    //MovieList
    GridView gvMovieList;
    MovieAdapter adapter;


    //鍒嗙被
    RadioGroup mCatalogGroup;
    List<Moviecat> moviecatList = new ArrayList<Moviecat>();
//	Button btTest;

    RadioGroup mAreaGroup;
    List<Moviecat> mMovieAreaList = new ArrayList<Moviecat>();
    //骞翠唤
    RadioGroup mYearGroup;
    int groupTextSize = 20;
    List<Moviecat> mMovieYearList = new ArrayList<Moviecat>();
    int mMovieCatalogSelection = 0;
    int mMovieAreaSelection = 0;
    int mMovieYearSelection = 0;
    boolean mClear = true;
    private OnAutoLoad mAutoLoadListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (movieCondition == null) {
            movieCondition = new MovieCondition();
            movieCondition.setPageNo(1);
            movieCondition.setPageSize(20);
            movieCondition.setOrderByType(0);
        }
        mAutoLoadListener = new OnAutoLoad(new OnAutoLoad.OnAutoLoadCallBack() {

            @Override
            public void execute(String url) {
                movieCondition.setPageNo(movieCondition.getPageNo()+1);
                mClear = false;
                reload();
            }
        });
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = (HtMainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);

        mProgressBar = (ProgressBar)view.findViewById(R.id.progress);
        mProgressBar.setVisibility(View.INVISIBLE);

        //鍒嗙被
        mCatalogGroup = (RadioGroup) view.findViewById(R.id.catalog_radio_group);
        //鍦板尯
        mAreaGroup = (RadioGroup) view.findViewById(R.id.area_radio_group);
        //骞翠唤
        mYearGroup = (RadioGroup) view.findViewById(R.id.year_radio_group);

        adapter = new MovieAdapter(inflater);


        movieCondition.setPageSize(20);//姣忔鏌ヨ
        groupTextSize = 20;



        int numColumn = 4;//鍒楁暟
        itemLayoutParams = mActivity.getGridLayoutParams(numColumn);

        gvMovieList = (GridView) view.findViewById(R.id.gvMovieList);

        gvMovieList.setNumColumns(numColumn);



        gvMovieList.setAdapter(adapter);
        boolean pauseOnScroll = ImageDisplayConfig.pauseOnScroll(); // or true
        boolean pauseOnFling = ImageDisplayConfig.pauseOnFling(); // or false
        PauseOnScrollListener listener = new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll,
                pauseOnFling, mAutoLoadListener);
        gvMovieList.setOnScrollListener(listener);
        gvMovieList.setFocusableInTouchMode(true);
        gvMovieList.setFocusable(true);
        gvMovieList.requestFocusFromTouch();
        gvMovieList.requestFocus();

        gvMovieList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                try {
//                    int typeMode = MyApplication.getInstance().mPref.getInt("BoxMode", 0);
//                    if(typeMode==2){
//                        addMoviePlay(((MovieAdapter) gvMovieList.getAdapter()).getItem(position).getId());
//                    }else {
                        Intent intent = new Intent(mActivity, MovieActivity.class);
                        intent.putExtra("movieId", ((MovieAdapter) gvMovieList.getAdapter()).getItem(position).getId());
                        mActivity.startActivityForResult(intent, 0x100);
//                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        new QueryMovieListTask().execute(movieCondition);

        Log.d(TAG, "鐢靛奖鍒楄〃MovieListFragment鍒涘缓瀹屾瘯锛�");
        return view;
    }


    public class MovieAdapter extends BaseAdapter {

        private LayoutInflater mInflater;
        DisplayImageOptions options = null;

        public MovieAdapter(LayoutInflater inflater) {
            mInflater = inflater;

            options = new DisplayImageOptions.Builder()
                    .resetViewBeforeLoading()
                    .cacheInMemory()
                    .cacheOnDisc()
                    .bitmapConfig(Bitmap.Config.RGB_565).build();
        }

        public int getCount() {
            if (movieList == null) {
                return 0;
            }
            return movieList.size();
        }

        public SimpleMovie getItem(int position) {
            return movieList.get(position);
        }

        public long getItemId(int position) {
            return getItem(position).getId();
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.fragment_movie_item, null);
                convertView.setLayoutParams(itemLayoutParams);
            }
            SimpleMovie simpleMovie = getItem(position);
            ImageView movieImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
            TextView movieName = (TextView) convertView.findViewById(R.id.tvMovieName);
            TextView tvEwatchName = (TextView) convertView.findViewById(R.id.tvEwatchName);
            tvEwatchName.setVisibility(View.GONE);
            movieName.setText(simpleMovie.getName());
            ImageLoader.getInstance().displayImage(simpleMovie.getBigPoster(), movieImage,options);
            return convertView;
        }

    }

    class QueryMovieListTask extends AsyncTask<MovieCondition, Void, BaseReturn> {
        @SuppressWarnings("unchecked")
        @Override
        protected BaseReturn doInBackground(MovieCondition... params) {

            StringBuffer urlSB1 = new StringBuffer("http://"+MyApplication.getInstance().serverIp+"/movie/simplemovielist.php");
            urlSB1.append("?").append(params[0].toUrlString());
            BaseReturn baseReturn = XMLDataGetter.doGetHttpRequest(urlSB1.toString());

            if (baseReturn.getCode() == BaseReturn.SUCCESS) {
                MyApplication.getInstance().xmlParser.parserPage(baseReturn);
                moviePage = (Page) baseReturn.getOtherObject();
                MyApplication.getInstance().xmlParser.parserMovieList(baseReturn);
                if(mClear)
                    movieList.clear();
                List<SimpleMovie> movies =(List<SimpleMovie>) baseReturn.getOtherObject();

                Map<Integer,SimpleMovie> map = new HashMap<Integer,SimpleMovie>();
                for(SimpleMovie sm : movieList){
                    map.put(sm.getId(),sm);
                }
                for(SimpleMovie sm : movies){
                    if(map.get(sm.getId())==null) movieList.add(sm);
                }


            } else {
                return baseReturn;
            }
//            urlSB = new StringBuffer(MyApplication.getInstance().guide.getMovieQueryConditionUrl());


           StringBuffer urlSB = new StringBuffer("http://"+MyApplication.getInstance().serverIp+"/stg/moviequerycondition.php");
            if (params[0].getMoviecatId() != null) {
                urlSB.append("&moviecat_id=").append(params[0].getMoviecatId());
            }
            if (params[0].getTimes() != null) {
                try {
                    urlSB.append("&times=").append(URLEncoder.encode(params[0].getTimes()+"", "GB18030"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
            if (params[0].getArea() != null) {
                try {

                    urlSB.append("&area=").append(URLEncoder.encode(params[0].getArea(), "GB18030"));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

             baseReturn = XMLDataGetter.doGetHttpRequest(urlSB.toString());

            if (baseReturn.getCode() == BaseReturn.SUCCESS) {
                MyApplication.getInstance().xmlParser.parserMovieQueryCondition(baseReturn);
                movieQueryCondition = (MovieQueryCondition) baseReturn.getOtherObject();
                //鍒嗙被
                int count = 0;
                moviecatList.clear();
                Moviecat m = new Moviecat();
                m.setName("全部");
                m.setId(0);
                moviecatList.add(m);
                for (Moviecat e : movieQueryCondition.getMoviecatList()) {
                    moviecatList.add(e);
                }
                //鍦板尯
                count = 0;
                mMovieAreaList.clear();
                if (movieQueryCondition.getAreaList() != null) {
                    m = new Moviecat();
                    m.setName("全部");
                    m.setId(0);
                    mMovieAreaList.add(m);
                    for (Area a : movieQueryCondition.getAreaList()) {
                        Moviecat c = new Moviecat();
                        c.setName(a.getArea());
                        c.setId(++count);
                        mMovieAreaList.add(c);
                    }
                }
                //骞翠唤
                mMovieYearList.clear();
                count = 0;
                m = new Moviecat();
                m.setName("全部");
                m.setId(0);
                mMovieYearList.add(m);
                for (Times t : movieQueryCondition.getTimesList()) {
                    Moviecat c = new Moviecat();
                    c.setName(t.getTimes());
                    c.setId(++count);
                    mMovieYearList.add(c);
                }
            }
            return baseReturn;
        }

        @Override
        protected void onPreExecute() {
            //mActivity.showLayoutWaitLoading();
            //if(!mProgressBar.isShown())
                mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(BaseReturn result) {
            adapter.notifyDataSetChanged();

            //if(mProgressBar.isShown())
                mProgressBar.setVisibility(View.INVISIBLE);
            if(moviecatList.size()>0){
                mCatalogGroup.removeAllViews();
                for(Moviecat c:moviecatList){
                    RadioButton b = (RadioButton)LayoutInflater.from(getActivity()).inflate(R.layout.radio,mCatalogGroup,false);

                    b.setTextSize(groupTextSize);
                    b.setText(c.getName());
                    b.setTag(c);
                    if(mMovieCatalogSelection==c.getId().intValue()) {
                        //b.setSelected(true);
                        b.performClick();
                    }
                    b.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Moviecat m = (Moviecat) v.getTag();
                            movieCondition.setMoviecatId(m.getId());
                            mMovieCatalogSelection=m.getId();
                            movieCondition.setPageNo(1);
                            movieCondition.setSearch("");
                            mClear=true;
                            reload();
                        }
                    });
                    mCatalogGroup.addView(b);
                }
            }
            if(mMovieAreaList.size()>0){
                mAreaGroup.removeAllViews();
                for(Moviecat c:mMovieAreaList){
                    RadioButton b = (RadioButton)LayoutInflater.from(getActivity()).inflate(R.layout.radio,mYearGroup,false);
                    b.setTextSize(groupTextSize);
                    b.setText(c.getName());
                    b.setTag(c);
                    if(mMovieAreaSelection==c.getId().intValue()) {
                        //b.setSelected(true);
                        b.performClick();
                    }
                    b.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Moviecat m = (Moviecat) v.getTag();
                            movieCondition.setArea(m.getName());
                            mMovieAreaSelection = m.getId();
                            movieCondition.setPageNo(1);
                            movieCondition.setSearch("");
                            mClear=true;
                            reload();
                        }
                    });
                    mAreaGroup.addView(b);
                }
            }

            if(mMovieYearList.size()>0){
                mYearGroup.removeAllViews();
                for(Moviecat c:mMovieYearList){
                    RadioButton b = (RadioButton)LayoutInflater.from(getActivity()).inflate(R.layout.radio,mYearGroup,false);
                    b.setTextSize(groupTextSize);
                    b.setText(c.getName());
                    b.setTag(c);
                    if(mMovieYearSelection==c.getId().intValue()) {
                        //b.setSelected(true);
                        b.performClick();
                    }
                    b.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            Moviecat m = (Moviecat) v.getTag();
                            movieCondition.setTimes(m.getName());
                            mMovieYearSelection = m.getId();
                            movieCondition.setPageNo(1);
                            movieCondition.setSearch("");
                            mClear=true;
                            reload();
                        }
                    });
                    mYearGroup.addView(b);
                }
            }
            if(!StringHelper.isEmpty(movieCondition.getSearch())  && movieList.size()==0 ){
                mActivity.showToast("没有找到相关的影片", 0);
            }

            mActivity.showLayoutMain();




        }

    }

    @Override
    public void reload() {
        new QueryMovieListTask().execute(movieCondition);
    }

    @Override
    public void setPageNo(int page){
        movieCondition.setPageNo(page);
    }

    /**
     * 璁剧疆鍏抽敭瀛楁悳绱㈢殑鏃跺�欏氨涓嶇敤鍏朵粬鏉′欢
     * @param search
     */
    public void setMovieConditionSearch(String search){
        movieCondition.setSearch(search);
            movieCondition.setMoviecatId(0);
            movieCondition.setArea("");
            movieCondition.setTimes("");

    }

}
