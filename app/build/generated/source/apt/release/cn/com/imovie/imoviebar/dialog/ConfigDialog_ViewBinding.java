// Generated code from Butter Knife. Do not modify!
package cn.com.imovie.imoviebar.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import cn.com.imovie.imoviebar.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ConfigDialog_ViewBinding implements Unbinder {
  private ConfigDialog target;

  private View view2130968597;

  private View view2130968589;

  @UiThread
  public ConfigDialog_ViewBinding(final ConfigDialog target, View source) {
    this.target = target;

    View view;
    target.txt_text = Utils.findRequiredViewAsType(source, R.id.txt_text, "field 'txt_text'", TextView.class);
    target.ip1 = Utils.findRequiredViewAsType(source, R.id.etIpAddr1, "field 'ip1'", EditText.class);
    target.ip2 = Utils.findRequiredViewAsType(source, R.id.etIpAddr2, "field 'ip2'", EditText.class);
    target.ip3 = Utils.findRequiredViewAsType(source, R.id.etIpAddr3, "field 'ip3'", EditText.class);
    target.ip4 = Utils.findRequiredViewAsType(source, R.id.etIpAddr4, "field 'ip4'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_save, "field 'btn_save' and method 'onSave'");
    target.btn_save = Utils.castView(view, R.id.btn_save, "field 'btn_save'", Button.class);
    view2130968597 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onSave();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_cancel, "field 'btn_cancel' and method 'onCancel'");
    target.btn_cancel = Utils.castView(view, R.id.btn_cancel, "field 'btn_cancel'", Button.class);
    view2130968589 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCancel();
      }
    });
    target.layout_progress = Utils.findRequiredViewAsType(source, R.id.layout_progress, "field 'layout_progress'", LinearLayout.class);
    target.progressBar = Utils.findRequiredViewAsType(source, R.id.progressBar, "field 'progressBar'", ProgressBar.class);
    target.txt_progress = Utils.findRequiredViewAsType(source, R.id.txt_progress, "field 'txt_progress'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ConfigDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_text = null;
    target.ip1 = null;
    target.ip2 = null;
    target.ip3 = null;
    target.ip4 = null;
    target.btn_save = null;
    target.btn_cancel = null;
    target.layout_progress = null;
    target.progressBar = null;
    target.txt_progress = null;

    view2130968597.setOnClickListener(null);
    view2130968597 = null;
    view2130968589.setOnClickListener(null);
    view2130968589 = null;
  }
}
