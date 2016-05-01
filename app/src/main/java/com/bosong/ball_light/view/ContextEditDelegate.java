package com.bosong.ball_light.view;

import com.bosong.ball_light.R;
import com.bosong.framework.view.AppDelegate;

import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by mike on 1/15/16.
 */
public class ContextEditDelegate extends AppDelegate {

    private TextView titleLeft;
    private TextView titleRight;

    private ImageView mContextImage;
    private ImageView mStatusImage;

    private GridView mGridView;
    private ListView mListView;

    @Override
    public int getRootLayoutId(){
        return R.layout.delegate_context_edit;
    }

    @Override
    public void initWidget() {
        super.initWidget();

        titleLeft = get(R.id.title_left);
        titleRight = get(R.id.title_right);

        mContextImage = get(R.id.image_context);
        mStatusImage = get(R.id.image_status);

        mGridView = (GridView) get(R.id.gridview_groups);
        mListView= (ListView) get(R.id.listview);
    }

    public void setTitleLeft(int title_left) {
        this.titleLeft.setText(title_left);
    }

    public void setTitleRight(int title_right) {
        this.titleRight.setText(title_right);
    }

    public ImageView getImageContext() {
        return mContextImage;
    }

    public ImageView getImageStatus() {
        return mStatusImage;
    }

    public GridView getGridView(){
        return mGridView;
    }

    public ListView getListView() {
        return mListView;
    }
}
