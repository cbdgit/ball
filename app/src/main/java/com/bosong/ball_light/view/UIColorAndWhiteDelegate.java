package com.bosong.ball_light.view;

import android.widget.TextView;

import com.bosong.ball_light.R;
import com.bosong.framework.view.AppDelegate;

/**
 * Created by mike on 1/22/16.
 */
public class UIColorAndWhiteDelegate extends AppDelegate {

    private TextView titleLeft;
    private TextView titleRight;
    private TextView titleCenter;

    @Override
    public int getRootLayoutId(){
        return R.layout.delegate_activity_color_and_white;
    }

    @Override
    public void initWidget(){
        super.initWidget();

        titleLeft = get(R.id.title_left);
        titleRight = get(R.id.title_right);
        titleCenter = get(R.id.title_center);
    }

    public void setTitleLeft(int title_left) {
        this.titleLeft.setText(title_left);
    }

    public void setTitleRight(int title_right) {
        this.titleRight.setText(title_right);
    }

    public void setTitleCenter(String title_center) {
        this.titleCenter.setText(title_center);
        this.titleCenter.setTextSize(18);
    }

}
