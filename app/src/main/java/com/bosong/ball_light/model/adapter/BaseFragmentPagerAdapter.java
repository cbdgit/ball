package com.bosong.ball_light.model.adapter;

import com.bosong.ball_light.R;
import com.bosong.ball_light.presenter.fragment.AllDevidesFragment;
import com.bosong.ball_light.presenter.fragment.ContextFragment;
import com.bosong.ball_light.presenter.fragment.TimerFragment;
import com.bosong.ball_light.presenter.fragment.MusicFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class BaseFragmentPagerAdapter extends FragmentPagerAdapter {
    
    private Context mContext;
    private AllDevidesFragment allDevidesFragment = AllDevidesFragment.newInstance();
    private ContextFragment contextFragment = new ContextFragment();
    private TimerFragment timerFragment = new TimerFragment();
    private MusicFragment musicFragment = new MusicFragment();

    public BaseFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        //return SampleFragment.newInstance(CONTENT[position % CONTENT.length]);
        switch(position % 4){
            case 0:
                if(allDevidesFragment == null) {
                    allDevidesFragment = AllDevidesFragment.newInstance();
                }
                return allDevidesFragment;
            case 1:
                if(contextFragment == null) {
                    contextFragment = new ContextFragment();
                }
                return contextFragment;
            case 2:
                if(timerFragment == null) {
                    timerFragment = new TimerFragment();
                }
                return timerFragment;
            case 3:
                if(musicFragment == null) {
                    musicFragment = new MusicFragment();
                }
                return musicFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return new String[]{
                mContext.getString(R.string.tab1_text),
                mContext.getString(R.string.tab2_text),
                mContext.getString(R.string.tab3_text),
                mContext.getString(R.string.tab4_text)}[position];
    }

    @Override
    public int getCount() {
        return 4;
    }
}