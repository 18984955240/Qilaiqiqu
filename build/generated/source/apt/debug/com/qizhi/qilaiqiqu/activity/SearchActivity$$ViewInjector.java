// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class SearchActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.SearchActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559268, "field 'imgBack' and method 'onClick'");
    target.imgBack = finder.castView(view, 2131559268, "field 'imgBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559269, "field 'edtInput'");
    target.edtInput = finder.castView(view, 2131559269, "field 'edtInput'");
    view = finder.findRequiredView(source, 2131559270, "field 'txtSearch' and method 'onClick'");
    target.txtSearch = finder.castView(view, 2131559270, "field 'txtSearch'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559271, "field 'txtQYJ' and method 'onClick'");
    target.txtQYJ = finder.castView(view, 2131559271, "field 'txtQYJ'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559273, "field 'txtHD' and method 'onClick'");
    target.txtHD = finder.castView(view, 2131559273, "field 'txtHD'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559275, "field 'txtPQS' and method 'onClick'");
    target.txtPQS = finder.castView(view, 2131559275, "field 'txtPQS'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559277, "field 'txtRWZ' and method 'onClick'");
    target.txtRWZ = finder.castView(view, 2131559277, "field 'txtRWZ'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131559279, "field 'listList'");
    target.listList = finder.castView(view, 2131559279, "field 'listList'");
  }

  @Override public void reset(T target) {
    target.imgBack = null;
    target.edtInput = null;
    target.txtSearch = null;
    target.txtQYJ = null;
    target.txtHD = null;
    target.txtPQS = null;
    target.txtRWZ = null;
    target.listList = null;
  }
}
