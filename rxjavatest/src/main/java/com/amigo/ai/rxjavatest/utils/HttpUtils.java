package com.amigo.ai.rxjavatest.utils;

import android.content.Context;
import android.databinding.adapters.Converters;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amigo.ai.http.ParamNames;
import com.amigo.ai.rxjavatest.plugin.MyApplication;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wf on 18-4-2.
 */

public class HttpUtils {
    public final static String TYPE_HOTMOVIE = "hot_movie";
    private static volatile HttpUtils instance;
    private Object hotMoviehttps;

    public final static String API_DOUBAN = "Https://api.douban.com/";
    private Gson gson;
    private Context mContext;


    private HttpUtils() {
    }

    public static HttpUtils getInstance(){
        if(instance == null){
            synchronized (HttpUtils.class){
                if(instance == null){
                    instance = new HttpUtils();
                }
            }
        }
        return instance;
    }

    public void init(Context context, boolean debug) {
        this.mContext = context;
        //HttpHead.init(context);
    }

    public Retrofit.Builder getRetrofitBuilder(String url) {
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        retrofitBuilder.client(getOkClient());
        retrofitBuilder.baseUrl(url);
        retrofitBuilder.addConverterFactory(new NullOnEmptyConverterFactory());
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create(getGson()));
        retrofitBuilder.addCallAdapterFactory(RxJavaCallAdapterFactory.create());
        return retrofitBuilder;
    }

    public OkHttpClient getOkClient() {
        return getUnsafeOkHttpClient();
    }

    public OkHttpClient getUnsafeOkHttpClient() {
        TrustManager[] trustManagers = new TrustManager[]{new X509TrustManager() {
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
        }
        };

        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null,trustManagers,new SecureRandom());
            File httpCacheDirectory = new File(mContext.getCacheDir(),"response");
            int cacheSize = 50 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory,cacheSize);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(30, TimeUnit.SECONDS);
        builder.connectTimeout(30,TimeUnit.SECONDS);
        builder.writeTimeout(30,TimeUnit.SECONDS);
        builder.addInterceptor(new HttpHeadInterceptor());
        builder.addInterceptor(new AddCacheInterceptor(mContext));
        return builder.build();
    }

    public Gson getGson() {
        if(gson == null){
            GsonBuilder builder = new GsonBuilder();
            builder.setLenient();
            builder.setFieldNamingStrategy(new AnnotateNaming());
            builder.serializeNulls();
            gson = builder.create();
        }
        return gson;
    }

    private class AnnotateNaming implements FieldNamingStrategy {
        @Override
        public String translateName(Field field) {
            ParamNames a = field.getAnnotation(ParamNames.class);
            return a != null ? a.value() : FieldNamingPolicy.IDENTITY.translateName(field);
        }
    }

    private class NullOnEmptyConverterFactory extends Converter.Factory{
        @Nullable
        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody,?> delegate = retrofit.nextResponseBodyConverter(this,type,annotations);
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody value) throws IOException {
                    if(value.contentLength() == 0)
                        return null;
                    return delegate.convert(value);
                }
            };
        }
    }

    private class HttpHeadInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();//获取请求
            Request.Builder builder = request.newBuilder();
            builder.addHeader("Accept", "application/json;versions=1");
            //这里就是说判读我们的网络条件，要是有网络的话我么就直接获取网络上面的数据，要是没有网络的话我么就去缓存里面取数据
            if(!NetworkUtils.isNetworkConnected(MyApplication.getContext())){
                        //这个的话内容有点多啊，大家记住这么写就是只从缓存取，想要了解这个东西我等下在
                        // 给大家写连接吧。大家可以去看下，获取大家去找拦截器资料的时候就可以看到这个方面的东西反正也就是缓存策略。
                builder.cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("CacheInterceptor","no network");
            }
            Response originalResponse = chain.proceed(request);
            if(NetworkUtils.isNetworkConnected(MyApplication.getContext())){
                //这里大家看点开源码看看.header .removeHeader做了什么操作很简答，就是的加字段和减字段的。
                String cacheControl = request.cacheControl().toString();

                return originalResponse.newBuilder()
                        //这里设置的为0就是说不进行缓存，我们也可以设置缓存时间
                        .header("Cache-Control", "public, max-age=" + 0)
                        .removeHeader("Pragma")
                        .build();
            }else{
                int maxTime = 4*24*60*60;
                return originalResponse.newBuilder()
                        //这里的设置的是我们的没有网络的缓存时间，想设置多少就是多少。
                        .header("Cache-Control", "public, only-if-cached, max-stale="+maxTime)
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }

    private class AddCacheInterceptor implements Interceptor {
        private Context mContext;
        public AddCacheInterceptor(Context context) {
            super();
            this.mContext = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            CacheControl.Builder builder = new CacheControl.Builder();
            builder.maxAge(0,TimeUnit.SECONDS);
            builder.maxStale(365,TimeUnit.DAYS);
            CacheControl cacheControl = builder.build();
            Request request = chain.request();
            if (!NetworkUtils.isNetworkConnected(MyApplication.getContext())) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (NetworkUtils.isNetworkConnected(MyApplication.getContext())) {
                // read from cache
                int maxAge = 0;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                // tolerate 4-weeks stale
                int maxStale = 60 * 60 * 24 * 28;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }
}
