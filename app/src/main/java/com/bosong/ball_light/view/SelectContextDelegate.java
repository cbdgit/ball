package com.bosong.ball_light.view;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bosong.ball_light.R;
import com.bosong.ball_light.widget.WheelView;
import com.bosong.framework.view.AppDelegate;

/**
 * Created by mike on 1/13/16.
 */
public class SelectContextDelegate extends AppDelegate {

    private GridView memberListGV;
    private TextView title_left;
    private TextView title_center;
    private TextView title_right;
    private Button sure;
    private Button cancel;
    private EditText name;
    private ImageView mPopWindowImageView;
    private LayoutInflater inflater;
    private AlertDialog alertDialog;
    private View layout;

    @Override
    public int getRootLayoutId(){
        return R.layout.delegate_context;
    }

    @Override
    public void initWidget(){
        super.initWidget();

        title_left = get(R.id.title_left);
        title_center = get(R.id.title_center);
        title_right = get(R.id.title_right);

        memberListGV = (GridView) get(R.id.gridview_context);

    }

    public GridView getGridView(){
        return memberListGV;
    }

    public void setTitleCenter(String title_center) {
        this.title_center.setText(title_center);
    }
    public void setTitleLeft(String title_left) {
        this.title_left.setText(title_left);
    }

    public void setTitleRight(String title_right) {
        this.title_right.setText(title_right);
    }

    public void showAlertDialog(){
        View layout = LayoutInflater.from(getActivity()).inflate(R.layout.delegate_context_alertdialog, null);
        alertDialog = new AlertDialog.Builder(getActivity()).setView(layout).create();

        sure = (Button) layout.findViewById(R.id.sure);
        cancel = (Button) layout.findViewById(R.id.cancel);
        name = (EditText) layout.findViewById(R.id.popwindow_edit_name);
        mPopWindowImageView = (ImageView) layout.findViewById(R.id.popwindow_image);
        alertDialog.show();
    }

    public void disAlertDialog(){
        alertDialog.dismiss();
    }

    public Button getButtonSure(){
        return sure;
    }

    public Button getButtonCancel(){

        return cancel;
    }

    public EditText getName() {
        return name;
    }

    public ImageView getPopWindowImageView() {
        return mPopWindowImageView;
    }

}
