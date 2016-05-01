package com.bosong.ball_light.presenter.fragment;
import org.kymjs.kjframe.ui.KJFragment;
import org.kymjs.kjframe.ui.ViewInject;
import org.kymjs.kjframe.utils.DensityUtils;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.adapter.AbsPlayListAdapter;
import com.bosong.ball_light.model.adapter.MyMusicAdapter;
import com.bosong.ball_light.presenter.activity.MusicPlayActivity;
import com.bosong.ball_light.presenter.service.ScanMusic;
import com.bosong.ball_light.util.Config;
import com.bosong.ball_light.util.ListData;
import com.bosong.ball_light.util.PreferenceHelper;
import com.bosong.ball_light.widget.JSViewPager;

/**
 * 播放列表界面 fragment
 * 
 * @author kymjs
 */
public class MainFragment extends KJFragment {
    //顶部
    private TextView titleLeft;
    private TextView titleRight;
    private TextView titleCenter;
    private String scanToast;
    private int totalsongnum;
    // 界面控件
    private JSViewPager jsViewPager;
    private TextView mTextTab1, mTextTab2, mTextTab3;
    // 顶部图片
    private int currIndex = 0;// 当前页卡编号
    private ImageView mImgLine;

    // listView刷新广播接收器
    private final RefreshAdapterReceiver receiver = new RefreshAdapterReceiver();

    // ViewPager中的控件
    private AbsPlayListAdapter myMusicAdp, collectAdp; // 主界面中ListView适配器
    private ListView mMyMusicList, mCollectList; // Pager中的List

    @Override
    protected View inflaterView(LayoutInflater inflater, ViewGroup container,
            Bundle arg2) {
        View view = inflater.inflate(R.layout.frag_main, container, false);
        return view;
    }

    @Override
    public void initWidget(View parentView) {

        mTextTab1 = (TextView) parentView.findViewById(R.id.collect_title1);
        mTextTab2 = (TextView) parentView.findViewById(R.id.collect_title2);
        mTextTab3 = (TextView) parentView.findViewById(R.id.collect_title3);

        titleLeft = (TextView)parentView.findViewById(R.id.title_left);
        titleRight = (TextView)parentView.findViewById(R.id.title_right);
        titleCenter = (TextView)parentView.findViewById(R.id.title_center);
        titleLeft.setText("返回");
        titleCenter.setText("全部歌曲");
        titleRight.setText("");
        titleLeft.setOnClickListener(this);
        mTextTab1.setText("单曲/" +  Config.MUSIC_COUNT);
        mTextTab2.setText("歌手");
        mTextTab3.setText("专辑");
        mTextTab1.setOnClickListener(this);
        mTextTab2.setOnClickListener(this);
        mTextTab3.setOnClickListener(this);


        initViewPager(parentView);
        initImageLine(parentView, 3);
    }

    @Override
    public void widgetClick(View v) {
        if (v == mTextTab1) {
            jsViewPager.setCurrentItem(0);
        } else if (v == mTextTab2) {
            jsViewPager.setCurrentItem(1);
        } else if (v == mTextTab3) {
            jsViewPager.setCurrentItem(2);
        }else if(v==titleLeft){
            getActivity().finish();
        }
    }

    /**
     * 初始化主界面ViewPager
     */
    private void initViewPager(View parentView) {
        jsViewPager = (JSViewPager) parentView.findViewById(R.id.main_pager);
        jsViewPager.setAdapter(new MainPagerAdapter(3));
        jsViewPager.setCurrentItem(1);
        jsViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {}

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}

