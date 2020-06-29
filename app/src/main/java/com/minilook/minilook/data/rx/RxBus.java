package com.minilook.minilook.data.rx;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.PublishSubject;

public final class RxBus {
    private static PublishSubject<Object> bus = PublishSubject.create();

    public static void send(Object o) {
        bus.onNext(o);
    }

    public static Observable<Object> toObservable() {
        return bus;
    }

    public static boolean hasObservable() {
        return bus.hasObservers();
    }
}
