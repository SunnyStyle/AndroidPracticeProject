package com.amigo.ai.rxjavatest.model.domian;

import android.arch.lifecycle.MutableLiveData;

import com.amigo.ai.rxjavatest.model.exception.ErrorData;

import java.util.Collection;

/**
 * Created by wf on 18-4-2.
 */

public interface Repository<T> {

    public interface MainListCallback<T>{

        void onListLoaded(Collection<T> datas);

        void onError(ErrorData errorData);
    }

    public interface DetailsCallback<T>{

        void OnLoad(T detailResults);

        void onError(ErrorData errorData);

    }

    MutableLiveData<T> getMainList();

    MutableLiveData<T> getMainList(String key, MainListCallback mainListCallback);

    void getItemById(final int userId,DetailsCallback detailsCallback);

    void commitData(String key,Object data,MainListCallback userMainCallback);

    void commitData(String key,Object data,DetailsCallback userDetailsCallback);
}
