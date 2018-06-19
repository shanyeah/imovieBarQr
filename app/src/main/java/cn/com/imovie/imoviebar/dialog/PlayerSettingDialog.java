package cn.com.imovie.imoviebar.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.Ewatch;
import cn.com.imovie.imoviebar.event.Notify;
import cn.com.imovie.imoviebar.http.XMLDataGetter;
import cn.com.imovie.imoviebar.utils.ResourceStringHelper;

/**
 * Created by ???? on 2015/4/3.
 */
public class PlayerSettingDialog extends DialogFragment {

    public final static String TAG="PlayerSettingDialog";

    public static final PlayerSettingDialog newInstance(Ewatch ewatch){
        PlayerSettingDialog dialog = new PlayerSettingDialog();
        Bundle args = new Bundle();
        args.putSerializable("ewatch", ewatch);
        dialog.setArguments(args);
        dialog.setStyle(STYLE_NORMAL, android.R.style.Theme_Holo_Dialog);
        dialog.setCancelable(false);
        return dialog;
    }

    @BindView(R.id.txt_ewatch_sn)TextView txt_ewatch_sn;
    @BindView(R.id.edt_ewatch_name)EditText edt_ewatch_name;
    @BindView(R.id.chk_set_default)CheckBox chk_set_default;

    Ewatch ewatch;

    Notify notify;
    ResourceStringHelper resourceStringHelper;

    public void setNotify(Notify notify){
        this.notify = notify;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        resourceStringHelper = new ResourceStringHelper(activity.getResources());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_update_player,container,false);
        ButterKnife.bind(this, view);
        ewatch = (Ewatch) getArguments().getSerializable("ewatch");
        getDialog().setTitle("设置播映机");
        if(ewatch!=null) {
            txt_ewatch_sn.setText(resourceStringHelper.format(R.string.fmt_current_sn,ewatch.getSn()));
            edt_ewatch_name.setText(ewatch.getName());
            boolean defaultPlayer= MyApplication.getInstance().guide.getDefaultEwatchStatusId().intValue()==ewatch.getId().intValue();
            chk_set_default.setChecked(defaultPlayer);
        }
        return view;
    }

    @OnClick(R.id.btn_cancel)
    public void onCancel(){
        dismiss();
    }
    @OnClick(R.id.btn_ok)
    public void onOk(){
        new AsyncTask<Void,Void,BaseReturn>(){
            @Override
            protected BaseReturn doInBackground(Void... params) {
                String url = MyApplication.getInstance().guide.getEwatchStatusUrl();
                StringBuilder data = new StringBuilder();
                data.append("ewatch_status_id=").append(ewatch.getId());
                data.append("&ewatch_sn=").append(ewatch.getSn());
                data.append("&name=").append(edt_ewatch_name.getText());

                BaseReturn baseReturn = XMLDataGetter.doHttpRequest(url, data.toString(), "PUT");

                return baseReturn;
            }

            @Override
            protected void onPostExecute(BaseReturn baseReturn) {
                super.onPostExecute(baseReturn);
                if(baseReturn.getCode()==BaseReturn.SUCCESS){
                    Toast.makeText(getActivity(),"设置播放机成功",Toast.LENGTH_LONG).show();
                    if(chk_set_default.isChecked()) {
                        MyApplication.getInstance().guide.setDefaultEwatchStatusId(ewatch.getId());
                        /**
                        SharedPreferences.Editor editor = MyApplication.getInstance().mPref.edit();
                        editor.putInt("default_ewatch_status_id",ewatch.getId());
                        editor.commit();
                        */
                    }
                    if(notify!=null) {
                        Bundle bundle = new Bundle();
                        notify.onNotify("setting",bundle);
                    }
                    dismiss();
                }
            }
        }.execute();
    }
}
