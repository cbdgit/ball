package com.bosong.ball_light.presenter.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.IGroup;
import com.bosong.ball_light.model.adapter.BaseFragmentPagerAdapter;
import com.bosong.ball_light.model.adapter.ContextGroupsAdapter;
import com.bosong.ball_light.model.bean.GroupBean;
import com.bosong.ball_light.model.bean.GroupMemberBean;
import com.bosong.ball_light.model.impl.GroupImpl;
import com.bosong.ball_light.view.ContextEditDelegate;
import com.bosong.framework.presenter.ActivityPresenter;

/**
 * Created by mike on 1/15/16.
 */
public class ContextEditActivity extends ActivityPresenter<ContextEditDelegate> implements View.OnClickListener{

    private TextView titleLeft;
    private TextView titleRight;

    private ImageView mContextImage;
    private ImageView mStatusImage;

    private GridView groups;
    private ContextGroupsAdapter mContextGroupsAdapter;
    private ArrayAdapter<String> mArrayAdapter;
    private List<String> mGroupsList;

    private ListView mListView;

    private IGroup mIGroup;
    private List<GroupBean>  mGroupList;
    private List<List<GroupMemberBean>> mChildList;
    private List<String> mListGroupName;
    private List<String> mListLightName;
    private List<GroupMemberBean> mLights;

    private int p = 0;

    @Override
    protected Class<ContextEditDelegate> getDelegateClass(){
        return ContextEditDelegate.class;
    }

    @Override
    protected void bindEventListener() {
        super.bindEventListener();

        viewDelegate.setOnClickListener(this, R.id.title_left);
        viewDelegate.setOnClickListener(this, R.id.title_right);
        viewDelegate.setTitleLeft(R.string.retn);
        //viewDelegate.setTitleRight(R.string.title_setting);

        mContextImage = viewDelegate.getImageContext();
        mStatusImage = viewDelegate.getImageStatus();
        mStatusImage.setImageResource(R.drawable.switcher_green);

        Intent intent = getIntent();
        if (intent != null && intent.getParcelableExtra("CONTEXT") != null) {
            Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("CONTEXT");
            mContextImage.setImageBitmap(bitmap);

        }

        mIGroup = new GroupImpl(viewDelegate.getActivity());
        mGroupList = new ArrayList<GroupBean>();
        mChildList = new ArrayList<List<GroupMemberBean>>();
        mGroupList = mIGroup.initGroup();
        mChildList = mIGroup.initLight();

        mContextGroupsAdapter = new ContextGroupsAdapter(viewDelegate.getActivity(), getGroupNames());
        groups = viewDelegate.getGridView();
        groups.setAdapter(mContextGroupsAdapter);

        mListLightName = getLightNames();
        mArrayAdapter =
                new ArrayAdapter<String>(this, R.layout.list_context_lights, R.id.tv, mListLightName);
        mListView = viewDelegate.getListView();
        mListView.setAdapter(mArrayAdapter);
    }

    private List<String> getGroupNames() {
        mListGroupName = new ArrayList<String>();
        for (int i = 0; i < mGroupList.size(); i++){
            mListGroupName.add(mGroupList.get(i).getName());
        }
        return mListGroupName;
    }

    private List<String> getLightNames() {
        mListLightName = new ArrayList<String>();
        mLights = new ArrayList<GroupMemberBean>();
        mLights = mIGroup.selectSomeLights(p);
        for (int i = 0; i < mLights.size(); i++){
            mListLightName.add(mLights.get(i).getName());
        }
        return mListLightName;
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.title_left:
                finish();
                break;
        }
    }
}

