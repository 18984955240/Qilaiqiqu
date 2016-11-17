// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class OrderRefundApplyActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.OrderRefundApplyActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558961, "field 'refundApplyBack' and method 'onClick'");
    target.refundApplyBack = finder.castView(view, 2131558961, "field 'refundApplyBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558962, "field 'refundApplyCommit' and method 'onClick'");
    target.refundApplyCommit = finder.castView(view, 2131558962, "field 'refundApplyCommit'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558963, "field 'refundApplyPhone'");
    target.refundApplyPhone = finder.castView(view, 2131558963, "field 'refundApplyPhone'");
    view = finder.findRequiredView(source, 2131558964, "field 'refundApplyReason'");
    target.refundApplyReason = finder.castView(view, 2131558964, "field 'refundApplyReason'");
    view = finder.findRequiredView(source, 2131558965, "field 'refundExplain'");
    target.refundExplain = finder.castView(view, 2131558965, "field 'refundExplain'");
  }

  @Override public void reset(T target) {
    target.refundApplyBack = null;
    target.refundApplyCommit = null;
    target.refundApplyPhone = null;
    target.refundApplyReason = null;
    target.refundExplain = null;
  }
}
