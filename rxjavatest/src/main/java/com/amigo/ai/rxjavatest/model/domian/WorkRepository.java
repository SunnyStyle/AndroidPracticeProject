package com.amigo.ai.rxjavatest.model.domian;

/**
 * Created by wf on 18-4-2.
 */

public abstract class WorkRepository<T> implements Repository<T>{

    protected WorkRepository(){

    }

    public void getList(String key,MainListCallback callback){

    }

    public void getList(MainListCallback callback){

    }

    @Override
    public void commitData(String key, Object data, MainListCallback userMainCallback) {

    }

    @Override
    public void commitData(String key, Object data, DetailsCallback userDetailsCallback) {

    }

}
