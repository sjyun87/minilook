package com.minilook.minilook.data.rx;

import io.reactivex.rxjava3.core.SingleTransformer;

public class Transformer {

    public static <T> SingleTransformer<T, T> applySchedulers() {
        return upstream -> upstream.subscribeOn(SchedulersFacade.io())
            .observeOn(SchedulersFacade.ui());
    }
}
