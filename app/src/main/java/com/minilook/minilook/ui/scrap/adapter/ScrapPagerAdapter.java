package com.minilook.minilook.ui.scrap.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.minilook.minilook.ui.preorder.view.coming.PreorderComingFragment;
import com.minilook.minilook.ui.preorder.view.open.PreorderOpenFragment;
import com.minilook.minilook.ui.scrap.view.brand.ScrapBrandFragment;
import com.minilook.minilook.ui.scrap.view.product.ScrapProductFragment;

public class ScrapPagerAdapter extends FragmentStateAdapter {

    public ScrapPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull @Override public Fragment createFragment(int position) {
        switch (position) {
            default:
            case 0:
                return ScrapProductFragment.newInstance();
            case 1:
                return ScrapBrandFragment.newInstance();
        }
    }

    @Override public int getItemCount() {
        return 2;
    }
}
