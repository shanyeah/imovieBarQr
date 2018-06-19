package cn.com.imovie.imoviebar.dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import cn.com.imovie.imoviebar.MyApplication;
import cn.com.imovie.imoviebar.R;
import cn.com.imovie.imoviebar.activity.BalconyActivity;
import cn.com.imovie.imoviebar.activity.HtMainActivity;

public class BoxModeDialog extends Dialog {

    private Context context;
    private int typeMode = 0;
    private Button btnOk;
    private Button btnCancel;
    private RadioButton rbSaleMode;
    private RadioButton rbPlayMode;
    private HtMainActivity activity;


    public BoxModeDialog(final Context context, final HtMainActivity activity) {
        super(context, android.R.style.Theme_Holo_Dialog);
        setContentView(R.layout.dialog_boxmode_config);
        this.context = context;
        this.activity = activity;
        btnOk = (Button) findViewById(R.id.btnOk);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        typeMode = MyApplication.getInstance().mPref.getInt("BoxMode", 0);
        rbSaleMode = (RadioButton) findViewById(R.id.rb_server);
        rbPlayMode = (RadioButton) findViewById(R.id.rb_single);
        setTitle("模式设置");

        if (0 == typeMode || 1 == typeMode) {
            rbSaleMode.setChecked(true);
        } else if (2 == typeMode) {
            rbPlayMode.setChecked(true);
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        if (rbSaleMode.isChecked()) {
                            setBoxMode(1);
//                            activity.footerFragment.reload();
                            activity.tv_title.setText(MyApplication.getInstance().stginfo.getSimpleName());
                        } else if (rbPlayMode.isChecked()) {
                            setBoxMode(2);
                            Intent intent = new Intent(context,BalconyActivity.class);
                            context.startActivity(intent);
                        }
                dismiss();
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

    /**
     * 设置机顶盒网络配置模式
     * @param isServerMode  0、1:选片锁售 2：选片播放
     */
    public void setBoxMode(int isServerMode) {
        try {
            SharedPreferences.Editor editor = MyApplication.getInstance().mPref.edit();
            if (1 == isServerMode) {
                editor.putInt("BoxMode", 1);
            } else if (2 == isServerMode) {
                editor.putInt("BoxMode", 2);
            }
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

