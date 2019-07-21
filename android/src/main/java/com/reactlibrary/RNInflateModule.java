
package com.reactlibrary;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.ReadableMapKeySetIterator;
import com.facebook.react.bridge.ReadableType;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.HttpUrl;
import okhttp3.Callback;

public class RNInflateModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;
  private static final String REQUEST_ERROR = "REQUEST_ERROR";
  private String inflateData = "";

  public RNInflateModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNInflate";
  }

  @ReactMethod
  public void getRequest(String url, ReadableMap headers, ReadableMap params , final Promise promise) throws IOException {
    OkHttpClient client = new OkHttpClient();
    HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();

    // Map all params to QueryParams
    urlBuilder = addParams(urlBuilder, params);

    String builderUrl = urlBuilder.build().toString();
    Request.Builder builder = new Request.Builder();

    // Map all headers if headers were existed
    builder = addHeaders(builder, headers);
    Request request = builder.url(builderUrl).get().build();

    client.newCall(request).enqueue(new Callback() {
      @Override
      public void onFailure(Call call, IOException e) {
        promise.reject(REQUEST_ERROR, e.getCause());
        call.cancel();
      }

      @Override
      public void onResponse(Call call, Response response) throws IOException {
        final InputStream in = response.body().byteStream();
        inflateData = getDataInflate(in);

        promise.resolve(inflateData);
      }
    });
  }

  private String getDataInflate(InputStream in) {
    try {
      // Decompress the bytes
      Inflater deCompressor = new Inflater(true);
      InflaterInputStream input = new InflaterInputStream(in, deCompressor);

      BufferedReader b = new BufferedReader(new InputStreamReader(input, "UTF8"));
      String line;
      StringBuilder out = new StringBuilder();
      while ((line = b.readLine()) != null) {
        out.append(line);
      }

      b.close();
      input.close();
      deCompressor.end();
      return out.toString();

    } catch (Exception e) {
      e.printStackTrace();
    }

    return "";
  }

  private Request.Builder addHeaders(Request.Builder builder, ReadableMap headers) {
    ReadableMapKeySetIterator iterator = headers.keySetIterator();

    while (iterator.hasNextKey()) {
      String key = iterator.nextKey();
      String value = getStringValue(key, headers);

      builder.addHeader(key, value);
    }

    return  builder;
  }

  private HttpUrl.Builder addParams(HttpUrl.Builder urlBuilder, ReadableMap params) {
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
      default:
        value = map.getString(key);
        break;
    }

    return value;
  }
}