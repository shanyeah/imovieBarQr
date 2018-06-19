package cn.com.imovie.imoviebar.dialog;


import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.activity.HtMainActivity;
import cn.com.imovie.imoviebar.bean.BaseReturn;
import cn.com.imovie.imoviebar.bean.ErrorInfo;
import cn.com.imovie.imoviebar.http.XMLDataGetter;
import cn.com.imovie.imoviebar.utils.StringHelper;

public class LoginDialog extends Dialog {

    private Context context;
    private int typeMode = 0;
    private Button btnOk;
    private Button btnCancel;
    private EditText userName;
    private EditText password;
    private View view;
    private HtMainActivity activity;



    public LoginDialog(final Context context,final View view,final HtMainActivity activity) {
        super(context, android.R.style.Theme_Holo_Dialog);
        setContentView(R.layout.dialog_login);
        this.context = context;
        this.view = view;
        this.activity = activity;
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        typeMode = MyApplication.getInstance().mPref.getInt("BoxMode", 0);
        userName = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        setTitle("登录设置");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(StringHelper.isEmpty(userName.getText().toString()))
                {
                    Toast.makeText(context,"帐号不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(StringHelper.isEmpty(password.getText().toString()))
                {
                    Toast.makeText(context,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                login(userName.getText().toString(),password.getText().toString(),v);


//                dismiss();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    public void login(final String name, final String password,final View v){
//        mProgressBar.setVisibility(View.VISIBLE);
        new AsyncTask<Void,Void,ErrorInfo>(){
            @Override
            protected ErrorInfo doInBackground(Void... params) {
                String serverIP = MyApplication.getInstance().mPref.getString("serverIp","0.0.0.0");
                StringBuilder url = new StringBuilder("http://" + serverIP +"/stg/login.php?");
                url.append("project_type=2");
                url.append("&project_version="+"");
                url.append("&device_id=0");
                url.append("&user_name=" + name);
                url.append("&password=" + password);
                BaseReturn baseReturn = XMLDataGetter.doHttpRequest(url.toString(), null, XMLDataGetter.GET_REQUEST);
                if(baseReturn.getCode()==BaseReturn.SUCCESS){
                    MyApplication.getInstance().xmlParser.parserLogin(baseReturn);
                    ErrorInfo info = (cn.com.imovie.imoviebar.bean.ErrorInfo) baseReturn.getOtherObject();
                    return info;
                }else{
                    return null;
                }
            }

            @Override
            protected void onPostExecute(ErrorInfo info) {
                if(info==null){
                    Toast.makeText(context,"登录失败",Toast.LENGTH_SHORT).show();
                }else{
                    if(info.getCode().equals("1")){
                        Toast.makeText(context,""+info.getMessage(),Toast.LENGTH_SHORT).show();
                    }else{
//                        Toast.makeText(context,""+info.getMessage(),Toast.LENGTH_SHORT).show();
                        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(
                                    v.getWindowToken(), 0);
                        }

                        PopupMenu popupMenu = new PopupMenu(context, view);
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem menuItem) {
                                switch (menuItem.getItemId()){
                                    case R.id.item_setserver:
                                        ServerSettingDialog serverSettingDialog = ServerSettingDialog.newInstance();
                                        serverSettingDialog.show(activity.getFragmentManager(),"find Server");
                                       break;
                                    case R.id.item_setmode:
                                        BoxModeDialog dialog = new BoxModeDialog(context,activity);
                                        dialog.show();
                                        break;
                                }

                                return false;
                            }
                        });
                        popupMenu.inflate(R.menu.popup_menu);

                        popupMenu.show();
                                dismiss();
                    }
                }
//                mProgressBar.setVisibility(View.INVISIBLE);
            }
        }.execute();
    }
}

