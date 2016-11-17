// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class OrderFragmentAdapter$ViewHolder$$ViewInjector<T extends com.qizhi.qilaiqiqu.adapter.OrderFragmentAdapter.ViewHolder> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559659, "field 'txtOrderName'");
    target.txtOrderName = finder.castView(view, 2131559659, "field 'txtOrderName'");
    view = finder.findRequiredView(source, 2131559660, "field 'txtOrderTime'");
    target.txtOrderTime = finder.castView(view, 2131559660, "field 'txtOrderTime'");
    view = finder.findRequiredView(source, 2131559661, "field 'txtOrderCondition'");
    target.txtOrderCondition = finder.castView(view, 2131559661, "field 'txtOrderCondition'");
  }

  @Override public void reset(T target) {
    target.txtOrderName = null;
    target.txtOrderTime = null;
    target.txtOrderCondition = null;
  }
}
