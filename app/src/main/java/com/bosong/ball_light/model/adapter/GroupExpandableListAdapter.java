package com.bosong.ball_light.model.adapter;

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.bean.GroupBean;
import com.bosong.ball_light.model.bean.GroupMemberBean;
import com.bosong.ball_light.model.bluetooth.BleWrapper;
import com.bosong.ball_light.model.bluetooth.BleWrapperUiCallbacks;
import com.bosong.ball_light.model.bluetooth.BlutoothOpera;
import com.bosong.ball_light.presenter.activity.DegreeActivity;
import com.bosong.ball_light.presenter.activity.UIColorAndWhiteActivity;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by mike on 1/17/16.
 */
public class GroupExpandableListAdapter extends BaseExpandableListAdapter {


    private Activity mActivity;
    private List<GroupBean> mGroupList;
    private List<List<GroupMemberBean>> mChildList;
    private GroupMemberBean groupMemberBean;
    private Intent intent;
    private Bundle bundle;

    private boolean visible = false;
    private int single = 1;

    private ArrayList<BluetoothDevice> mDevices;
    private ArrayList<byte[]> mRecords;
    private ArrayList<Integer> mRSSIs;

    private BlutoothOpera mBleWrapper = null;


    public void addDevice(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if(mDevices.contains(device) == false) {
            mDevices.add(device);
            mRSSIs.add(rssi);
            mRecords.add(scanRecord);
            mChildList.get(mGroupList.size() - 1).add(new GroupMemberBean(device.getName(), device.getAddress(), true, false, false));
        }
    }

    public BluetoothDevice getDevice(int index) {
        return mDevices.get(index);
    }

    public int getRssi(int index) {
        return mRSSIs.get(index);
    }

    public void clearList() {
        mDevices.clear();
        mRSSIs.clear();
        mRecords.clear();
    }

    public BlutoothOpera getBleWrapper() {
        return mBleWrapper;
    }

    public GroupExpandableListAdapter(Activity mActivity,
                                      List<GroupBean> mGroupList,
                                      List<List<GroupMemberBean>> mChildList ) {
        mDevices  = new ArrayList<BluetoothDevice>();
        this.mActivity = mActivity;
        this.mGroupList = mGroupList;
        this.mChildList = mChildList;

        //this.mChildList.add(new ArrayList<GroupMemberBean>());

        mDevices  = new ArrayList<BluetoothDevice>();
        mRecords = new ArrayList<byte[]>();
        mRSSIs = new ArrayList<Integer>();
        //mInflater = par.getLayoutInflater();

        mBleWrapper = new BlutoothOpera(mActivity, new BleWrapperUiCallbacks.Null() {
            @Override
            public void uiDeviceFound(final BluetoothDevice device, final int rssi, final byte[] record) {
                handleFoundDevice(device, rssi, record);
            }
        });
    }

    public Object getChild(int groupPosition, int childPosition) {
        return mChildList.get(groupPosition).get(childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public int getChildrenCount(int groupPosition) {
        if (groupPosition > mChildList.size()) {
            return 0;
        } else {
            return mChildList.get(groupPosition).size();
        }
    }

    public View getChildView(int groupPosition,
                             int childPosition,
                             boolean isLastChild,
                             View convertView,
                             ViewGroup parent) {

        final GroupMemberBean mGroupMemberBean
                = mChildList.get(groupPosition).get(childPosition);
        View view = LayoutInflater.from(mActivity).inflate(R.layout.groupmember_item, null);
        final ImageView imageSelect = (ImageView) view.findViewById(R.id.item_select);
        ImageView imageType = (ImageView) view.findViewById(R.id.item_type);
        final ImageView imageLink = (ImageView) view.findViewById(R.id.item_link);
        final ImageView imageOnOff = (ImageView) view.findViewById(R.id.item_onoff);
        ImageView imageMore = (ImageView) view.findViewById(R.id.item_more);
        TextView textName = (TextView) view.findViewById(R.id.item_name);

        if (visible == false) {
            imageSelect.setVisibility(View.GONE);
        } else {
            imageSelect.setVisibility(View.VISIBLE);
            imageSelect.setImageResource(R.drawable.select_n);
        }
        imageSelect.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (single % 2 == 1) {
                    imageSelect.setImageResource(R.drawable.select_y);
                } else {
                    imageSelect.setImageResource(R.drawable.select_n);
                }
                single++;
            }
        });
        if (mGroupMemberBean.getType() == false) {
            imageType.setImageResource(R.drawable.light_degree);
        } else {
            imageType.setImageResource(R.drawable.light_color);
        }

