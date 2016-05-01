package com.bosong.ball_light.view;

import android.widget.TextView;

import com.bosong.ball_light.R;

import com.bosong.framework.view.AppDelegate;

import android.widget.ListView;
/**
 * Created by mike on 1/20/16.
 */
public class GroupManageDelegate extends AppDelegate {

    private TextView title_left;
    private TextView title_right;
    private ListView mListView;
    @Override
    public int getRootLayoutId(){
        return R.layout.delegate_groupmanage;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        title_left = get(R.id.title_left);
        title_right = get(R.id.title_right);

        mListView = get(R.id.lv_groupmanage);

    }

    public void setTitleLeft(int title_left) {
        this.title_left.setText(title_left);
    }

    public void setTitleRight(int title_right) {
        this.title_right.setText(title_right);
    }

    public ListView getListView(){
        return mListView;
    }
}
