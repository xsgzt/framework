package com.xsgzt.framework.http;

import android.content.Context;

import com.xsgzt.framework.http.upload.EngineUploadCallBack;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2018/4/18.
 * 网络引擎接口
 */

public interface IHttpEngine {
    void get(Context context, String url, Map<String,Object> params,HttpEngineCallBack callBack,boolean isCach);

    void post(Context context, String url, Map<String,Object> params,HttpEngineCallBack callBack,boolean isCach);

    void put(Context context, String url, Map<String,Object> params,HttpEngineCallBack callBack,boolean isCach);

    void delete(Context context, String url, Map<String,Object> params,HttpEngineCallBack callBack,boolean isCach);

    void postJson(Context context, String url, Map<String,Object> params,HttpEngineCallBack callBack,boolean isCach);

//    void download(String url, Map<String, Object> params, EngineDownLoadCallBack callBack);
//
    void upload(Context context, String url, Map<String, Object> params, EngineUploadCallBack callBack);


    void potsJsonJsonObject(Context context, String url, JSONObject params, HttpEngineCallBack callBack, boolean isCach);




}
