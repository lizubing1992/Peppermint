package com.app.peppermint;

import android.os.Environment;
import java.io.File;

/**
 * 类的作用 app 配置
 *
 * @author: lizubing
 */
public class AppConfig {

  // APP开发状态  true-开发状态    false-正式上线状态(关闭log日志等)
  public static boolean IS_DEVELOPING = true;

  public static class Net {

    //SESSION缓存参数
    public static String ACCESS_TOKEN = "";
    public static String COOKIE = "";
    public static String USER_ID = "";
    public static String ALI_OSS_SCHEMA = "https";
    public static String ALI_REMOTE = "gamma.nongfadai.com";
    //正式环境(上线配置)
    /*public static String IP = "https://gamma.nongfadai.com";
    public static String PORT = "443";*/
    public static String IP = "http://10.1.60.41";
    public static String PORT = "80";
    public static String IMAGE_URL_HEAD = "https://gamma.nongfadai.com";//图片地址
    public static String PREFIX = "/";
    public static String HOST = IP + ":" + PORT;
    public static final String CLIENT_ID = "6b8b9faf-de7b-4589-982e-0414faa39158";
    public static final String CLIENT_SECRET = "815c8dba-1fb9-4711-b574-51897151ae23";
    public static final String RESPONSE_TYPE = "code";
    public static final String GRANT_TYPE = "authorization_code";
    public static final String COOKIE_HEAD = "136a3d03-9748-4f83-a54f-9b2a93f979a1=";

    /**
     * 配置网络环境
     */
    public static void setupConfig(String ip, String port, String imageHead) {
      if (ip.contains("https")) {
        ALI_REMOTE = ip.substring(8, ip.length());
        IP = ip;
        PORT = "";
        PREFIX = "";
        ALI_OSS_SCHEMA = "https";
      } else {
        ALI_REMOTE = ip.substring(7, ip.length());
        ALI_OSS_SCHEMA = "http";
        if (ip.contains("http")) {
          IP = ip;
        } else {
          IP = "http://" + ip;
        }
        PREFIX = "";
        PORT = ":" + port + "/";
      }
      IMAGE_URL_HEAD = imageHead;
      HOST = IP + PORT;
    }
  }

  // 默认存放文件下载的路径
  public final static String DEFAULT_SAVE_FILE_PATH = Environment
      .getExternalStorageDirectory()
      + File.separator
      + "gamma"
      + File.separator + "download" + File.separator;

  public static final String PACKAGE_NAME = "com.nongfaidai.gamma";
  public static final String APP_NAME = "gamma";

  public static final String APP_PREF = "pref_" + APP_NAME;
  public static final String MAIN_DIR =
      BaseApplication.getContext().getExternalCacheDir() + File.separator;//自定义的SD卡app文件夹
  public static final String MAIN_DIR_IMG = AppConfig.MAIN_DIR + File.separator + "img";
  public static final String MAIN_DIR_CRASH = AppConfig.MAIN_DIR + File.separator + "crash";
  public static final String MAIN_DIR_CACHE = AppConfig.MAIN_DIR + File.separator + "cache";
  public static final String MAIN_DIR_CACHE_A = AppConfig.MAIN_DIR_CACHE + File.separator + "a";

  public static final String WX_APPID = "wxa71330c69d438ebb";
  public static final String WX_SECRET = "e6fc9fd31fd0f224ce16aeb4439b608b";
  public static final String QQ_APPID = "1105037126";
  public static final String QQ_APPKEY = "4m6r9wOP4WQiYy3Y";

  public static final String SERVICE_PHONE = "4009669290";


}