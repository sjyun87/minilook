package com.minilook.minilook.ui.challenge;

import android.view.View;
import androidx.annotation.ArrayRes;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.FontRes;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.FragmentChallengeBinding;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.base.widget.TabView;
import com.minilook.minilook.ui.challenge.adapter.ChallengePagerAdapter;
import com.minilook.minilook.ui.challenge.di.ChallengeArguments;
import com.minilook.minilook.ui.challenge_detail.ChallengeDetailActivity;
import java.util.Objects;

public class ChallengeFragment extends BaseFragment implements ChallengePresenter.View {

    public static ChallengeFragment newInstance() {
        return new ChallengeFragment();
    }

    @ArrayRes int tab_challenge = R.array.tab_challenge;

    @DimenRes int dp_36 = R.dimen.dp_36;

    @ColorRes int color_FF8140E5 = R.color.color_FF8140E5;
    @ColorRes int color_FF000000 = R.color.color_FF000000;

    @FontRes int font_regular = R.font.nanum_square_r;

    private FragmentChallengeBinding binding;
    private ChallengePresenter presenter;

    private ChallengePagerAdapter adapter;

    @Override protected View getBindingView() {
        binding = FragmentChallengeBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ChallengePresenterImpl(provideArguments());
        getViewLifecycleOwner().getLifecycle().addObserver(presenter);
    }

    private ChallengeArguments provideArguments() {
        return ChallengeArguments.builder()
            .view(this)
            .build();
    }

    @Override public void onLogin() {
        presenter.onLogin();
    }

    @Override public void onLogout() {
        presenter.onLogout();
    }

    @Override public void setupTabLayout() {
        for (String tabName : resources.getStringArray(tab_challenge)) {
            TabView tabView = TabView.builder()
                .context(getContext())
                .name(tabName)
                .width(resources.getDimen(dp_36))
                .selectedTextColor(resources.getColor(color_FF8140E5))
                .unselectedTextColor(resources.getColor(color_FF000000))
                .selectedTextFont(resources.getFont(font_regular))
                .unselectedTextFont(resources.getFont(font_regular))
                .build();

            TabLayout.Tab tab = binding.layoutTabPanel.newTab();
            tab.setCustomView(tabView);
            binding.layoutTabPanel.addTab(tab);
        }

        binding.layoutTabPanel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                binding.vpChallenge.setCurrentItem(tab.getPosition());
                getTabView(tab).setupSelected();
            }

            @Override public void onTabUnselected(TabLayout.Tab tab) {
                getTabView(tab).setupUnselected();
            }

            @Override public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        getTabView(0).setupSelected();
    }

    public TabView getTabView(TabLayout.Tab tab) {
        return (TabView) tab.getCustomView();
    }

    public TabView getTabView(int position) {
        return (TabView) Objects.requireNonNull(binding.layoutTabPanel.getTabAt(position)).getCustomView();
    }

    @Override public void setupViewPager() {
        adapter = new ChallengePagerAdapter(getChildFragmentManager(), getLifecycle());
        binding.vpChallenge.setAdapter(adapter);
        binding.vpChallenge.setOffscreenPageLimit(2);
        binding.vpChallenge.setCurrentItem(0);
        binding.vpChallenge.setUserInputEnabled(false);
    }

    @Override public void setHeaderExpand() {
        binding.appBar.setExpanded(true);
    }

    @Override public void navigateToChallengeDetail(int challengeNo) {
        ChallengeDetailActivity.start(getContext(), challengeNo);
    }
}
