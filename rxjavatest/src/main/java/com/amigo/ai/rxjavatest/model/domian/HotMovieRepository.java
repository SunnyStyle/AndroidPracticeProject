package com.amigo.ai.rxjavatest.model.domian;

import android.arch.lifecycle.MutableLiveData;

import com.amigo.ai.rxjavatest.hotmovie.bean.HotMovieBean;

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
        MutableLiveData<HotMovieBean> mutableLiveData = new MutableLiveData<>();
        //WorkerHelper.Builder.getHotMovieService().
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
