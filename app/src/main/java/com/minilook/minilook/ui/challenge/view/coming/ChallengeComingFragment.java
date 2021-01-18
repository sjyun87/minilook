package com.minilook.minilook.ui.challenge.view.coming;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.databinding.FragmentChallengeComingBinding;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.challenge.view.coming.adapter.ChallengeComingAdapter;
import com.minilook.minilook.ui.challenge.view.coming.di.ChallengeComingArguments;

public class ChallengeComingFragment extends BaseFragment implements ChallengeComingPresenter.View {

    public static ChallengeComingFragment newInstance() {
        return new ChallengeComingFragment();
    }

    private FragmentChallengeComingBinding binding;
    private ChallengeComingPresenter presenter;

    private final ChallengeComingAdapter adapter = new ChallengeComingAdapter();
    private final BaseAdapterDataView<ChallengeDataModel> adapterView = adapter;

    private EndlessOnScrollListener scrollListener;

    @Override protected View getBindingView() {
        binding = FragmentChallengeComingBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ChallengeComingPresenterImpl(provideArguments());
        getViewLifecycleOwner().getLifecycle().addObserver(presenter);
    }

    private ChallengeComingArguments provideArguments() {
        return ChallengeComingArguments.builder()
            .view(this)
            .adapter(adapter)
            .build();
    }

    @Override public void onLogin() {
        presenter.onLogin();
    }

    @Override public void onLogout() {
        presenter.onLogout();
    }

    @Override public void setupRecyclerView() {
        binding.rcvChallenge.setHasFixedSize(true);
        binding.rcvChallenge.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rcvChallenge.setAdapter(adapter);
        scrollListener = EndlessOnScrollListener.builder()
            .layoutManager(binding.rcvChallenge.getLayoutManager())
            .onLoadMoreListener(presenter::onLoadMore)
            .visibleThreshold(10)
            .build();
        binding.rcvChallenge.addOnScrollListener(scrollListener);
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

    @Override public void scrollToTop() {
        binding.rcvChallenge.scrollToPosition(0);
    }

    @Override public void clear() {
        binding.rcvChallenge.removeOnScrollListener(scrollListener);
        binding.rcvChallenge.setAdapter(null);
    }
}
