package cn.com.imovie.imoviebar.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;
import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.fragment.FooterFragment;
import cn.com.imovie.imoviebar.notify.OnFooterItemClick;

/**
 * Created by yujinping on 2015/2/5.
 */
public class MenuActivity extends Activity implements OnFooterItemClick{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.activity_menu);
        FragmentManager fragmentManager = getFragmentManager();
        FooterFragment footerFragment = FooterFragment.createInstance(MyApplication.getInstance().getSelectedFooterMenu(),true);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutMain,footerFragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

	@Override
	public void onFooterItemClick(int position) {
		setResult(position);
		finish();
	}
}