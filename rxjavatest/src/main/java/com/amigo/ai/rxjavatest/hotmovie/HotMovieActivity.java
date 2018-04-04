package com.amigo.ai.rxjavatest.hotmovie;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.amigo.ai.rxjavatest.R;
import com.amigo.ai.rxjavatest.hotmovie.bean.HotMovieBean;

/**
 * Created by wf on 18-3-31.
 */

public class HotMovieActivity extends AppCompatActivity {

    Button getHotMovie;

    HotMovieViewModel hotMovieViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.hotmovie_activity);

        hotMovieViewModel = ViewModelProviders.of(this).get(HotMovieViewModel.class);

        getHotMovie = (Button)findViewById(R.id.get_hotmovie_bt);
        getHotMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHotMovieData();
            }
        });

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
    }

    public void getHotMovieData() {
        hotMovieViewModel.getHotMovie().observe(this,hotMovieBean -> {
            if(hotMovieBean != null && hotMovieBean.getSubjects() != null){

            }
        });
    }
}
