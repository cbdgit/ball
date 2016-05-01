package com.bosong.ball_light.view;

import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bosong.ball_light.R;
import com.bosong.framework.view.AppDelegate;

import java.util.List;

/**
 * Created by mike on 1/13/16.
 */
public class TimerDelegate extends AppDelegate {

    private TextView title_left;
    private TextView title_right;
    private ImageView addIv;
    private ListView timerlistview;
    private PopupWindow mPopWindow;
    TextView pop_deviceTv;
    TextView pop_repeatTv;
    TextView pop_contextTv;

    @Override
    public int getRootLayoutId() {
        return R.layout.delegate_timer;
    }

    @Override
    public Toolbar getToolbar() {
        //Toolbar toolbar = (Toolbar) get(R.id.toolbar);
        //return toolbar;
        return null;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        title_left = get(R.id.title_left);
        title_right = get(R.id.title_right);
        title_right.setVisibility(View.INVISIBLE);
        addIv = get(R.id.addIv);
        addIv.setVisibility(View.VISIBLE);
        timerlistview = (ListView) get(R.id.timerLv);
        showPopupWindow();

    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.popuplayout, null);
        mPopWindow = new PopupWindow(contentView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop_deviceTv = (TextView) contentView.findViewById(R.id.pop_device);
        pop_repeatTv = (TextView) contentView.findViewById(R.id.pop_repeat);
        pop_contextTv = (TextView) contentView.findViewById(R.id.pop_context);
        //  mPopWindow.showAsDropDown(mMenuTv);

    }

    public ListView getListView() {
        return timerlistview;
    }

    public void setTitleLeft(String title_left) {
        this.title_left.setText(title_left);
    }

    public void setTitleRight(int resId) {
        this.addIv.setImageResource(resId);
    }

    public TextView getTextViewDevice() {
        return pop_deviceTv;
    }

    public TextView getTextViewRepeat() {
        return pop_repeatTv;
    }

    public TextView getTextViewContext() {
        return pop_contextTv;
    }

    public PopupWindow getPopupWindow() {
        return mPopWindow;
    }

    public void showPopWindow() {
        mPopWindow.showAsDropDown(addIv);
    }

    public void disPopWindow() {
        mPopWindow.dismiss();
    }

}
