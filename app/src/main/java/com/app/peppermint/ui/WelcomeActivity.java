package com.app.peppermint.ui;

import android.content.Intent;

import com.app.peppermint.R;
import com.app.peppermint.base.BaseActivity;
import com.app.peppermint.ui.activity.MainActivity;

public class WelcomeActivity extends BaseActivity {


  @Override
  protected int getLayoutId() {
    return R.layout.activity_welcome;
  }

  @Override
  protected void loadData() {
    mHandler.postDelayed(new Runnable() {
      @Override
      public void run() {
        startActivity(new Intent(mContext, MainActivity.class));
        finish();
      }
    }, 3000);
  }
}
