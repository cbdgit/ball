package com.bosong.ball_light.presenter.fragment;

import android.view.View;

import com.bosong.ball_light.view.UIWhiteDelegate;
import com.bosong.framework.presenter.FragmentPresenter;

/**
 * Created by mike on 1/22/16.
 */
public class UIWhiteFragment extends FragmentPresenter<UIWhiteDelegate> implements View.OnClickListener{
    public static UIWhiteFragment newInstance() {
        UIWhiteFragment mUIWhiteFragment = new UIWhiteFragment();
        return mUIWhiteFragment;
    }

    @Override
    protected Class<UIWhiteDelegate> getDelegateClass(){
        return UIWhiteDelegate.class;
    }

    @Override
    protected void bindEventListener() {
        super.bindEventListener();
    }

    @Override
    public void onClick(View v){

    }
}
