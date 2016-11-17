// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class StartActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.StartActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559290, "field 'startImage'");
    target.startImage = finder.castView(view, 2131559290, "field 'startImage'");
  }

  @Override public void reset(T target) {
    target.startImage = null;
  }
}
