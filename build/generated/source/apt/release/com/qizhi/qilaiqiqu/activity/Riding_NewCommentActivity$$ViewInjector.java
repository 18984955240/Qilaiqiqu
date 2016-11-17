// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Riding_NewCommentActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Riding_NewCommentActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559214, "field 'layoutRidingCommentBack' and method 'onClick'");
    target.layoutRidingCommentBack = finder.castView(view, 2131559214, "field 'layoutRidingCommentBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558561, "field 'imgRidingCommentPhoto'");
    target.imgRidingCommentPhoto = finder.castView(view, 2131558561, "field 'imgRidingCommentPhoto'");
    view = finder.findRequiredView(source, 2131558562, "field 'textCommentmessageactivityMessage'");
    target.textCommentmessageactivityMessage = finder.castView(view, 2131558562, "field 'textCommentmessageactivityMessage'");
    view = finder.findRequiredView(source, 2131559215, "field 'txtRidingCommentContent'");
    target.txtRidingCommentContent = finder.castView(view, 2131559215, "field 'txtRidingCommentContent'");
    view = finder.findRequiredView(source, 2131559216, "field 'listRidingComment'");
    target.listRidingComment = finder.castView(view, 2131559216, "field 'listRidingComment'");
    view = finder.findRequiredView(source, 2131559217, "field 'edtRidingCommentContent'");
    target.edtRidingCommentContent = finder.castView(view, 2131559217, "field 'edtRidingCommentContent'");
    view = finder.findRequiredView(source, 2131559218, "field 'btnRidingCommentComment' and method 'onClick'");
    target.btnRidingCommentComment = finder.castView(view, 2131559218, "field 'btnRidingCommentComment'");
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
    target.layoutRidingCommentBack = null;
    target.imgRidingCommentPhoto = null;
    target.textCommentmessageactivityMessage = null;
    target.txtRidingCommentContent = null;
    target.listRidingComment = null;
    target.edtRidingCommentContent = null;
    target.btnRidingCommentComment = null;
  }
}
