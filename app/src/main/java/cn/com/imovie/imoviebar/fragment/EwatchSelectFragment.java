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
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.activity.MovieActivity;
import cn.com.imovie.imoviebar.adapter.EwatchAdapter;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.Ewatch;
import cn.com.imovie.imoviebar.bean.Movie;
import cn.com.imovie.imoviebar.http.XMLDataGetter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class EwatchSelectFragment extends Fragment {

    public final static String TAG = "EwatchSelectFragment";

	private MovieActivity movieActivity;


    GridView listView;
    List<Ewatch> list = new ArrayList<Ewatch>();
    EwatchAdapter adapter;
    ProgressBar mProgressBar;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		movieActivity = (MovieActivity)activity;
	}	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ewatch_select, container, false);

        mProgressBar = (ProgressBar)view.findViewById(R.id.progress);
        mProgressBar.setVisibility(View.INVISIBLE);

        int numColumn = 4;//鍒楁暟

        GridView.LayoutParams itemLayoutParams = movieActivity.getGridLayoutParams(numColumn);

        listView = (GridView) view.findViewById(R.id.listView);

        adapter = new EwatchAdapter(inflater,list,itemLayoutParams);

        listView.setNumColumns(numColumn);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Ewatch item = list.get(position);
                Log.d(TAG,"i===="+item.getPlayStatus()+"   "+item.getMovieName());
                if(!item.isFree()){
                    movieActivity.showToast("閫夋嫨鐨勫寘鎴縖" + item.getName() + "]涓嶆槸绌洪棽鐘舵�侊紝璇烽�夋嫨绌洪棽鍖呮埧", 0);
                }else{
                    movieActivity.ewatch = item;
                    movieActivity.showConfirmDialog("纭畾鍦ㄥ寘鎴縖"+item.getName()+"]鎾斁褰辩墖[" + movieActivity.movie.getName() + "]鍚楋紵", new View.OnClickListener() {
                        @Override
                        public void onClick(View arg0) {
                            movieActivity.playMovie();
                            movieActivity.confirmDialog.dismiss();
                        }
                    });
                }
            }
        });

		//movieActivity.playMovie();

        //loadEwatchList();//鍖呮埧
		return view;
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
                    for(int i = 0;i<15;i++){
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
            protected void onPreExecute() {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                mProgressBar.setVisibility(View.INVISIBLE);
                adapter.notifyDataSetChanged();
            }


        }.execute();
    }
}

