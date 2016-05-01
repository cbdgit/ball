package com.bosong.ball_light.model.adapter;

import com.bosong.ball_light.R;
import com.bosong.ball_light.presenter.activity.ContextEditActivity;
import com.bosong.ball_light.model.bean.ContextMemberBean;
import com.bosong.ball_light.model.IContext;
import com.bosong.ball_light.model.impl.ContextImpl;
import com.bosong.ball_light.util.LogUtil;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ContextMemberAdapter extends BaseAdapter {
	private List<ContextMemberBean> mList = new LinkedList<ContextMemberBean>();
	private LayoutInflater inflater;
	private Context mContext;
	private String selectValue;
	private IContext mIContext;

	private enum Mode {
		DEL, NORMAL
	};

	private Mode mMode = Mode.NORMAL;

	public ContextMemberAdapter(Context mContext, List<ContextMemberBean> mList) {
		inflater = LayoutInflater.from(mContext);
		this.mContext = mContext;
		this.mList = mList;
		mIContext = new ContextImpl(mContext);
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		if (position >= mList.size())
			position = mList.size() - 1;
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if(position==0){
			//setSelectValue(mList.get(position).getName());
		}
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.grid_item_context, null);
			holder.icon = (ImageView) convertView.findViewById(R.id.item_icon);
			holder.name = (TextView) convertView.findViewById(R.id.item_name);
			holder.del = (ImageView) convertView.findViewById(R.id.item_del);
			holder.online = (ImageView) convertView
					.findViewById(R.id.item_online_state);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		if (position == mList.size()) {
			holder.icon.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			});
			holder.online.setVisibility(View.GONE);
			holder.del.setVisibility(View.GONE);
		} else if (position == mList.size() + 1) {
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
			holder.icon.setImageBitmap(mList.get(position).getBitmap());
			holder.name.setText(mList.get(position).getName());
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
						//点击后取值并赋值给selectValue属性
					//	setSelectValue(mList.get(position).getName());
						//Toast.makeText(mContext, mList.get(position).getName(),Toast.LENGTH_LONG).show();
					}
					refreshUI();
				}
			});
			holder.icon.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					Intent intent = new Intent(mContext, ContextEditActivity.class);
					holder.icon.setDrawingCacheEnabled(Boolean.TRUE);
					intent.putExtra("CONTEXT", holder.icon.getDrawingCache());
					mContext.startActivity(intent);
					return true;
				}
			});
			int resOnlineId = 0;
			if (mList.get(position).isOnline()) {
				resOnlineId = R.drawable.member_online;
				//选中后取值并赋值给selectValue属性
				setSelectValue(mList.get(position).getName());
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
					mIContext.deleteContext(mList.get(position).getName());
					refreshUI();
				}
			});

			holder.del.setOnKeyListener(new OnKeyListener() {

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
    public void setSelectValue(String value){
		this.selectValue=value;
	}

	public String getSelectValue(){
		return selectValue;
	}

	public void setMode(boolean b) {
		if (b == false){
			mMode = Mode.DEL;
		} else if (b == true) {
			mMode = Mode.NORMAL;
		}
		refreshUI();
	}

	public void addItem(ContextMemberBean contextMemberBean){
		mList.add(contextMemberBean);
		refreshUI();
	}
	public void deleteItem(int position){
		mList.remove(position);
		refreshUI();
	}

	private class ViewHolder {
		ImageView icon;
		TextView name;
		ImageView del;
		ImageView online;
	}
}
