/*
 * All rights Reserved, Designed By 农金圈  2017年08月28日15:48
 */
package com.app.peppermint;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

/**
 * Glide 配置文件
 * @author: lizubing
 */
@GlideModule
public class GlideConfig extends AppGlideModule {

  @Override
  public void applyOptions(Context context, GlideBuilder builder) {
    super.applyOptions(context, builder);
  }

  @Override
  public void registerComponents(Context context, Glide glide, Registry registry) {
    super.registerComponents(context, glide, registry);
  }

}
