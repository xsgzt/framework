package com.lzlmall.b2b.app.vendor.framework.http;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2018/5/7.
 */

public class HeadersIntercept implements Interceptor {

    private String token ="";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        Object param = PreferencesUtil.getInstance().getParam(Constant.TOKEN, "");
//        if (param == null) {
//
//        } else {
//            token = (String) param;
//        }

        Request request1 = request.newBuilder().addHeader("token", token).build();
        return chain.proceed(request1);
    }
}
