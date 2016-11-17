// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Rider_NewComemntResultActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Rider_NewComemntResultActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559187, "field 'InformBack' and method 'onClick'");
    target.InformBack = finder.castView(view, 2131559187, "field 'InformBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559188, "field 'InformPhoto'");
    target.InformPhoto = finder.castView(view, 2131559188, "field 'InformPhoto'");
    view = finder.findRequiredView(source, 2131559189, "field 'InformContent'");
    target.InformContent = finder.castView(view, 2131559189, "field 'InformContent'");
    view = finder.findRequiredView(source, 2131559190, "field 'InformMessage'");
    target.InformMessage = finder.castView(view, 2131559190, "field 'InformMessage'");
  }

  @Override public void reset(T target) {
    target.InformBack = null;
    target.InformPhoto = null;
    target.InformContent = null;
    target.InformMessage = null;
  }
}
