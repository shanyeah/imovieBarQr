// Generated code from Butter Knife. Do not modify!
package cn.com.imovie.imoviebar.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import cn.com.imovie.imoviebar.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PlayerSettingDialog_ViewBinding implements Unbinder {
  private PlayerSettingDialog target;

  private View view2130968589;

  private View view2130968593;

  @UiThread
  public PlayerSettingDialog_ViewBinding(final PlayerSettingDialog target, View source) {
    this.target = target;

    View view;
    target.txt_ewatch_sn = Utils.findRequiredViewAsType(source, R.id.txt_ewatch_sn, "field 'txt_ewatch_sn'", TextView.class);
    target.edt_ewatch_name = Utils.findRequiredViewAsType(source, R.id.edt_ewatch_name, "field 'edt_ewatch_name'", EditText.class);
    target.chk_set_default = Utils.findRequiredViewAsType(source, R.id.chk_set_default, "field 'chk_set_default'", CheckBox.class);
    view = Utils.findRequiredView(source, R.id.btn_cancel, "method 'onCancel'");
    view2130968589 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onCancel();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_ok, "method 'onOk'");
    view2130968593 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onOk();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PlayerSettingDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.txt_ewatch_sn = null;
    target.edt_ewatch_name = null;
    target.chk_set_default = null;

    view2130968589.setOnClickListener(null);
    view2130968589 = null;
    view2130968593.setOnClickListener(null);
    view2130968593 = null;
  }
}
