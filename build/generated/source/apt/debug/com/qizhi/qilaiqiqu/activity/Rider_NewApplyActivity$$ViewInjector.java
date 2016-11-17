// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Rider_NewApplyActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Rider_NewApplyActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558703, "field 'applyRiderBack' and method 'onClick'");
    target.applyRiderBack = finder.castView(view, 2131558703, "field 'applyRiderBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558704, "field 'applyRiderUserName'");
    target.applyRiderUserName = finder.castView(view, 2131558704, "field 'applyRiderUserName'");
    view = finder.findRequiredView(source, 2131558705, "field 'applyRiderDate'");
    target.applyRiderDate = finder.castView(view, 2131558705, "field 'applyRiderDate'");
    view = finder.findRequiredView(source, 2131558707, "field 'txtTime' and method 'onClick'");
    target.txtTime = finder.castView(view, 2131558707, "field 'txtTime'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558706, "field 'layoutTime'");
    target.layoutTime = finder.castView(view, 2131558706, "field 'layoutTime'");
    view = finder.findRequiredView(source, 2131558708, "field 'txtPrices'");
    target.txtPrices = finder.castView(view, 2131558708, "field 'txtPrices'");
    view = finder.findRequiredView(source, 2131558709, "field 'txtTotalPrices'");
    target.txtTotalPrices = finder.castView(view, 2131558709, "field 'txtTotalPrices'");
    view = finder.findRequiredView(source, 2131558710, "field 'edtPhone'");
    target.edtPhone = finder.castView(view, 2131558710, "field 'edtPhone'");
    view = finder.findRequiredView(source, 2131558711, "field 'edtText'");
    target.edtText = finder.castView(view, 2131558711, "field 'edtText'");
    view = finder.findRequiredView(source, 2131558645, "field 'txtExplain'");
    target.txtExplain = finder.castView(view, 2131558645, "field 'txtExplain'");
    view = finder.findRequiredView(source, 2131558712, "field 'applyRiderTotalPrice'");
    target.applyRiderTotalPrice = finder.castView(view, 2131558712, "field 'applyRiderTotalPrice'");
    view = finder.findRequiredView(source, 2131558713, "field 'applyRiderCommit' and method 'onClick'");
    target.applyRiderCommit = finder.castView(view, 2131558713, "field 'applyRiderCommit'");
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
    target.applyRiderBack = null;
    target.applyRiderUserName = null;
    target.applyRiderDate = null;
    target.txtTime = null;
    target.layoutTime = null;
    target.txtPrices = null;
    target.txtTotalPrices = null;
    target.edtPhone = null;
    target.edtText = null;
    target.txtExplain = null;
    target.applyRiderTotalPrice = null;
    target.applyRiderCommit = null;
  }
}
