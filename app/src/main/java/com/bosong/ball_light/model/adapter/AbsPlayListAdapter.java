package com.bosong.ball_light.model.adapter;

import android.widget.BaseAdapter;

public abstract class AbsPlayListAdapter extends BaseAdapter {
    abstract public int getCurrentPager();

    abstract public void refresh();

    public AbsPlayListAdapter(int currentPager) {
        super();
    }
}
