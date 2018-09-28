package com.app.peppermint.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.app.peppermint.R;
import com.app.peppermint.base.BaseActivity;
import com.app.peppermint.utils.LogUtils;

public class MainActivity extends BaseBgActivity {

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  protected void initView(Bundle savedInstanceState) {
    super.initView(savedInstanceState);
  }

  @Override
  protected void loadData() {
    super.loadData();
//    PlayService.startService(mContext);
  }

  @Override
  protected void setListener() {
    super.setListener();
  }
}
