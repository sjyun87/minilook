package com.minilook.minilook.ui.review_history.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import com.minilook.minilook.ui.review_history.view.writable.WritableReviewFragment;
import com.minilook.minilook.ui.review_history.view.written.WrittenReviewFragment;

public class ReviewHistoryPagerAdapter extends FragmentStateAdapter {

    public static final int PAGE_WRITTEN = 0;
    public static final int PAGE_WRITABLE = 1;

    public ReviewHistoryPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull @Override public Fragment createFragment(int position) {
        switch (position) {
            default:
            case PAGE_WRITTEN:
                return WrittenReviewFragment.newInstance();
            case PAGE_WRITABLE:
                return WritableReviewFragment.newInstance();
        }
    }

    @Override public int getItemCount() {
        return 2;
    }
}
