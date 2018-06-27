package com.lzlmall.b2b.app.vendor.framework.http;

import android.content.Context;

import com.lzlmall.b2b.app.vendor.framework.http.upload.EngineUploadCallBack;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/18.
 * http工具类
 */

public class HttpUtils {
    private static IHttpEngine mDefaultEngine = null;
    private IHttpEngine mIHttpEngine = null;

    public Context mContext;
    private static String mUrl;
    private int mType = POST;
    private final static int POST = 0x0001;
    private final static int GET = 0x0002;
    private final static int PUT = 0x0003;
    private final static int DELETE = 0x0004;
    private final static int POSTJSON = 0x0005;
    private final static int UPLOAD = 0x0006;
    private boolean mCache = false;
    private Map<String, Object> mParams;


    public HttpUtils(Context context) {
        mContext = context;
        mParams = new HashMap<>();
    }

    public static HttpUtils with(Context context) {
        return new HttpUtils(context);
    }

    public HttpUtils url(String url) {
        this.mUrl = url;
        return this;
    }

    public HttpUtils get() {
        this.mType = GET;
        return this;
    }

    public HttpUtils post() {
        this.mType = POST;
        return this;
    }

    public HttpUtils postJson() {
        this.mType = POSTJSON;
        return this;
    }

    public HttpUtils put() {
        this.mType = PUT;
        return this;
    }

    public HttpUtils delete() {
        this.mType = DELETE;
        return this;
    }


    public HttpUtils cache(boolean isCache) {
        this.mCache = isCache;
        return this;
    }

    public HttpUtils addParam(String key, Object value) {
        this.mParams.put(key, value);
        return this;
    }

    public HttpUtils addParams(Map<String, Object> params) {
        this.mParams.putAll(params);
        return this;
    }

    public static void init(IHttpEngine httpEngine, String url) {
        mUrl = url;
        mDefaultEngine = httpEngine;
    }

    public HttpUtils engine(IHttpEngine httpEngine) {
        this.mIHttpEngine = httpEngine;
        return this;
    }

    public void uplod(EngineUploadCallBack callBack) {
        if (callBack == null) {
            callBack = EngineUploadCallBack.DEFAULT_CALL_BACK;
        }

        if (this.mIHttpEngine == null) {
            this.mIHttpEngine = mDefaultEngine;
        }

        if (this.mIHttpEngine == null) {
            throw new NullPointerException("第三方的引擎为空，请在Application中初始化！");
        } else {
            callBack.onPreExecute(this.mContext, this.mParams);
            upload(this.mContext, this.mUrl, this.mParams, callBack);
        }
    }

    public void execute() {
        execute(null);
    }

    public void execute(HttpEngineCallBack callBack) {
        //执行  判断引擎初始化
        if (mUrl == null) {
            throw new NullPointerException("url链接为null！");
        }
        if (callBack == null) {
            callBack = HttpEngineCallBack.DEFAULT_CALL_BACK;
        }
        if (this.mIHttpEngine == null) {
            this.mIHttpEngine = mDefaultEngine;
        }
        if (mIHttpEngine == null) {
            throw new NullPointerException("第三方的引擎为空，请在Application中初始化！");
        } else {
            //请求
            callBack.onPreExecute(mContext, mParams);
            if (mType == GET) {
                get(mContext, mUrl, mParams, callBack, mCache);
            }
            if (mType == POST) {
                post(mContext, mUrl, mParams, callBack, mCache);
            }
            if (mType == PUT) {
                put(mContext, mUrl, mParams, callBack, mCache);
            }
            if (mType == DELETE) {
                delete(mContext, mUrl, mParams, callBack, mCache);
            }
            if (mType == POSTJSON) {
                postJson(mContext, mUrl, mParams, callBack, mCache);
            }


        }


    }


    public void get(Context context, String url, Map<String, Object> params, HttpEngineCallBack callBack, boolean isCache) {
        mIHttpEngine.get(context, url, params, callBack, isCache);
    }

    public void post(Context context, String url, Map<String, Object> params, HttpEngineCallBack callBack, boolean isCache) {
        mIHttpEngine.post(context, url, params, callBack, isCache);
    }

    public void put(Context context, String url, Map<String, Object> params, HttpEngineCallBack callBack, boolean isCache) {
        mIHttpEngine.put(context, url, params, callBack, isCache);
    }


    public void delete(Context context, String url, Map<String, Object> params, HttpEngineCallBack callBack, boolean isCache) {
        mIHttpEngine.delete(context, url, params, callBack, isCache);
    }

    public void postJson(Context context, String url, Map<String, Object> params, HttpEngineCallBack callBack, boolean isCache) {
        mIHttpEngine.postJson(context, url, params, callBack, isCache);
    }

    public void upload(Context context, String url, Map<String, Object> params, EngineUploadCallBack callBack) {
        mIHttpEngine.upload(context, url, params, callBack);
    }

    public void potsJsonJsonObject(JSONObject params, HttpEngineCallBack callBack, boolean isCache) {
        if (this.mIHttpEngine == null) {
            this.mIHttpEngine = mDefaultEngine;
        }
        mIHttpEngine.potsJsonJsonObject(mContext, mUrl, params, callBack, false);
    }


    /**
     * 拼接请求参数
     *
     * @param url
     * @param params
     * @return
     */
    public static String jointParams(String url, Map<String, Object> params) {
        if (params != null && params.size() > 0) {
            StringBuffer stringBuffer = new StringBuffer(url);
            if (!url.contains("?")) {
                stringBuffer.append("?");
            } else if (!url.endsWith("?")) {
                stringBuffer.append("&");
            }

            Iterator var3 = params.entrySet().iterator();

            while (var3.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry) var3.next();
                stringBuffer.append((String) entry.getKey() + "=" + entry.getValue() + "&");
            }

            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            return stringBuffer.toString();
        } else {
            return url;
        }
    }


    /**
     * 获取泛型上的类
     *
     * @param object
     * @return
     */
    public static Class<?> analysisClazzInfo(Object object) {
        Type genType = object.getClass().getGenericSuperclass();

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        return (Class) params[0];
    }
}
