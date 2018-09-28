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
    setListener();
    //加载数据
    loadData();
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

  protected void setOnItemClickListener() {

  }


  /**
   * 是否使用fragment
   *
   * @return 默认使用
   */
  public boolean useFragment() {
    return true;
  }


  /**
   * 刷新数据
   */
  protected void refreshData() {

  }


  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
      finishActivity();
      return true;
    }
    return super.onKeyDown(keyCode, event);
  }

  /**
   * @return 返回false则不显示返回navigation icon
   */
  protected boolean isCanBack() {
    return true;
  }


  protected void finishActivity() {
    finish();
  }

  protected void setListener() {
  }

  /**
   * 依赖注入的入口
   */
  protected void componentInject() {
    initHandler();
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


  @Override
  public void onBackPressed() {
    super.onBackPressed();
  }

  public void changButtomBarColor(int color) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
      getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
      getWindow().setNavigationBarColor(getResources().getColor(color));
    }
  }
}
