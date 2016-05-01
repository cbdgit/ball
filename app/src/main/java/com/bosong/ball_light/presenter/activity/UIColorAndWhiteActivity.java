package com.bosong.ball_light.presenter.activity;

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.adapter.ColorAndWhiteFragmentPagerAdapter;
import com.bosong.ball_light.view.UIColorAndWhiteDelegate;
import com.bosong.ball_light.presenter.activity.ModeActivity;
import com.bosong.framework.presenter.ActivityPresenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class UIColorAndWhiteActivity
        extends ActivityPresenter<UIColorAndWhiteDelegate>
        implements View.OnClickListener{

    private ColorAndWhiteFragmentPagerAdapter mColorAndWhiteFragmentPagerAdapter;
    private LineTabIndicator lineTabIndicator;
    private ViewPager viewPager;
    private Intent intent;
    private Bundle bundle;
    private TextView titleLeft;
    private TextView titleRight;
    private TextView titleCenter;

    @Override
    protected Class<UIColorAndWhiteDelegate> getDelegateClass(){
        return UIColorAndWhiteDelegate.class;
    }

    @Override
    protected void bindEventListener() {
        super.bindEventListener();

        mColorAndWhiteFragmentPagerAdapter = new ColorAndWhiteFragmentPagerAdapter(this, getSupportFragmentManager());

        viewDelegate.setOnClickListener(this, R.id.title_left);
        viewDelegate.setOnClickListener(this, R.id.title_right);

        lineTabIndicator = (LineTabIndicator) findViewById(R.id.line_tab_indicator);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(mColorAndWhiteFragmentPagerAdapter);
        lineTabIndicator.setViewPager(viewPager);

        viewDelegate.setTitleLeft(R.string.retn);
        viewDelegate.setTitleRight(R.string.mode);

        intent = getIntent();
        bundle = intent.getExtras();
        String name = bundle.getString("name");
        String addr = bundle.getString("addr");
        viewDelegate.setTitleCenter(name);

        Fragment mFragment1 = mColorAndWhiteFragmentPagerAdapter.getItem(0);
        Fragment mFragment2 = mColorAndWhiteFragmentPagerAdapter.getItem(1);
        Bundle mBundle = new Bundle();
        mBundle.putString("addr", addr);
        mFragment1.setArguments(mBundle);
        mFragment2.setArguments(mBundle);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                Intent intent = new Intent(UIColorAndWhiteActivity.this, ModeActivity.class);
                startActivity(intent);
                break;
        }
    }
}
