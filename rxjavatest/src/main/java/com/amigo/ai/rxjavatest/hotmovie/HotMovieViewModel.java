package com.amigo.ai.rxjavatest.hotmovie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.amigo.ai.rxjavatest.hotmovie.bean.HotMovieBean;
import com.amigo.ai.rxjavatest.model.domian.HotMovieRepository;
import com.amigo.ai.rxjavatest.model.domian.Repository;
import com.amigo.ai.rxjavatest.model.domian.RepositoryFactory;
import com.amigo.ai.rxjavatest.model.domian.WorkRepository;
import com.amigo.ai.rxjavatest.model.exception.ErrorData;

import java.util.Collection;

/**
 * Created by wf on 18-3-31.
 */

public class HotMovieViewModel extends ViewModel{

    private WorkRepository hotMovieRepository;
    private MutableLiveData<HotMovieBean> hotMovieBeanLiveData;

    /**
     * UserRepository parameter is provided by Dagger 2
     * public 必须，不然报错
     */
    public HotMovieViewModel() {
        hotMovieRepository = RepositoryFactory.createHotMovieRepository();
    }

    private void setHotMovieBean(MutableLiveData<HotMovieBean> hotMovieBean) {
        this.hotMovieBeanLiveData = hotMovieBean;
    }

    public LiveData<HotMovieBean> getHotMovie(){
        if(hotMovieBeanLiveData == null
                ||hotMovieBeanLiveData.getValue() == null
                || hotMovieBeanLiveData.getValue().getSubjects() == null
                || hotMovieBeanLiveData.getValue().getSubjects().size() == 0){
            hotMovieBeanLiveData = new MutableLiveData<>();
            MutableLiveData<HotMovieBean> mainList = hotMovieRepository.getMainList();
            setHotMovieBean(mainList);
            return mainList;
        }else{
            return hotMovieBeanLiveData;
        }
    }

}
