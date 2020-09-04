package com.minilook.minilook.data.rx;

import lombok.AllArgsConstructor;
import lombok.Getter;

public final class RxBusEvent {

    @AllArgsConstructor @Getter public final static class RxBusEventLogin {
    }

    @AllArgsConstructor @Getter public final static class RxBusEventLogout {
    }

    @AllArgsConstructor @Getter public final static class RxBusEventProductScrap {
        boolean isScrap;
        int product_id;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventBrandScrap {
        boolean isScrap;
        int brand_id;
    }
}
