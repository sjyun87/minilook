package com.minilook.minilook.data.model.lookbook;

import java.util.List;
import lombok.Data;

@Data public class LookBookDataModel {
    private boolean isReset;
    private List<LookBookModuleDataModel> lookbooks;
}
