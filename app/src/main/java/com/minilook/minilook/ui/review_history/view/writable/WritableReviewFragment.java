package com.minilook.minilook.ui.review_history.view.writable;

import android.view.View;
import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.order.OrderHistoryDataModel;
import com.minilook.minilook.databinding.FragmentWritableReviewBinding;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.review_history.view.writable.adapter.WritableReviewAdapter;
import com.minilook.minilook.ui.review_history.view.writable.di.WritableReviewArguments;

public class WritableReviewFragment extends BaseFragment implements WritableReviewPresenter.View {

    public static WritableReviewFragment newInstance() {
        return new WritableReviewFragment();
    }

    @DimenRes int dp_6 = R.dimen.dp_6;

    private FragmentWritableReviewBinding binding;
    private WritableReviewPresenter presenter;

    private final WritableReviewAdapter adapter = new WritableReviewAdapter();
    private final BaseAdapterDataView<OrderHistoryDataModel> adapterView = adapter;

    private EndlessOnScrollListener scrollListener;

    @Override protected View getBindingView() {
        binding = FragmentWritableReviewBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new WritableReviewPresenterImpl(provideArguments());
        getViewLifecycleOwner().getLifecycle().addObserver(presenter);
    }

    private WritableReviewArguments provideArguments() {
        return WritableReviewArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        binding.rcvOrder.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rcvOrder.setAdapter(adapter);
        DividerDecoration.builder(requireContext())
            .size(resources.getDimen(dp_6))
            .asSpace()
            .build()
            .addTo(binding.rcvOrder);
        scrollListener = EndlessOnScrollListener.builder()
            .layoutManager(binding.rcvOrder.getLayoutManager())
            .onLoadMoreListener(presenter::onLoadMore)
            .visibleThreshold(10)
            .build();
        binding.rcvOrder.addOnScrollListener(scrollListener);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }

    @Override public void showEmptyPanel() {
        binding.layoutEmptyPanel.setVisibility(View.VISIBLE);
    }

    @Override public void hideEmptyPanel() {
        binding.layoutEmptyPanel.setVisibility(View.GONE);
    }

    @Override public void showErrorDialog() {
        DialogManager.showErrorDialog(getActivity());
    }

    @Override public void scrollToTop() {
        binding.rcvOrder.scrollToPosition(0);
    }

    @Override public void clear() {
        binding.rcvOrder.removeOnScrollListener(scrollListener);
        binding.rcvOrder.setAdapter(null);
    }
}
