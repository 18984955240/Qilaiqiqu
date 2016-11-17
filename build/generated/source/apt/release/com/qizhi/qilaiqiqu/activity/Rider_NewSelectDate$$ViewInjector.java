// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Rider_NewSelectDate$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Rider_NewSelectDate> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558513, "field 'layoutBack' and method 'onClick'");
    target.layoutBack = finder.castView(view, 2131558513, "field 'layoutBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559114, "field 'listView'");
    target.listView = finder.castView(view, 2131559114, "field 'listView'");
    view = finder.findRequiredView(source, 2131558707, "field 'txtTime'");
    target.txtTime = finder.castView(view, 2131558707, "field 'txtTime'");
    view = finder.findRequiredView(source, 2131559116, "field 'txtCommit' and method 'onClick'");
    target.txtCommit = finder.castView(view, 2131559116, "field 'txtCommit'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void reset(T target) {
    target.layoutBack = null;
    target.listView = null;
    target.txtTime = null;
    target.txtCommit = null;
  }
}
