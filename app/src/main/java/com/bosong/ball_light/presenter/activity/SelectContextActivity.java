package com.bosong.ball_light.presenter.activity;



import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


import com.bosong.ball_light.R;
import com.bosong.ball_light.model.adapter.ContextMemberAdapter;
import com.bosong.ball_light.model.bean.ContextMemberBean;

import com.bosong.ball_light.view.SelectContextDelegate;

import com.bosong.framework.presenter.ActivityPresenter;

import java.util.LinkedList;
import java.util.List;

public class SelectContextActivity extends ActivityPresenter<SelectContextDelegate> implements View.OnClickListener{
    private GridView memberListGV;
    private ContextMemberAdapter contextMemberAdapter;
    private List<ContextMemberBean> mList = new LinkedList<ContextMemberBean>();
    private EditText name;
    private int single = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    private String picturePath;
    Intent intent=null;
   @Override
   protected Class<SelectContextDelegate> getDelegateClass(){
       return SelectContextDelegate.class;
   }
    @Override
    protected void bindEventListener(){
        super.bindEventListener();
        intent=new Intent();
        initList();
        viewDelegate.setOnClickListener(this, R.id.title_left);
        viewDelegate.setOnClickListener(this, R.id.title_right);
        viewDelegate.setTitleLeft("返回");
        viewDelegate.setTitleCenter("情景选择");
        viewDelegate.setTitleRight("");
        contextMemberAdapter = new ContextMemberAdapter(this, mList);
        memberListGV = viewDelegate.getGridView();
        memberListGV.setAdapter(contextMemberAdapter);

    }

    private void initList() {
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(this.getResources(), R.drawable.home), "home", true));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(this.getResources(), R.drawable.outside), "outside", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(this.getResources(), R.drawable.dinner), "dinner", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(this.getResources(), R.drawable.theatre), "theatre", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(this.getResources(), R.drawable.bed), "bed", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(this.getResources(), R.drawable.sleep), "sleep", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(this.getResources(), R.drawable.party), "party", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(this.getResources(), R.drawable.birthday), "birthday", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(this.getResources(), R.drawable.celebrate), "celebrate", false));
    }
    @Override
    public void onBackPressed() {
        intent.putExtra("qingjin", contextMemberAdapter.getSelectValue());
        setResult(RESULT_OK, intent);
      // 调用setResult()方法必须在finish()之前
        super.onBackPressed();
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.title_left:

                intent.putExtra("qingjin", contextMemberAdapter.getSelectValue());
                setResult(RESULT_OK, intent);
                finish();
               // Toast.makeText(this, contextMemberAdapter.getSelectValue(), Toast.LENGTH_LONG).show();

                break;

        }

    }
}
