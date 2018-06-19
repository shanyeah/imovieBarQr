package cn.com.imovie.imoviebar.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.HashMap;
import java.util.LinkedHashMap;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.activity.MovieActivity;
import cn.com.imovie.imoviebar.activity.SelectedMovieActivity;
import cn.com.imovie.imoviebar.bean.MoviePrice;
import cn.com.imovie.imoviebar.bean.SelectedMov;

public class MovieDetailFragment extends Fragment {

    private static final int GET_MOVIE_SUCCESS = 3000;

    private static final int GET_MOVIE_FAILURE = 3001;

    private MovieActivity movieActivity;

    RelativeLayout layoutWaitLoading;
    ScrollView layoutNewMovieDetail;
    LinearLayout layoutStgList;

    ImageView ivPoster;
    TextView tvName;
    TextView tvTimes;
    TextView tvArea;
    TextView tvLang;
    TextView tvTimeLong;
    TextView tvMovieType;
    TextView tvCategory;
    TextView tvDirectors;
    TextView tvActors;
    TextView tvMovieDesc;
    TextView tvTitle;
    TextView tvType;

    public Button btnPlay;

    public Button btnReturn;

    public int typeMode;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        movieActivity = (MovieActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        layoutWaitLoading = (RelativeLayout)view.findViewById(R.id.layoutWaitLoading);
        ivPoster = (ImageView)view.findViewById(R.id.ivPoster);

        tvName = (TextView)view.findViewById(R.id.tvName);
        tvTimes = (TextView)view.findViewById(R.id.tvTimes);
        tvArea = (TextView)view.findViewById(R.id.tvArea);
        tvLang = (TextView)view.findViewById(R.id.tvLang);
        tvDirectors = (TextView)view.findViewById(R.id.tvDirectors);
        tvCategory = (TextView)view.findViewById(R.id.tvCategory);
        tvTimeLong = (TextView)view.findViewById(R.id.tvTimeLong);
        tvActors = (TextView)view.findViewById(R.id.tvActors);
        tvMovieDesc = (TextView)view.findViewById(R.id.tvMovieDesc);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);

        tvType = (TextView) view.findViewById(R.id.tvTypes);

        tvTitle.setText(MyApplication.getInstance().stginfo.getSimpleName());

        btnPlay = (Button)view.findViewById(R.id.btnPlay);
        typeMode = MyApplication.getInstance().mPref.getInt("BoxMode", 0);
        if(typeMode==2){
            btnPlay.setText("开始播放");
//            addMoviePlay(((MovieAdapter) gvMovieList.getAdapter()).getItem(position).getId());
        }else {
            btnPlay.setText("确定选片");
        }
        btnPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Integer mov_id = movieActivity.movieId;

                if (typeMode == 2) {
                    int playerid = MyApplication.getInstance().mPref.getInt("EwatchId", 0);
                    if (playerid > 0) {
                        if (movieActivity.movieLastPlayTask != null && movieActivity.movieLastPlayTask.getPlayPosition() >= 5) {
                            movieActivity.showPlayPosition();
                        } else {
                            movieActivity.playMovie();
                        }

                    } else {
                        Toast.makeText(getActivity(), "请先设置默认的播映机", Toast.LENGTH_LONG).show();
                    }
                } else {
                    HashMap<Integer, Float> hashMap = new LinkedHashMap<Integer, Float>();
                    for (MoviePrice mp : movieActivity.movie.getMoviePriceList()) {
                        hashMap.put(Integer.valueOf(mp.getRoomCategoryId()), mp.getPrice());
                    }
                    SelectedMov mov = (new SelectedMov(movieActivity.movieId, movieActivity.movie.getName(), movieActivity.movie.getTimeLong(), movieActivity.movie.getServiceCategoryId(), hashMap));
                    Intent intent = new Intent(getActivity(), SelectedMovieActivity.class);
                    intent.putExtra("MOV", mov);
                    startActivity(intent);
//                    movieActivity.selectEwatch();
                }
            }
        });



        btnReturn = (Button)view.findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                //movieActivity.finish();
                //getActivity().onBackPressed();
                getActivity().finish();
            }
        });

        return view;
    }




    public void displayMovieDetail() {

        if(movieActivity.bigPosterBitmap!=null){
            ivPoster.setImageBitmap(movieActivity.bigPosterBitmap);
        }else{

            ImageLoader.getInstance().displayImage(movieActivity.movie.getBigPoster(), ivPoster);
        }
        tvTimes.setText("年份: "+movieActivity.movie.getTimes());
        tvArea.setText("地区: "+movieActivity.movie.getArea());
        tvLang.setText("语言: "+movieActivity.movie.getLang());
        tvTimeLong.setText("时长: "+movieActivity.movie.getTimeLong()+"分钟");
        tvCategory.setText("分类: "+movieActivity.movie.getCategory());
        tvDirectors.setText("导演: "+movieActivity.movie.getDirector());
        tvActors.setText("主演: "+movieActivity.movie.getActor());
        tvType.setText("类型: "+movieActivity.movie.getServiceCategoryName());
        tvMovieDesc.setText(movieActivity.movie.getMovieDesc());

    }


}
