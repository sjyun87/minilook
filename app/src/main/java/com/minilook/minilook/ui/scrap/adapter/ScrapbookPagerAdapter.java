package com.minilook.minilook.ui.scrap.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.minilook.minilook.ui.scrap.view.brand.ScrapbookBrandFragment;
import com.minilook.minilook.ui.scrap.view.product.ScrapbookProductFragment;

public class ScrapbookPagerAdapter extends FragmentStateAdapter {

    public ScrapbookPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull @Override public Fragment createFragment(int position) {
        switch (position) {
            default:
            case 0:
                return ScrapbookProductFragment.newInstance();
            case 1:
                return ScrapbookBrandFragment.newInstance();
        }
    }

    @Override public int getItemCount() {
        return 2;
    }
}
