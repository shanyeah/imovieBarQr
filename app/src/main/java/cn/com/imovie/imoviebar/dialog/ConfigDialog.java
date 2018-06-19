package cn.com.imovie.imoviebar.dialog;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.event.Notify;

/**
 * Created by 锟节斤拷平 on 2015/3/26.
 */
public class ConfigDialog extends DialogFragment {
    @BindView(R.id.txt_text)
    TextView txt_text;
    @BindView(R.id.etIpAddr1)
    EditText ip1;
    @BindView(R.id.etIpAddr2)
    EditText ip2;
    @BindView(R.id.etIpAddr3)
    EditText ip3;
    @BindView(R.id.etIpAddr4)
    EditText ip4;
    @BindView(R.id.btn_save)
    Button btn_save;
    @BindView(R.id.btn_cancel)
    Button btn_cancel;
    @BindView(R.id.layout_progress)
    LinearLayout layout_progress;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.txt_progress)
    TextView txt_progress;

    boolean passed = false;

    Notify notify;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_config, container, false);
        ButterKnife.bind(this, view);
        txt_text.setText(String.format("锟斤拷前锟斤拷锟斤拷锟斤拷IP:%s",MyApplication.getInstance().serverIp));
        progressBar.setVisibility(View.INVISIBLE);
        txt_progress.setVisibility(View.INVISIBLE);
        getDialog().setTitle("锟斤拷锟矫凤拷锟斤拷锟斤拷");
        return view;
    }

    public void setNotify(Notify notify){
        this.notify = notify;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(notify!=null){
            Bundle bundle = new Bundle();
            notify.onNotify("ConfigDialog",bundle);
        }
    }

    @OnClick(R.id.btn_save)
    public void onSave() {
        String s1 = ip1.getText().toString();
        String s2 = ip2.getText().toString();
        String s3 = ip3.getText().toString();
        String s4 = ip4.getText().toString();
        final String sip = String.format("%s.%s.%s.%s",s1,s2,s3,s4);
        if (s1.length() > 0 && s2.length() > 0 && s3.length() > 0 && s4.length() > 0) {
            MyApplication.getInstance().serverIp =sip;

            new AsyncTask<Void, Void, BaseReturn>() {
                @Override
                protected void onPreExecute() {
                    progressBar.setVisibility(View.VISIBLE);
                    txt_progress.setVisibility(View.VISIBLE);
                    txt_progress.setText("锟斤拷锟斤拷锟斤拷锟絀P锟侥凤拷锟斤拷锟斤拷锟斤拷锟斤拷通讯...");
                }

                @Override
                protected BaseReturn doInBackground(Void... voids) {
                    return MyApplication.getInstance().searchServer();
                }

                @Override
                protected void onPostExecute(BaseReturn baseReturn) {
                    progressBar.setVisibility(View.INVISIBLE);
                    txt_progress.setVisibility(View.VISIBLE);
                    if(baseReturn.getCode()==BaseReturn.SUCCESS && sip.equalsIgnoreCase(MyApplication.getInstance().serverIp)){
                        txt_progress.setText(String.format("锟缴癸拷锟斤拷锟斤拷锟斤拷锟斤拷IP锟斤拷锟斤拷为:%s,锟斤拷锟剿筹拷应锟斤拷锟斤拷锟铰斤拷锟斤拷!",sip));
//                        if(notify!=null){
//                            Bundle bundle = new Bundle();
//                            bundle.putBoolean("restart",true);
//                            notify.onNotify(2,bundle);
//                        }
                    }else{
                        txt_progress.setText(String.format("没锟斤拷锟斤拷锟斤拷锟斤拷IP=%s锟侥凤拷锟斤拷锟斤拷!",sip));
                    }
                }
            }.execute();
        }
    }

    @OnClick(R.id.btn_cancel)
    public void onCancel(){
        dismiss();
    }
}
