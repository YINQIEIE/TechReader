package com.yq.eie.utils;

import android.content.Context;

/**
 * Created by admin on 2017/9/30.
 * SharedPreference 工具类
 */

public class SPUtil {

    public static final String TAG = SPUtil.class.getSimpleName();
    private static final String SP_NAME = "sp_info";

    /**
     * 保存布尔类型的值
     *
     * @param context 上下文
     * @param key     key 值
     * @param val     value 值
     */
    public static void saveBooleanVal(Context context, String key, boolean val) {
        PrefUtils.put(context, SP_NAME, key, val);
    }

    public static boolean getBooleanByKey(Context context, String key, boolean defValue) {
        return (boolean) PrefUtils.get(context, SP_NAME, key, defValue);
    }

    public static String getStringValByKey(Context context, String key) {
        return (String) PrefUtils.get(context, SP_NAME, key, "");
    }

    public static String getStringValByKey(Context context, String key, String defValue) {
        return (String) PrefUtils.get(context, SP_NAME, key, defValue);
    }

    public static void saveString(Context context, String key, String val) {
        PrefUtils.put(context, SP_NAME, key, val);
    }

    public static void saveIntValue(Context context, String key, int val) {
        PrefUtils.put(context, SP_NAME, key, val);
    }

    public static int getIntValue(Context context, String key) {
        return (int) PrefUtils.get(context, SP_NAME, key, 0);
    }

    public static int getIntValue(Context context, String key, int defValue) {
        return (int) PrefUtils.get(context, SP_NAME, key, defValue);
    }

    public static void saveFloatValue(Context context, String key, float val) {
        PrefUtils.put(context, SP_NAME, key, val);
    }

    public static float getFloatValue(Context context, String key) {
        return (float) PrefUtils.get(context, SP_NAME, key, 0f);
    }

    public static float getFloatValue(Context context, String key, float defValue) {
        return (float) PrefUtils.get(context, SP_NAME, key, defValue);
    }
}
