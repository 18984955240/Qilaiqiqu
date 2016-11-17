// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Rider_NewOrderActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Rider_NewOrderActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558966, "field 'orderBack' and method 'onClick'");
    target.orderBack = finder.castView(view, 2131558966, "field 'orderBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558967, "field 'orderUserName'");
    target.orderUserName = finder.castView(view, 2131558967, "field 'orderUserName'");
    view = finder.findRequiredView(source, 2131558968, "field 'orderTime'");
    target.orderTime = finder.castView(view, 2131558968, "field 'orderTime'");
    view = finder.findRequiredView(source, 2131558969, "field 'orderAgree'");
    target.orderAgree = finder.castView(view, 2131558969, "field 'orderAgree'");
    view = finder.findRequiredView(source, 2131558970, "field 'orderMoney'");
    target.orderMoney = finder.castView(view, 2131558970, "field 'orderMoney'");
    view = finder.findRequiredView(source, 2131558971, "field 'orderSumMoney'");
    target.orderSumMoney = finder.castView(view, 2131558971, "field 'orderSumMoney'");
    view = finder.findRequiredView(source, 2131558973, "field 'orderPhone'");
    target.orderPhone = finder.castView(view, 2131558973, "field 'orderPhone'");
    view = finder.findRequiredView(source, 2131558975, "field 'textView'");
    target.textView = finder.castView(view, 2131558975, "field 'textView'");
    view = finder.findRequiredView(source, 2131558977, "field 'orderAliPay' and method 'onClick'");
    target.orderAliPay = finder.castView(view, 2131558977, "field 'orderAliPay'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558978, "field 'orderWxPay' and method 'onClick'");
    target.orderWxPay = finder.castView(view, 2131558978, "field 'orderWxPay'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558600, "field 'orderAgreement' and method 'onClick'");
    target.orderAgreement = finder.castView(view, 2131558600, "field 'orderAgreement'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558601, "field 'orderAgreementBtn' and method 'onClick'");
    target.orderAgreementBtn = finder.castView(view, 2131558601, "field 'orderAgreementBtn'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558979, "field 'orderButton' and method 'onClick'");
    target.orderButton = finder.castView(view, 2131558979, "field 'orderButton'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558980, "field 'orderApplication' and method 'onClick'");
    target.orderApplication = finder.castView(view, 2131558980, "field 'orderApplication'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558974, "field 'payLayout'");
    target.payLayout = finder.castView(view, 2131558974, "field 'payLayout'");
    view = finder.findRequiredView(source, 2131558602, "field 'orderExplain'");
    target.orderExplain = finder.castView(view, 2131558602, "field 'orderExplain'");
  }

  @Override public void reset(T target) {
    target.orderBack = null;
    target.orderUserName = null;
    target.orderTime = null;
    target.orderAgree = null;
    target.orderMoney = null;
    target.orderSumMoney = null;
    target.orderPhone = null;
    target.textView = null;
    target.orderAliPay = null;
    target.orderWxPay = null;
    target.orderAgreement = null;
    target.orderAgreementBtn = null;
    target.orderButton = null;
    target.orderApplication = null;
    target.payLayout = null;
    target.orderExplain = null;
  }
}
