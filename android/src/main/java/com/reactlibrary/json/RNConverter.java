package com.reactlibrary.json;

import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.WritableNativeArray;
import com.facebook.react.bridge.WritableNativeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.Iterator;


public class RNConverter {

    public static WritableMap jsonToReact(String mJson) {
        WritableMap mWrite = Arguments.createMap();
        try {

            Object json = new JSONTokener(mJson).nextValue();

            if (json instanceof JSONArray) {
                jsonToArray(mWrite, mJson);
            } else {
                jsonToMap(mWrite, mJson);
            }

        } catch (final JSONException e) {
            Log.e("JSONException", "Json parsing error: " + e.getMessage());
        }

        return mWrite;
    }

    public static WritableMap jsonToArray(WritableMap mWrite, String mJson) {
        WritableArray body;

        try {
            JSONArray mArray = new JSONArray(mJson);
            body = convertJsonToArray(mArray);

            mWrite.putArray("body", body);

        } catch (final JSONException e) {

        }

        return mWrite;
    }

    public static WritableMap jsonToMap(WritableMap mWrite, String mJson) {
        WritableMap body;

        try {
            JSONObject mObject = new JSONObject(mJson);
            body = convertJsonToMap(mObject);

            mWrite.putMap("body", body);
        } catch (final JSONException e) {
            Log.e("JSONException", "Json parsing error: " + e.getMessage());
        }

        return mWrite;
    }

    private static WritableArray convertJsonToArray(JSONArray jsonArray) throws JSONException {
        WritableArray array = new WritableNativeArray();

        for (int i = 0; i < jsonArray.length(); i++) {
            Object value = jsonArray.get(i);
            if (value instanceof JSONObject) {
                array.pushMap(convertJsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                array.pushArray(convertJsonToArray((JSONArray) value));
            } else if (value instanceof Boolean) {
                array.pushBoolean((Boolean) value);
            } else if (value instanceof Integer) {
                array.pushInt((Integer) value);
            } else if (value instanceof Double) {
                array.pushDouble((Double) value);
            } else if (value instanceof String) {
                array.pushString((String) value);
            } else {
                array.pushString(value.toString());
            }
        }
        return array;
    }

    private static WritableMap convertJsonToMap(JSONObject jsonObject) throws JSONException {
        WritableMap map = new WritableNativeMap();

        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                map.putMap(key, convertJsonToMap((JSONObject) value));
            } else if (value instanceof JSONArray) {
                map.putArray(key, convertJsonToArray((JSONArray) value));
            } else if (value instanceof  Boolean) {
                map.putBoolean(key, (Boolean) value);
            } else if (value instanceof  Integer) {
                map.putInt(key, (Integer) value);
            } else if (value instanceof  Double) {
                map.putDouble(key, (Double) value);
            } else if (value instanceof String)  {
                map.putString(key, (String) value);
            } else {
                map.putString(key, value.toString());
            }
        }
        return map;
    }
}

