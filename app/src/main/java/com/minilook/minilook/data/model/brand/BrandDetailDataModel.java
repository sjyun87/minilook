package com.minilook.minilook.data.model.brand;

import com.minilook.minilook.data.model.category.CategoryDataModel;
import com.minilook.minilook.data.model.common.SortDataModel;
import java.util.List;
import lombok.Data;

@Data public class BrandDetailDataModel {
    private BrandDataModel brand;
    private List<CategoryDataModel> cate_list;
    private List<SortDataModel> sort_list;
}