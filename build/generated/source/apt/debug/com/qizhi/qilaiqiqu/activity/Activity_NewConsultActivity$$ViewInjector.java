// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Activity_NewConsultActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Activity_NewConsultActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558557, "field 'layouttBack' and method 'onClick'");
    target.layouttBack = finder.castView(view, 2131558557, "field 'layouttBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558558, "field 'listView'");
    target.listView = finder.castView(view, 2131558558, "field 'listView'");
    view = finder.findRequiredView(source, 2131558559, "field 'edtContent'");
    target.edtContent = finder.castView(view, 2131558559, "field 'edtContent'");
    view = finder.findRequiredView(source, 2131558560, "field 'txtCommit' and method 'onClick'");
    target.txtCommit = finder.castView(view, 2131558560, "field 'txtCommit'");
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
    target.layouttBack = null;
    target.listView = null;
    target.edtContent = null;
    target.txtCommit = null;
  }
}
