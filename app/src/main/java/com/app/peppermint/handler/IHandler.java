/*
 * All rights Reserved, Designed By 农金圈 2017年3月30日 下午9:17:12
 */
package com.app.peppermint.handler;

/**
 * 接收到消息处理回调接口.
 */

import android.os.Message;

public interface IHandler {
    void handleMessage(Message msg);
}
