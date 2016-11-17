// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class Activity_NewCommentActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.Activity_NewCommentActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558544, "field 'layoutBack' and method 'onClick'");
    target.layoutBack = finder.castView(view, 2131558544, "field 'layoutBack'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558545, "field 'btnComfirm' and method 'onClick'");
    target.btnComfirm = finder.castView(view, 2131558545, "field 'btnComfirm'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558546, "field 'imgActiveImg'");
    target.imgActiveImg = finder.castView(view, 2131558546, "field 'imgActiveImg'");
    view = finder.findRequiredView(source, 2131558547, "field 'txtActivetitle'");
    target.txtActivetitle = finder.castView(view, 2131558547, "field 'txtActivetitle'");
    view = finder.findRequiredView(source, 2131558549, "field 'txtAward'");
    target.txtAward = finder.castView(view, 2131558549, "field 'txtAward'");
    view = finder.findRequiredView(source, 2131558550, "field 'imgAward'");
    target.imgAward = finder.castView(view, 2131558550, "field 'imgAward'");
    view = finder.findRequiredView(source, 2131558548, "field 'layoutAward' and method 'onClick'");
    target.layoutAward = finder.castView(view, 2131558548, "field 'layoutAward'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
    view = finder.findRequiredView(source, 2131558551, "field 'edtComment'");
    target.edtComment = finder.castView(view, 2131558551, "field 'edtComment'");
    view = finder.findRequiredView(source, 2131558552, "field 'txtCommentSize'");
    target.txtCommentSize = finder.castView(view, 2131558552, "field 'txtCommentSize'");
  }

  @Override public void reset(T target) {
    target.layoutBack = null;
    target.btnComfirm = null;
    target.imgActiveImg = null;
    target.txtActivetitle = null;
    target.txtAward = null;
    target.imgAward = null;
    target.layoutAward = null;
    target.edtComment = null;
    target.txtCommentSize = null;
  }
}
