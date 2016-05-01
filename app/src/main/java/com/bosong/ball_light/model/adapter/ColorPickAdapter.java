package com.bosong.ball_light.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.bean.ColorMemberBean;
import com.bosong.ball_light.presenter.activity.ContextEditActivity;
import com.bosong.ball_light.presenter.fragment.UIColorFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mike on 1/23/16.
 */
public class ColorPickAdapter extends BaseAdapter {
    private List<ColorMemberBean> mList = new LinkedList<ColorMemberBean>();
    private List<ColorMemberBean> mList2 = new LinkedList<ColorMemberBean>();
    private LayoutInflater inflater;
    private Context mContext;

    private enum Mode {
        DEL, NORMAL
    };

    private Mode mMode = Mode.NORMAL;

    public ColorPickAdapter(Context mContext, List<ColorMemberBean> mList) {
        inflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size() + 2;
    }

    @Override
    public Object getItem(int position) {
        if (position >= mList.size())
            return null;
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        holder = new ViewHolder();
        convertView = inflater.inflate(R.layout.grid_item_color_pick, null);
        holder.icon = (ImageView) convertView.findViewById(R.id.item_icon);
        holder.del = (ImageView) convertView.findViewById(R.id.item_del);
        holder.online = (ImageView) convertView
                .findViewById(R.id.item_online_state);

        if (position == mList.size()) {
            holder.icon.setBackgroundColor(android.graphics.Color.TRANSPARENT);
            holder.icon.setImageResource(R.mipmap.smiley_add_btn_pressed);
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addItem(new ColorMemberBean(UIColorFragment.colorInt, false));
                }
            });
            holder.online.setVisibility(View.GONE);
            holder.del.setVisibility(View.GONE);
        } else if (position == mList.size() + 1) {
            holder.icon.setBackgroundColor(android.graphics.Color.TRANSPARENT);
            holder.icon.setImageResource(R.mipmap.smiley_minus_btn_pressed);
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMode == Mode.NORMAL) {
                        mMode = Mode.DEL;
                    } else {
                        mMode = Mode.NORMAL;
                    }
                    refreshUI();
                }
            });
            holder.online.setVisibility(View.GONE);
            holder.del.setVisibility(View.GONE);
        } else {
            holder.icon.setBackgroundColor(mList.get(position).getColorId());
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mList.get(position).isOnline()) {
                        mList.get(position).setOnline(false);
                    } else {
                        //只有一项能被选择，其他都设置为未选择
                        for (int i = 0; i < mList.size(); i++) {
                            mList.get(i).setOnline(false);
                        }
                        mList.get(position).setOnline(true);
                    }
                    refreshUI();
                }
            });
            holder.icon.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Intent intent = new Intent(mContext, ContextEditActivity.class);
                    mContext.startActivity(intent);
                    return true;
                }
            });
            int resOnlineId = 0;
            if (mList.get(position).isOnline()) {
                resOnlineId = R.drawable.member_online;
            }
            holder.online.setVisibility(View.VISIBLE);
            holder.online.setImageResource(resOnlineId);
            if (mMode == Mode.NORMAL) {
                holder.del.setVisibility(View.GONE);
            } else {
                holder.del.setVisibility(View.VISIBLE);
                holder.online.setVisibility(View.GONE);
            }

            holder.del.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItem(position);
                    refreshUI();
                }
            });

            holder.del.setOnKeyListener(new View.OnKeyListener() {

                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                        mMode = Mode.NORMAL;
                    }
                    return false;
                }
            });
        }

        return convertView;
    }

    public void refreshUI() {
        notifyDataSetChanged();
    }

    public void setMode(boolean b) {
        if (b == false){
            mMode = Mode.DEL;
        } else if (b == true) {
            mMode = Mode.NORMAL;
        }
        refreshUI();
    }

    public void addItem(ColorMemberBean mColorMemberBean){
        mList.add(mColorMemberBean);
        refreshUI();
    }
    public void deleteItem(int position){
        mList.remove(position);
        refreshUI();
    }

    private class ViewHolder {
        ImageView icon;
        ImageView del;
        ImageView online;
    }
}