package cn.com.imovie.imoviebar.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.fragment.FooterFragment;

/**
 * Created by yujinping on 2015/2/2.
 */
public class FooterDialog extends DialogFragment{
    LinearLayout.LayoutParams layoutParams;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //WindowManager m = getActivity().getWindowManager();
//        Window window = getDialog().getWindow();
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width=200;
//        window.setAttributes(lp);
        View view = inflater.inflate(R.layout.activity_menu,container,false);
        LinearLayout main = (LinearLayout)view.findViewById(R.id.layoutMain);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(200, 200);
        main.setLayoutParams(p);
        FragmentManager fragmentManager = getChildFragmentManager();
        FooterFragment footerFragment = FooterFragment.createInstance(MyApplication.getInstance().getSelectedFooterMenu(),true);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.layoutMain,footerFragment);
        fragmentTransaction.commitAllowingStateLoss();
        return view;
    }
}
