// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class CharacterCommentAdapter$ViewHolder$$ViewInjector<T extends com.qizhi.qilaiqiqu.adapter.CharacterCommentAdapter.ViewHolder> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559486, "field 'imgPhoto'");
    target.imgPhoto = finder.castView(view, 2131559486, "field 'imgPhoto'");
    view = finder.findRequiredView(source, 2131559487, "field 'txtDiscusspeo'");
    target.txtDiscusspeo = finder.castView(view, 2131559487, "field 'txtDiscusspeo'");
    view = finder.findRequiredView(source, 2131559488, "field 'txtContent'");
    target.txtContent = finder.castView(view, 2131559488, "field 'txtContent'");
    view = finder.findRequiredView(source, 2131559489, "field 'layoutAuthorComment'");
    target.layoutAuthorComment = finder.castView(view, 2131559489, "field 'layoutAuthorComment'");
    view = finder.findRequiredView(source, 2131559490, "field 'txtAuthorComment'");
    target.txtAuthorComment = finder.castView(view, 2131559490, "field 'txtAuthorComment'");
  }

  @Override public void reset(T target) {
    target.imgPhoto = null;
    target.txtDiscusspeo = null;
    target.txtContent = null;
    target.layoutAuthorComment = null;
    target.txtAuthorComment = null;
  }
}
