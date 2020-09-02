package com.minilook.minilook.data.rx;

import lombok.AllArgsConstructor;
import lombok.Getter;

public final class RxBusEvent {

    @AllArgsConstructor @Getter public final static class RxBusEventLogin {
    }

    @AllArgsConstructor @Getter public final static class RxBusEventLogout {
    }
}
