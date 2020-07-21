package com.minilook.minilook.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.minilook.minilook.ui.main.fragment.category.PreorderFragment;
import com.minilook.minilook.ui.main.fragment.ipage.IpageFragment;
import com.minilook.minilook.ui.main.fragment.lookbook.LookBookFragment;
import com.minilook.minilook.ui.main.fragment.market.MarketFragment;

public class MainPagerAdapter extends FragmentStateAdapter {

    public MainPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull @Override public Fragment createFragment(int position) {
        switch (position) {
            default:
            case 0:
                return LookBookFragment.newInstance();
            case 1:
                return PreorderFragment.newInstance();
            case 2:

                return MarketFragment.newInstance();
            case 3:
                return IpageFragment.newInstance();
        }
    }

    @Override public int getItemCount() {
        return 4;
    }
}
