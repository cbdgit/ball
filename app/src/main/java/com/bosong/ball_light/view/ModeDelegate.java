package com.bosong.ball_light.view;

import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bosong.ball_light.R;
import com.bosong.framework.view.AppDelegate;
import com.bosong.ball_light.view.impl.WheelView;

/**
 * Created by mike on 1/23/16.
 */
public class ModeDelegate extends AppDelegate {

    private ImageView color_circle;

    private GridView memberListGV;
    private WheelView mWheelView;

    private TextView titleLeft;
    private TextView titleRight;

    private Button addMode;

    @Override
    public int getRootLayoutId(){
        return R.layout.delegate_activity_mode;
    }

    @Override
    public void initWidget(){
        super.initWidget();

        titleLeft = get(R.id.title_left);
        titleRight = get(R.id.title_right);

        mWheelView = get(R.id.wheelview);

        addMode = get(R.id.add_mode);
    }

    public void setTitleLeft(int title_left) {
        this.titleLeft.setText(title_left);
    }

    public void setTitleRight(int title_right) {
        this.titleRight.setText(title_right);
    }

    public WheelView getWheelView(){
        return mWheelView;
    }
}
