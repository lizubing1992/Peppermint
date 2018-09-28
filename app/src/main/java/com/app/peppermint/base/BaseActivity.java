package com.app.peppermint.base;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;
import com.app.peppermint.app.AppManager;
import com.app.peppermint.handler.IHandler;
import com.app.peppermint.handler.UIHandler;

/**
 * 类的作用 Activity基类
 *
 * @author: lizubing
 */
public abstract class BaseActivity extends AppCompatActivity implements
    IHandler {

  protected Context mContext;
  protected UIHandler mHandler;

  @Nullable
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    super.onCreate(savedInstanceState);
    this.mContext = this;
    setContentView(getLayoutId());
    initHandler();
    //初始化页面控件
    initView(savedInstanceState);
    //加载数据
    loadData();
    setListener();
    initRxBus();
    AppManager.getAppManager().addActivity(this);
  }

  protected void initView(Bundle savedInstanceState) {
  }

  protected void initRxBus() {
  }

  /**
   * xml文件id
   *
   * @return id
   */
  protected int getLayoutId() {
    return 0;
  }

  /**
   * 加载数据
   */
  protected void loadData() {

  }

  private void initHandler() {
    mHandler = new UIHandler(getMainLooper());
    mHandler.setHandler(this);
  }

  protected void setListener() {
  }

  /**
   * handler接受message
   */
  @Override
  public void handleMessage(Message msg) {

  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    AppManager.getAppManager().finishActivity(this);
  }
}
