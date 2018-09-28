package com.app.peppermint.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import com.app.peppermint.R;
import java.util.List;

/**
 * 类的作用
 *
 * @author: lizubing
 */
public class PhotoPagerAdapter extends PagerAdapter {

  private LayoutInflater mInflater;
  private List<Integer> dataList;

  public PhotoPagerAdapter(List<Integer> dataList) {
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
  public View instantiateItem(ViewGroup container, int position) {
    View view =  LayoutInflater.from(container.getContext()).inflate(R.layout.layout_picture_viewer, null);
    ImageView imageView = (ImageView) view.findViewById(R.id.imageIV);
    imageView.setImageResource(dataList.get(position));
    container.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
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

}
