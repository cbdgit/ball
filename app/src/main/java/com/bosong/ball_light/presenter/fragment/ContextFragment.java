package com.bosong.ball_light.presenter.fragment;

/**
 * Created by mike on 1/11/16.
 */
import com.bosong.ball_light.model.adapter.ContextMemberAdapter;
import com.bosong.ball_light.model.bean.ContextMemberBean;
import com.bosong.ball_light.model.IContext;
import com.bosong.ball_light.model.impl.ContextImpl;
import com.bosong.ball_light.presenter.activity.ContextEditActivity;
import com.bosong.ball_light.presenter.activity.MainActivity;
import com.bosong.ball_light.view.ContextDelegate;
import com.bosong.ball_light.R;
import com.bosong.framework.presenter.FragmentPresenter;


import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;


public class ContextFragment extends FragmentPresenter<ContextDelegate> implements View.OnClickListener  {

    private GridView memberListGV;
    private ContextMemberAdapter contextMemberAdapter;
    private List<ContextMemberBean> mList = new LinkedList<ContextMemberBean>();

    private IContext mIContext;

    private Button sure;
    private Button cancel;
    private EditText name;
    private ImageView mPopWindowImageView;

    private int single = 1;
    private static int RESULT_LOAD_IMAGE = 1;
    private String picturePath;

    public static ContextFragment newInstance() {
        ContextFragment contextFragment = new ContextFragment();
        return contextFragment;
    }

    @Override
    protected Class<ContextDelegate> getDelegateClass(){
        return ContextDelegate.class;
    }

    private void initList() {

        /*
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(viewDelegate.getActivity().getResources(), R.drawable.home), "home", true));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(viewDelegate.getActivity().getResources(), R.drawable.outside), "outside", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(viewDelegate.getActivity().getResources(), R.drawable.dinner), "dinner", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(viewDelegate.getActivity().getResources(), R.drawable.theatre), "theatre", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(viewDelegate.getActivity().getResources(), R.drawable.bed), "bed",false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(viewDelegate.getActivity().getResources(), R.drawable.sleep), "sleep", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(viewDelegate.getActivity().getResources(), R.drawable.party), "party", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(viewDelegate.getActivity().getResources(), R.drawable.birthday), "birthday", false));
        mList.add(new ContextMemberBean(BitmapFactory.decodeResource(viewDelegate.getActivity().getResources(), R.drawable.celebrate), "celebrate", false));
        */
    }

    @Override
    protected void bindEventListener(){
        super.bindEventListener();

        mIContext = new ContextImpl(viewDelegate.getActivity());
        //initList();
        mList = mIContext.initContext();
        viewDelegate.setOnClickListener(this, R.id.title_left);
        viewDelegate.setOnClickListener(this, R.id.title_right);
        viewDelegate.setTitleLeft(R.string.title_delete);
        viewDelegate.setTitleRight(R.string.title_new_create);

        contextMemberAdapter = new ContextMemberAdapter(viewDelegate.getActivity(), mList);
        memberListGV = viewDelegate.getGridView();
        memberListGV.setAdapter(contextMemberAdapter);

    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.title_left:
                if (single % 2 == 1){
                    contextMemberAdapter.setMode(false);
                    viewDelegate.setTitleLeft(R.string.title_cancel);
                } else {
                    contextMemberAdapter.setMode(true);
                    viewDelegate.setTitleLeft(R.string.title_delete);
                }
                single++;
                break;
            case R.id.title_right:
                viewDelegate.showAlertDialog();
                viewDelegate.getButtonSure().setOnClickListener(this);
                viewDelegate.getButtonCancel().setOnClickListener(this);
                viewDelegate.getPopWindowImageView().setOnClickListener(this);
                name = viewDelegate.getName();
                break;
            case R.id.popwindow_image:
                Intent i =
                        new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
                break;
            case R.id.sure:

                viewDelegate.disAlertDialog();
                contextMemberAdapter.addItem(new ContextMemberBean(getSmallBitmap(picturePath), name.getText().toString(), false));
                mIContext.insertContext(img(getSmallBitmap(picturePath)), name.getText().toString());
                break;
            case R.id.cancel:

                viewDelegate.disAlertDialog();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE  && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = viewDelegate.getActivity().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            //ImageView imageView = (ImageView) findViewById(R.id.imgView);
            //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            viewDelegate.getPopWindowImageView().setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
    }

    private byte[] img(Bitmap bitmap)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    private int calculateInSampleSize(BitmapFactory.Options options,int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    private Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 480);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

}
