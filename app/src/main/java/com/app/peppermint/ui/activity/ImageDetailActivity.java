package com.app.peppermint.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.app.peppermint.R;
import com.app.peppermint.base.BaseBgActivity;
import com.app.peppermint.entity.PhotoExtendEntity;
import com.app.peppermint.entity.PhotoItemEntity;
import com.app.peppermint.ui.adapter.PhotoPagerAdapter;
import com.app.peppermint.ui.adapter.PhotoPagerTipAdapter;

import java.util.ArrayList;

public class ImageDetailActivity extends BaseBgActivity {

    private ViewPager viewPager, viewPagerTip;
    private PhotoPagerAdapter pagerAdapter;
    private PhotoPagerTipAdapter tipAdapter;
    private boolean viewTipIsShow = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_image_detail;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        viewPager = findViewById(R.id.viewPager);
        viewPagerTip = findViewById(R.id.viewPagerTip);
        frontIV.setSelected(false);
        afterIV.setSelected(true);
        exitIV.setSelected(true);
        backIV.setSelected(true);
    }

    @Override
    protected void loadData() {
        super.loadData();

        ArrayList<PhotoItemEntity> list = new ArrayList<>();
        list.add(new PhotoItemEntity(R.drawable.legend_01));
        //特殊处理第二个 冥王是谁，点击
        PhotoItemEntity photoItemEntity2 = new PhotoItemEntity();
        photoItemEntity2.setUrlId(R.drawable.legend_02);
        photoItemEntity2.setTipId(R.drawable.ic_legend_02_btn);
        ArrayList<PhotoExtendEntity> list2 = new ArrayList<>();
        PhotoExtendEntity extend2 = new PhotoExtendEntity();
        extend2.setUrlId(R.drawable.legend_02_1);
        extend2.setTipId(R.drawable.ic_legend_02_1_btn_back);
        list2.add(extend2);
        photoItemEntity2.setExtend(list2);
        list.add(photoItemEntity2);
        list.add(new PhotoItemEntity(R.drawable.legend_03));
        //阴曹地府
        PhotoItemEntity photoItemEntity4 = new PhotoItemEntity();
        photoItemEntity4.setUrlId(R.drawable.legend_04);
        photoItemEntity4.setTipId(R.drawable.ic_legend_04_btn);
        ArrayList<PhotoExtendEntity> list4 = new ArrayList<>();
        PhotoExtendEntity extend4 = new PhotoExtendEntity();
        extend4.setUrlId(R.drawable.legend_04_1);
        extend4.setTipId(R.drawable.ic_legend_04_1_btn_back);
        list4.add(extend4);
        photoItemEntity4.setExtend(list4);
        list.add(photoItemEntity4);
        //古埃及
        PhotoItemEntity photoItemEntity5 = new PhotoItemEntity();
        photoItemEntity5.setUrlId(R.drawable.legend_05);
        photoItemEntity5.setTipId(R.drawable.ic_legend_05_btn);
        ArrayList<PhotoExtendEntity> list5 = new ArrayList<>();
        PhotoExtendEntity extend5 = new PhotoExtendEntity();
        extend5.setUrlId(R.drawable.legend_05_3);
        extend5.setTipId(R.drawable.ic_legend_05_3_btn_back);
        list5.add(new PhotoExtendEntity(R.drawable.legend_05_1));
        list5.add(new PhotoExtendEntity(R.drawable.legend_05_2));
        list5.add(extend5);
        photoItemEntity5.setExtend(list5);
        list.add(photoItemEntity5);
        pagerAdapter = new PhotoPagerAdapter(list);
        viewPager.setAdapter(pagerAdapter);
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
                processPageSelected(position,pagerAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        pagerAdapter.setImageTipsClick(new PhotoPagerAdapter.ImageTipsClick() {
            @Override
            public void tipClickListener(PhotoItemEntity entity, int position) {
                viewTipIsShow = true;
                viewPagerTip.setVisibility(View.VISIBLE);
                viewPager.setVisibility(View.GONE);
                processExitAndBackEnable(false);
                tipAdapter = new PhotoPagerTipAdapter(entity.getExtend());
                viewPagerTip.setAdapter(tipAdapter);
                if (entity.getExtend().size() > 1) {
                    frontIV.setSelected(false);
                    afterIV.setSelected(true);
                } else {
                    frontIV.setSelected(false);
                    afterIV.setSelected(false);
                }
                tipAdapter.setImageTipsClick(new PhotoPagerTipAdapter.ImageTipsClick() {
                    @Override
                    public void tipClickListener(PhotoExtendEntity entity, int position) {
                        viewTipIsShow = false;
                        viewPagerTip.setVisibility(View.GONE);
                        viewPager.setVisibility(View.VISIBLE);
                        processExitAndBackEnable(true);
                        processPageSelected(viewPager.getCurrentItem(),pagerAdapter.getCount());
                    }
                });
            }
        });
        viewPagerTip.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                processPageSelected(position,tipAdapter.getCount());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void processExitAndBackEnable(boolean enable) {
        exitIV.setClickable(enable);
        backIV.setClickable(enable);
        exitIV.setSelected(enable);
        backIV.setSelected(enable);
    }

    private void processPageSelected(int position,int count) {
        if (position == 0) {
            frontIV.setSelected(false);
            afterIV.setSelected(true);
        } else if (position == count - 1) {
            frontIV.setSelected(true);
            afterIV.setSelected(false);
        } else {
            frontIV.setSelected(true);
            afterIV.setSelected(true);
        }
    }

    @Override
    protected void frontClick() {
        super.frontClick();
        if (!viewTipIsShow) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
        } else {
            viewPagerTip.setCurrentItem(viewPagerTip.getCurrentItem() - 1, true);
        }
    }

    @Override
    protected void backClick() {
        super.backClick();
        finish();
    }


    @Override
    protected void afterClick() {
        super.afterClick();
        if (!viewTipIsShow) {
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1, true);
        } else {
            viewPagerTip.setCurrentItem(viewPagerTip.getCurrentItem() + 1, true);
        }
    }
}
