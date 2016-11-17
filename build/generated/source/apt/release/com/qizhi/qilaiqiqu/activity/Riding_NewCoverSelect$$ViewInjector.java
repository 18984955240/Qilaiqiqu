// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Riding_NewCoverSelect$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Riding_NewCoverSelect> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558529, "field 'imgCover'");
    target.imgCover = finder.castView(view, 2131558529, "field 'imgCover'");
    view = finder.findRequiredView(source, 2131558530, "field 'imgBack' and method 'onClick'");
    target.imgBack = finder.castView(view, 2131558530, "field 'imgBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558531, "field 'ImgReChoose' and method 'onClick'");
    target.ImgReChoose = finder.castView(view, 2131558531, "field 'ImgReChoose'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558532, "field 'imgPhoto'");
    target.imgPhoto = finder.castView(view, 2131558532, "field 'imgPhoto'");
    view = finder.findRequiredView(source, 2131558533, "field 'edtContent'");
    target.edtContent = finder.castView(view, 2131558533, "field 'edtContent'");
    view = finder.findRequiredView(source, 2131558534, "field 'tagBike'");
    target.tagBike = finder.castView(view, 2131558534, "field 'tagBike'");
    view = finder.findRequiredView(source, 2131558535, "field 'tagLine'");
    target.tagLine = finder.castView(view, 2131558535, "field 'tagLine'");
    view = finder.findRequiredView(source, 2131558536, "field 'tagAuthor'");
    target.tagAuthor = finder.castView(view, 2131558536, "field 'tagAuthor'");
    view = finder.findRequiredView(source, 2131558537, "field 'btnAddImg' and method 'onClick'");
    target.btnAddImg = finder.castView(view, 2131558537, "field 'btnAddImg'");
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
    target.imgCover = null;
    target.imgBack = null;
    target.ImgReChoose = null;
    target.imgPhoto = null;
    target.edtContent = null;
    target.tagBike = null;
    target.tagLine = null;
    target.tagAuthor = null;
    target.btnAddImg = null;
  }
}
