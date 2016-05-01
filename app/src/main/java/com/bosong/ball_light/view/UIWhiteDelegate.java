package com.bosong.ball_light.view;

import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bosong.ball_light.R;
import com.bosong.framework.view.AppDelegate;

/**
 * Created by mike on 1/22/16.
 */
public class UIWhiteDelegate extends AppDelegate {

    @Override
    public int getRootLayoutId(){
        return R.layout.delegate_ui_white;
    }

    @Override
    public Toolbar getToolbar(){
        //Toolbar toolbar = (Toolbar) get(R.id.toolbar);
        //return toolbar;
        return null;
    }

    @Override
    public void initWidget() {
        super.initWidget();
    }
}

