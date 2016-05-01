package com.bosong.ball_light.presenter.fragment;

import com.bosong.ball_light.R;
import com.bosong.ball_light.model.adapter.GroupExpandableListAdapter;
import com.bosong.ball_light.model.bean.GroupBean;
import com.bosong.ball_light.model.bean.GroupMemberBean;
import com.bosong.ball_light.model.bluetooth.BleWrapper;
import com.bosong.ball_light.model.bluetooth.BleWrapperUiCallbacks;
import com.bosong.ball_light.model.bluetooth.BlutoothOpera;
import com.bosong.ball_light.model.impl.GroupImpl;
import com.bosong.ball_light.model.IGroup;
import com.bosong.ball_light.presenter.activity.GroupManageActivity;
import com.bosong.ball_light.view.AllDevidesDelegate;

import com.bosong.framework.presenter.FragmentPresenter;

import android.content.Intent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by mike on 1/11/16.
 */
public class AllDevidesFragment extends FragmentPresenter<AllDevidesDelegate> implements View.OnClickListener {

    //private ExpandableListView mExpandableListView;
    private GroupExpandableListAdapter mGroupExpandableListAdapter;
    private ExpandableListView mExpandableListView;

    private List<GroupBean>  mGroupList;
    private List<List<GroupMemberBean>> mChildList;
    private List<String> mListGroupName;

    private Map mMemberMap = new HashMap();

    private IGroup mIGroup;

    private Spinner spinner;
    private ArrayAdapter<String> mArrayAdapter;

    private EditText mEditTextChild;
    private EditText mEditTextGroup;

    private int groupPosition;
    private int childPosition;
    private int single = 1;


    private static final long SCANNING_TIMEOUT = 5 * 1000; /* 5 seconds */
    private static final int ENABLE_BT_REQUEST_ID = 1;

    private boolean mScanning = false;
    private Handler mHandler = new Handler();
    //private DeviceListAdapter mDevicesListAdapter = null;
    private BlutoothOpera mBleWrapper = null;


    public static AllDevidesFragment newInstance() {
        AllDevidesFragment allDevidesFragment = new AllDevidesFragment();
        return allDevidesFragment;
    }

    @Override
    protected Class<AllDevidesDelegate> getDelegateClass(){
        return AllDevidesDelegate.class;
    }

