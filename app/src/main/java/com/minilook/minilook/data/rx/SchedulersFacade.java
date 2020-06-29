package com.minilook.minilook.data.rx;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SchedulersFacade {

    public static Scheduler io() {
        return Schedulers.io();
    }

    public static Scheduler computation() {
        return Schedulers.computation();
    }

    public static Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
