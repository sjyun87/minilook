package com.minilook.minilook.ui.review_history.view.written;

import android.view.View;
import androidx.annotation.DimenRes;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.fondesa.recyclerviewdivider.DividerDecoration;
import com.minilook.minilook.R;
import com.minilook.minilook.data.model.review.ReviewDataModel;
import com.minilook.minilook.databinding.FragmentWrittenReviewBinding;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.dialog.manager.DialogManager;
import com.minilook.minilook.ui.review_history.view.written.adapter.WrittenReviewAdapter;
import com.minilook.minilook.ui.review_history.view.written.di.ReviewWrittenArguments;

public class WrittenReviewFragment extends BaseFragment implements WrittenReviewPresenter.View {

    public static WrittenReviewFragment newInstance() {
        return new WrittenReviewFragment();
    }

    @DimenRes int dp_6 = R.dimen.dp_6;

    private FragmentWrittenReviewBinding binding;
    private WrittenReviewPresenter presenter;

    private final WrittenReviewAdapter adapter = new WrittenReviewAdapter();
    private final BaseAdapterDataView<ReviewDataModel> adapterView = adapter;

    private EndlessOnScrollListener scrollListener;

    @Override protected View getBindingView() {
        binding = FragmentWrittenReviewBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new WrittenReviewPresenterImpl(provideArguments());
        getViewLifecycleOwner().getLifecycle().addObserver(presenter);
    }

    private ReviewWrittenArguments provideArguments() {
        return ReviewWrittenArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void setupRecyclerView() {
        binding.rcvReview.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rcvReview.setAdapter(adapter);
        DividerDecoration.builder(requireContext())
            .size(resources.getDimen(dp_6))
            .asSpace()
            .build()
            .addTo(binding.rcvReview);
        scrollListener = EndlessOnScrollListener.builder()
            .layoutManager(binding.rcvReview.getLayoutManager())
            .onLoadMoreListener(presenter::onLoadMore)
            .visibleThreshold(10)
            .build();
        binding.rcvReview.addOnScrollListener(scrollListener);
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
        binding.rcvReview.scrollToPosition(0);
    }

    @Override public void clear() {
        binding.rcvReview.removeOnScrollListener(scrollListener);
        binding.rcvReview.setAdapter(null);
    }
}
