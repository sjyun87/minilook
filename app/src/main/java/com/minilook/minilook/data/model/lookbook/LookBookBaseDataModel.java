package com.minilook.minilook.data.model.lookbook;

import java.util.List;
import lombok.Data;

@Data public class LookBookBaseDataModel {
    private boolean isReset;
    private List<LookBookDataModel> lookbooks;
}
