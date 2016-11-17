// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class RiderOrderActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.RiderOrderActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559191, "field 'riderOrderBack' and method 'onClick'");
    target.riderOrderBack = finder.castView(view, 2131559191, "field 'riderOrderBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559192, "field 'riderOrderUserName'");
    target.riderOrderUserName = finder.castView(view, 2131559192, "field 'riderOrderUserName'");
    view = finder.findRequiredView(source, 2131559193, "field 'riderOrderTime'");
    target.riderOrderTime = finder.castView(view, 2131559193, "field 'riderOrderTime'");
    view = finder.findRequiredView(source, 2131559194, "field 'riderOrderMoney'");
    target.riderOrderMoney = finder.castView(view, 2131559194, "field 'riderOrderMoney'");
    view = finder.findRequiredView(source, 2131559195, "field 'riderOrderSumMoney'");
    target.riderOrderSumMoney = finder.castView(view, 2131559195, "field 'riderOrderSumMoney'");
    view = finder.findRequiredView(source, 2131559196, "field 'riderOrderPhone'");
    target.riderOrderPhone = finder.castView(view, 2131559196, "field 'riderOrderPhone'");
    view = finder.findRequiredView(source, 2131559197, "field 'riderOrderMessage'");
    target.riderOrderMessage = finder.castView(view, 2131559197, "field 'riderOrderMessage'");
    view = finder.findRequiredView(source, 2131559198, "field 'riderOrderButton' and method 'onClick'");
    target.riderOrderButton = finder.castView(view, 2131559198, "field 'riderOrderButton'");
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
    target.riderOrderBack = null;
    target.riderOrderUserName = null;
    target.riderOrderTime = null;
    target.riderOrderMoney = null;
    target.riderOrderSumMoney = null;
    target.riderOrderPhone = null;
    target.riderOrderMessage = null;
    target.riderOrderButton = null;
  }
}
