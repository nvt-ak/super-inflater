package com.reactlibrary.api;


import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableArray;
import com.reactlibrary.common.RequestManager;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import java.io.File;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import android.provider.MediaStore;
public class ApiManager {
    private RequestManager rManager;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public ApiManager() {
        this.rManager = new RequestManager();
    }

    public Request get(String url, ReadableMap headers, ReadableMap params) {
        Request.Builder rBuilder = new Request.Builder();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        // Map all params to QueryParams
        urlBuilder = this.rManager.paramsConverter(urlBuilder, params);

        String builderUrl = urlBuilder.build().toString();

        rBuilder.url(builderUrl);


        // Map all headers if headers were existed
        rBuilder = this.rManager.headersConverter(rBuilder, headers);

        return rBuilder.get().build();
    }

    public Request post(String url, ReadableMap headers, String body) {
        Request.Builder rBuilder = new Request.Builder();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        RequestBody rBody = RequestBody.create(JSON, body);
        String builderUrl = urlBuilder.build().toString();

        // Map all headers if headers were existed
        rBuilder = this.rManager.headersConverter(rBuilder, headers);

        return rBuilder.url(builderUrl).post(rBody).build();
    }

    public Request multiPost(String url, ReadableMap headers, String body, ReadableArray files) {
        Request.Builder rBuilder = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        MultipartBody.Builder bodyBuilder = new MultipartBody.Builder();
        bodyBuilder.setType(MultipartBody.FORM).addFormDataPart("dataSet", body);
        for (int i = 0; i < files.size(); i++) {
            ReadableMap frame = files.getMap(i);
            String type = frame.getString("type");
//            String uri = "";
//            try{
//                uri = URLDecoder.decode(frame.getString("uri"),"UTF-8");
//            } catch (Exception e){
//                System.out.println(e);
//            }
            File file = new File(frame.getString("uri"));
            RequestBody fileBody = RequestBody.create(MediaType.parse(type), file);
            bodyBuilder.addFormDataPart(String.format(Locale.ENGLISH, "file%d", i),file.getName(), fileBody);
        }
        String builderUrl = urlBuilder.build().toString();
        rBuilder = this.rManager.headersConverter(rBuilder, headers);
        MultipartBody multipartBody = bodyBuilder.build();
        return rBuilder.url(builderUrl).post(multipartBody).build();
    }

    public Request put(String url, ReadableMap headers, String body) {
        Request.Builder rBuilder = new Request.Builder();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

        RequestBody rBody = RequestBody.create(JSON, body);
        String builderUrl = urlBuilder.build().toString();

        // Map all headers if headers were existed
        rBuilder = this.rManager.headersConverter(rBuilder, headers);

        return rBuilder.url(builderUrl).put(rBody).build();
    }

    public Request delete(String url, ReadableMap headers) {
        Request.Builder rBuilder = new Request.Builder();

        // Map all headers if headers were existed
        rBuilder = this.rManager.headersConverter(rBuilder, headers);

        return rBuilder.url(url).delete().build();
    }
}
