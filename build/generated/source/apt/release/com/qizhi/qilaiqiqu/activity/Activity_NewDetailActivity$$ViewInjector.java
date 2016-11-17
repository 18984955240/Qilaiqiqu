// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Activity_NewDetailActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Activity_NewDetailActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558579, "field 'layoutlBack' and method 'onClick'");
    target.layoutlBack = finder.castView(view, 2131558579, "field 'layoutlBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558580, "field 'imgShare' and method 'onClick'");
    target.imgShare = finder.castView(view, 2131558580, "field 'imgShare'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558581, "field 'txtRevamp'");
    target.txtRevamp = finder.castView(view, 2131558581, "field 'txtRevamp'");
    view = finder.findRequiredView(source, 2131558582, "field 'imgCllection'");
    target.imgCllection = finder.castView(view, 2131558582, "field 'imgCllection'");
    view = finder.findRequiredView(source, 2131558583, "field 'txtDelete'");
    target.txtDelete = finder.castView(view, 2131558583, "field 'txtDelete'");
    view = finder.findRequiredView(source, 2131558584, "field 'web'");
    target.web = finder.castView(view, 2131558584, "field 'web'");
    view = finder.findRequiredView(source, 2131558585, "field 'layoutConsult' and method 'onClick'");
    target.layoutConsult = finder.castView(view, 2131558585, "field 'layoutConsult'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558586, "field 'txtPrice'");
    target.txtPrice = finder.castView(view, 2131558586, "field 'txtPrice'");
    view = finder.findRequiredView(source, 2131558587, "field 'txtButton' and method 'onClick'");
    target.txtButton = finder.castView(view, 2131558587, "field 'txtButton'");
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
    target.layoutlBack = null;
    target.imgShare = null;
    target.txtRevamp = null;
    target.imgCllection = null;
    target.txtDelete = null;
    target.web = null;
    target.layoutConsult = null;
    target.txtPrice = null;
    target.txtButton = null;
  }
}
