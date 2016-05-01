package com.bosong.ball_light.model.adapter;

import com.bosong.ball_light.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
/**
 * Created by mike on 1/20/16.
 */
public class GroupManageAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;

    public GroupManageAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount(){
        return mList.size();
    }

    @Override
    public Object getItem(int position){
        return mList.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.group_manage_item, null);
            viewHolder.groupName = (TextView) view.findViewById(R.id.text_group_name);
            viewHolder.rename = (TextView) view.findViewById(R.id.text_rename);
            viewHolder.delete = (TextView) view.findViewById(R.id.text_delete);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.groupName.setText(mList.get(position));
        viewHolder.rename.setText(R.string.rename);
        viewHolder.delete.setText(R.string.delete);

        viewHolder.rename.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

            }
        });
        return view;
    }

    class ViewHolder {
        TextView groupName;
        TextView rename;
        TextView delete;
    }
}
