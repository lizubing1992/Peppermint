package com.app.peppermint.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.app.peppermint.R;
import com.app.peppermint.base.BaseBgActivity;
import com.app.peppermint.ui.adapter.PhotoPagerAdapter;
import java.util.ArrayList;

public class ImageDetailActivity extends BaseBgActivity {

  private ViewPager viewPager;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_image_detail;
  }



  @Override
  protected void initView(Bundle savedInstanceState) {
    super.initView(savedInstanceState);
    viewPager = findViewById(R.id.viewPager);
  }

  @Override
  protected void loadData() {
    super.loadData();
    ArrayList<Integer> list = new ArrayList<>();
    list.add(R.drawable.legend_01);
    list.add(R.drawable.legend_02);
    list.add(R.drawable.legend_03);
    list.add(R.drawable.legend_04);
    list.add(R.drawable.legend_05);
    viewPager.setAdapter(new PhotoPagerAdapter(list));
  }

  @Override
  protected void setListener() {
    super.setListener();
    viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

      }

      @Override
      public void onPageSelected(int position) {

      }

      @Override
      public void onPageScrollStateChanged(int state) {

      }
    });
  }

  @Override
  protected void frontClick() {
    super.frontClick();
    viewPager.setCurrentItem(viewPager.getCurrentItem()-1,true);
  }

  @Override
  protected void backClick() {
    super.backClick();
    finish();
  }


  @Override
  protected void afterClick() {
    super.afterClick();
    viewPager.setCurrentItem(viewPager.getCurrentItem()+1,true);
  }
}
