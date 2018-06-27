package com.lzlmall.b2b.app.vendor.framework.http.upload;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by Administrator on 2018/5/18.
 */

public class UploadProgressRequstBody extends RequestBody {
    private RequestBody mRequestBody;
    private ProgressRequestListener mProgressRequestListener;
    private BufferedSink mBufferedSink;

    public UploadProgressRequstBody(RequestBody requestBody, ProgressRequestListener progressListener) {
        mRequestBody = requestBody;
        mProgressRequestListener = progressListener;

    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }


    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

//        BufferedSink
        //把他读出来   然后写进去
        if (mBufferedSink == null) {
            mBufferedSink = Okio.buffer(sink(sink));
        }

        mRequestBody.writeTo(mBufferedSink);
        mBufferedSink.flush();

    }

    private Sink sink(BufferedSink sink) {

        return new ForwardingSink(sink) {
            long contentLength = 0L;
            long bytesWritten = 0L;
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    contentLength = contentLength();
                }
                bytesWritten += byteCount;
                mProgressRequestListener.onRequestProgress(bytesWritten, contentLength, bytesWritten == contentLength);
            }
        };
    }


}
