package com.minilook.minilook.ui.main.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.minilook.minilook.ui.base.widget.BottomBar;
import com.minilook.minilook.ui.category.CategoryFragment;
import com.minilook.minilook.ui.challenge.ChallengeFragment;
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
            case BottomBar.POSITION_LOOKBOOK:
                return LookBookFragment.newInstance();
            case BottomBar.POSITION_MARKET:
                return MarketFragment.newInstance();
            case BottomBar.POSITION_CATEGORY:
                return CategoryFragment.newInstance();
            case BottomBar.POSITION_CHALLENGE:
                return ChallengeFragment.newInstance();
            case BottomBar.POSITION_IPAGE:
                return IpageFragment.newInstance();
        }
    }

    @Override public int getItemCount() {
        return 5;
    }
}
