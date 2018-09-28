package com.app.peppermint.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.app.peppermint.R;
import com.app.peppermint.entity.PhotoExtendEntity;
import com.app.peppermint.entity.PhotoItemEntity;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * 类的作用
 *
 * @author: lizubing
 */
public class PhotoPagerTipAdapter extends PagerAdapter {

  private LayoutInflater mInflater;
  private List<PhotoExtendEntity> dataList;
  private ImageTipsClick imageTipsClick;

  public void setImageTipsClick(ImageTipsClick imageTipsClick) {
    this.imageTipsClick = imageTipsClick;
  }

  public PhotoPagerTipAdapter(List<PhotoExtendEntity> dataList) {
    this.dataList = dataList;
  }

  @Override
  public int getCount() {
    return dataList.size();
  }


  public LayoutInflater getLayoutInflater(Context context) {
    if (mInflater == null) {
      mInflater = (LayoutInflater) context
          .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    return mInflater;
  }

  @Override
  public View instantiateItem(ViewGroup container, final int position) {
    View view =  LayoutInflater.from(container.getContext()).inflate(R.layout.layout_picture_viewer, null);
    ImageView imageView = (ImageView) view.findViewById(R.id.imageIV);
    ImageView imageTipIV = (ImageView) view.findViewById(R.id.imageTipIV);
    final PhotoExtendEntity entity = dataList.get(position);
    imageView.setImageResource(entity.getUrlId());
    if(entity.getTipId() != null && entity.getTipId() != 0){
      Glide.with(container.getContext()).load(entity.getTipId()).into(imageTipIV);
    }
    container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    imageTipIV.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if(imageTipsClick != null){
            imageTipsClick.tipClickListener(entity,position);
        }
      }
    });
    return view;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  public interface ImageTipsClick{
    void tipClickListener(PhotoExtendEntity entity, int position);
  }

}
