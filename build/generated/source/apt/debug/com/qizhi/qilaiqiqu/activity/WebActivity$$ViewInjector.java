// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class WebActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.WebActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559291, "field 'webView'");
    target.webView = finder.castView(view, 2131559291, "field 'webView'");
    view = finder.findRequiredView(source, 2131558700, "field 'imgAdvertisementActivityBack' and method 'onClick'");
    target.imgAdvertisementActivityBack = finder.castView(view, 2131558700, "field 'imgAdvertisementActivityBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
  }

  @Override public void reset(T target) {
    target.webView = null;
    target.imgAdvertisementActivityBack = null;
  }
}
