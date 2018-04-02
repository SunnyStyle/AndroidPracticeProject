package com.amigo.ai.rxjavatest.model.domian;

import com.amigo.ai.rxjavatest.hotmovie.HotMovieViewModel;

/**
 * Created by wf on 18-4-2.
 */

public class RepositoryFactory {
    private static HotMovieRepository hotMovieRepository;

    public static WorkRepository createHotMovieRepository(){
        if(hotMovieRepository == null){
            hotMovieRepository = new HotMovieRepository();
        }
        return hotMovieRepository;
    }
}
