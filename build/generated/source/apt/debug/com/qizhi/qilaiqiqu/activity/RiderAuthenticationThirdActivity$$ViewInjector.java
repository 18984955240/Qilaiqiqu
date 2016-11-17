// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class RiderAuthenticationThirdActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.RiderAuthenticationThirdActivity> implements Injector<T> {
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
    view = finder.findRequiredView(source, 2131559118, "field 'edtName'");
    target.edtName = finder.castView(view, 2131559118, "field 'edtName'");
    view = finder.findRequiredView(source, 2131558710, "field 'edtPhone'");
    target.edtPhone = finder.castView(view, 2131558710, "field 'edtPhone'");
    view = finder.findRequiredView(source, 2131559119, "field 'edtWeChat'");
    target.edtWeChat = finder.castView(view, 2131559119, "field 'edtWeChat'");
    view = finder.findRequiredView(source, 2131559120, "field 'edtZhifubao'");
    target.edtZhifubao = finder.castView(view, 2131559120, "field 'edtZhifubao'");
    view = finder.findRequiredView(source, 2131559115, "field 'btnCommit' and method 'onClick'");
    target.btnCommit = finder.castView(view, 2131559115, "field 'btnCommit'");
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
    target.edtWeChat = null;
    target.edtZhifubao = null;
    target.btnCommit = null;
  }
}
