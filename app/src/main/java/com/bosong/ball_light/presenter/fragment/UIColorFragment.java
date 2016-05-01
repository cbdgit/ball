package com.bosong.ball_light.presenter.fragment;

import android.bluetooth.BluetoothGattCharacteristic;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.GridView;

import com.bosong.ball_light.model.adapter.ColorPickAdapter;
import com.bosong.ball_light.model.bean.ColorMemberBean;
import com.bosong.ball_light.model.bluetooth.BleWrapper;
import com.bosong.ball_light.view.UIColorDelegate;
import com.bosong.ball_light.view.impl.ColorPickerView;
import com.bosong.framework.presenter.FragmentPresenter;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * Created by mike on 1/22/16.
 */
public class UIColorFragment extends FragmentPresenter<UIColorDelegate> implements View.OnClickListener{

    private ColorPickAdapter mColorMemberAdapter;
    private GridView mGridView;
    private ColorPickerView mColorPickerView;
    private BleWrapper mBleWrapper;
    private Bundle mBundle;
    private String addr;
    private BluetoothGattCharacteristic mCharacteristic;
    public static int colorInt = 0;



    private List<ColorMemberBean> mList = new LinkedList<ColorMemberBean>();

    public static UIColorFragment newInstance() {
        UIColorFragment mUIColorFragment = new UIColorFragment();
        return mUIColorFragment;
    }

    @Override
    protected Class<UIColorDelegate> getDelegateClass(){
        return UIColorDelegate.class;
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
                    addr = mBundle.getString("addr");
                    String newValue =  String.valueOf(colorInt).toLowerCase(Locale.getDefault());
                    byte[] dataToWrite = parseHexStringToBytes(newValue);
                    mBleWrapper.writeDataToCharacteristic(mCharacteristic, dataToWrite);
                    Log.d("colorInt", colorInt + "");
                    //viewDelegate.toast(addr);
                    break;

                default:
                    break;
            }
        };
    };


    public byte[] parseHexStringToBytes(final String hex) {
        String tmp = hex.substring(2).replaceAll("[^[0-9][a-f]]", "");
        byte[] bytes = new byte[tmp.length() / 2]; // every two letters in the string are one byte finally
        String part = "";
        for(int i = 0; i < bytes.length; ++i) {
            part = "0x" + tmp.substring(i*2, i*2+2);
            bytes[i] = Long.decode(part).byteValue();
        }
        return bytes;
    }

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

        mBundle = getArguments();

        mColorMemberAdapter = new ColorPickAdapter(viewDelegate.getActivity(), mList);
        mGridView = viewDelegate.getGridView();
        mGridView.setAdapter(mColorMemberAdapter);

        //colorCircle = viewDelegate.getColorCircle();
        //colorCircle.setBackgroundColor();
        if(mBleWrapper == null)
        {mBleWrapper = new BleWrapper(viewDelegate.getActivity(), null);
        }

        if(mBleWrapper.initialize() == false) {
            viewDelegate.getActivity().finish();
        }
        mColorPickerView = viewDelegate.getColorPickerView();
        mColorPickerView.setOnColorChangedListenner(new ColorPickerView.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {

            }
        });
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
