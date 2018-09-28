package com.app.peppermint.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.app.peppermint.R;
import com.app.peppermint.base.BaseActivity;
import com.app.peppermint.utils.LogUtils;

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
      }
    }, 3000);
  }
}
