package com.xsgzt.framework.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/18.
 */

public interface HttpEngineCallBack  {

    HttpEngineCallBack DEFAULT_CALL_BACK = new HttpEngineCallBack() {
        @Override
        public void onPreExecute(Context context, Map<String, Object> params) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };

    void onPreExecute(Context context, Map<String,Object> params);

    void onError(Exception e);

    void onSuccess(String result);
}
