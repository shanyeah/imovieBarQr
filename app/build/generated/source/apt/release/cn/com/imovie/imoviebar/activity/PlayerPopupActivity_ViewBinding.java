// Generated code from Butter Knife. Do not modify!
package cn.com.imovie.imoviebar.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import cn.com.imovie.imoviebar.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PlayerPopupActivity_ViewBinding implements Unbinder {
  private PlayerPopupActivity target;

  private View view2130968595;

  private View view2130968594;

  private View view2130968600;

  private View view2130968592;

  private View view2130968601;

  private View view2130968602;

  @UiThread
  public PlayerPopupActivity_ViewBinding(PlayerPopupActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PlayerPopupActivity_ViewBinding(final PlayerPopupActivity target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.btn_prev, "field 'btn_prev' and method 'onPrevClick'");
    target.btn_prev = Utils.castView(view, R.id.btn_prev, "field 'btn_prev'", ImageView.class);
    view2130968595 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPrevClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_play, "field 'btn_play' and method 'onPlayClick'");
    target.btn_play = Utils.castView(view, R.id.btn_play, "field 'btn_play'", ImageView.class);
    view2130968594 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onPlayClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_stop, "field 'btn_stop' and method 'onStopClick'");
    target.btn_stop = Utils.castView(view, R.id.btn_stop, "field 'btn_stop'", ImageView.class);
    view2130968600 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onStopClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_next, "field 'btn_next' and method 'onNextClick'");
    target.btn_next = Utils.castView(view, R.id.btn_next, "field 'btn_next'", ImageView.class);
    view2130968592 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onNextClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_volume_minus, "field 'btn_volume_minus' and method 'onValumeMinClick'");
    target.btn_volume_minus = Utils.castView(view, R.id.btn_volume_minus, "field 'btn_volume_minus'", ImageView.class);
    view2130968601 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onValumeMinClick();
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_volume_plus, "field 'btn_volume_plus' and method 'onVolumePlusClick'");
    target.btn_volume_plus = Utils.castView(view, R.id.btn_volume_plus, "field 'btn_volume_plus'", ImageView.class);
    view2130968602 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onVolumePlusClick();
      }
    });
    target.play_info = Utils.findRequiredViewAsType(source, R.id.play_info, "field 'play_info'", TextView.class);
    target.play_time = Utils.findRequiredViewAsType(source, R.id.play_time, "field 'play_time'", TextView.class);
    target.left_time = Utils.findRequiredViewAsType(source, R.id.left_time, "field 'left_time'", TextView.class);
    target.seekBar = Utils.findRequiredViewAsType(source, R.id.play_seek_bar, "field 'seekBar'", SeekBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PlayerPopupActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btn_prev = null;
    target.btn_play = null;
    target.btn_stop = null;
    target.btn_next = null;
    target.btn_volume_minus = null;
    target.btn_volume_plus = null;
    target.play_info = null;
    target.play_time = null;
    target.left_time = null;
    target.seekBar = null;

    view2130968595.setOnClickListener(null);
    view2130968595 = null;
    view2130968594.setOnClickListener(null);
    view2130968594 = null;
    view2130968600.setOnClickListener(null);
    view2130968600 = null;
    view2130968592.setOnClickListener(null);
    view2130968592 = null;
    view2130968601.setOnClickListener(null);
    view2130968601 = null;
    view2130968602.setOnClickListener(null);
    view2130968602 = null;
  }
}
