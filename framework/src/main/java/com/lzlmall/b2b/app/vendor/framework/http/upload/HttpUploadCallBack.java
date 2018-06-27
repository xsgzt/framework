package com.lzlmall.b2b.app.vendor.framework.http.upload;

import android.content.Context;

import com.google.gson.Gson;
import com.lzlmall.b2b.app.vendor.framework.base.BaseResult;
import com.lzlmall.b2b.app.vendor.framework.http.HttpUtils;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2017/6/5.
 */

public abstract class HttpUploadCallBack<T> implements EngineUploadCallBack {

    @Override
    public void onPreExecute(Context context, Map<String, Object> params) {

    }


    @Override
    public void onComplete() {

    }


    @Override
    public void onSuccess(String result) {
//        T bean = null;
//        try {
//            JSONObject jsonObject = new JSONObject(result);
//            String code = jsonObject.getString("code");
//            String msg = jsonObject.getString("message");
//            String data = jsonObject.getString("data");
//
//            Class<T> clazz = (Class<T>) HttpUtils.analysisClazzInfo(this);
//
//            if ("200".equals(code)) {
//                bean = (T) new Gson().fromJson(data, clazz);
//            } else {
//                BaseResult model = (BaseResult) clazz.newInstance();
//                bean = (T) model;
//                model.code = code;
//                model.message = msg;
//            }
//
//            if ("500".equals(code)) {
//                onError(new Exception(msg));
//                return;
//            }
//            onSuccess(bean);
//
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            onError(e);
//        }
    }

    /**
     * 成功回调
     *
     * @param result
     */
    public abstract void onSuccess(T result);
}
