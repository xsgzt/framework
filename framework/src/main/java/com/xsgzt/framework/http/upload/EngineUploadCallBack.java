package com.xsgzt.framework.http.upload;

import android.content.Context;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/18.
 */

public interface EngineUploadCallBack {
    EngineUploadCallBack DEFAULT_CALL_BACK = new EngineUploadCallBack() {
        @Override
        public void onPreExecute(Context var1, Map<String, Object> var2) {

        }

        @Override
        public void onError(Exception var1) {

        }

        @Override
        public void onComplete() {

        }

        @Override
        public void onResponse(long var1, long var3) {

        }

        @Override
        public void onSuccess(String resultJson) {

        }
    };
    void onPreExecute(Context var1, Map<String, Object> var2);
    void onError(Exception var1);
    void onComplete();
    void onResponse(long currenSize, long contenLeng);

    void onSuccess(String resultJson);
}
