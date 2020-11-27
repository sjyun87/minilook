package com.minilook.minilook.ui.lookbook.view.preview;

import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;
import com.minilook.minilook.data.model.lookbook.LookBookModuleDataModel;
import com.minilook.minilook.databinding.FragmentLookbookPreviewBinding;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.lookbook.view.preview.adapter.LookBookModuleAdapter;
import com.minilook.minilook.ui.lookbook.view.preview.di.LookBookPreviewArguments;

public class LookBookPreviewFragment extends BaseFragment implements LookBookPreviewPresenter.View {

    public static LookBookPreviewFragment newInstance() {
        return new LookBookPreviewFragment();
    }

    private FragmentLookbookPreviewBinding binding;
    private LookBookPreviewPresenter presenter;

    private final LookBookModuleAdapter adapter = new LookBookModuleAdapter();
    private final BaseAdapterDataView<LookBookModuleDataModel> adapterView = adapter;

    private EndlessOnScrollListener scrollListener;

    @Override protected View getBindingView() {
        binding = FragmentLookbookPreviewBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new LookBookPreviewPresenterImpl(provideArguments());
        getViewLifecycleOwner().getLifecycle().addObserver(presenter);
    }

    private LookBookPreviewArguments provideArguments() {
        return LookBookPreviewArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupViewPager() {
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setOffscreenPageLimit(2);
        binding.viewpager.registerOnPageChangeCallback(OnPageChangeCallback);
        scrollListener = EndlessOnScrollListener.builder()
            .layoutManager(getRecyclerView().getLayoutManager())
            .onLoadMoreListener(presenter::onLoadMore)
            .visibleThreshold(5)
            .build();
        getRecyclerView().addOnScrollListener(scrollListener);
    }

    private RecyclerView getRecyclerView() {
        return (RecyclerView) binding.viewpager.getChildAt(0);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }

    @Override public void scrollToNextModule() {
        binding.viewpager.setCurrentItem(binding.viewpager.getCurrentItem() + 1);
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(getActivity());
    }

    @Override public void clear() {
        binding.viewpager.unregisterOnPageChangeCallback(OnPageChangeCallback);
        getRecyclerView().removeOnScrollListener(scrollListener);
    }

    private final ViewPager2.OnPageChangeCallback OnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override public void onPageSelected(int position) {
            presenter.onPageSelected(position);
        }
    };
}
