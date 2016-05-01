package com.bosong.ball_light.view;

import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.bosong.ball_light.R;
import com.bosong.ball_light.view.impl.ColorPickerView;
import com.bosong.framework.view.AppDelegate;

/**
 * Created by mike on 2/24/16.
 */
public class AddModeDelegate extends AppDelegate {
    //private ImageView colorCircle;
    private TextView titleLeft;
    private TextView titleRight;

    //private ImageView colorCircle;
    private GridView memberListGV;
    private ColorPickerView mColorPickerView;

    private Button modeJump;
    private Button modeFade;
    private Button modeShade;

    @Override
    public int getRootLayoutId(){
        return R.layout.delegate_activity_addmode;
    }

    @Override
    public void initWidget(){
        super.initWidget();

        titleLeft = get(R.id.title_left);
        titleRight = get(R.id.title_right);

        mColorPickerView = get(R.id.color_circle);
        memberListGV = (GridView) get(R.id.gridview_color);
        modeJump = get(R.id.mode_jump);
        modeFade = get(R.id.mode_fade);
        modeShade = get(R.id.mode_shade);


    }

    public GridView getGridView(){
        return memberListGV;
    }

    public ColorPickerView getColorPickerView(){
        return mColorPickerView;
    }
}


