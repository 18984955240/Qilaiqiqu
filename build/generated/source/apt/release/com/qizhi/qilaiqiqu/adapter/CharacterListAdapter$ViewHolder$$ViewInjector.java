// Generated code from Butter Knife. Do not modify!
package com.qizhi.qilaiqiqu.adapter;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.Injector;

public class CharacterListAdapter$ViewHolder$$ViewInjector<T extends com.qizhi.qilaiqiqu.adapter.CharacterListAdapter.ViewHolder> implements Injector<T> {
  @Override public void inject(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131559458, "field 'imgCharacterImage'");
    target.imgCharacterImage = finder.castView(view, 2131559458, "field 'imgCharacterImage'");
    view = finder.findRequiredView(source, 2131559459, "field 'imgCharacterTitle'");
    target.imgCharacterTitle = finder.castView(view, 2131559459, "field 'imgCharacterTitle'");
    view = finder.findRequiredView(source, 2131559460, "field 'txtCharacterPhoto'");
    target.txtCharacterPhoto = finder.castView(view, 2131559460, "field 'txtCharacterPhoto'");
    view = finder.findRequiredView(source, 2131559461, "field 'txtCharacterName'");
    target.txtCharacterName = finder.castView(view, 2131559461, "field 'txtCharacterName'");
    view = finder.findRequiredView(source, 2131559462, "field 'txtCharacterBrowse'");
    target.txtCharacterBrowse = finder.castView(view, 2131559462, "field 'txtCharacterBrowse'");
    view = finder.findRequiredView(source, 2131559463, "field 'txtCharacterRecomment'");
    target.txtCharacterRecomment = finder.castView(view, 2131559463, "field 'txtCharacterRecomment'");
  }

  @Override public void reset(T target) {
    target.imgCharacterImage = null;
    target.imgCharacterTitle = null;
    target.txtCharacterPhoto = null;
    target.txtCharacterName = null;
    target.txtCharacterBrowse = null;
    target.txtCharacterRecomment = null;
  }
}
