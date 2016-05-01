package com.bosong.ball_light.presenter.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.bosong.ball_light.model.adapter.ColorModeAdapter;
import com.bosong.ball_light.model.bean.ColorMemberBean;
import com.bosong.ball_light.view.AddModeDelegate;
import com.bosong.ball_light.view.impl.ColorPickerView;
import com.bosong.framework.presenter.ActivityPresenter;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mike on 2/24/16.
 */
public class AddModeActivity extends ActivityPresenter<AddModeDelegate> implements View.OnClickListener{

    private TextView titleLeft;
    private TextView titleRight;

    private ColorModeAdapter mColorModeAdapter;
    private GridView mGridView;
    private ColorPickerView mColorPickerView;
    public static int colorInt = 0;

    private List<ColorMemberBean> mList = new LinkedList<ColorMemberBean>();


    @Override
    protected Class<AddModeDelegate> getDelegateClass(){
        return AddModeDelegate.class;
    }


    public static boolean flagOfColorChange = false;
    private final static int COLOR_CHANGE = 1;
    Handler mColorhandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch(msg.what)
            {
                case COLOR_CHANGE:
                    colorInt = ColorPickerView.ColorText;
                    break;

                default:
                    break;
            }
        };
    };

    private void initList() {
        mList.add(new ColorMemberBean(android.graphics.Color.rgb(0xFF, 0x63, 0x47), true));
        mList.add(new ColorMemberBean(android.graphics.Color.rgb(0x7C, 0xCD, 0x7C), false));
        mList.add(new ColorMemberBean(android.graphics.Color.rgb(0x7B, 0x68, 0xEE), false));
        mList.add(new ColorMemberBean(android.graphics.Color.rgb(0x8B, 0x00, 0x8B), false));
        mList.add(new ColorMemberBean(android.graphics.Color.rgb(0xEE, 0xEE, 0x00), false));
        //mList.add(new ContextMemberBean(R.drawable.smiley_add_btn_pressed, false));
        //mList.add(new ContextMemberBean(R.drawable.smiley_minus_btn_pressed, false));
    }

    @Override
    protected void bindEventListener() {
        super.bindEventListener();

        initList();

        mColorModeAdapter = new ColorModeAdapter(viewDelegate.getActivity(), mList);
        mGridView = viewDelegate.getGridView();
        mGridView.setAdapter(mColorModeAdapter);

        //colorCircle = viewDelegate.getColorCircle();
        //colorCircle.setBackgroundColor();

        mColorPickerView = viewDelegate.getColorPickerView();

        //用线程监听 是否颜色已经改变
        new Thread(new Runnable() {

            @Override
            public void run() {
                // TODO Auto-generated method stub
                while (true) {
                    if (flagOfColorChange) {
                        flagOfColorChange = false;
                        mColorhandler.sendEmptyMessage(COLOR_CHANGE);
                    }
                }
            }
        }).start();

    }

    @Override
    public void onClick(View v){

    }
}


