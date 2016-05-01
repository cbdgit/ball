package com.bosong.ball_light.view;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bosong.ball_light.R;

import com.bosong.ball_light.widget.WheelView;
import com.bosong.framework.view.AppDelegate;

/**
 * Created by mike on 1/13/16.
 */
public class AddTimerDelegate extends AppDelegate {

    private TextView title_left;
    private TextView title_center;
    private TextView title_right;
    private ImageView addIv;

    private RadioGroup genderGroup;
    private RadioButton timeronRadioButton;
    private RadioButton timeroffRadioButton;
    private EditText editText1;
    private EditText editText2;
    private EditText nameEt;
    private int secTv;
    private int minTv;
    LinearLayout lll;
    View view;
    View view1;
    LayoutInflater inflater;
    Context context;
    WheelView min;
    WheelView sec;
    TextView repeatContentTv;
    TextView qinjinContentTv;
    TextView groupTv;
    private ListView timerlistview;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_add_timer;
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
        title_center = get(R.id.title_center);
        title_right = get(R.id.title_right);

        lll = (LinearLayout) get(R.id.lll);
        genderGroup = (RadioGroup) get(R.id.genderGroup);
        timeronRadioButton = (RadioButton) get(R.id.timeronButton);
        timeroffRadioButton = (RadioButton) get(R.id.timeroffButton);
        editText1 = (EditText) get(R.id.editText1);
        editText2 = (EditText) get(R.id.editText2);
        nameEt = (EditText) get(R.id.nameEt);
        repeatContentTv = (TextView) get(R.id.repeatContentTv);
        qinjinContentTv = (TextView) get(R.id.qinjinContentTv);
        groupTv = (TextView) get(R.id.groupTv);


        view=LayoutInflater.from(getActivity()).inflate(R.layout.wheel_date_picker, null);
        view1=LayoutInflater.from(getActivity()).inflate(R.layout.delegate_timer, null);
        timerlistview = (ListView) view1.findViewById(R.id.timerLv);
        min = (WheelView) view.findViewById(R.id.min);
        sec = (WheelView) view.findViewById(R.id.sec);


    }

    public View getWhellView(){
        return view;
    }

    public TextView getRepeatContentTv() {
        return repeatContentTv;
    }
    public TextView getQinjinContentTv() {
        return qinjinContentTv;
    }
    public TextView getGroupTv() {
        return groupTv;
    }
    public WheelView getWheelViewMin() {
        return min;
    }
    public WheelView getWheelViewSec() {
        return sec;
    }
    public ListView getListView() {
        return timerlistview;
    }

    public LinearLayout getLineraLyout(){
        return lll;
    }

    public RadioGroup getRadioGroup(){
        return genderGroup;
    }
    public RadioButton getRadioButtonOff(){
        return timeroffRadioButton;
    }
    public RadioButton getRadioButtonOn(){
        return timeronRadioButton;
    }

    public EditText getEditText1(){
        return editText1;
    }
    public EditText getNameEt(){
        return nameEt;
    }

    public EditText getEditText2(){
        return editText2;
    }
    public void setTitleLeft(String title_left) {
        this.title_left.setText(title_left);
    }

    public void setTitleCenter(String title_center) {
        this.title_center.setText(title_center);
    }

    public void setTitleRight(String title_right) {
        this.title_right.setText(title_right);
    }


}
