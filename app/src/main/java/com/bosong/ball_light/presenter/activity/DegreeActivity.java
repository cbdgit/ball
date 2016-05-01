package com.bosong.ball_light.presenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bosong.ball_light.R;
import com.bosong.ball_light.view.DegreeDelegate;
import com.bosong.framework.presenter.ActivityPresenter;

/**
 * Created by mike on 1/25/16.
 */
public class    DegreeActivity extends ActivityPresenter<DegreeDelegate> implements View.OnClickListener{

    private Intent intent;
    private Bundle bundle;

    private TextView titleLeft;
    private TextView titleRight;

    @Override
    protected Class<DegreeDelegate> getDelegateClass(){
        return DegreeDelegate.class;
    }

    @Override
    protected void bindEventListener() {
        super.bindEventListener();
        viewDelegate.setOnClickListener(this, R.id.title_left);
        viewDelegate.setTitleLeft(R.string.retn);
        intent = getIntent();
        bundle = intent.getExtras();
        String name = bundle.getString("name");
        viewDelegate.setTitleCenter(name);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.title_left:
                finish();
                break;
        }
    }
}
