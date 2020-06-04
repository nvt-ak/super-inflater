
package com.reactlibrary;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.WritableMap;
import com.reactlibrary.api.ApiManager;
import com.reactlibrary.common.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Callback;

public class RNInflateModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private final ApiManager mApiManager;
  private final Utils mUtils;
  private static final String REQUEST_ERROR = "REQUEST_ERROR";
  private final OkHttpClient mClient;
  private WritableMap inflateData;

  public RNInflateModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
    this.mApiManager = new ApiManager();
    this.mClient = new OkHttpClient();
    this.mUtils = new Utils();
  }

  @Override
  public String getName() {
    return "RNInflate";
  }

  @ReactMethod
  public void get(String url, ReadableMap headers, ReadableMap params , final Promise promise) throws IOException {
    Request mRequest = this.mApiManager.get(url, headers, params);

    mClient.newCall(mRequest).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        promise.reject(REQUEST_ERROR, e.getCause());
        call.cancel();
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        inflateData = mUtils.getDataInflate(response);
        promise.resolve(inflateData);
      }
    });
  }

  @ReactMethod
  public void post(String url, ReadableMap headers, String body , final Promise promise) throws IOException {
    Request mRequest = this.mApiManager.post(url, headers, body);

    mClient.newCall(mRequest).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        promise.reject(REQUEST_ERROR, e.getCause());
        call.cancel();
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        inflateData = mUtils.getDataInflate(response);
        promise.resolve(inflateData);
      }
    });
  }

  @ReactMethod
  public void multiPost(String url, ReadableMap headers, String body , ReadableArray files, final Promise promise) throws IOException {
    Request mRequest = this.mApiManager.multiPost(url, headers, body, files);

    mClient.newCall(mRequest).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        promise.reject(REQUEST_ERROR, e.getCause());
        call.cancel();
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        inflateData = mUtils.getDataInflate(response);
        promise.resolve(inflateData);
      }
    });
  }

  @ReactMethod
  public void put(String url, ReadableMap headers, String body , final Promise promise) throws IOException {
    Request mRequest = this.mApiManager.put(url, headers, body);

    mClient.newCall(mRequest).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        promise.reject(REQUEST_ERROR, e.getCause());
        call.cancel();
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        inflateData = mUtils.getDataInflate(response);
        promise.resolve(inflateData);
      }
    });
  }

  @ReactMethod
  public void delete(String url, ReadableMap headers, final Promise promise) throws IOException {
    Request mRequest = this.mApiManager.delete(url, headers);
    mClient.newCall(mRequest).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        promise.reject(REQUEST_ERROR, e.getCause());
        call.cancel();
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        inflateData = mUtils.getDataInflate(response);
        promise.resolve(inflateData);
      }
    });
  }
}