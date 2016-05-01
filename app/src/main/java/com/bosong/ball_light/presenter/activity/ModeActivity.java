package com.bosong.ball_light.presenter.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.bosong.ball_light.R;
import com.bosong.ball_light.presenter.activity.AddModeActivity;
import com.bosong.ball_light.view.ModeDelegate;
import com.bosong.ball_light.view.impl.WheelView;
import com.bosong.framework.presenter.ActivityPresenter;

import java.util.Arrays;

/**
 * Created by mike on 1/23/16.
 */
public class ModeActivity extends ActivityPresenter<ModeDelegate> implements View.OnClickListener{

    private static final String[] MODES =
            new String[]{"静态红色", "静态绿色", "静态蓝色", "三色渐变", "七色渐变", "三色频闪", "七色频闪", "三色跳变","七色跳变"};

    private TextView titleLeft;
    private TextView titleRight;
    private WheelView mWheelView;

    @Override
    protected Class<ModeDelegate> getDelegateClass(){
        return ModeDelegate.class;
    }

    @Override
    protected void bindEventListener() {
        super.bindEventListener();

        viewDelegate.setOnClickListener(this, R.id.title_left);
        viewDelegate.setOnClickListener(this, R.id.title_right);
        viewDelegate.setTitleLeft(R.string.retn);
        viewDelegate.setTitleRight(R.string.color_circle);

        mWheelView = viewDelegate.getWheelView();
        mWheelView.setOffset(1);
        mWheelView.setItems(Arrays.asList(MODES));
        mWheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                viewDelegate.toast("selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });

        viewDelegate.setOnClickListener(this, R.id.add_mode);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.title_right:
                //Intent intent = new Intent(UIColorAndWhiteActivity.this, ModeActivity.class);
                //startActivity(intent);
                finish();
                break;
            case R.id.add_mode:
                Intent intent = new Intent(ModeActivity.this, AddModeActivity.class);
                startActivity(intent);
                break;
        }
    }
}