    @Override
    protected void bindEventListener(){
        super.bindEventListener();

        viewDelegate.setOnClickListener(this, R.id.title_left);
        viewDelegate.setOnClickListener(this, R.id.title_right);
        viewDelegate.setTitleLeft(R.string.title_select);
        viewDelegate.setTitleRight(R.string.title_setting);
        viewDelegate.setOnClickListener(this, R.id.button_groupmanage);
        viewDelegate.getButtonGroupDelete().setOnClickListener(this);
        viewDelegate.getButtonGroupSure().setOnClickListener(this);
        viewDelegate.getButtonChildSure().setOnClickListener(this);
        viewDelegate.getButtonChildCancel().setOnClickListener(this);

        mEditTextChild = viewDelegate.getEditTextChild();
        mEditTextGroup = viewDelegate.getEditTextGroup();

        mIGroup = new GroupImpl(viewDelegate.getActivity());

        //mIGroup.addLight(new GroupMemberBean("色温灯1", false, false, false), 0);
        //mIGroup.addLight(new GroupMemberBean("色彩灯1", true, false, false), 0);
        //mIGroup.addLight(new GroupMemberBean("色温灯2", false, false, false), 0);
        //mIGroup.addLight(new GroupMemberBean("色彩灯2", true, false, false), 0);

        mGroupList = new ArrayList<GroupBean>();
        mChildList = new ArrayList<List<GroupMemberBean>>();
        mGroupList = mIGroup.initGroup();
        mChildList = mIGroup.initLight();

        mGroupExpandableListAdapter =
                new GroupExpandableListAdapter(getActivity(), mGroupList, mChildList);
        mExpandableListView = viewDelegate.getExpandableListView();
        mExpandableListView.setAdapter(mGroupExpandableListAdapter);

        /* ExpandableListView Click Listener (Backup)
        mExpandableListView.setOnChildClickListener(new OnChildClickListener(){
            @Override
            public boolean onChildClick(ExpandableListView parent,
                                        View v,
                                        int groupPosition,
                                        int childPosition,
                                        long id){
                viewDelegate.toast("Child");
                //viewDelegate.toast(parent + " - " + " -  " + v + " - " + groupPosition + " - " + childPosition);
                return false;
            }
        });
        */
        mExpandableListView.setOnItemLongClickListener(new OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                groupPosition = (int) view.getTag(R.id.item_type);
                boolean tag = (boolean) view.getTag(R.id.item_name);
                childPosition = (int) view.getTag(R.id.item_link);
                if (childPosition == -1) {
                    if (tag != false ) {
                        viewDelegate.showAlertDialogGroup();
                        viewDelegate.setEditRenameGroup(mGroupList.get(groupPosition).getName());
                    }
                    return false;
                } else {
                    viewDelegate.showAlertDialogChild();
                    viewDelegate.setEditRenameChild(mChildList.get(groupPosition).get(childPosition).getName());
                    spinner.setSelection(groupPosition);
                    return false;
                }
            }
        });

        mListGroupName = new ArrayList<String>();
        for(int i = 0; i< mGroupList.size(); i++){
            mListGroupName.add(mGroupList.get(i).getName());
        }
        mArrayAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,mListGroupName);
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = viewDelegate.getSpinner();
        spinner.setAdapter(mArrayAdapter);

        //添加事件Spinner事件监听
        spinner.setOnItemSelectedListener(new OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                viewDelegate.toast("spinner");
                mIGroup.uptLightPosition(new GroupMemberBean(mChildList.get(groupPosition).get(childPosition).getName(), null, false, false, false), position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        mBleWrapper = mGroupExpandableListAdapter.getBleWrapper();

        // check if we have BT and BLE on board
        if(mBleWrapper.checkBleHardwareAvailable() == false) {
            bleMissing();
        }


        // on every Resume check if BT is enabled (user could turn it off while app was in background etc.)
        if(mBleWrapper.isBtEnabled() == false) {
            // BT is not turned on - ask user to make it enabled
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, ENABLE_BT_REQUEST_ID);
            // see onActivityResult to check what is the status of our request
        }

        // initialize BleWrapper object
        mBleWrapper.initialize();

        //mDevicesListAdapter = new DeviceListAdapter(this);
        //setListAdapter(mDevicesListAdapter);

        // Automatically start scanning for devices
        mScanning = true;
        // remember to add timeout for scanning to not run it forever and drain the battery
        addScanningTimeout();
        mBleWrapper.startScanning();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.title_left:
                if (single % 2 == 1){
                    //contextMemberAdapter.setMode(false);
                    viewDelegate.setTitleLeft(R.string.title_cancel);
                    mGroupExpandableListAdapter.setSelectVisible(true);
                    mGroupExpandableListAdapter.notifyDataSetChanged();
                } else {
                    //contextMemberAdapter.setMode(true);
                    viewDelegate.setTitleLeft(R.string.title_select);
                    mGroupExpandableListAdapter.setSelectVisible(false);
                    mGroupExpandableListAdapter.notifyDataSetChanged();
                }
                single++;
                break;
            case R.id.title_right:
                viewDelegate.toast("点了" + v.getId());
                break;
            case R.id.button_groupmanage:
                GroupBean mGroupBeanNew = new GroupBean("新分组", false);
                mIGroup.addGroup(mGroupBeanNew);
                GroupMemberBean beanNewA = new GroupMemberBean("RGBW灯New", null, true, false, false);
                GroupMemberBean beanNewB = new GroupMemberBean("色温灯New", null, false, false, false);
                mIGroup.addLight(beanNewA, mIGroup.getGroupCount() - 1);
                mIGroup.addLight(beanNewB, mIGroup.getGroupCount() - 1);

                mGroupList = mIGroup.initGroup();
                mChildList = mIGroup.initLight();
                mGroupExpandableListAdapter.notifyDataSetChanged();
                //Intent intent = new Intent(getActivity(), GroupManageActivity.class);
                //intent.putStringArrayListExtra("groupname", (ArrayList<String>)mListGroupName);
                //startActivity(intent);
                break;
            case R.id.button_delete:
                //mGroupList.remove(mGroupList.get(groupPosition));
                mIGroup.delGroup(mGroupList.get(groupPosition));
                mIGroup.delGroupLight(groupPosition);
                mGroupList = mIGroup.initGroup();
                mChildList = mIGroup.initLight();
                mGroupExpandableListAdapter.notifyDataSetChanged();
                viewDelegate.disAlertDialogGroup();
                break;
            case R.id.button_sure:
                mIGroup.uptGroup(mGroupList.get(groupPosition), new GroupBean(mEditTextGroup.getText().toString(), false));
                mGroupExpandableListAdapter.notifyDataSetChanged();
                viewDelegate.disAlertDialogGroup();
                break;
            case R.id.button_child_sure:
                mIGroup.uptLight(mChildList.get(groupPosition).get(childPosition), new GroupMemberBean(mEditTextChild.getText().toString(), null, false, false, false));
                mChildList = mIGroup.initLight();
                mGroupExpandableListAdapter.notifyDataSetChanged();
                viewDelegate.disAlertDialogChild();
                break;
            case R.id.button_child_cancel:
                //mGroupExpandableListAdapter.notifyDataSetChanged();
                viewDelegate.disAlertDialogChild();
                break;
        }
    }

    public interface CallBack {
        void onSuccess(List<GroupBean> mGroupList, List<List<GroupMemberBean>> mChildList);
        void onFail(Throwable error);
    }

    /* make sure that potential scanning will take no longer
	 * than <SCANNING_TIMEOUT> seconds from now on */
    private void addScanningTimeout() {
        Runnable timeout = new Runnable() {
            @Override
            public void run() {
                if(mBleWrapper == null) return;
                mScanning = false;
                mBleWrapper.stopScanning();
                //invalidateOptionsMenu();
            }
        };
        mHandler.postDelayed(timeout, SCANNING_TIMEOUT);
    }

    private void btDisabled() {
        viewDelegate.toast("Sorry, BT has to be turned ON for us to work!");
        //finish();
    }

    private void bleMissing() {
        viewDelegate.toast("BLE Hardware is required but not available!");
        //finish();
    }
}