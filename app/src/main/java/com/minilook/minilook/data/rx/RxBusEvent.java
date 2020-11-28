package com.minilook.minilook.data.rx;

import com.minilook.minilook.data.model.brand.BrandDataModel;
import com.minilook.minilook.data.model.product.ProductDataModel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public final class RxBusEvent {

    @AllArgsConstructor @Getter public final static class RxBusEventLogin {
    }

    @AllArgsConstructor @Getter public final static class RxBusEventLogout {
    }

    @AllArgsConstructor @Getter public final static class RxBusEventProductScrap {
        private final ProductDataModel data;
    }

    @AllArgsConstructor @Getter public final static class RxBusEventBrandScrap {
        private final BrandDataModel data;
    }
}
