package com.amigo.ai.rxjavatest.model.domian;

import android.arch.lifecycle.MutableLiveData;

import com.amigo.ai.rxjavatest.hotmovie.bean.HotMovieBean;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by wf on 18-4-2.
 */

public class HotMovieRepository extends WorkRepository{

    protected HotMovieRepository() {

    }


    @Override
    public void commitData(String key, Object data, MainListCallback userMainCallback) {

    }

    @Override
    public MutableLiveData<HotMovieBean> getMainList() {
        final MutableLiveData<HotMovieBean> mutableLiveData = new MutableLiveData<>();
        HttpsClientApi.Builder
                .getHotMovieService()
                .getHotMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotMovieBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mutableLiveData.setValue(null);
                    }

                    @Override
                    public void onNext(HotMovieBean hotMovieBean) {
                        mutableLiveData.setValue(hotMovieBean);
                    }
                });
        return mutableLiveData;
    }

    @Override
    public MutableLiveData<HotMovieBean> getMainList(String key, MainListCallback mainListCallback) {
        MutableLiveData<HotMovieBean> hotMovieBeanLD = new MutableLiveData<>();
        return hotMovieBeanLD;
    }

    @Override
    public void getItemById(int userId, DetailsCallback detailsCallback) {

    }
}
