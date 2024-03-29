package ir.mlibs.mylibraryimageprogressbar;

import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.GlideModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;

import okhttp3.ConnectionSpec;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.TlsVersion;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * Created by vali on 2017-05-10.
 */

public class OkHttpProgressGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        //OkHttpClient.Builder builder = new OkHttpClient.Builder();
       // builder.networkInterceptors().add(createInterceptor(new DispatchingProgressListener()));
        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(getNewHttpClient()));
    }

    private static Interceptor createInterceptor(final ResponseProgressListener listener) {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Response response = chain.proceed(request);
                return response.newBuilder()
                        .body(new OkHttpProgressResponseBody(request.url(), response.body(), listener))
                        .build();
            }
        };
    }

    public interface UIProgressListener {
        void onProgress(long bytesRead, long expectedLength);

        /**
         * Control how often the listener needs an update. 0% and 100% will always be dispatched.
         *
         * @return in percentage (0.2 = call {@link #onProgress} around every 0.2 percent of progress)
         */
        float getGranualityPercentage();
    }

    public static void forget(String url) {
        DispatchingProgressListener.forget(url);
    }

    public static void expect(String url, UIProgressListener listener) {
        DispatchingProgressListener.expect(url, listener);
    }

    private interface ResponseProgressListener {
        void update(HttpUrl url, long bytesRead, long contentLength);
    }

    private static class DispatchingProgressListener implements ResponseProgressListener {
        private static final Map<String, UIProgressListener> LISTENERS = new ConcurrentHashMap<>();
        private static final Map<String, Long> PROGRESSES = new ConcurrentHashMap<>();

        private final Handler handler;

        DispatchingProgressListener() {
            this.handler = new Handler(Looper.getMainLooper());
        }

        static void forget(String url) {
            LISTENERS.remove(url);
            PROGRESSES.remove(url);
        }

        static void expect(String url, UIProgressListener listener) {
            LISTENERS.put(url, listener);
        }

        @Override
        public void update(HttpUrl url, final long bytesRead, final long contentLength) {
            //System.out.printf("%s: %d/%d = %.2f%%%n", url, bytesRead, contentLength, (100f * bytesRead) / contentLength);
            String key = url.toString();
            final UIProgressListener listener = LISTENERS.get(key);
            if (listener == null) {
                return;
            }
            //长度是错误的移除监听
            if (contentLength <= bytesRead) {
                forget(key);
            }
            if (needsDispatch(key, bytesRead, contentLength, listener.getGranualityPercentage())) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.onProgress(bytesRead, contentLength);
                    }
                });
            }
        }

        private boolean needsDispatch(String key, long current, long total, float granularity) {
            if (granularity == 0 || current == 0 || total == current) {
                return true;
            }
            float percent = 100f * current / total;
            long currentProgress = (long) (percent / granularity);
            Long lastProgress = PROGRESSES.get(key);
            if (lastProgress == null || currentProgress != lastProgress) {
                PROGRESSES.put(key, currentProgress);
                return true;
            } else {
                return false;
            }
        }
    }

    private static class OkHttpProgressResponseBody extends ResponseBody {
        private final HttpUrl url;
        private final ResponseBody responseBody;
        private final ResponseProgressListener progressListener;
        private BufferedSource bufferedSource;

        OkHttpProgressResponseBody(HttpUrl url, ResponseBody responseBody,
                                   ResponseProgressListener progressListener) {
            this.url = url;
            this.responseBody = responseBody;
            this.progressListener = progressListener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (bufferedSource == null) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    long fullLength = responseBody.contentLength();
                    if (bytesRead == -1) { // this source is exhausted
                        totalBytesRead = fullLength;
                    } else {
                        totalBytesRead += bytesRead;
                    }
                    progressListener.update(url, totalBytesRead, fullLength);
                    return bytesRead;
                }
            };
        }
    }
    public static OkHttpClient.Builder enableTls12OnPreLollipop(OkHttpClient.Builder client) {
        if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 22) {
            try {
                SSLContext sc = SSLContext.getInstance("TLSv1.2");
                sc.init(null, null, null);
                client.sslSocketFactory(new Tls12SocketFactory(sc.getSocketFactory()));

                ConnectionSpec cs = new ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                        .tlsVersions(TlsVersion.TLS_1_2)
                        .build();

                List<ConnectionSpec> specs = new ArrayList<>();
                specs.add(cs);
                specs.add(ConnectionSpec.COMPATIBLE_TLS);
                specs.add(ConnectionSpec.CLEARTEXT);

                client.connectionSpecs(specs);
            } catch (Exception exc) {
                Log.e("OkHttpTLSCompat", "Error while setting TLS 1.2", exc);
            }
        }

        return client;
    }

    private OkHttpClient getNewHttpClient() {
        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .cache(null)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS);
        client.networkInterceptors().add(createInterceptor(new DispatchingProgressListener()));
        return enableTls12OnPreLollipop(client).build();
    }
}
//    @Override public void applyOptions(Context context, GlideBuilder builder) {	}
//    @Override public void registerComponents(Context context, Glide glide) {
//        OkHttpClient client = new OkHttpClient();
//        client.networkInterceptors().add(createInterceptor(new DispatchingProgressListener()));
//        glide.register(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client));
//    }
//
//    private static Interceptor createInterceptor(final ResponseProgressListener listener) {
//        return new Interceptor() {
//            @Override public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                Response response = chain.proceed(request);
//
//                return response.newBuilder()
//                        .body(new OkHttpProgressResponseBody(request.url(), response.body(), listener))
//                        .build();
//            }
//        };
//    }
//
//
//    public interface UIProgressListener {
//        void onProgress(long bytesRead, long expectedLength);
//        /**
//         * Control how often the listener needs an update. 0% and 100% will always be dispatched.
//         * @return in percentage (0.2 = call {@link #onProgress} around every 0.2 percent of progress)
//         */
//        float getGranualityPercentage();
//    }
//
//    public static void forget(String url) {
//        DispatchingProgressListener.forget(url);
//    }
//    public static void expect(String url, UIProgressListener listener) {
//        DispatchingProgressListener.expect(url, listener);
//    }
//
//    private interface ResponseProgressListener {
//        void update(HttpUrl url, long bytesRead, long contentLength);
//    }
//
//    private static class DispatchingProgressListener implements ResponseProgressListener {
//        private static final Map<String, UIProgressListener> LISTENERS = new HashMap<>();
//        private static final Map<String, Long> PROGRESSES = new HashMap<>();
//
//        private final Handler handler;
//        DispatchingProgressListener() {
//            this.handler = new Handler(Looper.getMainLooper());
//        }
//
//        static void forget(String url) {
//            LISTENERS.remove(url);
//            PROGRESSES.remove(url);
//        }
//        static void expect(String url, UIProgressListener listener) {
//            LISTENERS.put(url, listener);
//        }
//
//        @Override public void update(HttpUrl url, final long bytesRead, final long contentLength) {
//            //System.out.printf("%s: %d/%d = %.2f%%%n", url, bytesRead, contentLength, (100f * bytesRead) / contentLength);
//            String key = url.toString();
//            final UIProgressListener listener = LISTENERS.get(key);
//            if (listener == null) {
//                return;
//            }
//            if (contentLength <= bytesRead) {
//                forget(key);
//            }
//            if (needsDispatch(key, bytesRead, contentLength, listener.getGranualityPercentage())) {
//                handler.post(new Runnable() {
//                    @Override public void run() {
//                        listener.onProgress(bytesRead, contentLength);
//                    }
//                });
//            }
//        }
//
//        private boolean needsDispatch(String key, long current, long total, float granularity) {
//            if (granularity == 0 || current == 0 || total == current) {
//                return true;
//            }
//            float percent = 100f * current / total;
//            long currentProgress = (long)(percent / granularity);
//            Long lastProgress = PROGRESSES.get(key);
//            if (lastProgress == null || currentProgress != lastProgress) {
//                PROGRESSES.put(key, currentProgress);
//                return true;
//            } else {
//                return false;
//            }
//        }
//    }
//
//    private static class OkHttpProgressResponseBody extends ResponseBody {
//        private final HttpUrl url;
//        private final ResponseBody responseBody;
//        private final ResponseProgressListener progressListener;
//        private BufferedSource bufferedSource;
//
//        OkHttpProgressResponseBody(HttpUrl url, ResponseBody responseBody,
//                                   ResponseProgressListener progressListener) {
//            this.url = url;
//            this.responseBody = responseBody;
//            this.progressListener = progressListener;
//        }
//
//        @Override public MediaType contentType() {
//            return responseBody.contentType();
//        }
//
//        @Override public long contentLength() {
//            return responseBody.contentLength();
//        }
//
//        @Override public BufferedSource source() {
//            if (bufferedSource == null) {
//                bufferedSource = Okio.buffer(source(responseBody.source()));
//            }
//            return bufferedSource;
//        }
//
//        private Source source(Source source) {
//            return new ForwardingSource(source) {
//                long totalBytesRead = 0L;
//                @Override public long read(Buffer sink, long byteCount) throws IOException {
//                    long bytesRead = super.read(sink, byteCount);
//                    long fullLength = responseBody.contentLength();
//                    if (bytesRead == -1) { // this source is exhausted
//                        totalBytesRead = fullLength;
//                    } else {
//                        totalBytesRead += bytesRead;
//                    }
//                    progressListener.update(url, totalBytesRead, fullLength);
//                    return bytesRead;
//                }
//            };
//        }
//    }
//}
