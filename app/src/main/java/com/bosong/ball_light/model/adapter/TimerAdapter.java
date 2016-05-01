package com.bosong.ball_light.model.adapter;

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

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.bean.TimerBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Jones on 2016/1/17.
 */
public class TimerAdapter extends BaseAdapter {
	private List<TimerBean> timerList = new ArrayList<TimerBean>();
	private LayoutInflater inflater;
	private Context mContext;

	private enum Mode {
		DEL, NORMAL
	};
	private Mode mMode = Mode.NORMAL;

	public TimerAdapter(Context mContext, List<TimerBean> timerList) {
		inflater = LayoutInflater.from(mContext);
		this.mContext = mContext;
		this.timerList = timerList;
	}

	@Override
	public int getCount() {
		return timerList.size();
	}

	@Override
	public Object getItem(int position) {
		return timerList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final  ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.timer_list_item, null);
			holder.timer_delete_Iv = (ImageView) convertView.findViewById(R.id.timer_delete_Iv);
			holder.timer_open_Iv = (ImageView) convertView.findViewById(R.id.timer_open_Iv);
			holder.timer_setIv = (ImageView) convertView.findViewById(R.id.timer_setIv);
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.timer_openTv = (TextView) convertView.findViewById(R.id.timer_openTv);
			holder.timer_closeTv = (TextView) convertView.findViewById(R.id.timer_closeTv);
			holder.groupTv = (TextView) convertView.findViewById(R.id.groupTv);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
			if (mMode == Mode.NORMAL) {
				holder.timer_delete_Iv.setVisibility(View.GONE);
			} else {
				holder.timer_delete_Iv.setVisibility(View.VISIBLE);

			}
			holder.timer_delete_Iv.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					deleteItem(position);
					refreshUI();
				}
			});

			holder.timer_delete_Iv.setOnKeyListener(new OnKeyListener() {

				@Override
				public boolean onKey(View v, int keyCode, KeyEvent event) {
					if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
						mMode = Mode.NORMAL;
					}
					return false;
				}
			});


		holder.timer_open_Iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (timerList.get(position).getSwitcherStatus() == 1) {
					timerList.get(position).setSwitcherStatus(0);
				} else {
					timerList.get(position).setSwitcherStatus(1);
				}
				refreshUI();
			}
		});

		if (timerList.get(position).getSwitcherStatus()==1){
			holder.timer_open_Iv.setImageResource(R.drawable.timer_open_icon);
		}else{
			holder.timer_open_Iv.setImageResource(R.drawable.close_icon);
		}

		holder.timer_setIv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (timerList.get(position).getLinkOr()==false){
					timerList.get(position).setLinkOr(true);
					holder.timer_setIv.setImageResource(R.drawable.link_off);
				}else{
					timerList.get(position).setLinkOr(false);
					holder.timer_setIv.setImageResource(R.drawable.link_on);
				}
			}
		});

		if (timerList.get(position).getLinkOr()==false){
			holder.timer_setIv.setImageResource(R.drawable.link_off);
		}else{
			holder.timer_setIv.setImageResource(R.drawable.link_on);
		}

		holder.timer_delete_Iv.setImageResource(timerList.get(position).getDeleteId());
		holder.timer_setIv.setImageResource(timerList.get(position).getSetId());

		holder.tvTitle.setText(timerList.get(position).getTimer_titlle());
		holder.timer_openTv.setText(timerList.get(position).getTimer_open());
		holder.timer_closeTv.setText(timerList.get(position).getTimer_close());
		holder.groupTv.setText(timerList.get(position).getTimer_group());

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

	public void addItem(TimerBean timerbean){
		timerList.add(timerbean);
		refreshUI();
	}
	public void deleteItem(int position){
		timerList.remove(position);
		refreshUI();
	}

	private class ViewHolder {
		ImageView timer_delete_Iv;
		ImageView timer_open_Iv;
		ImageView timer_setIv;
		TextView tvTitle;
		TextView timer_openTv;
		TextView timer_closeTv;
		TextView groupTv;
	}
}
