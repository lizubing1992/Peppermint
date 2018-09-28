/*
 * All rights Reserved, Designed By 农金圈  2018年04月26日16:37
 */
package com.app.peppermint.utils;

import android.app.ActivityManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.EditText;
import android.widget.TextView;
import com.app.peppermint.BaseApplication;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类的作用
 *
 * @author: lizubing
 */
public class StringUtils {

  public final static String MOBILE = "^[1][0-9]{10}$";
  public final static String PASS_WORD = "^.{6,16}$";


  /**
   * 是否 不为空字符串 或 null
   */
  public static String isNullShowNoTime(String s) {
    if (s != null && s.length() > 0) {
      return s;
    } else {
      return "未知时间和地址";
    }
  }


  /**
   * @param rationalString 度分秒格式的经纬度字符串,形如: 114/1,23/1,538547/10000 或 30/1,28/1,432120/10000
   * @param ref 东西经 或 南北纬 的标记 S南纬 W西经
   * @return double格式的 经纬度
   */
  public static float convertRationalLatLonToFloat(String rationalString, String ref) {
    if (StringUtils.isEmpty(rationalString) || StringUtils.isEmpty(ref)) {
      return 0;
    }

    try {
      String[] parts = rationalString.split(",");

      String[] pair;
      pair = parts[0].split("/");
      double degrees = parseDouble(pair[0].trim(), 0)
          / parseDouble(pair[1].trim(), 1);

      pair = parts[1].split("/");
      double minutes = parseDouble(pair[0].trim(), 0)
          / parseDouble(pair[1].trim(), 1);

      pair = parts[2].split("/");
      double seconds = parseDouble(pair[0].trim(), 0)
          / parseDouble(pair[1].trim(), 1);

      double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
      if (("S".equals(ref) || "W".equals(ref))) {
        return (float) -result;
      }
      return (float) result;
    } catch (NumberFormatException e) {
      return 0;
    } catch (ArrayIndexOutOfBoundsException e) {
      return 0;
    } catch (Throwable e) {
      return 0;
    }
  }

  private static double parseDouble(String doubleValue, double defaultValue) {
    try {
      return Double.parseDouble(doubleValue);
    } catch (Throwable t) {
      return defaultValue;
    }
  }

  /**
   * 是否是url地址或html类容
   */
  public static boolean isUrlOrHtml(String str) {
    return str.startsWith("http") || str.contains("html");
  }


  public static String formatIdCardDate(String date, boolean isShow) {
    String year = date.substring(0, 4);
    String mouth = date.substring(4, 6);
    String day = date.substring(6, 8);
    if (isShow) {
      return year + "年" + mouth + "月" + day + "日";
    } else {
      return year + "/" + mouth + "/" + day;
    }
  }


  public static int getFileByKB(long length) {
    double d = (double) length / 1024.0;
    return (int) (d + 1);
  }

