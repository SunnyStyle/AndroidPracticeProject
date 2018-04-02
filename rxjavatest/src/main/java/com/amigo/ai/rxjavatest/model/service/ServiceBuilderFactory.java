package com.amigo.ai.rxjavatest.model.service;

/**
 * Created by wf on 18-4-2.
 */

public class ServiceBuilderFactory {
    private static ServiceBuilderFactory instance;
    private Object dabanHttps;

    public static ServiceBuilderFactory getInstance(){
        if (instance == null){
            instance = new ServiceBuilderFactory();
        }
        return instance;
    }

    public <T> T create(Class<T> a,String type){
        if(dabanHttps == null){
            synchronized (ServiceBuilderFactory.class){
                if(dabanHttps == null){
                    //dabanHttps =
                }
            }
        }
        return (T)dabanHttps;
    }
}
