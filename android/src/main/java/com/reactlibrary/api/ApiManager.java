package com.reactlibrary.api;


import com.facebook.react.bridge.ReadableMap;
import com.reactlibrary.common.RequestManager;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

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
