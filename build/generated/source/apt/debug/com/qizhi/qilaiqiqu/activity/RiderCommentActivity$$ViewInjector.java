// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class RiderCommentActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.RiderCommentActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559121, "field 'layoutRiderCommentActivityBack' and method 'onClick'");
    target.layoutRiderCommentActivityBack = finder.castView(view, 2131559121, "field 'layoutRiderCommentActivityBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559122, "field 'btnRiderCommentActivityComfirm' and method 'onClick'");
    target.btnRiderCommentActivityComfirm = finder.castView(view, 2131559122, "field 'btnRiderCommentActivityComfirm'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559123, "field 'txtRiderCommentActivityReturnName'");
    target.txtRiderCommentActivityReturnName = finder.castView(view, 2131559123, "field 'txtRiderCommentActivityReturnName'");
    view = finder.findRequiredView(source, 2131559124, "field 'txtRiderCommentActivityReturnTime'");
    target.txtRiderCommentActivityReturnTime = finder.castView(view, 2131559124, "field 'txtRiderCommentActivityReturnTime'");
    view = finder.findRequiredView(source, 2131559126, "field 'txtRiderCommentActivityReturnAward'");
    target.txtRiderCommentActivityReturnAward = finder.castView(view, 2131559126, "field 'txtRiderCommentActivityReturnAward'");
    view = finder.findRequiredView(source, 2131559127, "field 'imgRiderCommentActivityReturnAward'");
    target.imgRiderCommentActivityReturnAward = finder.castView(view, 2131559127, "field 'imgRiderCommentActivityReturnAward'");
    view = finder.findRequiredView(source, 2131559125, "field 'riderCommentActivityFrameLayout' and method 'onClick'");
    target.riderCommentActivityFrameLayout = finder.castView(view, 2131559125, "field 'riderCommentActivityFrameLayout'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559128, "field 'edtRiderCommentActivityMyReturn'");
    target.edtRiderCommentActivityMyReturn = finder.castView(view, 2131559128, "field 'edtRiderCommentActivityMyReturn'");
    view = finder.findRequiredView(source, 2131559129, "field 'txtRiderCommentActivityReturnNum'");
    target.txtRiderCommentActivityReturnNum = finder.castView(view, 2131559129, "field 'txtRiderCommentActivityReturnNum'");
  }

  @Override public void reset(T target) {
    target.layoutRiderCommentActivityBack = null;
    target.btnRiderCommentActivityComfirm = null;
    target.txtRiderCommentActivityReturnName = null;
    target.txtRiderCommentActivityReturnTime = null;
    target.txtRiderCommentActivityReturnAward = null;
    target.imgRiderCommentActivityReturnAward = null;
    target.riderCommentActivityFrameLayout = null;
    target.edtRiderCommentActivityMyReturn = null;
    target.txtRiderCommentActivityReturnNum = null;
  }
}
