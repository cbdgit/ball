package com.bosong.ball_light.view;

import com.bosong.framework.view.AppDelegate;
import com.bosong.ball_light.R;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by mike on 1/11/16.
 */
public class AllDevidesDelegate extends AppDelegate {

    private TextView titleLeft;
    private TextView titleRight;

    private Button groupManage;
    private Button groupDelete;
    private Button groupSure;
    private Button childSure;
    private Button childCancel;

    private ExpandableListView mExpandableListView;

    private AlertDialog alertDialog1;
    private AlertDialog alertDialog2;
    private Spinner spinner;
    private EditText editRename1;
    private EditText editRename2;

    @Override
    public int getRootLayoutId(){
        return R.layout.delegate_alldevides;
    }

    @Override
    public void initWidget(){
        super.initWidget();

        titleLeft = get(R.id.title_left);
        titleRight = get(R.id.title_right);

        groupManage = get(R.id.button_groupmanage);

        mExpandableListView = get(R.id.expandablelistview_group);

        View layout1 = LayoutInflater.from(getActivity()).inflate(R.layout.delegate_alldevides_alertdialog_child, null);
        alertDialog1 = new AlertDialog.Builder(getActivity()).setView(layout1).create();

        editRename1 = (EditText) layout1.findViewById(R.id.edit_rename);
        spinner = (Spinner) layout1.findViewById(R.id.spinner_adjust);
        childSure = (Button) layout1.findViewById(R.id.button_child_sure);
        childCancel = (Button) layout1.findViewById(R.id.button_child_cancel);

        View layout2 = LayoutInflater.from(getActivity()).inflate(R.layout.delegate_alldevides_alertdialog_group, null);
        alertDialog2 = new AlertDialog.Builder(getActivity()).setView(layout2).create();

        editRename2 = (EditText) layout2.findViewById(R.id.edit_rename);
        groupDelete = (Button) layout2.findViewById(R.id.button_delete);
        groupSure = (Button) layout2.findViewById(R.id.button_sure);

    }

    public void setTitleLeft(int title_left) {
        this.titleLeft.setText(title_left);
    }

    public void setTitleRight(int title_right) {
        this.titleRight.setText(title_right);
    }

    public void setEditRenameChild(String edit_rename) {
        this.editRename1.setText(edit_rename, TextView.BufferType.EDITABLE);
    }

    public void setEditRenameGroup(String edit_rename) {
        this.editRename2.setText(edit_rename, TextView.BufferType.EDITABLE);
    }

    public EditText getEditTextChild() {
        return editRename1;
    }

    public EditText getEditTextGroup() {
        return editRename2;
    }
    public Button getButtonGroupDelete(){
        return groupDelete;
    }

    public Button getButtonGroupSure() {
        return groupSure;
    }

    public Button getButtonChildSure() {
        return childSure;
    }

    public Button getButtonChildCancel() {
        return childCancel;
    }

    public Button getGroupManage(){
        return groupManage;
    }

    public ExpandableListView getExpandableListView(){
        return mExpandableListView;
    }

    public Spinner getSpinner(){
        return spinner;
    }

    public void showAlertDialogChild(){
        alertDialog1.show();
    }

    public void disAlertDialogChild(){
        alertDialog1.dismiss();
    }

    public void showAlertDialogGroup(){
        alertDialog2.show();
    }

    public void disAlertDialogGroup(){
        alertDialog2.dismiss();
    }
}
