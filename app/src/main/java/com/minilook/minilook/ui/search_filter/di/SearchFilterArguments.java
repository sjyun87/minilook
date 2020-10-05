package com.minilook.minilook.ui.search_filter.di;

import com.minilook.minilook.data.model.common.CodeDataModel;
import com.minilook.minilook.data.model.common.ColorDataModel;
import com.minilook.minilook.data.model.common.GenderDataModel;
import com.minilook.minilook.ui.base.BaseAdapterDataModel;
import com.minilook.minilook.ui.search_filter.SearchFilterPresenter;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class SearchFilterArguments {
    private final SearchFilterPresenter.View view;
    private final BaseAdapterDataModel<GenderDataModel> genderAdapter;
    private final BaseAdapterDataModel<CodeDataModel> categoryAdapter;
    private final BaseAdapterDataModel<ColorDataModel> colorAdapter;
}