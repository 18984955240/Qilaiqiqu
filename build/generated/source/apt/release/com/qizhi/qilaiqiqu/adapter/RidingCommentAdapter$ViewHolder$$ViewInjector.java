// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class RidingCommentAdapter$ViewHolder$$ViewInjector<T extends com.qizhi.qilaiqiqu.adapter.RidingCommentAdapter.ViewHolder> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559486, "field 'portraitImg'");
    target.portraitImg = finder.castView(view, 2131559486, "field 'portraitImg'");
    view = finder.findRequiredView(source, 2131559487, "field 'discusspeoTxt'");
    target.discusspeoTxt = finder.castView(view, 2131559487, "field 'discusspeoTxt'");
    view = finder.findRequiredView(source, 2131559488, "field 'contentTxt'");
    target.contentTxt = finder.castView(view, 2131559488, "field 'contentTxt'");
    view = finder.findRequiredView(source, 2131559495, "field 'discussReturnLayout'");
    target.discussReturnLayout = finder.castView(view, 2131559495, "field 'discussReturnLayout'");
  }

  @Override public void reset(T target) {
    target.portraitImg = null;
    target.discusspeoTxt = null;
    target.contentTxt = null;
    target.discussReturnLayout = null;
  }
}
