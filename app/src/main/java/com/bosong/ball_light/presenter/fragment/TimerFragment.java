package com.bosong.ball_light.presenter.fragment;

/**
 * Created by mike on 1/11/16.
 */

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.adapter.TimerAdapter;
import com.bosong.ball_light.model.bean.TimerBean;
import com.bosong.ball_light.presenter.activity.AddTimerActivity;
import com.bosong.ball_light.view.TimerDelegate;
import com.bosong.framework.presenter.FragmentPresenter;

import java.util.ArrayList;
import java.util.List;

public class TimerFragment extends FragmentPresenter<TimerDelegate> implements View.OnClickListener {
    private ListView timerlistview;
    private TimerAdapter timeradapter;
    private List<TimerBean> timerlist = new ArrayList<TimerBean>();
    TimerBean timerbean;
    private int single = 1;

    public static TimerFragment newInstance() {
        TimerFragment timerFragment = new TimerFragment();
        return timerFragment;
    }


    @Override
    protected Class<TimerDelegate> getDelegateClass() {
        return TimerDelegate.class;
    }

    private void getListData() {
      /*  for (int i = 1; i < 4; i++) {
            timerbean = new TimerBean(1, R.drawable.timer_delete_icon, R.drawable.timer_open_icon, R.drawable.timer_set_icon, "起床" + i, "定时开:7：00", "定时关:7：00", "分组一，分组二，分组三");
            timerlist.add(timerbean);
        }*/


    }


    @Override
    protected void bindEventListener() {
        super.bindEventListener();
        getListData();
        viewDelegate.setOnClickListener(this, R.id.title_left);
        viewDelegate.setOnClickListener(this, R.id.addIv);
        viewDelegate.setTitleLeft("删除");
        viewDelegate.setTitleRight(R.drawable.add_icon);

        timeradapter = new TimerAdapter(viewDelegate.getActivity(), timerlist);
        timerlistview = viewDelegate.getListView();
        timerlistview.setAdapter(timeradapter);

        viewDelegate.getTextViewDevice().setOnClickListener(this);
        viewDelegate.getTextViewRepeat().setOnClickListener(this);
        viewDelegate.getTextViewContext().setOnClickListener(this);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case 3:
                Bundle b=data.getExtras(); //data为B中回传的Intent
                String titlename=b.getString("titlename");//str即为回传的值
                String timeropen=b.getString("timeropen");//str即为回传的值
                String timerclose=b.getString("timerclose");//str即为回传的值
                String group=b.getString("group");//str即为回传的值
                timerbean=new TimerBean();
                timerbean.setSwitcherStatus(0);
                timerbean.setDeleteId(R.drawable.timer_delete_icon);
                timerbean.setOpenId(R.drawable.close_icon);
                timerbean.setSetId(R.drawable.link_off);
                timerbean.setTimer_titlle(titlename);
                timerbean.setTimer_close("定时关:" + timerclose);
                timerbean.setTimer_open("定时开:"+timeropen);
                timerbean.setTimer_group(group);
                timerlist.add(0,timerbean);
                timeradapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_left:
                if (single % 2 == 1) {
                    timeradapter.setMode(false);
                    viewDelegate.setTitleLeft("取消");
                } else {
                    timeradapter.setMode(true);
                    viewDelegate.setTitleLeft("删除");
                }
                single++;
                break;
            case R.id.addIv:
                 Intent intent=new Intent(viewDelegate.getActivity(), AddTimerActivity.class);
                  this.startActivityForResult(intent, 0);
                break;

            default:
                break;
        }
    }
}
