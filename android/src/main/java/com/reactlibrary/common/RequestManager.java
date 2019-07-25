package com.reactlibrary.common;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;

import okhttp3.HttpUrl;
import okhttp3.Request;

public class RequestManager {


    public Request.Builder headersConverter(Request.Builder builder, ReadableMap headers) {
        ReadableMapKeySetIterator iterator = headers.keySetIterator();

        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            String value = getStringValue(key, headers);

            builder.addHeader(key, value);
        }

        return  builder;
    }

    public HttpUrl.Builder paramsConverter(HttpUrl.Builder urlBuilder, ReadableMap params) {
        ReadableMapKeySetIterator iterator = params.keySetIterator();

        while (iterator.hasNextKey()) {
            String key = iterator.nextKey();
            String value = getStringValue(key, params);

            urlBuilder.addQueryParameter(key, value);
        }

        return  urlBuilder;
    }


    private String getStringValue(String key, ReadableMap map) {
        ReadableType type = map.getType(key);
        String value;

        switch (type) {
            case Number:
                value = Integer.toString(map.getInt(key));
                break;
            case Boolean:
                value = Boolean.toString(map.getBoolean(key));
                break;
            case Null:
                value = "";
                break;
            default:
                value = map.getString(key);
                break;
        }

        return value;
    }
}
