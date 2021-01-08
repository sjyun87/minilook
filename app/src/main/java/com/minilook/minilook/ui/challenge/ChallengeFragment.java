package com.minilook.minilook.ui.challenge;

import android.view.View;
import com.minilook.minilook.databinding.FragmentChallengeBinding;
import com.minilook.minilook.ui.base.BaseFragment;
import com.minilook.minilook.ui.challenge.di.ChallengeArguments;

public class ChallengeFragment extends BaseFragment implements ChallengePresenter.View {

    public static ChallengeFragment newInstance() {
        return new ChallengeFragment();
    }

    private FragmentChallengeBinding binding;
    private ChallengePresenter presenter;

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
}
