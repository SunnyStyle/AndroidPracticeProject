package com.amigo.ai.rxjavatest.model.domian;

import com.amigo.ai.rxjavatest.hotmovie.bean.HotMovieBean;
import com.amigo.ai.rxjavatest.model.service.ServiceBuilderFactory;
import com.amigo.ai.rxjavatest.utils.HttpUtils;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by wf on 18-4-2.
 */

public interface HttpsClientApi {
    /*public static HttpsClientApi helper;

    private HttpsClientApi() {
    }

    public static HttpsClientApi getInstance(){
        if(helper == null){
            helper = new HttpsClientApi();
        }
        return helper;
    }*/

    class Builder{
        public static HttpsClientApi getHotMovieService(){
            return ServiceBuilderFactory.getInstance().create(HttpsClientApi.class
                    , HttpUtils.TYPE_HOTMOVIE);
        }
    }

    @GET("v2/movie/in_theaters")
    Observable<HotMovieBean> getHotMovie();

}
