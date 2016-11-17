// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Activity_NewOrderActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Activity_NewOrderActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558588, "field 'layoutBack' and method 'onClick'");
    target.layoutBack = finder.castView(view, 2131558588, "field 'layoutBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558589, "field 'txtTitle'");
    target.txtTitle = finder.castView(view, 2131558589, "field 'txtTitle'");
    view = finder.findRequiredView(source, 2131558590, "field 'txtActiveTitle'");
    target.txtActiveTitle = finder.castView(view, 2131558590, "field 'txtActiveTitle'");
    view = finder.findRequiredView(source, 2131558591, "field 'txtPriceExplain'");
    target.txtPriceExplain = finder.castView(view, 2131558591, "field 'txtPriceExplain'");
    view = finder.findRequiredView(source, 2131558592, "field 'txtPrice'");
    target.txtPrice = finder.castView(view, 2131558592, "field 'txtPrice'");
    view = finder.findRequiredView(source, 2131558593, "field 'txtTotal'");
    target.txtTotal = finder.castView(view, 2131558593, "field 'txtTotal'");
    view = finder.findRequiredView(source, 2131558596, "field 'imgZhifubao'");
    target.imgZhifubao = finder.castView(view, 2131558596, "field 'imgZhifubao'");
    view = finder.findRequiredView(source, 2131558595, "field 'layoutZhifubao' and method 'onClick'");
    target.layoutZhifubao = finder.castView(view, 2131558595, "field 'layoutZhifubao'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558598, "field 'imgWeixin'");
    target.imgWeixin = finder.castView(view, 2131558598, "field 'imgWeixin'");
    view = finder.findRequiredView(source, 2131558597, "field 'layoutWeixin' and method 'onClick'");
    target.layoutWeixin = finder.castView(view, 2131558597, "field 'layoutWeixin'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558600, "field 'orderAgreement'");
    target.orderAgreement = finder.castView(view, 2131558600, "field 'orderAgreement'");
    view = finder.findRequiredView(source, 2131558601, "field 'orderAgreementBtn'");
    target.orderAgreementBtn = finder.castView(view, 2131558601, "field 'orderAgreementBtn'");
    view = finder.findRequiredView(source, 2131558602, "field 'orderExplain'");
    target.orderExplain = finder.castView(view, 2131558602, "field 'orderExplain'");
    view = finder.findRequiredView(source, 2131558594, "field 'layoutPay'");
    target.layoutPay = finder.castView(view, 2131558594, "field 'layoutPay'");
    view = finder.findRequiredView(source, 2131558603, "field 'btnOrder' and method 'onClick'");
    target.btnOrder = finder.castView(view, 2131558603, "field 'btnOrder'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558604, "field 'txtOrder' and method 'onClick'");
    target.txtOrder = finder.castView(view, 2131558604, "field 'txtOrder'");
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
    target.txtTitle = null;
    target.txtActiveTitle = null;
    target.txtPriceExplain = null;
    target.txtPrice = null;
    target.txtTotal = null;
    target.imgZhifubao = null;
    target.layoutZhifubao = null;
    target.imgWeixin = null;
    target.layoutWeixin = null;
    target.orderAgreement = null;
    target.orderAgreementBtn = null;
    target.orderExplain = null;
    target.layoutPay = null;
    target.btnOrder = null;
    target.txtOrder = null;
  }
}
