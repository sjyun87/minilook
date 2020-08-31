package com.minilook.minilook.ui.search_address.di;

import com.minilook.minilook.ui.search_address.SearchAddressPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class SearchAddressArguments {
    private final SearchAddressPresenter.View view;
}