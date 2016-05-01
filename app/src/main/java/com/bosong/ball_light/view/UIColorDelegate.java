package com.bosong.ball_light.view;

import android.support.v7.widget.Toolbar;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bosong.ball_light.R;
import com.bosong.ball_light.view.impl.ColorPickerView;
import com.bosong.framework.view.AppDelegate;

import android.widget.ImageView;

/**
 * Created by mike on 1/22/16.
 */
public class UIColorDelegate extends AppDelegate {

    //private ImageView colorCircle;
    private GridView memberListGV;
    private ColorPickerView mColorPickerView;

    @Override
    public int getRootLayoutId(){
        return R.layout.delegate_ui_color;
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

        //colorCircle = get(R.id.color_circle);
        memberListGV = (GridView) get(R.id.gridview_color);;

        mColorPickerView = get(R.id.color_circle);
    }

    public GridView getGridView(){
        return memberListGV;
    }

    public ColorPickerView getColorPickerView(){
        return mColorPickerView;
    }
}

