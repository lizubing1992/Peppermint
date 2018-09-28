package com.app.peppermint.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.app.peppermint.BaseApplication;
import com.app.peppermint.R;
import com.app.peppermint.app.AppManager;
import com.app.peppermint.base.BaseBgActivity;
import com.app.peppermint.utils.LogUtils;

public class MainActivity extends BaseBgActivity {

  private ImageView menu1IV;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  protected void initView(Bundle savedInstanceState) {
    super.initView(savedInstanceState);
    menu1IV = findViewById(R.id.menu1IV);
  }

  @Override
  protected void loadData() {
    super.loadData();
//    PlayService.startService(mContext);
  }

  @Override
  protected void setListener() {
    super.setListener();
    menu1IV.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(mContext,ImageDetailActivity.class));
      }
    });
  }

  private static long mLastExitTime;

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
      if ((System.currentTimeMillis() - mLastExitTime) > 2000) {
        BaseApplication.showToast("再按一次返回键退出");
        mLastExitTime = System.currentTimeMillis();
      } else {
        AppManager.getAppManager().exitApp();
      }
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

}
