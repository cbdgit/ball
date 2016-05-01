package com.bosong.ball_light.view;

import com.bosong.ball_light.presenter.activity.FadeTabIndicator;
import com.bosong.ball_light.R;

import com.bosong.ball_light.presenter.service.ScanMusic;
import com.bosong.ball_light.util.Config;
import com.bosong.ball_light.util.PreferenceHelper;
import com.bosong.framework.view.AppDelegate;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

/**
 * Created by mike on 1/13/16.
 */
public class MainDelegate extends AppDelegate {

    private FadeTabIndicator fadeTabIndicator;
    private ViewPager viewPager;

    private TextView title_left;
    private TextView title_right;

    @Override
    public int getRootLayoutId(){
        return R.layout.delegate_main;
    }

    @Override
    public Toolbar getToolbar(){
        //Toolbar toolbar = get(R.id.toolbar);
        //return toolbar;
        return null;
    }

    @Override
    public void initWidget(){
        title_left = get(R.id.title_left);
        title_right = get(R.id.title_right);

        fadeTabIndicator = (FadeTabIndicator) get(R.id.fade_tab_indicator);
        viewPager = (ViewPager) get(R.id.view_pager);
    }



    public ViewPager getViewPager(){
        return viewPager;
    }

    public FadeTabIndicator getIndicator(){
        return fadeTabIndicator;
    }

    public void setTitleLeft(String title_left) {
        this.title_left.setText(title_left);
    }

    public void setTitleRight(String title_right) {
        this.title_right.setText(title_right);
    }
}
