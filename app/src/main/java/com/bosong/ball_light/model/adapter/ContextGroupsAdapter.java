package com.bosong.ball_light.model.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.IGroup;
import com.bosong.ball_light.model.bean.ColorMemberBean;
import com.bosong.ball_light.model.bean.ContextMemberBean;
import com.bosong.ball_light.model.bean.GroupMemberBean;
import com.bosong.ball_light.model.impl.GroupImpl;
import com.bosong.ball_light.presenter.activity.ContextEditActivity;
import com.bosong.ball_light.presenter.fragment.UIColorFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mike on 2/26/16.
 */
public class ContextGroupsAdapter extends BaseAdapter {
    private List<String> mList = new LinkedList<String>();
    private List<String> mLightNames = new ArrayList<String>();
    private List<ColorMemberBean> mList2 = new LinkedList<ColorMemberBean>();
    private LayoutInflater inflater;
    private Context mContext;
    private int p = 0;

    private enum Mode {
        DEL, NORMAL
    };

    private Mode mMode = Mode.NORMAL;

    public ContextGroupsAdapter(Context mContext, List<String> mList) {
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
        final ViewHolder holder;
        holder = new ViewHolder();
        convertView = inflater.inflate(R.layout.grid_item_group, null);
        holder.group = (TextView) convertView.findViewById(R.id.group);
        holder.icon = (ImageView) convertView.findViewById(R.id.icon);
        holder.del = (ImageView) convertView.findViewById(R.id.del);

        if (position == mList.size()) {
            holder.icon.setBackgroundColor(android.graphics.Color.TRANSPARENT);
            holder.icon.setImageResource(R.mipmap.smiley_add_btn_pressed);
            holder.icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //addItem(new ColorMemberBean(UIColorFragment.colorInt, false));
                    //Toast.makeText(mContext, "add", Toast.LENGTH_SHORT).show();
                }
            });
            holder.del.setVisibility(View.GONE);
            holder.group.setVisibility(View.GONE);
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
                    //Toast.makeText(mContext, "sub", Toast.LENGTH_SHORT).show();
                }
            });
            holder.del.setVisibility(View.GONE);
            holder.group.setVisibility(View.GONE);
        } else {
            //holder.icon.setBackgroundColor();
            //holder.group.setBackgroundColor(android.graphics.Color.WHITE);
            holder.icon.setVisibility(View.GONE);
            holder.group.setText(mList.get(position));
            holder.group.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    p = position;
                }
            });

            if (mMode == Mode.NORMAL) {
                holder.del.setVisibility(View.GONE);
            } else {
                holder.del.setVisibility(View.VISIBLE);
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
        //mList.add(mColorMemberBean);
        refreshUI();
    }
    public void deleteItem(int position){
        mList.remove(position);
        refreshUI();
    }

    private class ViewHolder {
        TextView group;
        ImageView icon;
        ImageView del;
   }

}
