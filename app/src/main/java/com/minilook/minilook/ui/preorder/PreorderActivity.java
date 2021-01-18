package com.minilook.minilook.ui.preorder;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.annotation.ArrayRes;
import com.google.android.material.tabs.TabLayout;
import com.minilook.minilook.R;
import com.minilook.minilook.databinding.ActivityPreorderBinding;
import com.minilook.minilook.ui.base.BaseActivity;
import com.minilook.minilook.ui.base.widget.TabView;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.preorder.adapter.PreorderPagerAdapter;
import com.minilook.minilook.ui.preorder.di.PreorderArguments;
import com.minilook.minilook.ui.preorder_detail.PreorderDetailActivity;
import com.minilook.minilook.ui.preorder_info.PreorderInfoActivity;
import java.util.Objects;

public class PreorderActivity extends BaseActivity implements PreorderPresenter.View {

    public static void start(Context context) {
        Intent intent = new Intent(context, PreorderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        context.startActivity(intent);
    }

    @ArrayRes int tab_preorder = R.array.tab_preorder;

    private ActivityPreorderBinding binding;
    private PreorderPresenter presenter;
    private PreorderPagerAdapter adapter;

    @Override protected View getBindingView() {
        binding = ActivityPreorderBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new PreorderPresenterImpl(provideArguments());
        getLifecycle().addObserver(presenter);
    }

    private PreorderArguments provideArguments() {
        return PreorderArguments.builder()
            .view(this)
            .build();
    }

    @Override public void setupTabLayout() {
        for (String tabName : resources.getStringArray(tab_preorder)) {
            TabView tabView = TabView.builder()
                .context(this)
                .name(tabName)
                .build();

            TabLayout.Tab tab = binding.layoutTabPanel.newTab();
            tab.setCustomView(tabView);
            binding.layoutTabPanel.addTab(tab);
        }

        binding.layoutTabPanel.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override public void onTabSelected(TabLayout.Tab tab) {
                presenter.onTabClick(tab.getPosition());
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
        adapter = new PreorderPagerAdapter(getSupportFragmentManager(), getLifecycle());
        binding.vpPreorder.setAdapter(adapter);
        binding.vpPreorder.setOffscreenPageLimit(2);
        binding.vpPreorder.setUserInputEnabled(false);
        binding.vpPreorder.setCurrentItem(0);
    }

    @Override public void setupCurrentPage(int position) {
        binding.vpPreorder.setCurrentItem(position);
    }

    @Override public void navigateToPreorderInfo() {
        PreorderInfoActivity.start(this);
    }

    @Override public void hideClosePreorderTab() {
        binding.layoutTabPanel.removeTabAt(binding.layoutTabPanel.getTabCount() - 1);
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(this);
    }

    @Override public void navigateToPreorderDetail(int preorderNo) {
        PreorderDetailActivity.start(this, preorderNo);
    }

    @Override public void sendDynamicLink(String link) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, link);
        startActivity(Intent.createChooser(intent, "친구에게 공유하기"));
    }
}
