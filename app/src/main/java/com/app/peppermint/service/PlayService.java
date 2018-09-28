package com.app.peppermint.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import com.app.peppermint.R;
import com.app.peppermint.utils.LogUtils;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 音乐播放的服务组件
 * 实现功能:
 * 播放
 * 暂停
 * 获取当前歌曲的播放进度
 * 实现功能(播放模式play_mode):
 * 单曲循环
 */
public class PlayService extends Service implements OnCompletionListener, OnErrorListener {

  private MediaPlayer mPlayer;
  private int currentPosition;//当前正在播放的歌曲的位置
  private MusicUpdatrListener musicUpdatrListener;
  //创建一个单实力的线程,用于更新音乐信息
  private ExecutorService es = Executors.newSingleThreadExecutor();

  //播放模式
  public static final int SINGLE_PLAY = 3;//单曲循环
  private int play_mode = SINGLE_PLAY;//播放模式,默认为顺序播放


  private boolean isPause = false;//歌曲播放中的暂停状态

  public boolean isPause() {
    return isPause;
  }

  public PlayService() {
  }

  //MediaPlayer.Completion 播放完成 实现播放下一首功能
  //播放完成以后,判断播放模式(曲目循环方式)
  //为了实现循环后,可以显示音乐信息,需要在PlayAcivity的change里添加对应代码
  @Override
  public void onCompletion(MediaPlayer mp) {
    play();
    mPlayer.setLooping(true);
  }

  //MediaPlayer.Error 播放错误 处理实现播放下一首功能出现的错误
  @Override
  public boolean onError(MediaPlayer mp, int what, int extra) {
    mp.reset();//重启
    return false;
  }

  @Override
  public IBinder onBind(Intent intent) {
    return  null;
  }

  @Override
  public void onCreate() {
    super.onCreate();
    mPlayer = MediaPlayer.create(this,R.raw.bg_music);
    mPlayer.setOnCompletionListener(this);//注册播放完成事件
    mPlayer.setOnErrorListener(this);//注册播放错误事件
    es.execute(updateSteatusRunnable);//更新进度值
    play();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    //回收线程
    if (es != null && !es.isShutdown()) {//当进度值等于空,并且,进度值没有关闭
      es.shutdown();
      es = null;
    }
  }

  //利用Runnable来实现多线程
  /**
   * Runnable
   * Java中实现多线程有两种途径:继承Thread类或者实现Runnable接口.
   * Runnable接口非常简单,就定义了一个方法run(),继承Runnable并实现这个
   * 方法就可以实现多线程了,但是这个run()方法不能自己调用,必须由系统来调用,否则就和别的方法没有什么区别了.
   * 好处:数据共享
   */
  Runnable updateSteatusRunnable = new Runnable() {//更新状态
    @Override
    public void run() {
      //不断更新进度值
      while (true) {
        //音乐更新监听不为空,并且,媒体播放不为空,并且媒体播放为播放状态
        if (musicUpdatrListener != null && mPlayer != null && mPlayer.isPlaying()) {
          musicUpdatrListener.onPublish(getCurrentProgress());//获取当前的进度值
        }
        try {
          Thread.sleep(500);//500毫秒更新一次
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  };



  public static void startService(Context context) {
    Intent intent = new Intent(context, PlayService.class);
    context.startService(intent);
  }

  //播放
  public void play() {
    //进行播放,播放前判断
    try {
      mPlayer.start();//开始(播放)
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //暂停
  public void pause() {
    if (mPlayer.isPlaying()) {
      mPlayer.pause();
      isPause = true;
    }
  }

  //默认开始播放的方法
  public void start() {
    if (mPlayer != null && !mPlayer.isPlaying()) {
      //判断当前歌曲不等于空,并且没有在播放的状态
      mPlayer.start();
    }
  }

  //获取当前是否为播放状态,提供给MyMusicListFragment的播放暂停按钮点击事件判断状态时调用
  public boolean isPlaying() {
    if (mPlayer != null) {
      return mPlayer.isPlaying();
    }
    return false;
  }

  //获取当前的进度值
  public int getCurrentProgress() {
    if (mPlayer != null && mPlayer.isPlaying()) {
      //mPlayer不为空,并且,为播放状态
      return mPlayer.getCurrentPosition();
    }
    return 0;
  }

  //更新状态的接口(PlayService的内部接口),并在BaseActivity中实现
  public interface MusicUpdatrListener {//音乐更新监听器

    public void onPublish(int progress);//发表进度事件(更新进度条)

    public void onChange(int position); //更新歌曲位置.按钮的状态等信息
    //声明MusicUpdatrListener后,添加set方法
  }

  //set方法
  public void setMusicUpdatrListener(MusicUpdatrListener musicUpdatrListener) {
    this.musicUpdatrListener = musicUpdatrListener;
  }
}
