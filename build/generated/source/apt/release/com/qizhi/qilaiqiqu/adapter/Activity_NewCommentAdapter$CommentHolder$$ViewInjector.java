// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Activity_NewCommentAdapter$CommentHolder$$ViewInjector<T extends com.qizhi.qilaiqiqu.adapter.Activity_NewCommentAdapter.CommentHolder> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558554, "field 'txtComment'");
    target.txtComment = finder.castView(view, 2131558554, "field 'txtComment'");
    view = finder.findRequiredView(source, 2131559701, "field 'txtReward'");
    target.txtReward = finder.castView(view, 2131559701, "field 'txtReward'");
    view = finder.findRequiredView(source, 2131559702, "field 'txtCommenter'");
    target.txtCommenter = finder.castView(view, 2131559702, "field 'txtCommenter'");
    view = finder.findRequiredView(source, 2131559703, "field 'txtCommentTime'");
    target.txtCommentTime = finder.castView(view, 2131559703, "field 'txtCommentTime'");
  }

  @Override public void reset(T target) {
    target.txtComment = null;
    target.txtReward = null;
    target.txtCommenter = null;
    target.txtCommentTime = null;
  }
}
