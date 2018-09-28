package com.app.peppermint.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import com.app.peppermint.R;
import com.app.peppermint.app.AppManager;
import com.app.peppermint.base.BaseDialogFragment;

/**
 * 类的作用
 *
 * @author: lizubing
 */
public class ExitDialogFragment extends BaseDialogFragment {

  private ImageView exitIV,returnIV;
  @Override
  protected int getLayout() {
    return R.layout.dialog_fragment_exit;
  }


  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setStyle(DialogFragment.STYLE_NORMAL, R.style.select_stat_dialog);
  }
  @Override
  protected void initView(View rootView) {
    super.initView(rootView);
    getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
    returnIV = rootView.findViewById(R.id.returnIV);
    exitIV = rootView.findViewById(R.id.exitIV);
  }

  @Override
  protected void setListener() {
    super.setListener();
    returnIV.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ExitDialogFragment.this.dismiss();
      }
    });
    exitIV.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        AppManager.getAppManager().exitApp();
      }
    });
  }
}
