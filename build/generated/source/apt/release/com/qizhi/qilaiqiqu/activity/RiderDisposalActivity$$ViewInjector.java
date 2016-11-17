// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class RiderDisposalActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.RiderDisposalActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559171, "field 'disposalBack' and method 'onClick'");
    target.disposalBack = finder.castView(view, 2131559171, "field 'disposalBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559172, "field 'disposalUserName'");
    target.disposalUserName = finder.castView(view, 2131559172, "field 'disposalUserName'");
    view = finder.findRequiredView(source, 2131559173, "field 'disposalTime'");
    target.disposalTime = finder.castView(view, 2131559173, "field 'disposalTime'");
    view = finder.findRequiredView(source, 2131559175, "field 'disposalPhone'");
    target.disposalPhone = finder.castView(view, 2131559175, "field 'disposalPhone'");
    view = finder.findRequiredView(source, 2131559176, "field 'disposalMessage'");
    target.disposalMessage = finder.castView(view, 2131559176, "field 'disposalMessage'");
    view = finder.findRequiredView(source, 2131559178, "field 'disposalAgree' and method 'onClick'");
    target.disposalAgree = finder.castView(view, 2131559178, "field 'disposalAgree'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559179, "field 'disposalDecline' and method 'onClick'");
    target.disposalDecline = finder.castView(view, 2131559179, "field 'disposalDecline'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559180, "field 'disposalCheckOrder' and method 'onClick'");
    target.disposalCheckOrder = finder.castView(view, 2131559180, "field 'disposalCheckOrder'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559174, "field 'disposalTime1'");
    target.disposalTime1 = finder.castView(view, 2131559174, "field 'disposalTime1'");
    view = finder.findRequiredView(source, 2131559177, "field 'disposal'");
    target.disposal = finder.castView(view, 2131559177, "field 'disposal'");
  }

  @Override public void reset(T target) {
    target.disposalBack = null;
    target.disposalUserName = null;
    target.disposalTime = null;
    target.disposalPhone = null;
    target.disposalMessage = null;
    target.disposalAgree = null;
    target.disposalDecline = null;
    target.disposalCheckOrder = null;
    target.disposalTime1 = null;
    target.disposal = null;
  }
}