        if (mGroupMemberBean.getLinkOr() == false) {
            imageLink.setImageResource(R.drawable.link_off);
        } else {
            imageLink.setImageResource(R.drawable.link_on);
        }
        imageLink.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGroupMemberBean.getLinkOr() == false) {
                    imageLink.setImageResource(R.drawable.link_off);
                    mBleWrapper.diconnect();
                    mGroupMemberBean.setLinkOr(true);
                } else {
                    imageLink.setImageResource(R.drawable.link_on);
                    mBleWrapper.connect(mGroupMemberBean.getAddr());
                    mGroupMemberBean.setLinkOr(false);
                }
            }
        });

        if (mGroupMemberBean.getOnOrOff() == false) {
            imageOnOff.setImageResource(R.drawable.switcher_white);
        } else {
            imageOnOff.setImageResource(R.drawable.switcher_green);
        }
        imageOnOff.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGroupMemberBean.getOnOrOff() == false) {
                    imageOnOff.setImageResource(R.drawable.switcher_white);
                    mGroupMemberBean.setOnOrOff(true);
                } else {
                    imageOnOff.setImageResource(R.drawable.switcher_green);
                     mGroupMemberBean.setOnOrOff(false);
                }
            }
        });

        imageMore.setImageResource(R.drawable.more);
        imageMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mGroupMemberBean.getType() == true) {
                    intent = new Intent(mActivity, UIColorAndWhiteActivity.class);
                    bundle = new Bundle();
                    bundle.putString("name", mGroupMemberBean.getName());
                    bundle.putString("addr", mGroupMemberBean.getAddr());
                    intent.putExtras(bundle);
                    mActivity.startActivity(intent);
                } else {
                    intent = new Intent(mActivity, DegreeActivity.class);
                    bundle = new Bundle();
                    bundle.putString("name", mGroupMemberBean.getName());
                    intent.putExtras(bundle);
                    mActivity.startActivity(intent);
                }
            }
        });

        textName.setText(mGroupMemberBean.getName());

        view.setTag(R.id.item_type, groupPosition);
        view.setTag(R.id.item_link, childPosition);
        view.setTag(R.id.item_name, true);

        return view;
        /*
        GroupMemberBean mGroupMemberBean
                = mChildList.get(groupPosition).get(childPosition);
        ViewHolder viewHolder;
        View view;
        if(convertView == null) {
            view = LayoutInflater.from(mActivity).inflate(R.layout.groupmember_item, null);
            viewHolder = new ViewHolder();
            viewHolder.imageType = (ImageView) view.findViewById(R.id.item_type);
            viewHolder.imageLink = (ImageView) view.findViewById(R.id.item_link);
            viewHolder.imageOnOff = (ImageView) view.findViewById(R.id.item_onoff);
            viewHolder.imageMore = (ImageView) view.findViewById(R.id.item_more);
            viewHolder.textName = (TextView) view.findViewById(R.id.item_name);
            if(mGroupMemberBean.getType() == false){
                viewHolder.imageType.setImageResource(R.drawable.light_degree);
            } else {
                viewHolder.imageType.setImageResource(R.drawable.light_color);
            }
            if(mGroupMemberBean.getLinkOr() == false){
                viewHolder.imageLink.setImageResource(R.drawable.link_off);
            } else {
                viewHolder.imageLink.setImageResource(R.drawable.link_on);
            }
            if(mGroupMemberBean.getOnOrOff() == false){
                viewHolder.imageOnOff.setImageResource(R.drawable.switcher_white);
            } else {
                viewHolder.imageOnOff.setImageResource(R.drawable.switcher_green);
            }
            viewHolder.textName.setText(mGroupMemberBean.getName());
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        return  view;
        */
    }

    // group method stub
    public Object getGroup(int groupPosition) {
        return mGroupList.get(groupPosition);
    }

    public  int  getGroupCount() {
        return mGroupList.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition,
                             boolean isExpanded,
                             View convertView,
                             ViewGroup parent) {

        View view = LayoutInflater.from(mActivity).inflate(R.layout.group_item, null);
        ImageView imageArrow = (ImageView) view.findViewById(R.id.item_arrow);
        final ImageView imageStatus = (ImageView) view.findViewById(R.id.item_status);
        TextView  textName = (TextView) view.findViewById(R.id.item_name);

        final GroupBean mGroupBean = mGroupList.get(groupPosition);
        if (groupPosition >= mGroupList.size() - 1) {
            imageStatus.setImageResource(R.drawable.fresh);
            imageStatus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mBleWrapper.startScanning();
                }
            });
            textName.setText(mGroupBean.getName());
            //textName.setText("未分组");

            if (isExpanded == false) {
                imageArrow.setImageResource(R.drawable.arrow_right);
            } else {
                imageArrow.setImageResource(R.drawable.arrow_bottom);
            }

            view.setTag(R.id.item_name, false);
        } else {
            //final GroupBean mGroupBean = mGroupList.get(groupPosition);
            if (isExpanded == false) {
                imageArrow.setImageResource(R.drawable.arrow_right);
            } else {
                imageArrow.setImageResource(R.drawable.arrow_bottom);
            }

            if (mGroupBean.getStatus() == false) {
                imageStatus.setImageResource(R.drawable.switcher_white);
            } else {
                imageStatus.setImageResource(R.drawable.switcher_green);
            }

            imageStatus.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mGroupBean.getStatus() == false) {
                        imageStatus.setImageResource(R.drawable.switcher_white);
                        mGroupBean.setStatus(true);
                    } else {
                        imageStatus.setImageResource(R.drawable.switcher_green);
                        mGroupBean.setStatus(false);
                    }
                }
            });
            textName.setText(mGroupBean.getName());
            view.setTag(R.id.item_name, true);
        }

        view.setTag(R.id.item_type, groupPosition);
        view.setTag(R.id.item_link, -1);

        return view;
    }


    public boolean hasStableIds() {
        return false;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setSelectVisible(boolean visible) {
        this.visible = visible;
    }

    class ViewHolder {
        ImageView imageSelect;
        ImageView imageType;
        ImageView imageLink;
        ImageView imageOnOff;
        ImageView imageMore;
        TextView textName;
    }

    /* add device to the current list of devices */
    private void handleFoundDevice(final BluetoothDevice device,
                                   final int rssi,
                                   final byte[] scanRecord)
    {
        // adding to the UI have to happen in UI thread
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                addDevice(device, rssi, scanRecord);
                notifyDataSetChanged();
            }
        });
    }
}

