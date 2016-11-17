// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Activity_NewCommentAdapter$TipHolder$$ViewInjector<T extends com.qizhi.qilaiqiqu.adapter.Activity_NewCommentAdapter.TipHolder> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559117, "field 'textTip'");
    target.textTip = finder.castView(view, 2131559117, "field 'textTip'");
  }

  @Override public void reset(T target) {
    target.textTip = null;
  }
}
