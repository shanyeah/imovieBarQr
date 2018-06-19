// Generated code from Butter Knife. Do not modify!
package cn.com.imovie.imoviebar.dialog;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import cn.com.imovie.imoviebar.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ServerSettingDialog_ViewBinding implements Unbinder {
  private ServerSettingDialog target;

  @UiThread
  public ServerSettingDialog_ViewBinding(ServerSettingDialog target, View source) {
    this.target = target;

    target.tvconServerIp = Utils.findRequiredViewAsType(source, R.id.tvconServerIp, "field 'tvconServerIp'", TextView.class);
    target.tvIp1 = Utils.findRequiredViewAsType(source, R.id.tvIp1, "field 'tvIp1'", EditText.class);
    target.tvIp2 = Utils.findRequiredViewAsType(source, R.id.tvIp2, "field 'tvIp2'", EditText.class);
    target.tvIp3 = Utils.findRequiredViewAsType(source, R.id.tvIp3, "field 'tvIp3'", EditText.class);
    target.tvIp4 = Utils.findRequiredViewAsType(source, R.id.tvIp4, "field 'tvIp4'", EditText.class);
    target.btnOk = Utils.findRequiredViewAsType(source, R.id.btnOk, "field 'btnOk'", Button.class);
    target.btnCancel = Utils.findRequiredViewAsType(source, R.id.btnCancel, "field 'btnCancel'", Button.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ServerSettingDialog target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvconServerIp = null;
    target.tvIp1 = null;
    target.tvIp2 = null;
    target.tvIp3 = null;
    target.tvIp4 = null;
    target.btnOk = null;
    target.btnCancel = null;
  }
}
