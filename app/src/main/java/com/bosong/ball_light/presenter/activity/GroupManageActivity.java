package com.bosong.ball_light.presenter.activity;

/**
 * Created by mike on 1/20/16.
 */

import android.os.Bundle;

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.adapter.GroupManageAdapter;
import com.bosong.ball_light.view.GroupManageDelegate;
import com.bosong.framework.presenter.ActivityPresenter;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;

public class GroupManageActivity extends ActivityPresenter<GroupManageDelegate> implements View.OnClickListener {

    private Intent intent;
    private ListView mListView;
    private List<String> mListGroupName;
    private GroupManageAdapter mGroupManageAdapter;
    @Override
    protected Class<GroupManageDelegate> getDelegateClass(){
        return GroupManageDelegate.class;
    }

    @Override
    protected void bindEventListener(){
        super.bindEventListener();

        viewDelegate.setOnClickListener(this, R.id.title_left);
        viewDelegate.setOnClickListener(this, R.id.title_right);
        viewDelegate.setTitleLeft(R.string.title_add_group);
        viewDelegate.setTitleRight(R.string.title_close_group);

        intent = getIntent();
        mListGroupName = new ArrayList<String>();
        mListGroupName = intent.getStringArrayListExtra("groupname");
        Log.v("Y", mListGroupName.toString());
        mGroupManageAdapter = new GroupManageAdapter(this, mListGroupName);
        mListView = viewDelegate.getListView();
        mListView.setAdapter(mGroupManageAdapter);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.title_left:
                viewDelegate.toast("Left");
                break;
            case R.id.title_right:
                finish();
                break;
        }

    }
}
