// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.activity;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class ForgetActivity$$ViewInjector<T extends com.qizhi.qilaiqiqu.activity.ForgetActivity> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558812, "field 'backLauout'");
    target.backLauout = finder.castView(view, 2131558812, "field 'backLauout'");
    view = finder.findRequiredView(source, 2131558822, "field 'commitTxt'");
    target.commitTxt = finder.castView(view, 2131558822, "field 'commitTxt'");
    view = finder.findRequiredView(source, 2131558814, "field 'phoneEdt'");
    target.phoneEdt = finder.castView(view, 2131558814, "field 'phoneEdt'");
    view = finder.findRequiredView(source, 2131558817, "field 'codeEdt'");
    target.codeEdt = finder.castView(view, 2131558817, "field 'codeEdt'");
    view = finder.findRequiredView(source, 2131558815, "field 'sendBtn'");
    target.sendBtn = finder.castView(view, 2131558815, "field 'sendBtn'");
    view = finder.findRequiredView(source, 2131558819, "field 'newPassEdt'");
    target.newPassEdt = finder.castView(view, 2131558819, "field 'newPassEdt'");
    view = finder.findRequiredView(source, 2131558821, "field 'confirmEdt'");
    target.confirmEdt = finder.castView(view, 2131558821, "field 'confirmEdt'");
  }

  @Override public void reset(T target) {
    target.backLauout = null;
    target.commitTxt = null;
    target.phoneEdt = null;
    target.codeEdt = null;
    target.sendBtn = null;
    target.newPassEdt = null;
    target.confirmEdt = null;
  }
}
