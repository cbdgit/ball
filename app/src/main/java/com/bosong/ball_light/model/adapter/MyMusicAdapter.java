package com.bosong.ball_light.model.adapter;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bosong.ball_light.R;
import com.bosong.ball_light.model.bean.Music;
import com.bosong.ball_light.util.Config;
import com.bosong.ball_light.util.ListData;

/**
 * 本地音乐ListView适配器
 * 
 * @author kymjs
 */
public class MyMusicAdapter extends AbsPlayListAdapter {
    private final Context mContext;
    private final int currentPager;
    private List<Music> datas = null;

    public MyMusicAdapter(Context context, int current) {
        super(current);
        mContext = context;
        this.currentPager = current;
        datas = ListData.getLocalList(context);
        if (datas == null) {
            datas = new ArrayList<Music>();
        }
    }

    @Override
    public void refresh() {
        datas = ListData.getCollectList(mContext);
        notifyDataSetChanged();
    }

    @Override
    public int getCurrentPager() {
        return currentPager;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int arg0) {
        return datas.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return datas.get(position).getId();
    }

    static class ViewHolder {
        TextView tv_title;
        TextView tv_artist;
        ImageView img_collect;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View
                    .inflate(mContext, R.layout.list_item_music, null);
            holder = new ViewHolder();
            holder.tv_title = (TextView) convertView
                    .findViewById(R.id.list_item_title);
            holder.tv_artist = (TextView) convertView
                    .findViewById(R.id.list_item_artist);
            holder.img_collect = (ImageView) convertView
                    .findViewById(R.id.list_img_button);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_title.setText(datas.get(position).getTitle());
        holder.tv_artist.setText(datas.get(position).getArtist());
        return convertView;
    }

    /*
     * 判断歌曲是否已经被收藏
     */
    private boolean isCollect(int position) {
        return datas.get(position).getCollect() != 0;
    }
}
