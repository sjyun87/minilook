package com.minilook.minilook.ui.challenge;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

public interface ChallengePresenter extends LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreateView();

    interface View {

        void setupTabLayout();

        void setupViewPager();

        void navigateToChallengeDetail(int challengeNo);
    }
}
