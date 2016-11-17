// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Activity_NewCommentListActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Activity_NewCommentListActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558513, "field 'layoutBack' and method 'onClick'");
    target.layoutBack = finder.castView(view, 2131558513, "field 'layoutBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick();
        }
      });
    view = finder.findRequiredView(source, 2131558556, "field 'listComment'");
    target.listComment = finder.castView(view, 2131558556, "field 'listComment'");
    view = finder.findRequiredView(source, 2131558515, "field 'swipeContainer'");
    target.swipeContainer = finder.castView(view, 2131558515, "field 'swipeContainer'");
  }

  @Override public void reset(T target) {
    target.layoutBack = null;
    target.listComment = null;
    target.swipeContainer = null;
  }
}
