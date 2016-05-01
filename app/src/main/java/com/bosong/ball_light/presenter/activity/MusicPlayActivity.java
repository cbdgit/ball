package com.bosong.ball_light.presenter.activity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bosong.ball_light.R;
import com.bosong.ball_light.presenter.fragment.MainFragment;
import com.bosong.ball_light.presenter.service.PlayerService;
import com.bosong.ball_light.util.Config;
import com.bosong.ball_light.util.Player;

import org.kymjs.kjframe.KJActivity;
import org.kymjs.kjframe.ui.ViewInject;

/**
 * Created by mike on 1/25/16.
 */
public class MusicPlayActivity extends KJActivity {
    //顶部
    private TextView titleLeft;
    private TextView titleRight;
    private TextView titleCenter;
    //底部
    private Button mBtnNext, mBtnPrevious, mBtnPlay;
    private ImageView mImg;
    private TextView mTvTitle, mTvArtist;
    /** 音乐播放器服务 */
    public PlayerService mPlayersService;
    private final Connection conn = new Connection();
    private final MusicChangeReceiver changeReceiver = new MusicChangeReceiver();

    @Override
    public void setRootView() {
        setContentView(R.layout.music_activity);
    }

    @Override
    public void initWidget() {
         changeFragment(new MainFragment(), false);
        //initTopBar();
        initBottonBar();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent serviceIntent = new Intent(this, PlayerService.class);
        this.bindService(serviceIntent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void registerBroadcast() {
        super.registerBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.RECEIVER_MUSIC_CHANGE);
        filter.addAction(Config.RECEIVER_ERROR);
        registerReceiver(changeReceiver, filter);
    }

    @Override
    public void widgetClick(View v) {
        Player player = Player.getPlayer();
        switch (v.getId()) {
            case R.id.title_left:
                finish();
                break;
            case R.id.bottom_bar:
                // wantScroll();
                break;
            case R.id.bottom_btn_next:
                mPlayersService.next();
                break;
            case R.id.bottom_btn_previous:
                mPlayersService.previous();
                break;
            case R.id.bottom_btn_play:
                if (player.getPlaying() == Config.PLAYING_PLAY) {
                    mPlayersService.pause();
                } else if (player.getPlaying() == Config.PLAYING_PAUSE) {
                    mPlayersService.replay();
                } else {
                    mPlayersService.play();
                }

                break;
        }
    }

    /**
     * 刷新底部栏
     */
    private void refreshBottomBar() {
        Player player = Player.getPlayer();
        switch (player.getPlaying()) {
            case Config.PLAYING_PAUSE:
                mImg.setImageResource(R.drawable.image_dish);
                mBtnPlay.setBackgroundResource(R.drawable.selector_btn_play);
                mTvTitle.setText(player.getMusic().getTitle());
                mTvArtist.setText(player.getMusic().getArtist());
                break;
            case Config.PLAYING_PLAY:
                mImg.setImageResource(R.drawable.image_dish);
                mBtnPlay.setBackgroundResource(R.drawable.selector_btn_pause);
                mTvTitle.setText(player.getMusic().getTitle());
                mTvArtist.setText(player.getMusic().getArtist());
                break;
            case Config.PLAYING_STOP:
                mImg.setImageResource(R.drawable.image_dish);
                mBtnPlay.setBackgroundResource(R.drawable.selector_btn_play);
                mTvTitle.setText(Config.TITLE);
                mTvArtist.setText(Config.ARTIST);
                break;
        }
    }

    public void initTopBar() {
        titleLeft = (TextView) findViewById(R.id.title_left);
        titleRight = (TextView) findViewById(R.id.title_right);
        titleCenter = (TextView) findViewById(R.id.title_center);
        titleLeft.setText("返回");
        titleCenter.setText("全部歌曲");
        titleRight.setText("");
        titleLeft.setOnClickListener(this);
    }

    /**
     * 初始化底部栏
     */
    private void initBottonBar() {
        findViewById(R.id.bottom_bar).setOnClickListener(this);
        mImg = (ImageView) findViewById(R.id.bottom_img_image);
        mImg.setBackgroundResource(R.drawable.image_dish);
        mTvTitle = (TextView) findViewById(R.id.bottom_tv_title);
        mTvArtist = (TextView) findViewById(R.id.bottom_tv_artist);
        mTvTitle.setText(Config.TITLE);
        mTvArtist.setText(Config.ARTIST);
        mBtnNext = (Button) findViewById(R.id.bottom_btn_next);
        mBtnPrevious = (Button) findViewById(R.id.bottom_btn_previous);
        mBtnPlay = (Button) findViewById(R.id.bottom_btn_play);
        mBtnNext.setOnClickListener(this);
        mBtnPrevious.setOnClickListener(this);
        mBtnPlay.setOnClickListener(this);
    }
    /**
     * 改变界面的fragment
     */
    private void changeFragment(Fragment targetFragment, boolean pushStack) {
        changeFragment(R.id.main_fragment, targetFragment, pushStack);
    }

    /**
     * 改变界面的fragment
     */
    private void changeFragment(int resView, Fragment targetFragment,
                                boolean pushStack) {

        FragmentTransaction transaction = getFragmentManager()
                .beginTransaction();
        // 使用传入的fragment替换主界面的fragment
        transaction.replace(resView, targetFragment, "fragment");
        // 设置动画样式
        transaction
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        if (pushStack) {
            // 添加到返回栈（使用户按下返回键时可以返回上一个界面）
            transaction.addToBackStack(null);
        }
        // 提交
        transaction.commit();
    }

    @Override
    public void unRegisterBroadcast() {
        super.unRegisterBroadcast();
        unregisterReceiver(changeReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.unbindService(conn);
    }

    /**
     * ServiceConnection实现类
     */
    class Connection implements ServiceConnection {
        @Override
        public void onServiceDisconnected(ComponentName name) {
            ViewInject.toast("呀，音乐播放失败，退出再进试试");
            mPlayersService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPlayersService = ((PlayerService.LocalPlayer) service)
                    .getService();
        }
    }

    /**
     * BroadcastReceiver类
     */
    public class MusicChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Config.RECEIVER_MUSIC_CHANGE.equals(intent.getAction())) {
                if (Player.getPlayer().getPlaying() != Config.PLAYING_STOP) {
                    refreshBottomBar();
                }
            } else if (Config.RECEIVER_ERROR.endsWith(intent.getAction())) {
                ViewInject.toast(intent.getStringExtra("error"));
            }
        }
    }

}
