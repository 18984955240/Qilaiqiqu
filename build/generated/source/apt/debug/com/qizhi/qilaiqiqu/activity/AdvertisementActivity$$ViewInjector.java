// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class AdvertisementActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.AdvertisementActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558695, "field 'startImage'");
    target.startImage = finder.castView(view, 2131558695, "field 'startImage'");
    view = finder.findRequiredView(source, 2131558696, "field 'pass' and method 'onClick'");
    target.pass = finder.castView(view, 2131558696, "field 'pass'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558697, "field 'showDetails' and method 'onClick'");
    target.showDetails = finder.castView(view, 2131558697, "field 'showDetails'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558694, "field 'startAppLayout'");
    target.startAppLayout = finder.castView(view, 2131558694, "field 'startAppLayout'");
    view = finder.findRequiredView(source, 2131558699, "field 'webView'");
    target.webView = finder.castView(view, 2131558699, "field 'webView'");
    view = finder.findRequiredView(source, 2131558700, "field 'back' and method 'onClick'");
    target.back = finder.castView(view, 2131558700, "field 'back'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558698, "field 'webviewLyaout'");
    target.webviewLyaout = finder.castView(view, 2131558698, "field 'webviewLyaout'");
  }

  @Override public void reset(T target) {
    target.startImage = null;
    target.pass = null;
    target.showDetails = null;
    target.startAppLayout = null;
    target.webView = null;
    target.back = null;
    target.webviewLyaout = null;
  }
}
