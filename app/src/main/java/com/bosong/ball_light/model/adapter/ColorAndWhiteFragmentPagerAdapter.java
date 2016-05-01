package com.bosong.ball_light.model.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bosong.ball_light.R;
import com.bosong.ball_light.presenter.fragment.UIColorFragment;
import com.bosong.ball_light.presenter.fragment.UIWhiteFragment;

/**
 * Created by mike on 1/22/16.
 */
public class ColorAndWhiteFragmentPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private UIColorFragment mUIColorFragment;
    private UIWhiteFragment mUIWhiteFragment;

    public ColorAndWhiteFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        //return SampleFragment.newInstance(CONTENT[position % CONTENT.length]);
        switch(position % 2){
            case 0:
                if(mUIColorFragment == null) {
                    mUIColorFragment = UIColorFragment.newInstance();
                }
                return mUIColorFragment;
            case 1:
                if(mUIWhiteFragment == null) {
                    mUIWhiteFragment = UIWhiteFragment.newInstance();
                }
                return mUIWhiteFragment;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return new String[]{
                mContext.getString(R.string.tab_color),
                mContext.getString(R.string.tab_white),
                }[position];
    }

    @Override
    public int getCount() {
        return 2;
    }
}
