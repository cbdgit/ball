package com.bosong.ball_light.presenter.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.adapter.TimerAdapter;
import com.bosong.ball_light.model.bean.TimerBean;
import com.bosong.ball_light.view.AddTimerDelegate;


import com.bosong.ball_light.widget.NumericWheelAdapter;
import com.bosong.ball_light.widget.OnWheelScrollListener;
import com.bosong.ball_light.widget.WheelView;
import com.bosong.framework.presenter.ActivityPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddTimerActivity extends ActivityPresenter<AddTimerDelegate> implements View.OnClickListener{
     int mCurrentTab=0;
     LayoutInflater inflater;
     WheelView min;
     WheelView sec;
     int minTv;
     int secTv;
     TimerAdapter adapter=null;
     List<TimerBean> list;
    TimerBean timerbean;
    @Override
    protected Class<AddTimerDelegate> getDelegateClass(){
        return AddTimerDelegate.class;
    }

    @Override
    protected void bindEventListener() {
        super.bindEventListener();
       
        inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        viewDelegate.setOnClickListener(this, R.id.title_left);
        viewDelegate.setOnClickListener(this, R.id.title_right);
        viewDelegate.setOnClickListener(this, R.id.repeatRl);
        viewDelegate.setOnClickListener(this, R.id.qingjinRl);
        viewDelegate.setTitleLeft("取消");
        viewDelegate.setTitleCenter("新建任务");
        viewDelegate.setTitleRight("完成");
        viewDelegate.getLineraLyout().addView(getDataPick());
        mCurrentTab = 0;
        viewDelegate.getRadioButtonOn().setTextColor(Color.GRAY);
        viewDelegate.getRadioButtonOff().setTextColor(Color.BLACK);
        viewDelegate.getEditText1().setText("00" + ":" + "00");
        viewDelegate.getEditText2().setText("00" + ":" + "00");
        viewDelegate.getEditText1().setSelection(viewDelegate.getEditText1().getText().toString().length());
        viewDelegate.getEditText2().setSelection(viewDelegate.getEditText2().getText().toString().length());
        list=new ArrayList<TimerBean>();


        //给RadioGroup设置事件监听
        viewDelegate.getRadioGroup().setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if (checkedId == viewDelegate.getRadioButtonOn().getId()) {
                    viewDelegate.getRadioButtonOn().setTextColor(Color.GRAY);
                    viewDelegate.getRadioButtonOff().setTextColor(Color.BLACK);
                    mCurrentTab = 0;

                } else if (checkedId ==  viewDelegate.getRadioButtonOff().getId()) {
                    viewDelegate.getRadioButtonOn().setTextColor(Color.BLACK);
                    viewDelegate.getRadioButtonOff().setTextColor(Color.GRAY);
                    mCurrentTab = 1;
                }
            }
        });

    }
     private View getDataPick() {

         NumericWheelAdapter numericWheelAdapter3 = new NumericWheelAdapter(this, 0, 23, "%02d");
         numericWheelAdapter3.setLabel("时");
         viewDelegate.getWheelViewMin().setViewAdapter(numericWheelAdapter3);
         viewDelegate.getWheelViewMin().setCyclic(true);
         viewDelegate.getWheelViewMin().addScrollingListener(scrollListener);
         NumericWheelAdapter numericWheelAdapter4 = new NumericWheelAdapter(this, 0, 59, "%02d");
         numericWheelAdapter4.setLabel("分");
         viewDelegate.getWheelViewSec().setViewAdapter(numericWheelAdapter4);
         viewDelegate.getWheelViewSec().setCyclic(true);
         viewDelegate.getWheelViewSec().addScrollingListener(scrollListener);
         viewDelegate.getWheelViewMin().setVisibleItems(7);
         viewDelegate.getWheelViewSec().setVisibleItems(7);
         // year.setCurrentItem(curYear - 1950);
         // month.setCurrentItem(curMonth - 1);
         // day.setCurrentItem(curDate - 1);

         return viewDelegate.getWhellView();
     }

     OnWheelScrollListener scrollListener = new OnWheelScrollListener() {
         @Override
         public void onScrollingStarted(WheelView wheel) {

         }

         @Override
         public void onScrollingFinished(WheelView wheel) {
             minTv=viewDelegate.getWheelViewMin().getCurrentItem();
             secTv=viewDelegate.getWheelViewSec().getCurrentItem();
             String minContent=String.valueOf(minTv);
             String secContent=String.valueOf(secTv);
             if(minTv<10){
                 minContent="0"+minContent;
             }
             if(secTv<10){
                 secContent="0"+secContent;
             }
             if( mCurrentTab== 0){
                 viewDelegate.getEditText1().setText(minContent + ":" + secContent);
             }else{
                 viewDelegate.getEditText2().setText(minContent + ":" + secContent);
             }
             viewDelegate.getEditText1().setSelection(viewDelegate.getEditText1().getText().toString().length());
             viewDelegate.getEditText2().setSelection(viewDelegate.getEditText2().getText().toString().length());
           //  Toast.makeText(AddTimerActivity.this, minTv + "时" + secTv + "分", 0).show();

         }
     };

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
             case RESULT_OK:
                 Bundle b=data.getExtras(); //data为B中回传的Intent
                 String qingjin=b.getString("qingjin");//str即为回传的值
                 viewDelegate.getQinjinContentTv().setText(qingjin);
                 break;
             default:
                 break;
         }
     }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.qingjinRl:
                Intent intent=new Intent(this,SelectContextActivity.class);
                startActivityForResult(intent, 0);
                break;
            case R.id.repeatRl:
                AlertDialog.Builder builder3=new AlertDialog.Builder(AddTimerActivity.this);
                builder3.setTitle("选择日期");
                final String[] items=new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期日","永不重复"};
                builder3.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(AddTimerActivity.this, items[which], Toast.LENGTH_LONG).show();
                         viewDelegate.getRepeatContentTv().setText(items[which]);
                        dialog.dismiss();
                    }
                });
                builder3.create().show();
                break;
            case R.id.title_right:
                 if(TextUtils.isEmpty(viewDelegate.getNameEt().getText().toString())){
                     Toast.makeText(this,"请输入名称",Toast.LENGTH_SHORT).show();
                     return;
                 }
                  if(Integer.parseInt(viewDelegate.getEditText1().getText().toString().substring(0,2))>Integer.parseInt(viewDelegate.getEditText2().getText().toString().substring(0, 2))) {
                      Toast.makeText(this, "定时关时间必须在定时开时间之后", Toast.LENGTH_SHORT).show();
                      return;
                  }
                 else  if(Integer.parseInt(viewDelegate.getEditText1().getText().toString().substring(0,2))==Integer.parseInt(viewDelegate.getEditText2().getText().toString().substring(0, 2))){
                     if(Integer.parseInt(viewDelegate.getEditText1().getText().toString().substring(3,5))>=Integer.parseInt(viewDelegate.getEditText2().getText().toString().substring(3, 5))) {
                         Toast.makeText(this,"定时关时间必须在定时开时间之后",Toast.LENGTH_SHORT).show();
                         return;
                     }else{
                         Intent i=new Intent();
                         i.putExtra("titlename",viewDelegate.getNameEt().getText().toString());
                         i.putExtra("timeropen", viewDelegate.getEditText1().getText().toString());
                         i.putExtra("timerclose",viewDelegate.getEditText2().getText().toString());
                         i.putExtra("group", viewDelegate.getGroupTv().getText().toString());
                         setResult(3,i);
                         finish();
                     }
                 }else{
                      Intent i=new Intent();
                      i.putExtra("titlename",viewDelegate.getNameEt().getText().toString());
                      i.putExtra("timeropen", viewDelegate.getEditText1().getText().toString());
                      i.putExtra("timerclose",viewDelegate.getEditText2().getText().toString());
                      i.putExtra("group", viewDelegate.getGroupTv().getText().toString());
                      setResult(3,i);
                      finish();
                  }


                break;
            default:
                break;
        }
    }


}
