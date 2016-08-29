package com.quocvusolution.utility;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

public class JsonUtility {
    public static String getString(JSONObject jObject, String key) {
        String val = "";
        try {
            val = jObject.getString(key);
        } catch (Exception e) {
        }
        return val;
    }

    public static int getInt(JSONObject jObject, String key) {
        int val = 0;
        try {
            val = jObject.getInt(key);
        } catch (Exception e) {
        }
        return val;
    }

    public static long getLong(JSONObject jObject, String key) {
        long val = 0;
        try {
            val = jObject.getLong(key);
        } catch (Exception e) {
        }
        return val;
    }

    public static double getDouble(JSONObject jObject, String key) {
        double val = 0;
        try {
            val = jObject.getDouble(key);
        } catch (Exception e) {
        }
        return val;
    }

    public static boolean getBoolean(JSONObject jObject, String key) {
        boolean val = false;
        try {
            val = jObject.getBoolean(key);
        } catch (Exception e) {
        }
        return val;
    }

    public static Date getDate(JSONObject jObject, String key) {
        Date val = new Date();
        try {
            val = new Date(Long.parseLong(jObject.getString(key).replaceAll("\\D", "")));
        } catch (Exception e) {
        }
        return val;
    }

    public static String[] getStrings(JSONObject jObject, String key) {
        String[] val = new String[0];
        try {
            JSONArray jObjectArr = jObject.getJSONArray(key);
            String[] arr = new String[jObjectArr.length()];
            for (int i = 0; i < jObjectArr.length(); i++) {
                arr[i] = jObjectArr.getString(i);
            }
            val = arr;
        } catch (Exception e) {
        }
        return val;
    }

    public static int[] getInts(JSONObject jObject, String key) {
        int[] val = new int[0];
        try {
            JSONArray jObjectArr = jObject.getJSONArray(key);
            int[] arr = new int[jObjectArr.length()];
            for (int i = 0; i < jObjectArr.length(); i++) {
                arr[i] = jObjectArr.getInt(i);
            }
            val = arr;
        } catch (Exception e) {
        }
        return val;
    }

    public static long[] getLongs(JSONObject jObject, String key) {
        long[] val = new long[0];
        try {
            JSONArray jObjectArr = jObject.getJSONArray(key);
            long[] arr = new long[jObjectArr.length()];
            for (int i = 0; i < jObjectArr.length(); i++) {
                arr[i] = jObjectArr.getLong(i);
            }
            val = arr;
        } catch (Exception e) {
        }
        return val;
    }

    public static double[] getDoubles(JSONObject jObject, String key) {
        double[] val = new double[0];
        try {
            JSONArray jObjectArr = jObject.getJSONArray(key);
            double[] arr = new double[jObjectArr.length()];
            for (int i = 0; i < jObjectArr.length(); i++) {
                arr[i] = jObjectArr.getDouble(i);
            }
            val = arr;
        } catch (Exception e) {
        }
        return val;
    }

    public static boolean[] getBooleans(JSONObject jObject, String key) {
        boolean[] val = new boolean[0];
        try {
            JSONArray jObjectArr = jObject.getJSONArray(key);
            boolean[] arr = new boolean[jObjectArr.length()];
            for (int i = 0; i < jObjectArr.length(); i++) {
                arr[i] = jObjectArr.getBoolean(i);
            }
            val = arr;
        } catch (Exception e) {
        }
        return val;
    }

    public static Date[] getDates(JSONObject jObject, String key) {
        Date[] val = new Date[0];
        try {
            JSONArray jObjectArr = jObject.getJSONArray(key);
            Date[] arr = new Date[jObjectArr.length()];
            for (int i = 0; i < jObjectArr.length(); i++) {
                arr[i] = new Date(Long.parseLong(jObjectArr.getString(i).replaceAll("\\D", "")));
            }
            val = arr;
        } catch (Exception e) {
        }
        return val;
    }
}
