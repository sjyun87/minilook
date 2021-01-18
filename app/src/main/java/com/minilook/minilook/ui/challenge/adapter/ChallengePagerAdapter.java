package com.minilook.minilook.ui.challenge.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.minilook.minilook.ui.challenge.view.close.ChallengeCloseFragment;
import com.minilook.minilook.ui.challenge.view.coming.ChallengeComingFragment;
import com.minilook.minilook.ui.challenge.view.open.ChallengeOpenFragment;

public class ChallengePagerAdapter extends FragmentStateAdapter {

    public static final int PAGE_OPEN = 0;
    public static final int PAGE_COMING = 1;
    public static final int PAGE_CLOSE = 2;

    public ChallengePagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull @Override public Fragment createFragment(int position) {
        switch (position) {
            default:
            case PAGE_OPEN:
                return ChallengeOpenFragment.newInstance();
            case PAGE_COMING:
                return ChallengeComingFragment.newInstance();
            case PAGE_CLOSE:
                return ChallengeCloseFragment.newInstance();
        }
    }

    @Override public int getItemCount() {
        return 3;
    }
}
