package com.xsgzt.framework.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings.Secure;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 通用工具类
 */
@SuppressLint("SimpleDateFormat")
public class GeneralUtil {
    public static boolean isEmoji(String string) {
        Pattern p = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(string);
        return m.find();
    }

    /**
     * 获取apk的版本号 currentVersionCode
     *
     * @param ctx
     * @return
     */
    public static int getAPPVersionCodeFromAPP(Context ctx) {
        int currentVersionCode = 0;
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            String appVersionName = info.versionName; // 版本名
            currentVersionCode = info.versionCode; // 版本号
            System.out.println(currentVersionCode + " " + appVersionName);
        } catch (Exception e) {
            // TODO Auto-generated catch blockd
            e.printStackTrace();
        }
        return currentVersionCode;
    }

    /**
     * 获取apk的版本号 currentVersionCode
     *
     * @param ctx
     * @return
     */
    public static String getAPPVersionNameFromAPP(Context ctx) {
        String appVersionName = "1.0";
        PackageManager manager = ctx.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
            appVersionName = info.versionName; // 版本名
        } catch (Exception e) {
            // TODO Auto-generated catch blockd
            e.printStackTrace();
        }
        return appVersionName;
    }

    /**
     * 判断性别
     *
     * @param sex
     * @return
     */
    public static boolean JudgeSexQual(String sex) {
        if ("男".equals(sex) || "女".equals(sex)) {
            return true;
        }
        return false;
    }


    /**
     * 判断座机号码
     *
     * @param number
     * @return
     */
    public static boolean judgeTelephone(String number) {
        return number.matches("^0[0-9]{3}-?[0-9]{7,8}$");
    }

    /**
     * 判断手机号码（传入号码）
     *
     * @param number
     * @return
     */
    public static boolean judgePhoneQual(String number) {
        return number
                .matches("^(\\+86-|\\+86|86-|86){0,1}1[3|4|5|7|8]{1}\\d{9}$");
    }

    /**
     * 判断手机号码（传入控件）
     *
     * @param numberEt
     * @return
     */
    public static boolean judgePhoneQual(EditText numberEt) {
        return numberEt.getText().toString().trim().matches("^1[34568]\\d{9}$");
    }

    /**
     * 判断邮箱（传入字符）
     *
     * @param email
     * @return
     */
    public static boolean judgeEmailQual(String email) {
        return email
                .matches("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    }

    /**
     * 判断邮箱（传入控件）
     *
     * @param emailEt
     * @return
     */
    public static boolean judgeEmailQual(EditText emailEt) {
        return emailEt
                .getText()
                .toString()
                .trim()
                .matches(
                        "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    }

    /**
     * dip转Px
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px转dip
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 保存一个txt文本到指定路径
     *
     * @param data
     * @throws Exception
     */
    public static void StringToTxt(String data, String savePath)
            throws Exception {
        File file = new File(savePath);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(data);
        bw.flush();
        bw.close();
        fw.close();
    }

    /**
     * 把base64字符存到指定路径文件
     *
     * @param base64Code
     * @param savePath
     * @throws Exception
     */
    @SuppressLint("NewApi")
    public static void decoderBase64File(String base64Code, String savePath)
            throws Exception {
        byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
    }

    /**
     * 根据网络路径读取文件内容
     *
     * @param urlpath : URL address
     * @return
     * @throws Exception
     */
    public static String getString(String urlpath) throws Exception {
        URL url = new URL(urlpath);
        try {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(6 * 1000);
            if (conn.getResponseCode() == 200) {
                InputStream inStream = conn.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len = -1;
                while ((len = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }
                outStream.close();
                inStream.close();
                byte[] data = outStream.toByteArray();
                return new String(data, "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除文件
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
            for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
                deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
            }
            return;
        } else {
            // 文件
            file.delete();
        }

    }

    /**
     * 根据路径删除文件
     *
     * @param path
     */
    public static void deleteFile(String path) {
        deleteFile(new File(path));
    }

    /**
     * 输入框为空时抖动提示
     *
     * @param counts How many under 1 second to shake
     * @return
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        // 设置一个循环加速器，使用传入的次数就会出现摆动的效果。
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(500);
        return translateAnimation;
    }

    /**
     * 截取电话号码后四位
     *
     * @param phone
     * @return
     */
    public static String getBehindFourNumber(String phone) {
        return phone.substring(phone.length() - 4, phone.length());
    }

    /**
     * 指定文本最多显示的字数，多余的部分显示…
     *
     * @param text
     * @return
     */
    public static String InterceptionCharacters(String text, int number) {
        if (TextUtils.isEmpty(text)) {
            return "";
        }
        if (text.length() > number) {
            text = text.substring(0, number) + "...";
        }
        return text;
    }

    /**
     * 根据布局文件id实例化view
     *
     * @param layoutId
     * @return
     */
    public static View getView(Context context, int layoutId, ViewGroup group) {
        return View.inflate(context, layoutId, group);
    }


    /**
     * 得到随机文字(中文汉字)
     *
     * @return
     */
    public static char getRandomChar() {
        String str = "";
        int hightPos;
        int lowPos;
        Random random = new Random();
        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = Integer.valueOf(hightPos).byteValue();
        b[1] = Integer.valueOf(lowPos).byteValue();
        try {
            str = new String(b, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str.charAt(0);
    }

    /**
     * 设置view不可用
     *
     * @param views
     */
    public static void setViewUnAble(View... views) {
        for (View view : views)
            view.setEnabled(false);
    }

    /**
     * 设置view不可见（占位）
     *
     * @param views
     */
    public static void setViewInVisible(View... views) {
        for (View view : views)
            view.setVisibility(View.INVISIBLE);
    }

    /**
     * 设置view可见
     *
     * @param views
     */
    public static void setViewVisible(View... views) {
        for (View view : views)
            view.setVisibility(View.VISIBLE);
    }

    /**
     * 设置view不可见（不占位）
     *
     * @param views
     */
    public static void setViewGone(View... views) {
        for (View view : views)
            view.setVisibility(View.GONE);
    }

    /**
     * 设置view是否可见
     */
    public static void setViewVisibility(int visibility, View... views) {
        for (View view : views)
            view.setVisibility(visibility);
    }

    /**
     * 设置view可用
     *
     * @param views
     */
    public static void setViewAble(View... views) {
        for (View view : views)
            view.setEnabled(true);
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftInputFromWindow(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 隐藏软键盘
     *
     * @param activity
     */
    public static void hideSoftInputFromWindow(Activity activity, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 显示软键盘
     *
     * @param view
     */
    public static void showSoftInputFromWindow(final View view) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                view.setFocusable(true);
                view.setFocusableInTouchMode(true);
                view.requestFocus();
                InputMethodManager inputManager = (InputMethodManager) view
                        .getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }

        }, 200);
    }


    /**
     * @throws
     * @MethodName:closeInputMethod
     * @Description:关闭系统软键盘
     */

//    static public void closeInputMethod(Context context, View view) {
//
//        try {
//            //获取输入法的服务
//            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//            //判断是否在激活状态
//            if (imm.isActive()) {
//                //隐藏输入法!!,
//                imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//            }
//
//        } catch (Exception e) {
//        } finally {
//        }
//
//    }
//
    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideSoftInputFromWindow(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

    /**
     * 将临时数据添加到总列表
     *
     * @param <T>
     */
    public static <T> ArrayList<T> addTempListData(ArrayList<T> allList,
                                                   ArrayList<T> tempList) {
        allList.addAll(tempList);
        return allList;
    }

    /**
     * 检测返回数据列表
     *
     * @param resultList
     * @return
     */
    public static boolean checkResultList(ArrayList<?> resultList) {
        return resultList != null && resultList.size() > 0;
    }

    /**
     * 检测密码的强度
     *
     * @param password
     * @return
     */
    public static int checkPwdStrength(String password) {
        int score = 0;
        if (password.length() <= 6) {
            score = 1;
        } else if (password.length() >= 7 && password.length() <= 10) {
            score = 2;
        } else {
            score = 3;
        }
        return score;
    }

    /**
     * 检验某个服务是否活着
     *
     * @param context 服务的包名
     * @return
     */
    public static boolean isServiceRunning(Context context, String serviceName) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningServiceInfo> infos = am.getRunningServices(100);
        for (RunningServiceInfo info : infos) {
            String name = info.service.getClassName();
            if (serviceName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 得到HashMap中的值
     *
     * @param hashMap
     * @return
     */
    public static ArrayList<String> getValuesByHashMap(
            HashMap<String, String> hashMap) {
        ArrayList<String> values = new ArrayList<String>();
        Iterator<Entry<String, String>> iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Entry<String, String> entry = iter.next();
            values.add(entry.getValue());
        }
        return values;
    }

    /**
     * 创建文件夹
     *
     * @param file
     * @return
     */
    public static boolean createFile(File file) {
        if (!file.exists()) {
            return file.mkdir();
        }
        return false;
    }


    /**
     * 判断是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        if (TextUtils.isEmpty(str))
            return false;

        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 通知父布局，占用的宽，高
     *
     * @param view
     */
    public static void measureView(View view) {
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
        int height;
        int tempHeight = p.height;
        if (tempHeight > 0) {
            height = MeasureSpec.makeMeasureSpec(tempHeight,
                    MeasureSpec.EXACTLY);
        } else {
            height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        }
        view.measure(width, height);
    }


    /**
     * 验证身份证号码是否合格
     *
     * @param IDStr
     * @return
     */
    public static String IDCardValidate(String IDStr) {
        String errorInfo = "";// 记录错误信息
        String[] ValCodeArr = {"1", "0", "x", "9", "8", "7", "6", "5", "4",
                "3", "2"};
        String[] Wi = {"7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7",
                "9", "10", "5", "8", "4", "2"};
        String Ai = "";
        // ================ 号码的长度 15位或18位 ================
        if (IDStr.length() != 15 && IDStr.length() != 18) {
            errorInfo = "身份证号码长度应该为15位或18位。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 数字 除最后以为都为数字 ================
        if (IDStr.length() == 18) {
            Ai = IDStr.substring(0, 17);
        } else if (IDStr.length() == 15) {
            Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
        }
        if (isNumeric(Ai) == false) {
            errorInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。";
            return errorInfo;
        }
        // =======================(end)========================

        // ================ 出生年月是否有效 ================
        String strYear = Ai.substring(6, 10);// 年份
        String strMonth = Ai.substring(10, 12);// 月份
        String strDay = Ai.substring(12, 14);// 月份
        if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
            errorInfo = "身份证生日无效。";
            return errorInfo;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
                    || (gc.getTime().getTime() - s.parse(
                    strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
                errorInfo = "身份证生日不在有效范围。";
                return errorInfo;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
            errorInfo = "身份证月份无效";
            return errorInfo;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
            errorInfo = "身份证日期无效";
            return errorInfo;
        }
        // =====================(end)=====================

        // ================ 地区码时候有效 ================
        Hashtable<String, String> h = GetAreaCode();
        if (h.get(Ai.substring(0, 2)) == null) {
            errorInfo = "身份证地区编码错误。";
            return errorInfo;
        }
        // ==============================================

        // ================ 判断最后一位的值 ================
        int TotalmulAiWi = 0;
        for (int i = 0; i < 17; i++) {
            TotalmulAiWi = TotalmulAiWi
                    + Integer.parseInt(String.valueOf(Ai.charAt(i)))
                    * Integer.parseInt(Wi[i]);
        }
        int modValue = TotalmulAiWi % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Ai = Ai + strVerifyCode;

        if (IDStr.length() == 18) {
            if (Ai.equals(IDStr) == false) {
                errorInfo = "身份证无效，不是合法的身份证号码";
                return errorInfo;
            }
        } else {
            return "";
        }
        // =====================(end)=====================
        return "";
    }

    /**
     * 功能：设置地区编码
     *
     * @return HashTable 对象
     */
    private static Hashtable<String, String> GetAreaCode() {
        Hashtable<String, String> hashtable = new Hashtable<String, String>();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
    }

    /**
     * 获取一段字符串中的数字
     *
     * @param originalStr
     * @return
     */
    public static final String getStringNum(String originalStr) {
        String numberStr = "";
        originalStr.trim();
        if (originalStr != null && !"".equals(originalStr)) {
            for (int i = 0; i < originalStr.length(); i++) {
                if (originalStr.charAt(i) >= 48 && originalStr.charAt(i) <= 57) {
                    numberStr += originalStr.charAt(i);
                }
            }
        }

        return numberStr;
    }

    /**
     * 功能：判断字符串是否为日期格式
     *
     * @return
     */
    public static boolean isDate(String strDate) {
        Pattern pattern = Pattern
                .compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
        Matcher m = pattern.matcher(strDate);
        if (m.matches()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 对给定的字符串返回唯一的标记字符串<br>
     * 主要应用于网络url的标记，将url转换映射成唯一的标识字符串<br>
     * 写法参考Volley中的写法<br>
     *
     * @param str 需要转换的字符串
     * @return 返回唯一的标识
     */
    public static String toHash(String str) {
        String ret = null;
        if (str != null && str.length() > 0) {
            int len = str.length();
            String part1 = str.substring(0, len / 2).hashCode() + "";
            String part2 = str.substring(len / 2).hashCode() + "";
            ret = part1 + part2;
        }
        return ret;
    }

    /**
     * 获取设备信息
     *
     * @param context
     * @return
     */
     /*
    public static String getDeviceId(Context context) {
//        String deviceId = null;
//        try {
//            TelephonyManager tm = (TelephonyManager) context
//                    .getSystemService(Context.TELEPHONY_SERVICE);
//            deviceId = tm.getDeviceId();
//
//            LogUtils.e("TAG",deviceId);
//
//            tm = null;
//            if (TextUtils.isEmpty(deviceId)) {
//                deviceId = Secure.getString(context.getContentResolver(),
//                        Secure.ANDROID_ID);
//            }
//
//        } catch (Exception e) {
//            deviceId = Secure.getString(context.getContentResolver(),
//                    Secure.ANDROID_ID);
//        }
//
//        return deviceId;

     // 首先判断本地有没有
        DefaultIOHandler ioHandler = new DefaultIOHandler();
        String deviceId = ioHandler.getString("DeviceId");
        if (!TextUtils.isEmpty(deviceId)) {
            return deviceId;
        }

        String szImei = null;
        try {
            TelephonyManager TelephonyMgr = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
            szImei = TelephonyMgr.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String m_szDevIDShort = "35" + //we make this look like a valid IMEI
                Build.BOARD.length() % 10 +
                Build.BRAND.length() % 10 +
                Build.CPU_ABI.length() % 10 +
                Build.DEVICE.length() % 10 +
                Build.DISPLAY.length() % 10 +
                Build.HOST.length() % 10 +
                Build.ID.length() % 10 +
                Build.MANUFACTURER.length() % 10 +
                Build.MODEL.length() % 10 +
                Build.PRODUCT.length() % 10 +
                Build.TAGS.length() % 10 +
                Build.TYPE.length() % 10 +
                Build.USER.length() % 10; //13 digits

        String m_szAndroidID = Secure.getString(context.getContentResolver(), Secure.ANDROID_ID);

        String m_szLongID = szImei + m_szDevIDShort
                + m_szAndroidID + UUID.randomUUID();
        // compute md5
        MessageDigest m = null;
        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
        // get md5 bytes
        byte p_md5Data[] = m.digest();
        // create a hex string
        String m_szUniqueID = new String();
        for (int i = 0; i < p_md5Data.length; i++) {
            int b = (0xFF & p_md5Data[i]);
            // if it is a single digit, make sure it have 0 in front (proper padding)
            if (b <= 0xF)
                m_szUniqueID += "0";
            // add number to string
            m_szUniqueID += Integer.toHexString(b);
        }   // hex string to uppercase


        // 存入本地
        ioHandler.save("DeviceId", m_szUniqueID.toUpperCase());

        return m_szUniqueID.toUpperCase();

    }
*/

    /**
     * @param context
     * @return String
     * @throws
     * @Title: getDeviceID
     * @Description: 获取手机设备号
     */
    public static final String getDeviceID(Context context) {
        String deviceId = null;
        try {
            TelephonyManager tm = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
            tm = null;
            if (TextUtils.isEmpty(deviceId)) {
                deviceId = Secure.getString(context.getContentResolver(),
                        Secure.ANDROID_ID);
            }

        } catch (Exception e) {
            deviceId = Secure.getString(context.getContentResolver(),
                    Secure.ANDROID_ID);
        }

        return deviceId;
    }


    /**
     * 对数据（字节）进行Base64编码
     *
     * @param data 要编码的数据（字节数组）
     * @return 返回编码后的字符串
     */
    public static String Base64Encode(byte[] data) {
        String ret = null;
        if (data != null && data.length > 0) {
            ret = Base64.encodeToString(data, Base64.NO_WRAP);
        }
        return ret;
    }

    /**
     * 对Base64编码后的数据进行还原
     *
     * @param data 使用Base64编码过的数据
     * @return 返回还原后的数据（字节数组）
     */
    public static byte[] Base64Decode(String data) {
        byte[] ret = null;
        if (data != null && data.length() > 0) {
            ret = Base64.decode(data, Base64.NO_WRAP);
        }
        return ret;
    }

    /**
     * 使用MD5获取数据的摘要信息
     *
     * @param data 数据
     * @return 摘要信息
     */
    public static String toMD5(byte[] data) {
        String ret = null;
        try {
            byte[] digest = MessageDigest.getInstance("md5").digest(data);
            ret = Base64Encode(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 打开拨号盘，并将手机号码显示到拨号盘
     *
     * @param context
     * @param phoneNumber
     */
    public static void showPhoneDial(Context context, String phoneNumber) {
        // 先判断电话号码对不对
        /*if (!judgePhoneQual(phoneNumber)) {
            return;
        }*/
        // 打开拨号盘，并将电话号码显示到拨号盘
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    /**
     * 打开拨号盘，并将电话号码显示到拨号盘
     */
    public static void showTelePhoneDial(Context context, String phoneNumber) {
        // 打开拨号盘，并将电话号码显示到拨号盘
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    /**
     * 输入框获取焦点，让光标显示在最后
     *
     * @param editText input box
     */
    public static void cursorToEnd(EditText editText) {
        Editable text = editText.getText();
        Spannable spanText = text;
        Selection.setSelection(spanText, text.length());
    }

    /**
     * 判断是不是一个合法的url
     *
     * @param url
     * @return
     */
    public static boolean checkUrl(String url) {
        if (TextUtils.isEmpty(url)) return true;

        return url.matches("^((https|http|ftp|rtsp|mms)?://)"
                + "+(([0-9a-z_!~*'().&=+$%-]+: )?[0-9a-z_!~*'().&=+$%-]+@)?"
                + "(([0-9]{1,3}\\.){3}[0-9]{1,3}" + "|"
                + "([0-9a-z_!~*'()-]+\\.)*"
                + "([0-9a-z][0-9a-z-]{0,61})?[0-9a-z]\\." + "[a-z]{2,6})"
                + "(:[0-9]{1,4})?" + "((/?)|"
                + "(/[0-9a-z_!~*'().;?:@&=+$,%#-]+)+/?)$");
    }


    /**
     * 判断是不是整数，最多可以有两位小数
     *
     * @param number
     * @return ^[0-9]+(.[0-9]{2})?$
     */
    public static boolean checkMoney(String number) {
        return number.matches("^[-|+]?\\d*([.]\\d{0,2})?$");
    }

    /**
     * 给电话号码自动添加空格
     *
     * @param phoneEt
     */
    public static void phoneAddSpace(final EditText phoneEt) {
        phoneEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s == null || s.length() == 0)
                    return;
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    if (i != 3 && i != 8 && s.charAt(i) == ' ') {
                        continue;
                    } else {
                        sb.append(s.charAt(i));
                        if ((sb.length() == 4 || sb.length() == 9)
                                && sb.charAt(sb.length() - 1) != ' ') {
                            sb.insert(sb.length() - 1, ' ');
                        }
                    }
                }
                if (!sb.toString().equals(s.toString())) {
                    int index = start + 1;
                    if (sb.charAt(start) == ' ') {
                        if (before == 0) {
                            index++;
                        } else {
                            index--;
                        }
                    } else {
                        if (before == 1) {
                            index--;
                        }
                    }
                    phoneEt.setText(sb.toString());
                    phoneEt.setSelection(index);
                }

                if (s.length() == 13) {
                    Log.e("onTextChanged: ", "校验电话号码的");
                    boolean b = judgePhoneQual(removeAllSpace(s.toString()));
                    if (!b) {
                        Toast.makeText(phoneEt.getContext(), "电话号码不正确", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 给银行卡自动添加空格
     *
     * @param phoneEt
     */
    public static void bankcardAddSpace(final EditText phoneEt) {
        phoneEt.addTextChangedListener(new TextWatcher() {

            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
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
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = phoneEt.getSelectionEnd();
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

                    phoneEt.setText(str);
                    Editable etable = phoneEt.getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });
    }

    /**
     * 移除字符串中的所有空格
     *
     * @param str
     */
    public static String removeAllSpace(String str) {
        String tmpstr = str.replace(" ", "");
        return tmpstr;
    }

    /**
     * json转对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T parseObject(String json, Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    // MD5相关函数------------start---------------
    private static final char HEX_DIGITS[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
            'e', 'f'};

    /**
     * MD5运算
     *
     * @param s
     * @return String 返回密文
     */
    public static String getMd5(final String s) {
        try {
            // Create MD5 Hash
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(s.trim().getBytes());
            final byte messageDigest[] = digest.digest();
            return toHexString(messageDigest);
        } catch (final NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 转换为十六进制字符串
     *
     * @param b byte数组
     * @return String byte数组处理后字符串
     */
    public static String toHexString(final byte[] b) {// String to byte
        final StringBuilder sb = new StringBuilder(b.length * 2);
        for (final byte element : b) {
            sb.append(HEX_DIGITS[(element & 0xf0) >>> 4]);
            sb.append(HEX_DIGITS[element & 0x0f]);
        }
        return sb.toString();
    }

    // MD5相关函数------------end---------------

    // 网络判断相关------------start---------------
    /**
     * 没有连接网络
     */
    public static final int NETWORK_NONE = -1;
    /**
     * 移动网络
     */
    public static final int NETWORK_MOBILE = 0;
    /**
     * 无线网络
     */
    public static final int NETWORK_WIFI = 1;


    /**
     * 获取网络类型
     *
     * @param context
     * @return
     */
    public static int getNetWorkState(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }
        }
        return NETWORK_NONE;
    }

    /**
     * 判断是否有网络
     *
     * @param context
     * @return
     */
    public static boolean isNetConnect(Context context) {
        if (NETWORK_NONE == getNetWorkState(context)) {
            return false;
        }
        return true;
    }
    // 网络判断相关------------end---------------


    /**
     * 判断app处在前台还是后台
     *
     * @param context
     * @return
     */
    public static boolean isBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    System.out.print(String.format("Foreground App:", appProcess.processName));
                    return false;
                } else {
                    System.out.print("Background App:" + appProcess.processName);
                    return true;
                }
            }
        }
        return false;
    }

    //获取电话号码
    public static String getNativePhoneNumber(Context context) {
        String nativePhoneNumber = "";
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        nativePhoneNumber = telephonyManager.getLine1Number();
        return nativePhoneNumber;
    }

    /**
     * 判断 用户是否安装QQ客户端
     */
    public static boolean isInstallQQ(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String pn = packageInfos.get(i).packageName;
                if (pn.equalsIgnoreCase("com.tencent.qqlite") || pn.equalsIgnoreCase("com.tencent.mobileqq")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断用户是否安装微博客户端
     */
    public static boolean isInstallSina(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String pn = packageInfos.get(i).packageName;
                if (pn.equalsIgnoreCase("com.sina.weibo")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isInstallWX(Context context) {
        PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String pn = packageInfos.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    public static CharSequence fromHtml(String text) {
        return Html.fromHtml(text);
    }

    /**
     * 获取手机厂商
     *
     * @return 手机厂商
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * 获取手机型号
     *
     * @return 获取手机型号
     */
    public static String getSystemModel() {
        return Build.MODEL;
    }

    /***
     * 获取当前手机横屏状态
     *
     * @param activity 活动
     * @return int
     ***/
    public static boolean isLand(@NonNull Context activity) {
        Resources resources = activity.getResources();
        return !(resources == null || resources.getConfiguration() == null) && resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }


    /**
     * 隐藏navigationBar
     *
     * @param context
     */
    public static void hideNavKey(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = ((Activity) context).getWindow().getDecorView();

            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

}
