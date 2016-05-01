package com.bosong.ball_light.view;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bosong.ball_light.R;
import com.bosong.framework.view.AppDelegate;

/**
 * Created by mike on 1/12/16.
 */

public class MusicDelegate extends AppDelegate {

    private TextView title_left;
    private TextView title_right;
    private Button yuekuTv;

    @Override
    public int getRootLayoutId(){
        return R.layout.delegate_music;
    }

    @Override
    public Toolbar getToolbar(){
        //Toolbar toolbar = (Toolbar) get(R.id.toolbar);
        //return toolbar;
        return null;
    }

    @Override
    public void initWidget(){
        super.initWidget();
        yuekuTv = get(R.id.yuekuTv);
        title_left = get(R.id.title_left);
        title_right = get(R.id.title_right);
    }

    public void setTitleLeft(String title_left) {
        this.title_left.setText(title_left);
    }

    public void setTitleRight(String title_right) {
        this.title_right.setText(title_right);
    }
    public Button getYueKuButton(){
        return yuekuTv;
    }
}