  /**
   * Url decode by "UTF-8"
   */
  public static String decodeUrl(String str) {
    try {
      return URLDecoder.decode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }


  /**
   * string内容转化为float
   */
  public static float parseFloat(String str) {
    float num = 0;
    try {
      if (StringUtils.isNotEmpty(str)) {
        num = Float.parseFloat(str);
      }
    } catch (Exception e) {
      LogUtils.e("number format exception:" + str);
    }
    return num;
  }

  /**
   * string内容转化为double
   */
  public static double parseDouble(String str) {
    double num = 0;
    try {
      if (StringUtils.isNotEmpty(str)) {
        num = Double.parseDouble(str);
      }
    } catch (Exception e) {
      LogUtils.e("number format exception:" + str);
    }
    return num;
  }


  /**
   * 判断是否是新版本
   *
   * @param currentVersion 当前版本名  格式：1.0.0
   * @param latestVersion 最新发布的版本名   格式：1.0.0
   * @return true 有最新版本
   */
  public static boolean hasNewVersion(String currentVersion, String latestVersion) {
    if (StringUtils.isNotEmpty(latestVersion) && StringUtils.isNotEmpty(currentVersion)
        && !StringUtils.equals(latestVersion, currentVersion)) {
      //去小数点
      String lv = latestVersion.replaceAll("\\.", "");
      //去小数点
      String cv = currentVersion.replaceAll("\\.", "");
      LogUtils.i("==========版本信息========最新版本:" + lv + "/当前版本:" + cv);
      if (lv.length() < 3) {
        //转化为3位数
        lv += "0";
      }
      if (cv.length() < 3) {
        //转化为3位数
        cv += "0";
      }
      LogUtils.i("lv=" + lv + "cv=" + cv);
      if (StringUtils.parseDouble(lv) > StringUtils.parseDouble(cv)) {
        LogUtils.i("true");
        return true;
      }
    }
    return false;
  }


  /**
   * 转化为百
   */
  public static String formatHundred(String str) {
    double dd = parseDouble(str) / 100;
    NumberFormat formater = new DecimalFormat("####.00");
    formater.setMinimumIntegerDigits(1);
    return formater.format(dd);
  }

  /**
   * 转化为万
   */
  public static String formatTenThousands(String str) {
    double dd = parseDouble(str) / 10000;
    NumberFormat formater = new DecimalFormat("####.00");
    formater.setMinimumIntegerDigits(1);
    return formater.format(dd);
  }


  /**
   * 转化为亿
   */
  public static String formatHundredMil(String str) {
    double dd = parseDouble(str) / 100000000;
    NumberFormat formater = new DecimalFormat("####.00");
    formater.setMinimumIntegerDigits(1);
    return formater.format(dd);
  }


  public static boolean checkNumberOverTenThousands(String str) {
    double dd = parseDouble(str) / 10000;
    if (dd > 1.0f) {
      return true;
    } else {
      return false;
    }
  }

  public static void handleTenThousandsAmt(String amt, TextView titleTV, TextView amtTV,
      String title) {
    if (checkNumberOverTenThousands(amt)) {
      titleTV.setText(title + "(万元)");
      amtTV.setText(StringUtils.formatAmount((parseDouble(amt) / 10000) + ""));
    } else {
      titleTV.setText(title + "(元)");
      amtTV.setText(StringUtils.formatAmount(amt));
    }
  }

  public static void handleTenThousandsAmt(String amt, TextView amtTV) {
    if (checkNumberOverTenThousands(amt)) {
      amtTV.setText(StringUtils.formatAmount((parseDouble(amt) / 10000) + "") + "万元");
    } else {
      amtTV.setText(StringUtils.formatAmount(amt) + "元");
    }
  }


  public static boolean checkNumberOverHunderdaMil(String str) {
    double dd = parseDouble(str) / 100000000;
    if (dd > 1.0f) {
      return true;
    } else {
      return false;
    }
  }


  public static String handleMilThousandsAmt(String amt) {
    if (checkNumberOverHunderdaMil(amt)) {
      return StringUtils.formatHundredMil(amt) + "亿元";
    } else {
      if (checkNumberOverTenThousands(amt)) {
        return StringUtils.formatTenThousands(amt) + "万元";
      } else {
        return StringUtils.formatNumberThousandAmount(amt) + "元";
      }
    }
  }


  public static String handleTenThousands2Decrease(String amt) {
    if (checkNumberOverTenThousands(amt)) {
      return StringUtils.formatTenThousands(amt) + "万";
    } else {
      return StringUtils.formatAmount(amt);
    }
  }

  /**
   * 转化为万
   */
  public static String formatTenThousands2(String str) {
    double dd = div(parseDouble(str), 10000);
    LogUtils.e("dd=" + dd);
    return dd + "";
  }


  /**
   * 字符串对比
   */
  public static boolean equals(String str1, String str2) {
    if (str1 != null) {
      return str1.equals(str2);
    }
    return str2 == null;
  }

  /*
   * 金额格式化 ####,###.00
   */
  public static String formatAmount(String s) {
    int len = 2;//小数位数
    if (s == null || s.length() < 1) {
      return "- -";
    }
    NumberFormat formater = null;
    double num = Double.parseDouble(s);
    if (len == 0) {
      formater = new DecimalFormat("###,###");
    } else {
      StringBuffer buff = new StringBuffer();
      buff.append("###,###.00");
      for (int i = 0; i < len; i++) {
        buff.append("#");
      }
      formater = new DecimalFormat(buff.toString());
      formater.setMinimumIntegerDigits(1);
    }
    return formater.format(num);
  }

  /*
   * 金额格式化 ####,###.00
   */
  public static String formatAmountZero(String s) {
    int len = 2;//小数位数
    if (s == null || s.length() < 1) {
      return "0.00";
    }
    NumberFormat formater = null;
    double num = Double.parseDouble(s);
    if (len == 0) {
      formater = new DecimalFormat("###,###");
    } else {
      StringBuffer buff = new StringBuffer();
      buff.append("###,###.00");
      for (int i = 0; i < len; i++) {
        buff.append("#");
      }
      formater = new DecimalFormat(buff.toString());
      formater.setMinimumIntegerDigits(1);
    }
    return formater.format(num);
  }

  /**
   * 金额格式化 ####,###.00
   */
  public static String formatNumberThousandAmount(String s) {
    //小数位数
    int len = 0;
    if (s == null || s.length() < 1) {
      return "";
    }
    NumberFormat formater = null;
    double num = Double.parseDouble(s);
    if (len == 0) {
      formater = new DecimalFormat("###,###");
    } else {
      StringBuffer buff = new StringBuffer();
      buff.append("###,###");
      for (int i = 0; i < len; i++) {
        buff.append("#");
      }
      formater = new DecimalFormat(buff.toString());
      formater.setMinimumIntegerDigits(1);
    }
    return formater.format(num);
  }

  private static Pattern NUMBER_PATTERN = Pattern.compile(".*\\d+.*");

  /**
   * 判断一个字符串是否含有数字
   */
  public static boolean hasDigit(String content) {
    boolean flag = false;
    Matcher m = NUMBER_PATTERN.matcher(content);
    if (m.matches()) {
      flag = true;
    }
    return flag;
  }


  /**
   * 去掉小数点和无用的0
   */
  public static String removeDot(String num) {
    if (StringUtils.isNotEmpty(num) && num.indexOf(".") > 0) {
      //正则表达
      num = num.replaceAll("0+?$", "");//去掉后面无用的零
      num = num.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
    }
    return num;
  }


  /**
   * 字节大小转换为mb
   */
  public static String bytes2MB(long bytes) {
    double d = (double) bytes / 1024.0 / 1024.0;
    NumberFormat formater = new DecimalFormat("####.00");
    formater.setMinimumIntegerDigits(1);
    return formater.format(d);
  }


  /**
   * 字节大小转换为mb
   */
  public static String bytes2KB(long bytes) {
    double d = (double) bytes / 1024.0;
    NumberFormat formater = new DecimalFormat("####.00");
    formater.setMinimumIntegerDigits(1);
    return formater.format(d);
  }


  /**
   * 字节大小转换为mb
   */
  public static String calculateFileSize(long bytes) {
    double d = (double) bytes / 1024.0;
    if (d / 1024.0 > 1.0) {
      return bytes2MB(bytes) + "MB";
    } else {
      return bytes2KB(bytes) + "KB";
    }
  }

  public static int getScale(int maxWidth) {
    if (maxWidth > 1920) {
      return (int) ((1920f / maxWidth) * 100);
    } else {
      return 100;
    }
  }


  /**
   * 银行卡四位加空格
   */

  public static void bankCardNumAddSpace(final EditText mEditText) {
    mEditText.addTextChangedListener(new TextWatcher() {
      int beforeTextLength = 0;
      int onTextLength = 0;
      boolean isChanged = false;

      int location = 0;// 记录光标的位置
      private char[] tempChar;
      private StringBuffer buffer = new StringBuffer();
      int konggeNumberB = 0;

      @Override
      public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        beforeTextLength = s.length();
        if (buffer.length() > 0) {
          buffer.delete(0, buffer.length());
        }
        konggeNumberB = 0;
        for (int i = 0; i < s.length(); i++) {
          if (s.charAt(i) == ' ') {
            konggeNumberB++;
          }
        }
      }

      @Override
      public void onTextChanged(CharSequence s, int start, int before, int count) {
        onTextLength = s.length();
        buffer.append(s.toString());
        if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
          isChanged = false;
          return;
        }
        isChanged = true;
      }

      @Override
      public void afterTextChanged(Editable s) {
        if (isChanged) {
          location = mEditText.getSelectionEnd();
          int index = 0;
          while (index < buffer.length()) {
            if (buffer.charAt(index) == ' ') {
              buffer.deleteCharAt(index);
            } else {
              index++;
            }
          }

          index = 0;
          int konggeNumberC = 0;
          while (index < buffer.length()) {
            if ((index == 4 || index == 9 || index == 14 || index == 19)) {
              buffer.insert(index, ' ');
              konggeNumberC++;
            }
            index++;
          }

          if (konggeNumberC > konggeNumberB) {
            location += (konggeNumberC - konggeNumberB);
          }

          tempChar = new char[buffer.length()];
          buffer.getChars(0, buffer.length(), tempChar, 0);
          String str = buffer.toString();
          if (location > str.length()) {
            location = str.length();
          } else if (location < 0) {
            location = 0;
          }

          mEditText.setText(str);
          Editable etable = mEditText.getText();
          Selection.setSelection(etable, location);
          isChanged = false;
        }
      }
    });
  }


  public static float setRate(String str) {
    float i = Float.valueOf(str);
    return i / 100;
  }

  public static String getRate(String str) {
    float i = Float.valueOf(str);
    return (i * 100) + "%";
  }

  /**
   * 如果返回字符串是空的话，返回“ -- ”
   *
   * @param str str
   * @return str
   */
  public static String getAvailableString(String str) {
    if (TextUtils.isEmpty(str)) {
      return "--";
    } else {
      return str;
    }
  }


  /**
   * kb转化成mb
   */
  public static String parseToMB(double kbSize) {
    double d = kbSize / 1024 / 1024;
    NumberFormat formater = new DecimalFormat("####.00");
    formater.setMinimumIntegerDigits(1);
    return formater.format(d);
  }

  /**
   * 字符全角化
   */
  public static String toDBC(String input) {
    char[] c = input.toCharArray();
    for (int i = 0; i < c.length; i++) {
      if (c[i] == 12288) {
        c[i] = (char) 32;
        continue;
      }
      if (c[i] > 65280 && c[i] < 65375) {
        c[i] = (char) (c[i] - 65248);
      }
    }
    return new String(c);
  }


  public static String encryptBankCode(String str) {
    String regex = "(\\w{4})(.*)(\\w{4})";
    Matcher m = Pattern.compile(regex).matcher(str);
    if (m.find()) {
      String rep = m.group(2);
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < rep.length(); i++) {
        sb.append("*");
      }
      return str.replaceAll(rep, sb.toString());
    }
    return str;
  }

  public static String formatBankCode(String str) {
    try {
//            String bankCode = encryptBankCode(str);
      StringBuilder sb = new StringBuilder(str);
      int length = str.length() / 4 + str.length();
      for (int i = 0; i < length; i++) {
        if (i % 5 == 0) {
          sb.insert(i, " ");
        }
      }
      return sb.deleteCharAt(0).toString();
    } catch (Exception e) {
      return str;
    }
  }

  public static String formatPhoneCode(String str) {
    try {
      StringBuilder sb = new StringBuilder(str);
      sb.insert(3, " ");
      sb.insert(8, " ");
      return sb.toString();
    } catch (Exception e) {
      return str;
    }
  }


  /**
   * 身份证验证
   */
  public static boolean checkIdCard(String idCard) {
    String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
    return Pattern.matches(regex, idCard);
  }

  /**
   * 是否包含字母
   */
  public static boolean isIncludeEnglish(String text) {
    String pattern = "[a-zA-Z]";
    Pattern p = Pattern.compile(pattern);
    Matcher m = p.matcher(text);
    if (m.find()) {
      return true;
    }
    return false;
  }


  public static String formatRealRate(String rate) {
    rate = rate.replaceAll("%", "").replace("奖", "");
    return rate;
  }


  /**
   * 验证短信验证码格式
   */
  public static boolean isMsgCode(String msgCode) {
    if (StringUtils.isEmpty(msgCode)
        || msgCode.length() != 6) {
      return false;
    }
    return true;
  }

  /**
   * 是否 不为空字符串 或 null
   */
  public static String isNullShowEmpty(String s) {
    if (s != null && s.length() > 0) {
      return s;
    } else {
      return "- -";
    }
  }

  /**
   * 是否 不为空字符串 或 null
   */
  public static String showEmpty(String s) {
    if ((s != null && s.length() > 0)) {
      if ("null".equals(s)) {
        return "";
      } else {
        return s;
      }
    } else {
      return "";
    }
  }

  /**
   * 为空显示未填写
   */
  public static String showFillOutEmpty(String s) {
    if (s != null && s.length() > 0) {
      return s;
    } else {
      return "未填写";
    }
  }

  /**
   * 是否 为空字符串 或 null
   */
  public static boolean isEmpty(String s) {
    return s == null || s.length() <= 0;
  }


  /**
   * 截取oss key
   */
  public static String getImageUrlOssKey(String url) {
    Uri uri = Uri.parse(url);
    String oSSAccessKeyId = uri.getQueryParameter("OSSAccessKeyId");
    String signature = uri.getQueryParameter("Signature");
    String expires = uri.getQueryParameter("Expires");
    LogUtils.w("oSSAccessKeyId=" + oSSAccessKeyId + "**********signature=" + signature
        + "**********expires=" + expires);
    return "";
  }


  /**
   * 金额格式化 ####.00
   */
  public static String formatAmount2(String s) {
    double num = Double.parseDouble(s);
    NumberFormat formater = new DecimalFormat("####.00");
    formater.setMinimumIntegerDigits(1);
    return formater.format(num);
  }

  /**
   * 金额格式化 ####
   */
  public static String formatAmount3(String s) {
    double num = Double.parseDouble(s);
    NumberFormat formater = new DecimalFormat("####");
    formater.setMinimumIntegerDigits(1);
    return formater.format(num);
  }

  /**
   * 获取指定格式日期的当前时间
   */
  public static String getCurrDate(String strFormat) {
    SimpleDateFormat formatter = new SimpleDateFormat(strFormat);
    Date date1 = new Date();
    return formatter.format(date1);
  }

  /**
   * 身份证号码格式化******
   */
  public static String formatCertNo(String certNo) {
    String fname = "";
    if (certNo == null || "".equals(certNo)) {
      return fname;
    }
    int len = certNo.length();
    if (len == 18) {
      fname = certNo.substring(0, 4) + "**********" + certNo.substring(len - 4, len);
    } else {
      fname = certNo;
    }
    return fname;
  }

  /**
   * 电话号码格式化*******
   */
  public static String formatMobile(String mobile) {
    String fname;
    if (mobile == null || "".equals(mobile)) {
      return "";
    }
    int len = mobile.length();
    if (len == 11) {
      fname = mobile.substring(0, 3) + "****" + mobile.substring(len - 4, len);
    } else {
      fname = mobile;
    }
    return fname;
  }

  /**
   * 用户名格式化
   */
  public static String formatUserName(String name) {
    String fname;
    if (isEmpty(name)) {
      return "";
    }
    int len = name.length();
    if (len == 1) {
      return name;
    } else if (len == 2) {
      fname = name.substring(0, 1) + "*";
    } else if (len == 3) {
      fname = name.substring(0, 1) + "**";
    } else if (len == 4) {
      fname = name.substring(0, 2) + "**";
    } else if (len == 5) {
      fname = name.substring(0, 2) + "***";
    } else if (len == 6) {
      fname = name.substring(0, 3) + "***";
    } else {
      fname = name.substring(0, 3) + "****";
    }
    return fname;
  }

  /**
   * @param data 字符串
   * @param suffix 后缀
   * @param textSize 内容字体大小
   * @param suffix 后缀字体大小
   */
  public static SpannableString setSpannableCustom(String data, String suffix, int textSize,
      int suffixSize) {
    data = data + suffix;
    SpannableString styledText = new SpannableString(data);
    styledText.setSpan(new AbsoluteSizeSpan(textSize, true), 0, data.length() - suffix.length(),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    styledText.setSpan(new AbsoluteSizeSpan(suffixSize, true), data.length() - suffix.length(),
        data.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return styledText;
  }

  /**
   * @param data 字符串
   * @param rateMax 大的年化率
   * @param rateMini 小的年化率
   * @param textSize 内容字体大小
   * @param suffixSize 后缀字体大小
   */
  public static SpannableString setSpannableSizeAndColor(String data, String rateMini,
      String rateMax, int textSize,
      int suffixSize, String textColor, String suffixColor) {
    String suffix = rateMini + "%-" + rateMax + "%";
    //17.0 -- 22.0
    String text = data + suffix;
    SpannableString styledText = new SpannableString(text);
    ForegroundColorSpan firstSpan = new ForegroundColorSpan(Color.parseColor(textColor + ""));
    ForegroundColorSpan secondSpan = new ForegroundColorSpan(Color.parseColor(suffixColor + ""));
    styledText.setSpan(new AbsoluteSizeSpan(textSize, true), 0, text.length(),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    styledText.setSpan(new AbsoluteSizeSpan(suffixSize, true), data.length(),
        data.length() + rateMini.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    styledText.setSpan(new AbsoluteSizeSpan(suffixSize, true), text.length() - rateMax.length() - 1,
        text.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    styledText.setSpan(firstSpan, 0, data.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    styledText
        .setSpan(secondSpan, data.length(), text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    return styledText;
  }

  /**
   * @param data 字符串
   * @param prefix 前缀
   */
  public static SpannableString setSpannableSizeAndColor(String data, String prefix, int textSize,
      int prefixSize, String textColor, String prefixColor) {
    String text = prefix + data;
    SpannableString styledText = new SpannableString(text);
    ForegroundColorSpan textSpan = new ForegroundColorSpan(Color.parseColor(textColor + ""));
    ForegroundColorSpan prefixSpan = new ForegroundColorSpan(Color.parseColor(prefixColor + ""));
    styledText.setSpan(new AbsoluteSizeSpan(prefixSize, true), 0,
        prefix.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    styledText.setSpan(new AbsoluteSizeSpan(textSize, true), prefix.length(),
        text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    styledText.setSpan(prefixSpan, 0, prefix.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    styledText
        .setSpan(textSpan, prefix.length(), text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    return styledText;
  }

  /**
   * 改变特定字符的颜色
   *
   * @param data 字符串
   * @param suffix 后缀
   * @param textColor 内容字体颜色
   * @param suffixColor 后缀字体颜色
   */
  public static SpannableStringBuilder setSpannableColor(String data, String suffix,
      String textColor, String suffixColor) {
    String text = data + suffix;
    SpannableStringBuilder builder = new SpannableStringBuilder(text);
    ForegroundColorSpan firstSpan = new ForegroundColorSpan(Color.parseColor(textColor + ""));
    ForegroundColorSpan secondSpan = new ForegroundColorSpan(Color.parseColor(suffixColor + ""));
    builder.setSpan(firstSpan, 0, data.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    builder.setSpan(secondSpan, data.length(), text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    return builder;
  }

  /**
   * 改变特定字符的颜色
   *
   * @param prefix 字符前缀
   * @param data 字符串
   * @param suffix 后缀
   * @param prefixColor 前缀字体颜色
   * @param dataColor 字符串字体颜色
   * @param suffixColor 前后缀字体颜色
   * @param textSize 前后缀不包括字符串的字体大小
   * @param suffixSize 前后缀字体大小
   */
  public static SpannableStringBuilder setSpannableColor(String prefix, String data, String suffix,
      String prefixColor, String dataColor, String suffixColor,
      int textSize, int suffixSize) {
    String text = prefix + data + suffix;
    SpannableStringBuilder builder = new SpannableStringBuilder(text);
    ForegroundColorSpan prefixSpan = new ForegroundColorSpan(Color.parseColor(prefixColor + ""));
    ForegroundColorSpan dataSpan = new ForegroundColorSpan(Color.parseColor(dataColor + ""));
    ForegroundColorSpan suffixSpan = new ForegroundColorSpan(Color.parseColor(suffixColor + ""));
    builder.setSpan(new AbsoluteSizeSpan(suffixSize, true), 0, text.length(),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    builder.setSpan(new AbsoluteSizeSpan(textSize, true), prefix.length(),
        prefix.length() + data.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    builder.setSpan(prefixSpan, 0, prefix.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    builder.setSpan(dataSpan, prefix.length(), prefix.length() + data.length(),
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    builder.setSpan(suffixSpan, prefix.length() + data.length(), text.length(),
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    return builder;
  }

  /**
   * 改变特定字符的颜色
   *
   * @param prefix 字符前缀
   * @param data 字符串
   * @param suffix 后缀
   * @param textSize 前后缀不包括字符串的字体大小
   * @param suffixSize 前后缀字体大小
   */
  public static SpannableStringBuilder setSpannableColor(String prefix, String data, String suffix,
      int textSize, int suffixSize) {
    String text = prefix + data + suffix;
    SpannableStringBuilder builder = new SpannableStringBuilder(text);
    builder.setSpan(new AbsoluteSizeSpan(suffixSize, true), 0, text.length(),
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    builder.setSpan(new AbsoluteSizeSpan(textSize, true), prefix.length(),
        prefix.length() + data.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    return builder;
  }


  /**
   * 改变特定字符的颜色
   *
   * @param prefix 字符前缀
   * @param data 字符串
   * @param suffix 后缀
   * @param textColor 内容字体颜色
   * @param suffixColor 前后缀字体颜色
   */
  public static SpannableStringBuilder setSpannableColor(String prefix, String data, String suffix,
      String textColor, String suffixColor) {
    String text = prefix + data + suffix;
    SpannableStringBuilder builder = new SpannableStringBuilder(text);
    ForegroundColorSpan firstSpan = new ForegroundColorSpan(Color.parseColor(textColor + ""));
    ForegroundColorSpan secondSpan = new ForegroundColorSpan(Color.parseColor(suffixColor + ""));
    builder.setSpan(secondSpan, 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
    builder.setSpan(firstSpan, prefix.length(), +prefix.length() + data.length(),
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    return builder;
  }

  private static Pattern BankPattern = Pattern.
      compile("^\\d{16,19}$|^\\d{6}[- ]\\d{10,13}$|^\\d{4}[- ]\\d{4}[- ]\\d{4}[- ]\\d{4,7}$");

  /**
   * 验证银卡卡号
   */
  public static boolean isBankCard(String cardNo) {
    Matcher m = BankPattern.matcher(cardNo);
    return m.matches();
  }

  /**
   * 格式化金额
   *
   * @param hasDot 是否去除小数点后无用的0
   */
  public static String formatAmount(String s, boolean hasDot) {
    if (hasDot) {
      return formatAmount(s);
    } else {
      return removeDot(formatAmount(s));
    }
  }


  public static String getUrlParams(Map<String, String> map) {
    StringBuilder builder = new StringBuilder();
    for (Map.Entry<String, String> entry : map.entrySet()) {
      builder.append("&" + entry.getKey() + "=" + entry.getValue());
    }
    return builder.toString().replaceFirst("$", "");
  }

  /**
   * 计算预计收益
   *
   * @param deadline 期限，单位（天）
   */
  public static String calculateRevenue(String amountStr, String rateStr, int deadline) {
    if (parseDouble(amountStr) > 0) {
      BigDecimal rate = new BigDecimal(rateStr);
      BigDecimal amount = new BigDecimal(amountStr);
      BigDecimal dayRate = new BigDecimal(
          Math.pow((rate.setScale(15, BigDecimal.ROUND_HALF_UP).doubleValue() + 1), (1.0 / 360))
              - 1);//计算日收益率
      BigDecimal income = amount
          .multiply(new BigDecimal(1).add(dayRate).pow(deadline).subtract(new BigDecimal(1)))
          .setScale(2, BigDecimal.ROUND_HALF_UP);//计算收益，四舍五入保留2位小数
      return String.valueOf(income.doubleValue());
    }
    return "0.00";
  }

  /**
   * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
   *
   * @param v1 被除数
   * @param v2 除数
   * @return 两个参数的商
   */
  public static double div(double v1, double v2) {
    return div(v1, v2, 3);
  }

  /**
   * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
   *
   * @param v1 被除数
   * @param v2 除数
   * @param scale 表示需要精确到小数点以后几位。
   * @return 两个参数的商
   */
  public static double div(double v1, double v2, int scale) {
    if (scale < 0) {
      throw new IllegalArgumentException(
          "The scale must be a positive integer or zero");
    }
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
  }


  /**
   * 去除所有指定字符
   */
  public static String removeSymbol(String str, String symbol) {
    if (StringUtils.isEmpty(str)) {
      return "";
    }
    if (StringUtils.isEmpty(symbol)) {
      return str;
    }
    return str.replace(symbol, "");//replace()、replaceAll()都是全部替换，但是后者支持正则表达式，使用请小心
  }

  /**
   * 将小数转化成百分率
   */
  public static String formatPercent(String rate) {
    double d = 0;
    try {
      d = Double.valueOf(rate);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    double dd = d * 100;
    DecimalFormat format = new DecimalFormat("0.00");
    return format.format(dd);
  }

  /**
   * 将小数转化成百分率
   */
  public static String formatPercent2(String rate) {
    double d = 0;
    try {
      d = Double.valueOf(rate);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    double dd = d * 100;
    DecimalFormat format = new DecimalFormat("0.0");
    return format.format(dd);
  }

  /**
   * 将小数转化成百分率
   */
  public static String formatPercentNumber(String rate) {
    double d = 0;
    try {
      d = Double.valueOf(rate);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    double dd = d * 100;
    DecimalFormat format = new DecimalFormat("0.0");
    return format.format(dd);
  }

  /**
   * 将小数转化成百分率
   */
  public static String formatPercent(String rate, DecimalFormat format) {
    double d = 0;
    try {
      d = Double.valueOf(rate);
    } catch (NumberFormatException e) {
      e.printStackTrace();
    }
    double dd = d * 100;
    return format.format(dd);
  }


  /**
   * 验证密码格式
   */
  public static boolean isPassword(String password) {
    return Pattern.matches(PASS_WORD, password);
  }


  /**
   * string内容转化为int
   */
  public static int parseInt(String str) {
    int num = 0;
    try {
      if (StringUtils.isNotEmpty(str)) {
        num = Integer.parseInt(str);
      }
    } catch (Exception e) {
      LogUtils.e("number format exception:" + str);
    }
    return num;
  }

  /**
   * string内容转化为long
   */
  public static long parseLong(String str) {
    long num = 0;
    try {
      if (StringUtils.isNotEmpty(str)) {
        num = Long.parseLong(str);
      }
    } catch (Exception e) {
      LogUtils.e("number format exception:" + str);
    }
    return num;
  }

  /* 复制字符串到剪切板
   *
   * @param context context
   * @param content 内容
   * @param msg     复制后的提示toast内容
   */
  public static void copyToBoard(Context context, String content, String msg) {
    ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
    cmb.setText(content);
    if (StringUtils.isNotEmpty(msg)) {
      BaseApplication.showToast(msg);
    }
  }


  /**
   * 获取当前应用进程名字
   */
  public static String getCurProcessName(Context context) {
    try {
      int pid = android.os.Process.myPid();
      ActivityManager mActivityManager = (ActivityManager) context
          .getSystemService(Context.ACTIVITY_SERVICE);
      for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
          .getRunningAppProcesses()) {
        if (appProcess.pid == pid) {
          return appProcess.processName;
        }
      }
    } catch (Exception e) {

    }
    return "";
  }


  public static PackageInfo getPackageInfo(Context context) {
    PackageManager pm = context.getPackageManager();
    try {
      return pm.getPackageInfo(context.getPackageName(), 0);
    } catch (PackageManager.NameNotFoundException e) {
      LogUtils.e(e.getLocalizedMessage());
    }
    return new PackageInfo();
  }

  /**
   * 提供精确的加法运算。
   *
   * @param v1 被加数
   * @param v2 加数
   * @return 两个参数的和
   */
  public static double add(double v1, double v2) {
    BigDecimal b1 = new BigDecimal(Double.toString(v1));
    BigDecimal b2 = new BigDecimal(Double.toString(v2));
    return b1.add(b2).doubleValue();
  }

  /**
   * 搜索前检查
   */
  public static boolean checkEditText(String tag) {
    if (tag != null && !"".equals(tag)) {
      return true;
    }
    return false;
  }

  public static String updateListItemNum(String title, String count) {
    if (title.contains("(") || title.contains(")")) {
      int startsWith = title.lastIndexOf("(");
      return title.substring(0, startsWith) + "\\n(" + count + ")";
    } else {
      return title + "\\n(" + count + ")";
    }
  }

  /**
   * 验证手机号格式
   */
  public static boolean isPhoneNum(String phoneNum) {
    return Pattern.matches(MOBILE, phoneNum);
  }


  /**
   * 是否 不为空字符串 或 null
   */
  public static boolean isNotEmpty(String s) {
    return s != null && s.length() > 0;
  }

}
