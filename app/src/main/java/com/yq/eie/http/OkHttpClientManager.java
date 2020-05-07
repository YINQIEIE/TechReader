package com.yq.eie.http;


import android.content.Context;
import android.util.Log;

import com.yq.eie.BuildConfig;
import com.yq.eie.utils.CheckNetwork;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * OkHttpClient管理类，用来获取 OkHttpClient 实例
 */
public class OkHttpClientManager {

    private static OkHttpClient client = null;
    private static OkHttpClient.Builder builder = null;
    private static OkHttpClient.Builder httpsBuilder = null;

    private OkHttpClientManager() {
    }

    public synchronized static OkHttpClient getClient() {
        if (client == null) {
            if (builder == null) {
                builder = new OkHttpClient.Builder();
            }
            try {
                SSLSocketFactory factory = new SSLSocketFactoryCompat();
                builder.sslSocketFactory(factory);
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            //okhttp日志打印
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new OkHttpLogger());
            if (BuildConfig.DEBUG)
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            else
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            builder.addNetworkInterceptor(logInterceptor);
            client = builder.connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
        }
        return client;
    }

    public synchronized static OkHttpClient getHttpsClient(Context context) {
        try {
            if (httpsBuilder == null) {
                httpsBuilder = new OkHttpClient.Builder();
            }
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[]{};
                }
            }};
            // Install the all-trusting trust manager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            //okhttp日志打印
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new OkHttpLogger());
            if (BuildConfig.DEBUG)
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            else
                logInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            httpsBuilder.addNetworkInterceptor(logInterceptor);
            httpsBuilder.addInterceptor(new HttpHeadInterceptor(context));
            httpsBuilder.sslSocketFactory(sslSocketFactory);
            httpsBuilder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            httpsBuilder.connectTimeout(20, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS);
            return httpsBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 日志打印类
     */
    private static class OkHttpLogger implements HttpLoggingInterceptor.Logger {
        @Override
        public void log(String message) {
            Log.i("requestBody", message);
        }
    }

    static class HttpHeadInterceptor implements Interceptor {
        private Context mContext;

        public HttpHeadInterceptor(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request.Builder builder = request.newBuilder();
            builder.addHeader("Accept", "application/json;versions=1");
            if (CheckNetwork.isNetworkConnected(mContext.getApplicationContext())) {
                int maxAge = 60;
                builder.addHeader("Cache-Control", "public, max-age=" + maxAge);
            } else {
                int maxStale = 60 * 60 * 24 * 28;
                builder.addHeader("Cache-Control", "public, only-if-cached, max-stale=" + maxStale);
            }
            // 可添加token
//            if (listener != null) {
//                builder.addHeader("token", listener.getToken());
//            }
            // 如有需要，添加请求头
//            builder.addHeader("a", HttpHead.getHeader(request.method()));
            return chain.proceed(builder.build());
        }
    }


}