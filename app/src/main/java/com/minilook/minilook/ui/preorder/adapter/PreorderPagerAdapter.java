package com.minilook.minilook.ui.preorder.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.minilook.minilook.ui.preorder.view.coming.PreorderComingFragment;
import com.minilook.minilook.ui.preorder.view.open.PreorderOpenFragment;

public class PreorderPagerAdapter extends FragmentStateAdapter {

    public PreorderPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull @Override public Fragment createFragment(int position) {
        switch (position) {
            default:
            case 0:
                return PreorderOpenFragment.newInstance();
            case 1:
                return PreorderComingFragment.newInstance();
        }
    }

    @Override public int getItemCount() {
        return 2;
    }
}
