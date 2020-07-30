package com.minilook.minilook.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.minilook.minilook.ui.preorder.PreorderFragment;
import com.minilook.minilook.ui.ipage.IpageFragment;
import com.minilook.minilook.ui.lookbook.LookBookFragment;
import com.minilook.minilook.ui.market.MarketFragment;

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
