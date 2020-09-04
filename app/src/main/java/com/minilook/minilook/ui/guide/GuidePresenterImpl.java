package com.minilook.minilook.ui.guide;

import com.minilook.minilook.data.common.PrefsKey;
import com.minilook.minilook.ui.base.BasePresenterImpl;
import com.minilook.minilook.ui.guide.di.GuideArguments;
import com.pixplicity.easyprefs.library.Prefs;

public class GuidePresenterImpl extends BasePresenterImpl implements GuidePresenter {

    private final View view;

    public GuidePresenterImpl(GuideArguments args) {
        view = args.getView();
    }

    @Override public void onCreate() {
        view.setupViewPager();
    }

    @Override public void onPageSelected(int position) {
        if (position == 3) {
            view.showStartButton();
        } else {
            view.hideStartButton();
        }
    }

    @Override public void onGuideEndClick() {
        Prefs.putInt(PrefsKey.KEY_GUIDE_VISIBLE_COUNT, 3);
        view.navigateToMain();
    }
}