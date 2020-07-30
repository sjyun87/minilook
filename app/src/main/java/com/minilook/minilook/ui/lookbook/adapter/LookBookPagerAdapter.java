package com.minilook.minilook.ui.lookbook.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.minilook.minilook.ui.lookbook.view.detail.LookBookDetailFragment;
import com.minilook.minilook.ui.lookbook.view.preview.LookBookPreviewFragment;

public class LookBookPagerAdapter extends FragmentStateAdapter {

    public LookBookPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull @Override public Fragment createFragment(int position) {
        switch (position) {
            default:
            case 0:
                return LookBookPreviewFragment.newInstance();
            case 1:
                return LookBookDetailFragment.newInstance();
        }
    }

    @Override public int getItemCount() {
        return 2;
    }
}
