// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Activity_NewApplyActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Activity_NewApplyActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558522, "field 'layoutBack' and method 'onClick'");
    target.layoutBack = finder.castView(view, 2131558522, "field 'layoutBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558523, "field 'edtName'");
    target.edtName = finder.castView(view, 2131558523, "field 'edtName'");
    view = finder.findRequiredView(source, 2131558524, "field 'edtPhone'");
    target.edtPhone = finder.castView(view, 2131558524, "field 'edtPhone'");
    view = finder.findRequiredView(source, 2131558525, "field 'edtNumber'");
    target.edtNumber = finder.castView(view, 2131558525, "field 'edtNumber'");
    view = finder.findRequiredView(source, 2131558526, "field 'txtExplain'");
    target.txtExplain = finder.castView(view, 2131558526, "field 'txtExplain'");
    view = finder.findRequiredView(source, 2131558527, "field 'txtPrices'");
    target.txtPrices = finder.castView(view, 2131558527, "field 'txtPrices'");
    view = finder.findRequiredView(source, 2131558528, "field 'txtCommit' and method 'onClick'");
    target.txtCommit = finder.castView(view, 2131558528, "field 'txtCommit'");
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
    target.edtName = null;
    target.edtPhone = null;
    target.edtNumber = null;
    target.txtExplain = null;
    target.txtPrices = null;
    target.txtCommit = null;
  }
}
