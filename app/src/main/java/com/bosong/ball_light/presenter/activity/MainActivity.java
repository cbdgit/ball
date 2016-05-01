
package com.bosong.ball_light.presenter.activity;

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.adapter.BaseFragmentPagerAdapter;
import com.bosong.ball_light.presenter.service.ScanMusic;
import com.bosong.ball_light.util.Config;
import com.bosong.ball_light.util.PreferenceHelper;
import com.bosong.ball_light.view.MainDelegate;

import com.bosong.framework.presenter.ActivityPresenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends ActivityPresenter<MainDelegate> {

    private FadeTabIndicator fadeTabIndicator;
    private ViewPager viewPager;
    private String scanToast;
    private int totalsongnum;
    private String picturePath;

    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected Class<MainDelegate> getDelegateClass(){
        return MainDelegate.class;
    }

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadRes();
        writeLog();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.RECEIVER_UPDATE_MUSIC_LIST);
        filter.addAction(Config.RECEIVER_MUSIC_SCAN_FAIL);
        registerReceiver(scanReceiver,filter);
    };

    /**
     * 扫描本地歌曲
     */
    private void loadRes() {
        Intent it = new Intent();
        it.setClass(this, ScanMusic.class);
        startService(it);
    }

    /**
     * 写入本地记录
     */
    private void writeLog() {
        PreferenceHelper.write(this, Config.FIRSTINSTALL_FILE,
                Config.FIRSTINSTALL_KEY, false);
    }


    BroadcastReceiver scanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Config.RECEIVER_UPDATE_MUSIC_LIST.equals(intent.getAction())) {
                totalsongnum=intent.getIntExtra(Config.SCAN_MUSIC_COUNT, 0);
                Config.MUSIC_COUNT=totalsongnum;
                scanToast = "扫描到 "
                        + totalsongnum
                        + " intent.getIntExtra(Config.SCAN_MUSIC_COUNT, 0)首歌曲";
            } else if (Config.RECEIVER_MUSIC_SCAN_FAIL.equals(intent
                    .getAction())) {
                scanToast = "呀，扫描失败了，再试一试？";
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(scanReceiver);
    }
    @Override
    protected void bindEventListener() {
        super.bindEventListener();

        viewPager = viewDelegate.getViewPager();
        fadeTabIndicator = viewDelegate.getIndicator();

        viewPager.setOffscreenPageLimit(3);
        viewPager.setAdapter(new FadeTabFragmentPagerAdapter(this, getSupportFragmentManager()));
        fadeTabIndicator.setViewPager(viewPager);
        fadeTabIndicator.setCurrentItem(0);
        //搜索时 可以让tab标签不被弹出的键盘挤上去
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }
    private boolean isExit=false;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {//
            Timer tExit = null;
            if (isExit == false) {
                isExit = true; // 准备退出
                Toast.makeText(MainActivity.this, "再按一次退出程序",
                        Toast.LENGTH_SHORT).show();
                tExit = new Timer();
                tExit.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        isExit = false; // 取消退出
                    }
                }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

            }
            else {
                MainActivity.this.finish();
                // System.exit(0);
            }
        }
        return false;
    }



    private class FadeTabFragmentPagerAdapter extends BaseFragmentPagerAdapter implements FadeTabIndicator.FadingTab {

        public FadeTabFragmentPagerAdapter(Context context, FragmentManager fm) {
            super(context, fm);
        }

        @Override
        public int getTabNormalIconResId(int position) {
            return new int[]{
                    R.drawable.ic_1_1,
                    R.drawable.ic_2_1,
                    R.drawable.ic_3_1,
                    R.drawable.ic_4_1}[position];
        }

        @Override
        public int getTabSelectIconResId(int position) {
            return new int[]{
                    R.drawable.ic_1_0,
                    R.drawable.ic_2_0,
                    R.drawable.ic_3_0,
                    R.drawable.ic_4_0}[position];
        }

        @Override
        public int getTabNormalTextColorResId(int position) {
            return R.color.text_normal;
        }

        @Override
        public int getTabSelectTextColorResId(int position) {
            return R.color.text_select;
        }
    }
}
