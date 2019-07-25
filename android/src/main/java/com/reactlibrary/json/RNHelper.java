package com.reactlibrary.json;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;

public class RNHelper {
    public static WritableMap jsonToReact(String mJson) {
        WritableMap mWrite = Arguments.createMap();

        mWrite.putString("body", mJson);
        mWrite.putString("message", "Fetch data success");

        return mWrite;
    }
}
