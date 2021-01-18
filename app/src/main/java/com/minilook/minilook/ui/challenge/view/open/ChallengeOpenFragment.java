package com.minilook.minilook.ui.challenge.view.open;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.databinding.FragmentChallengeOpenBinding;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.base.listener.EndlessOnScrollListener;
import com.minilook.minilook.ui.challenge.view.open.adapter.ChallengeOpenAdapter;
import com.minilook.minilook.ui.challenge.view.open.di.ChallengeOpenArguments;

public class ChallengeOpenFragment extends BaseFragment implements ChallengeOpenPresenter.View {

    public static ChallengeOpenFragment newInstance() {
        return new ChallengeOpenFragment();
    }

    private FragmentChallengeOpenBinding binding;
    private ChallengeOpenPresenter presenter;

    private final ChallengeOpenAdapter adapter = new ChallengeOpenAdapter();
    private final BaseAdapterDataView<ChallengeDataModel> adapterView = adapter;

    private EndlessOnScrollListener scrollListener;

    @Override protected View getBindingView() {
        binding = FragmentChallengeOpenBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ChallengeOpenPresenterImpl(provideArguments());
        getViewLifecycleOwner().getLifecycle().addObserver(presenter);
    }

    private ChallengeOpenArguments provideArguments() {
        return ChallengeOpenArguments.builder()
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
