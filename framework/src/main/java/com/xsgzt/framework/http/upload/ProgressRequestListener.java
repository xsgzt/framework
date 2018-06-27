package com.xsgzt.framework.http.upload;

/**
 * Created by Administrator on 2018/5/18.
 */

public interface ProgressRequestListener {
    void onRequestProgress(long bytesWritten, long contentLength, boolean done);
}
