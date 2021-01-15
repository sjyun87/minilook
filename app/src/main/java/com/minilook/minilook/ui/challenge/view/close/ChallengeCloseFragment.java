package com.minilook.minilook.ui.challenge.view.close;

import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.minilook.minilook.data.model.challenge.ChallengeDataModel;
import com.minilook.minilook.databinding.FragmentChallengeCloseBinding;
import com.minilook.minilook.ui.base.BaseAdapterDataView;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.challenge.view.close.adapter.ChallengeCloseAdapter;
import com.minilook.minilook.ui.challenge.view.close.di.ChallengeCloseArguments;

public class ChallengeCloseFragment extends BaseFragment implements ChallengeClosePresenter.View {

    public static ChallengeCloseFragment newInstance() {
        return new ChallengeCloseFragment();
    }

    private FragmentChallengeCloseBinding binding;
    private ChallengeClosePresenter presenter;

    private final ChallengeCloseAdapter adapter = new ChallengeCloseAdapter();
    private final BaseAdapterDataView<ChallengeDataModel> adapterView = adapter;

    @Override protected View getBindingView() {
        binding = FragmentChallengeCloseBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override protected void createPresenter() {
        presenter = new ChallengeClosePresenterImpl(provideArguments());
        getViewLifecycleOwner().getLifecycle().addObserver(presenter);
    }

    private ChallengeCloseArguments provideArguments() {
        return ChallengeCloseArguments.builder()
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

    @Override public void setupSwipeRefresh() {
        binding.layoutSwipeRefresh.setOnRefreshListener(presenter::onSwipeRefresh);
    }

    @Override public void setRefreshing(boolean flag) {
        if (binding.layoutSwipeRefresh.isRefreshing() != flag) binding.layoutSwipeRefresh.setRefreshing(flag);
    }

    @Override public void setupRecyclerView() {
        binding.rcvChallenge.setHasFixedSize(true);
        binding.rcvChallenge.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rcvChallenge.setAdapter(adapter);
    }

    @Override public void refresh() {
        adapterView.refresh();
    }

    @Override public void refresh(int start, int rows) {
        adapterView.refresh(start, rows);
    }

    @Override public void scrollToTop() {
        binding.rcvChallenge.scrollToPosition(0);
    }

    @Override public void clear() {
        binding.rcvChallenge.setAdapter(null);
    }
}
