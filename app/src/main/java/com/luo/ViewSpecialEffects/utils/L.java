package com.luo.ViewSpecialEffects.utils;


public class L {
    private static final String tag = "test";


    public L() {
    }

    public static void d(String msg) {
        android.util.Log.d(tag, msg + "");
    }

    public static void d(int msg) {
        android.util.Log.d(tag, msg + "");
    }
}
