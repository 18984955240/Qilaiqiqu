// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class OrderActivityAdapter$ViewHolder$$ViewInjector<T extends com.qizhi.qilaiqiqu.adapter.OrderActivityAdapter.ViewHolder> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559657, "field 'txtName'");
    target.txtName = finder.castView(view, 2131559657, "field 'txtName'");
    view = finder.findRequiredView(source, 2131559658, "field 'txtState'");
    target.txtState = finder.castView(view, 2131559658, "field 'txtState'");
  }

  @Override public void reset(T target) {
    target.txtName = null;
    target.txtState = null;
  }
}
