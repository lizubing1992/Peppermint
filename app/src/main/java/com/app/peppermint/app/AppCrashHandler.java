/*
 * All rights Reserved, Designed By 农金圈 2017年3月30日 下午9:17:12
 */
package com.app.peppermint.app;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.util.Log;
import com.app.peppermint.AppConfig;
import com.app.peppermint.BaseApplication;
import com.app.peppermint.utils.StringUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类的作用 捕捉APP中的全局异常，并且处理
 * @author: lizubing
 */
public class AppCrashHandler implements Thread.UncaughtExceptionHandler {

    private static String TAG = AppConfig.APP_NAME;
    private static String CRASH = "crashNumber";
    private int crashNumber = StringUtils.parseInt(BaseApplication.getACache().getAsString(CRASH));

    // CrashHandler 实例
    private static AppCrashHandler INSTANCE = new AppCrashHandler();

    // 系统默认的 UncaughtException 处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * 保证只有一个 CrashHandler 实例
     */
    private AppCrashHandler() {
    }

    /**
     * 获取 CrashHandler 实例 ,单例模式
     */
    public static AppCrashHandler getInstance() {
        return INSTANCE;
    }

    // 用于格式化日期,作为日志文件名的一部分
    @SuppressLint("SimpleDateFormat")
    private DateFormat formatter = new SimpleDateFormat("yyyyMMdd(HHmmss)");

    /**
     * 初始化
     */
    public void init() {
        // 获取系统默认的 UncaughtException 处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该 CrashHandler 为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当 UncaughtException 发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            AppManager.getAppManager().exitApp();
            android.os.Process.killProcess(android.os.Process.myPid()); //关闭进程
            System.exit(1); //退出程序
        }
    }


    /**
     * 自定义错误处理，收集错误信息，发送错误报告等操作均在此完成
     *
     * @param ex
     * @return true：如果处理了该异常信息；否则返回 false
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast 来显示异常信息
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                BaseApplication.showToast("对不起，出现未知异常...");
                Looper.loop();
            }
        }.start();
        String errorStr = getErrorInfo(ex);
        printError(errorStr);
        BaseApplication.getACache().put("CrashNumber",crashNumber++);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 打印异常信息
     *
     * @param errorStr
     */
    private void printError(String errorStr) {
        Log.e(TAG, "================================打印异常=================================");
        Log.e(TAG, errorStr);
        Log.e(TAG, "================================打印结束=================================");
    }

    /**
     * 获取异常的相应信息的字符串，并打印Log
     *
     * @param ex
     * @return
     */
    private String getErrorInfo(Throwable ex) {
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        return writer.toString();
    }

    /**
     * 保存错误信息到文件中
     *
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private String saveToFile(String errorStr) {
        FileOutputStream fos = null;
        try {
            String name = "Log" + formatter.format(new Date()) + ".txt";
            String path = AppConfig.MAIN_DIR_CRASH;
            File file = new File(path, name);
            fos = new FileOutputStream(file);
            fos.write(errorStr.getBytes());
            fos.flush();
            return name;
        } catch (Exception e) {
            Log.e(TAG, "an error occured while writing file...", e);
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}