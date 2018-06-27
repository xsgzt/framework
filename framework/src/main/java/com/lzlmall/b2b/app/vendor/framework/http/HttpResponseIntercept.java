package com.lzlmall.b2b.app.vendor.framework.http;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2018/5/4.
 */

public class HttpResponseIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        MediaType mediaType = response.body().contentType();
        String string= response.body().string();
        if(!TextUtils.isEmpty(string)) {
            //保存再sp
        }

        return response.newBuilder()
                .body(ResponseBody.create(mediaType, string))
                .build();
    }
}
