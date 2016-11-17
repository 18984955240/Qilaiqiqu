// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Activity_NewCommentInformActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Activity_NewCommentInformActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558513, "field 'layoutBack' and method 'onClick'");
    target.layoutBack = finder.castView(view, 2131558513, "field 'layoutBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558553, "field 'imgPhoto'");
    target.imgPhoto = finder.castView(view, 2131558553, "field 'imgPhoto'");
    view = finder.findRequiredView(source, 2131558514, "field 'txtTitle'");
    target.txtTitle = finder.castView(view, 2131558514, "field 'txtTitle'");
    view = finder.findRequiredView(source, 2131558554, "field 'txtComment'");
    target.txtComment = finder.castView(view, 2131558554, "field 'txtComment'");
    view = finder.findRequiredView(source, 2131558555, "field 'txtSeeAll' and method 'onClick'");
    target.txtSeeAll = finder.castView(view, 2131558555, "field 'txtSeeAll'");
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
    target.imgPhoto = null;
    target.txtTitle = null;
    target.txtComment = null;
    target.txtSeeAll = null;
  }
}