            @Override
            public void onPageSelected(int arg0) {
                int offset = mTextTab3.getMeasuredWidth();
                Animation animation = new TranslateAnimation(offset
                        * (currIndex - 1), offset * (arg0 - 1), 0, 0);
                currIndex = arg0;
                animation.setFillAfter(true);// 图片停在动画结束位置
                animation.setDuration(300);
                mImgLine.startAnimation(animation);

            }
        });
    }

    /**
     * 初始化滚动线并设置滚动线的宽度
     *
     * @param pagers
     *            viewpager的页面数
     */
    private void initImageLine(View parentView, int pagers) {
        mImgLine = (ImageView) parentView.findViewById(R.id.collect_cursor);
        // 从资源获取一个bitmap
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_collect_line);
        // 获取设备屏幕宽度
        WindowManager window = (WindowManager) getActivity().getSystemService(
                Context.WINDOW_SERVICE);
        int screenW = DensityUtils.getScreenW(getActivity());

        Matrix matrix = new Matrix();
        // 将宽度按(目标宽度/原宽度)放大，高度没有改变，则比例为1
        matrix.postScale((screenW / pagers) / bitmap.getWidth(), 1);
        // 得到放大的图片
       // bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
              //  bitmap.getHeight(), matrix, true);
        // matrix.postTranslate(offset, 0);//默认显示位置
        // mImgLine.setImageMatrix(matrix);// 设置动画初始位置
        // 设置ImageView的图片
        mImgLine.setImageBitmap(bitmap);
        bitmap = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Config.RECEIVER_UPDATE_MUSIC_LIST);
        filter.addAction(Config.RECEIVER_MUSIC_SCAN_FAIL);
        getActivity().registerReceiver(receiver, filter);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        getActivity().unregisterReceiver(receiver);
        //getActivity().unregisterReceiver(scanReceiver);
        super.onDestroy();
    }

    /**
     * 刷新Adapter的广播
     * 
     * @author kymjs
     */
    public class RefreshAdapterReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Config.RECEIVER_UPDATE_MUSIC_LIST.equalsIgnoreCase(intent
                    .getAction())) {
                // 我不知道为什么，调用刷新方法却没有刷新控件，好像必须销毁ListView再重新创建才能有刷新作用
                // if (collectAdp != null) {
                // collectAdp.refresh();
                // mCollectList.invalidate();
                // }
                // if (myMusicAdp != null) {
                // myMusicAdp.refresh();
                // mMyMusicList.invalidate();
                // }
                myMusicAdp = new MyMusicAdapter(getActivity(), 0);
                mMyMusicList.setAdapter(myMusicAdp);
               // collectAdp = new CollectListAdapter(getActivity(), 1);
               // mCollectList.setAdapter(collectAdp);

            } else {
                ViewInject.toast("呀，扫描失败了，退出再进试试？");
            }
        }
    }

    /***************************************************************************************
     * 
     * 主界面ViewPager控件的适配器
     * 
     ***************************************************************************************/
    class MainPagerAdapter extends PagerAdapter implements OnItemClickListener {
        private final int pagers;

        public MainPagerAdapter(int pagers) {
            super();
            this.pagers = pagers;
        }

        @Override
        public int getCount() {
            return pagers;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View mainPagerView = getPagerView(position);
            if (mainPagerView == null) {
                mainPagerView = View.inflate(getActivity(),
                        R.layout.pager_item_main, null);
            }
            (container).addView(mainPagerView);
            return mainPagerView;
        }

        // 对不同的界面设置不同的适配器
        private View getPagerView(int pager) {
            View pagerView = null;
            switch (pager) {
            case 0:
                mMyMusicList = (ListView) View.inflate(getActivity(),
                        R.layout.pager_item_main, null);
                myMusicAdp = new MyMusicAdapter(getActivity(), pager);
                mMyMusicList.setAdapter(myMusicAdp);
                mMyMusicList.setOnItemClickListener(this);
                pagerView = mMyMusicList;
                break;
            case 1:

                break;
            case 2:

                break;
            }
            return pagerView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position,
                long id) {
            switch (((AbsPlayListAdapter) parent.getAdapter())
                    .getCurrentPager()) {
            case 0:
                ((MusicPlayActivity) getActivity()).mPlayersService.play(
                        ListData.getLocalList(getActivity()), position);
                break;
            case 1:

                break;
            }

        }
    }
}
