package com.app.peppermint;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.app.peppermint.app.ACache;
import com.app.peppermint.app.AppPreferences;
import com.app.peppermint.utils.FileUtils;
import com.app.peppermint.utils.LogUtils;
import java.io.File;

/**
 * 类的作用
 *
 * @author: lizubing
 */
public class BaseApplication extends Application {
  static private BaseApplication mApplication;
  static protected Context context;
  private static ACache mCache;

  @Override
  public void onCreate() {
    super.onCreate();
    mApplication = this;
    context = this.getApplicationContext();
    initLog();
    initAppDir();
  }

  private void initAppDir() {
    LogUtils.e("initAppDir*************");
    AppPreferences.initPreferences(AppConfig.APP_PREF);//初始化sharedPreference
    FileUtils.makeDir(new File(AppConfig.MAIN_DIR));//创建程序主文件夹
    FileUtils.makeDir(new File(AppConfig.MAIN_DIR_IMG));//创建存放图片的文件夹
    FileUtils.makeDir(new File(AppConfig.MAIN_DIR_CRASH));//创建存放异常日志的文件夹
    FileUtils.makeDir(new File(AppConfig.MAIN_DIR_CACHE));//创建存放缓存的文件夹
    File aCacheDir = new File(AppConfig.MAIN_DIR_CACHE_A);
    FileUtils.makeDir(aCacheDir);//创建存放ACache的文件夹
    mCache = ACache.get(aCacheDir);
  }

  @Override
  public void onTerminate() {
    super.onTerminate();

  }

  /**
   * 返回上下文
   *
   * @return
   */
  public static Context getContext() {
    return context;
  }




  public static ACache getACache() {
    return mCache;
  }



  /**
   * 初始化LogUtils配置
   */
  private void initLog() {
    LogUtils.init(AppConfig.IS_DEVELOPING);
  }



  /**
   * 返回上下文
   *
   * @return
   */
  public static BaseApplication getInstance() {
    return mApplication;
  }

  private static String lastMsg = "";
  private static long lastTime;
  private static Toast toast;

  public static void showToast(int message) {
    showToast(message, Toast.LENGTH_SHORT, 0);
  }

  public static void showToast(String message) {
    showToast(message, Toast.LENGTH_SHORT, 0, Gravity.FILL_HORIZONTAL
        | Gravity.TOP);
  }

  public static void showToast(int message, int icon) {
    showToast(message, Toast.LENGTH_SHORT, icon);
  }

  public static void showToast(String message, int icon) {
    showToast(message, Toast.LENGTH_SHORT, icon, Gravity.FILL_HORIZONTAL
        | Gravity.TOP);
  }

  public static void showToastShort(int message) {
    showToast(message, Toast.LENGTH_SHORT, 0);
  }

  public static void showToastShort(String message) {
    showToast(message, Toast.LENGTH_SHORT, 0, Gravity.FILL_HORIZONTAL
        | Gravity.TOP);
  }

  public static void showToastShort(int message, Object... args) {
    showToast(message, Toast.LENGTH_SHORT, 0, Gravity.FILL_HORIZONTAL
        | Gravity.TOP, args);
  }

  public static void showToast(int message, int duration, int icon) {
    showToast(message, duration, icon, Gravity.FILL_HORIZONTAL
        | Gravity.TOP);
  }

  public static void showToast(int message, int duration, int icon,
      int gravity) {
    showToast(context.getString(message), duration, icon, gravity);
  }

  public static void showToast(int message, int duration, int icon,
      int gravity, Object... args) {
    showToast(context.getString(message, args), duration, icon, gravity);
  }

  /**
   * 显示自定义的toast
   *
   * @param message  toast内容
   * @param duration toast延迟
   * @param icon
   * @param gravity
   */
  public static void showToast(String message, int duration, int icon,
      int gravity) {
    if (message != null && !"".equalsIgnoreCase(message)) {
      long time = System.currentTimeMillis();
      if (!message.equalsIgnoreCase(lastMsg)
          || Math.abs(time - lastTime) > 2000) {
        View view = LayoutInflater.from(context).inflate(
            R.layout.layout_toast, null);
        ((TextView) view.findViewById(R.id.toastTV)).setText(message);
        if (icon != 0) {
          ImageView iconIV = (ImageView) view.findViewById(R.id.iconIV);
          iconIV.setVisibility(View.VISIBLE);
          iconIV.setImageResource(icon);
        }
        if (toast == null) {
          toast = new Toast(context);
        }
        toast.setView(view);
        toast.setDuration(duration);
        toast.show();

        lastMsg = message;
        lastTime = System.currentTimeMillis();
      }
    }
  }

}
