// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Riding_NewReleaseActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Riding_NewReleaseActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558606, "field 'ridingIist'");
    target.ridingIist = finder.castView(view, 2131558606, "field 'ridingIist'");
    view = finder.findRequiredView(source, 2131558609, "field 'btnAdd' and method 'onClick'");
    target.btnAdd = finder.castView(view, 2131558609, "field 'btnAdd'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558612, "field 'btnPreview' and method 'onClick'");
    target.btnPreview = finder.castView(view, 2131558612, "field 'btnPreview'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558614, "field 'btnRelease' and method 'onClick'");
    target.btnRelease = finder.castView(view, 2131558614, "field 'btnRelease'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558615, "field 'btnKeep' and method 'onClick'");
    target.btnKeep = finder.castView(view, 2131558615, "field 'btnKeep'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558607, "field 'imgBack'");
    target.imgBack = finder.castView(view, 2131558607, "field 'imgBack'");
    view = finder.findRequiredView(source, 2131558608, "field 'imgReChoose'");
    target.imgReChoose = finder.castView(view, 2131558608, "field 'imgReChoose'");
    view = finder.findRequiredView(source, 2131558611, "field 'layoutRelease'");
    target.layoutRelease = finder.castView(view, 2131558611, "field 'layoutRelease'");
    view = finder.findRequiredView(source, 2131558610, "field 'layoutPreview'");
    target.layoutPreview = finder.castView(view, 2131558610, "field 'layoutPreview'");
    view = finder.findRequiredView(source, 2131558613, "field 'layoutKeep'");
    target.layoutKeep = finder.castView(view, 2131558613, "field 'layoutKeep'");
  }

  @Override public void reset(T target) {
    target.ridingIist = null;
    target.btnAdd = null;
    target.btnPreview = null;
    target.btnRelease = null;
    target.btnKeep = null;
    target.imgBack = null;
    target.imgReChoose = null;
    target.layoutRelease = null;
    target.layoutPreview = null;
    target.layoutKeep = null;
  }
}
