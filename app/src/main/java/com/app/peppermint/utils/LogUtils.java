package com.app.peppermint.utils;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class LogUtils {

    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final int JSON_INDENT = 4;
    private static final int JSON = 0x7;
    private static boolean IS_SHOW_LOG = true;
    /*private static final int STACK_TRACE_INDEX = 5;
    public static final String SUFFIX = ".java";*/

    private static final int V = 0x1;
    private static final int D = 0x2;
    private static final int I = 0x3;
    private static final int W = 0x4;
    private static final int E = 0x5;

    public static void init(boolean isShowLog) {
        IS_SHOW_LOG = isShowLog;
    }

    public static void v(String msg) {
        printLog(V, null, msg);
    }

    public static void v(String tag, String msg) {
        printLog(V, tag, msg);
    }

    public static void d(String msg) {
        printLog(D, null, msg);
    }

    public static void d(String tag, String msg) {
        printLog(D, tag, msg);
    }

    public static void i(String msg) {
        printLog(I, null, msg);
    }

    public static void i(String tag, String msg) {
        printLog(I, tag, msg);
    }

    public static void w(String msg) {
        printLog(W, null, msg);
    }

    public static void w(String tag, String msg) {
        printLog(W, tag, msg);
    }

    public static void e(String msg) {
        printLog(E, null, msg);
    }

    public static void e(String tag, String msg) {
        printLog(E, tag, msg);
    }

    public static void json(String msg){
        printLog(JSON, null, msg);
    }

    public static void json(String tag, String msg){
        printLog(JSON, tag, msg);
    }

    private static void printLog(int type, String tag, String msg) {

        if(!IS_SHOW_LOG || msg == null){
            return;
        }

        if(tag == null){
            tag = getDefaultTag();
        }

        switch (type) {
            case V:
                Log.v(tag, msg);
                break;
            case D:
                Log.d(tag, msg);
                break;
            case I:
                Log.i(tag, msg);
                break;
            case W:
                Log.w(tag, msg);
                break;
            case E:
                Log.e(tag, msg);
                break;
            case JSON:
                printJson(tag, msg);
                break;
            default: break;
        }
    }

    /**
     * 获取默认tag
     * @return
     */
    private static String getDefaultTag() {
        return "peppermint";
    }

    /**
     * 获取log调用处的代码行数信息
     * @return
     */
    /*private static String getPrintLineInfo(){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement targetElement = stackTrace[STACK_TRACE_INDEX];

        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }
        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();

        if (lineNumber < 0) {
            lineNumber = 0;
        }

        String methodNameShort = methodName.substring(0, 1).toUpperCase() + methodName.substring(1);

        return "[(" + className + ":" + lineNumber + ")#" + methodNameShort + "]";
    }*/

    private static void printJson(String tag, String msg) {

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(JSON_INDENT);
            } else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(JSON_INDENT);
            } else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }

        Log.i(tag, "╔════════════════════════════════════════");
        message = LINE_SEPARATOR + message;
        String[] lines = message.split(LINE_SEPARATOR);
        for (String line : lines) {
            Log.i(tag, "║ " + line);
        }
        Log.i(tag, "╚════════════════════════════════════════");
    }

}
