package com.amigo.ai.rxjavatest.model.domian;

import com.amigo.ai.rxjavatest.model.service.ServiceBuilderFactory;
import com.amigo.ai.rxjavatest.utils.HttpUtils;

/**
 * Created by wf on 18-4-2.
 */

public interface WorkerHelper {
    /*public static WorkerHelper helper;

    private WorkerHelper() {
    }

    public static WorkerHelper getInstance(){
        if(helper == null){
            helper = new WorkerHelper();
        }
        return helper;
    }*/

    class Builder{
        public static WorkerHelper getHotMovieService(){
            return ServiceBuilderFactory.getInstance().create(WorkerHelper.class, HttpUtils.TYPE_HOTMOVIE);
        }
    }

}
