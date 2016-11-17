// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class CharacterCommentActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.CharacterCommentActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558767, "field 'layoutBack' and method 'onClick'");
    target.layoutBack = finder.castView(view, 2131558767, "field 'layoutBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558768, "field 'layoutTitle'");
    target.layoutTitle = finder.castView(view, 2131558768, "field 'layoutTitle'");
    view = finder.findRequiredView(source, 2131558769, "field 'layoutTime'");
    target.layoutTime = finder.castView(view, 2131558769, "field 'layoutTime'");
    view = finder.findRequiredView(source, 2131558770, "field 'layoutBrowse'");
    target.layoutBrowse = finder.castView(view, 2131558770, "field 'layoutBrowse'");
    view = finder.findRequiredView(source, 2131558771, "field 'layoutComment'");
    target.layoutComment = finder.castView(view, 2131558771, "field 'layoutComment'");
    view = finder.findRequiredView(source, 2131558773, "field 'layoutContent'");
    target.layoutContent = finder.castView(view, 2131558773, "field 'layoutContent'");
    view = finder.findRequiredView(source, 2131558774, "field 'layoutSubmit' and method 'onClick'");
    target.layoutSubmit = finder.castView(view, 2131558774, "field 'layoutSubmit'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558772, "field 'layoutList'");
    target.layoutList = finder.castView(view, 2131558772, "field 'layoutList'");
  }

  @Override public void reset(T target) {
    target.layoutBack = null;
    target.layoutTitle = null;
    target.layoutTime = null;
    target.layoutBrowse = null;
    target.layoutComment = null;
    target.layoutContent = null;
    target.layoutSubmit = null;
    target.layoutList = null;
  }
}
