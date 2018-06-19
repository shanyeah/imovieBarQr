package cn.com.imovie.imoviebar.dialog;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.activity.SplashActivity;

/**
 * Created by ???? on 2015/4/3.
 */
public class ServerSettingDialog extends DialogFragment {

    public final static String TAG = "ServerSettingDialog";
    @BindView(R.id.tvconServerIp)
    TextView tvconServerIp;
    @BindView(R.id.tvIp1)
    EditText tvIp1;
    @BindView(R.id.tvIp2)
    EditText tvIp2;
    @BindView(R.id.tvIp3)
    EditText tvIp3;
    @BindView(R.id.tvIp4)
    EditText tvIp4;
    @BindView(R.id.btnOk)
    Button btnOk;
    @BindView(R.id.btnCancel)
    Button btnCancel;

    private Unbinder unbinder;

    public static final ServerSettingDialog newInstance() {
        ServerSettingDialog dialog = new ServerSettingDialog();
        Bundle args = new Bundle();

        dialog.setArguments(args);
        dialog.setStyle(STYLE_NORMAL, android.R.style.Theme_Holo_Dialog);
        dialog.setCancelable(false);
        return dialog;
    }


//    Ewatch ewatch;

//    Notify notify;
//    ResourceStringHelper resourceStringHelper;

    //    public void setNotify(Notify notify){
//        this.notify = notify;
//    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        resourceStringHelper = new ResourceStringHelper(activity.getResources());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_conserver_config, container, false);
        unbinder = ButterKnife.bind(this, view);



        String serverIP = MyApplication.getInstance().mPref.getString("serverIp","0.0.0.0");

        String[] iplist = serverIP.split("\\.");

        if (iplist !=null && iplist.length ==4)
        {
            tvIp1.setText(iplist[0]);
            tvIp2.setText(iplist[1]);
            tvIp3.setText(iplist[2]);
            tvIp4.setText(iplist[3]);

        }



        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                        String ip1,ip2,ip3,ip4;

                        ip1 = tvIp1.getText().toString().trim(); ip2 = tvIp2.getText().toString().trim();
                        ip3 = tvIp3.getText().toString().trim(); ip4 = tvIp4.getText().toString().trim();


                        if( ip1.isEmpty() ||  ip2.isEmpty()   || ip3.isEmpty()  || ip4.isEmpty() ){
                            Toast.makeText(getActivity(), "请输入正确的IP地址", Toast.LENGTH_LONG).show();

                            return;



                        }

                        SharedPreferences.Editor editor =  MyApplication.getInstance().mPref.edit();

                        editor.putString("serverIp", ip1 + "." + ip2 + "." + ip3 + "." + ip4);
                        editor.commit();


                      MyApplication.getInstance().serverIp = ip1 + "." + ip2 + "." + ip3 + "." + ip4;







                         Intent intent = new Intent(getActivity(), SplashActivity.class);
                         startActivity(intent);





                       dismiss();






            }
        });



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        getDialog().setTitle(R.string.connectServer);

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
