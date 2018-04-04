package com.amigo.ai.rxjavatest.model.service;

import com.amigo.ai.rxjavatest.utils.HttpUtils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by wf on 18-4-2.
 */

public class ServiceBuilderFactory {

    public final static String API_GANKIO = "https://gank.io/api/";
    public final static String API_DOUBAN = "Https://api.douban.com/";
    public final static String API_TING = "https://tingapi.ting.baidu.com/v1/restserver/";
    public final static String API_FIR = "http://api.fir.im/apps/";
    public final static String API_WAN_ANDROID = "http://www.wanandroid.com/";
    public final static String API_NHDZ = "https://ic.snssdk.com/";
    public final static String API_QSBK = "http://m2.qiushibaike.com/";

    private static ServiceBuilderFactory instance;
    private Object dabanHttps;
    private OkHttpClient okClient;

    public static ServiceBuilderFactory getInstance(){
        if (instance == null){
            instance = new ServiceBuilderFactory();
        }
        return instance;
    }

    public <T> T create(Class<T> a, String type) {
        switch (type){
            case API_DOUBAN:
                if (dabanHttps == null) {
                    synchronized (ServiceBuilderFactory.class) {
                        if (dabanHttps == null) {
                            dabanHttps = HttpUtils.getInstance().getRetrofitBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) dabanHttps;
            default:
                if (dabanHttps == null) {
                    synchronized (ServiceBuilderFactory.class) {
                        if (dabanHttps == null) {
                            dabanHttps = HttpUtils.getInstance().getRetrofitBuilder(type).build().create(a);
                        }
                    }
                }
                return (T) dabanHttps;
        }


    }

}
