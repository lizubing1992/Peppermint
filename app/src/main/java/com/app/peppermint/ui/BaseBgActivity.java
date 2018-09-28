package com.app.peppermint.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.app.peppermint.R;
import com.app.peppermint.base.BaseActivity;

public class BaseBgActivity extends BaseActivity {

  protected ImageView backIV;
  protected ImageView frontIV;
  protected ImageView afterIV;
  protected ImageView exitIV;

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  @Override
  protected void initView(Bundle savedInstanceState) {
    super.initView(savedInstanceState);
    backIV = (ImageView) findViewById(R.id.backIV);
    frontIV = (ImageView) findViewById(R.id.frontIV);
    afterIV = (ImageView) findViewById(R.id.afterIV);
    exitIV = (ImageView) findViewById(R.id.exitIV);
  }

  @Override
  protected void setListener() {
    super.setListener();
    backIV.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        backClick();
      }
    });

    frontIV.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        frontClick();
      }
    });

    afterIV.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        afterClick();
      }
    });

    exitIV.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        exitClick();
      }
    });
  }

  protected void frontClick() {

  }

  protected void afterClick() {

  }

  protected void exitClick() {

  }

  protected void backClick() {

  }
}
