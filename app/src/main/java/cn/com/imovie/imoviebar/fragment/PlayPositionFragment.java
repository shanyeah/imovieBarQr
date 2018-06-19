package cn.com.imovie.imoviebar.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
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

import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.activity.MovieActivity;
import cn.com.imovie.imoviebar.bean.Movie;


public class PlayPositionFragment extends Fragment {
	
	private static final int GET_MOVIE_SUCCESS = 3000;
	
	private static final int GET_MOVIE_FAILURE = 3001;	
	
	private MovieActivity movieActivity;
	
	RelativeLayout layoutWaitLoading;
	ScrollView layoutNewMovieDetail;
	LinearLayout layoutStgList;
	
	ImageView ivPoster;
	TextView tvName;
	TextView tvInfo;

	public Button btnAgain;	
	public Button btnContinue;	
	
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		movieActivity = (MovieActivity)activity;
	}	
	public void updateView(Bitmap poster,Movie movie){
		if(poster!=null && movie!=null){
			ivPoster.setImageBitmap(poster);
			tvName.setText(movie.getName());
			//tvInfo.setText(movie.getMovieDesc());
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_play_position, container, false);
		
    	ivPoster = (ImageView)view.findViewById(R.id.ivPoster);
    	tvName = (TextView)view.findViewById(R.id.tvName);
		tvInfo = (TextView)view.findViewById(R.id.tvInfo);
		
		btnAgain = (Button)view.findViewById(R.id.btnAgain);
		btnAgain.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {	
				movieActivity.playPosition = 0;
				movieActivity.playMovie();
			}    		
    	});
		btnContinue = (Button)view.findViewById(R.id.btnContinue);
		btnContinue.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				movieActivity.playPosition = movieActivity.movieLastPlayTask.getPlayPosition();
				movieActivity.playMovie();
			}    		
    	}); 			
		return view;
	}	
	
	public void displayPlayPosition() {
		ivPoster.setImageBitmap(movieActivity.bigPosterBitmap);
		tvName.setText(movieActivity.movie.getName());
        tvInfo.setText("�õ�Ӱ�ܹ� "+movieActivity.playTask.getTimeLong()+" ���ӣ����ϴο����� "+movieActivity.movieLastPlayTask.getPlayPosition()+" ���ӣ�����Ҫ���ϴε�λ�ü���ۿ���" );
	}
}

