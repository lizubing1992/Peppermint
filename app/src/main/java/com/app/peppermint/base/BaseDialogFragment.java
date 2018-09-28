package com.app.peppermint.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 类的作用
 *
 * @author: lizubing
 */
public class BaseDialogFragment extends DialogFragment {
  private View rootView;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    rootView = inflater.inflate(getLayout(), null);
    initView(rootView);
    loadData();
    setListener();
    return rootView;
  }

  protected void loadData() {

  }


  protected void setListener() {

  }

  protected void initView(View rootView) {

  }

  protected int getLayout() {
    return 0;
  }

}
