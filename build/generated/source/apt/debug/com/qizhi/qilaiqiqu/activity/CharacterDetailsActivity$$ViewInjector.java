// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class CharacterDetailsActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.CharacterDetailsActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558775, "field 'layoutCharacterback' and method 'onClick'");
    target.layoutCharacterback = finder.castView(view, 2131558775, "field 'layoutCharacterback'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558776, "field 'characterWeb'");
    target.characterWeb = finder.castView(view, 2131558776, "field 'characterWeb'");
    view = finder.findRequiredView(source, 2131558778, "field 'comment'");
    target.comment = finder.castView(view, 2131558778, "field 'comment'");
    view = finder.findRequiredView(source, 2131558777, "field 'content' and method 'onClick'");
    target.content = finder.castView(view, 2131558777, "field 'content'");
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
    target.layoutCharacterback = null;
    target.characterWeb = null;
    target.comment = null;
    target.content = null;
  }
}
