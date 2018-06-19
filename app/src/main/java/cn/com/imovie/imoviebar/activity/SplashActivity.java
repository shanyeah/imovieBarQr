package cn.com.imovie.imoviebar.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.fragment.HtSplashFragment;

public class SplashActivity extends BaseActivity {

	private HtSplashFragment splashFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.setContentView(R.layout.activity_splash);
		super.onCreate(savedInstanceState);


		MyApplication.getInstance().splashActivity = this;

		 splashFragment = new HtSplashFragment();
		
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.layoutMain, splashFragment);
		ft.show(splashFragment);
		ft.commitAllowingStateLoss();		
		
		showLayoutMain();	        
	}

	@Override
	protected void onDestroy() {
		MyApplication.getInstance().splashActivity = null;
		super.onDestroy();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);

		/*Fragment splashFragment = new HtSplashFragment();

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.add(R.id.layoutMain, splashFragment);
		ft.show(splashFragment);
		ft.commitAllowingStateLoss();*/

		if (splashFragment != null) {
			splashFragment.searchServer();
		}
		else{
			splashFragment = new HtSplashFragment();

			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.add(R.id.layoutMain, splashFragment);
			ft.show(splashFragment);
			ft.commitAllowingStateLoss();




		}

		showLayoutMain();

	}

}