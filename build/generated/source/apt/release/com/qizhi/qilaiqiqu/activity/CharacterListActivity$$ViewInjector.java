// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class CharacterListActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.CharacterListActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558779, "field 'layoutBack' and method 'onClick'");
    target.layoutBack = finder.castView(view, 2131558779, "field 'layoutBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558780, "field 'listList'");
    target.listList = finder.castView(view, 2131558780, "field 'listList'");
    view = finder.findRequiredView(source, 2131558515, "field 'swipeLayout'");
    target.swipeLayout = finder.castView(view, 2131558515, "field 'swipeLayout'");
  }

  @Override public void reset(T target) {
    target.layoutBack = null;
    target.listList = null;
    target.swipeLayout = null;
  }
}
