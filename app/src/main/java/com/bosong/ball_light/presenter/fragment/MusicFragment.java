package com.bosong.ball_light.presenter.fragment;

import com.bosong.ball_light.R;
import com.bosong.ball_light.presenter.activity.MusicPlayActivity;
import com.bosong.ball_light.view.MusicDelegate;
import com.bosong.framework.presenter.FragmentPresenter;

import android.content.Intent;
import android.view.View;
import android.widget.Toast;

public class MusicFragment extends FragmentPresenter<MusicDelegate> implements View.OnClickListener {

    public static MusicFragment newInstance() {
        MusicFragment musicFragment = new MusicFragment();
        return musicFragment;
    }


    @Override
    protected Class<MusicDelegate> getDelegateClass() {
        return MusicDelegate.class;
    }

    @Override
    protected void bindEventListener() {
        super.bindEventListener();
        //viewDelegate.setOnClickListener(this, R.id.title_left);
        //viewDelegate.setOnClickListener(this, R.id.title_right);
        //viewDelegate.setTitleLeft("删除");
        //viewDelegate.setTitleRight("新建");
        viewDelegate.getYueKuButton().setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yuekuTv:
                Intent intent = new Intent(getActivity(), MusicPlayActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}