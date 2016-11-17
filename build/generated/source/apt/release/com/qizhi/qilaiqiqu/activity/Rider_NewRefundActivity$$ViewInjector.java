// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Rider_NewRefundActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Rider_NewRefundActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558944, "field 'refundBack' and method 'onClick'");
    target.refundBack = finder.castView(view, 2131558944, "field 'refundBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558945, "field 'refundName'");
    target.refundName = finder.castView(view, 2131558945, "field 'refundName'");
    view = finder.findRequiredView(source, 2131558946, "field 'rideTime'");
    target.rideTime = finder.castView(view, 2131558946, "field 'rideTime'");
    view = finder.findRequiredView(source, 2131558947, "field 'refundTime'");
    target.refundTime = finder.castView(view, 2131558947, "field 'refundTime'");
    view = finder.findRequiredView(source, 2131558948, "field 'refundOrder'");
    target.refundOrder = finder.castView(view, 2131558948, "field 'refundOrder'");
    view = finder.findRequiredView(source, 2131558949, "field 'ridePrice'");
    target.ridePrice = finder.castView(view, 2131558949, "field 'ridePrice'");
    view = finder.findRequiredView(source, 2131558950, "field 'refundPrice'");
    target.refundPrice = finder.castView(view, 2131558950, "field 'refundPrice'");
    view = finder.findRequiredView(source, 2131558951, "field 'ridePhone'");
    target.ridePhone = finder.castView(view, 2131558951, "field 'ridePhone'");
    view = finder.findRequiredView(source, 2131558952, "field 'refundPhone'");
    target.refundPhone = finder.castView(view, 2131558952, "field 'refundPhone'");
    view = finder.findRequiredView(source, 2131558953, "field 'rideReason'");
    target.rideReason = finder.castView(view, 2131558953, "field 'rideReason'");
    view = finder.findRequiredView(source, 2131558954, "field 'refundReason'");
    target.refundReason = finder.castView(view, 2131558954, "field 'refundReason'");
    view = finder.findRequiredView(source, 2131558955, "field 'riderExplain'");
    target.riderExplain = finder.castView(view, 2131558955, "field 'riderExplain'");
    view = finder.findRequiredView(source, 2131558956, "field 'refundExplain'");
    target.refundExplain = finder.castView(view, 2131558956, "field 'refundExplain'");
    view = finder.findRequiredView(source, 2131558958, "field 'refundAgree' and method 'onClick'");
    target.refundAgree = finder.castView(view, 2131558958, "field 'refundAgree'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558959, "field 'refundDecline' and method 'onClick'");
    target.refundDecline = finder.castView(view, 2131558959, "field 'refundDecline'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558957, "field 'disposalNormal'");
    target.disposalNormal = finder.castView(view, 2131558957, "field 'disposalNormal'");
    view = finder.findRequiredView(source, 2131558960, "field 'refundCheckOrder' and method 'onClick'");
    target.refundCheckOrder = finder.castView(view, 2131558960, "field 'refundCheckOrder'");
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
    target.refundBack = null;
    target.refundName = null;
    target.rideTime = null;
    target.refundTime = null;
    target.refundOrder = null;
    target.ridePrice = null;
    target.refundPrice = null;
    target.ridePhone = null;
    target.refundPhone = null;
    target.rideReason = null;
    target.refundReason = null;
    target.riderExplain = null;
    target.refundExplain = null;
    target.refundAgree = null;
    target.refundDecline = null;
    target.disposalNormal = null;
    target.refundCheckOrder = null;
  }
}
