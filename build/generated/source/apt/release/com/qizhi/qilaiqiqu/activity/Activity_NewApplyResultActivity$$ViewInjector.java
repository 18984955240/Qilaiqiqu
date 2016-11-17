// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Activity_NewApplyResultActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Activity_NewApplyResultActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558538, "field 'txtActiveTitle'");
    target.txtActiveTitle = finder.castView(view, 2131558538, "field 'txtActiveTitle'");
    view = finder.findRequiredView(source, 2131558539, "field 'txtPriceExplain'");
    target.txtPriceExplain = finder.castView(view, 2131558539, "field 'txtPriceExplain'");
    view = finder.findRequiredView(source, 2131558540, "field 'txtPrice'");
    target.txtPrice = finder.castView(view, 2131558540, "field 'txtPrice'");
    view = finder.findRequiredView(source, 2131558541, "field 'txtTotal'");
    target.txtTotal = finder.castView(view, 2131558541, "field 'txtTotal'");
    view = finder.findRequiredView(source, 2131558542, "field 'txtUserName'");
    target.txtUserName = finder.castView(view, 2131558542, "field 'txtUserName'");
    view = finder.findRequiredView(source, 2131558543, "field 'txtUserPhone'");
    target.txtUserPhone = finder.castView(view, 2131558543, "field 'txtUserPhone'");
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
  }

  @Override public void reset(T target) {
    target.txtActiveTitle = null;
    target.txtPriceExplain = null;
    target.txtPrice = null;
    target.txtTotal = null;
    target.txtUserName = null;
    target.txtUserPhone = null;
    target.layoutBack = null;
  }
}
