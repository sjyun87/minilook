package com.minilook.minilook.data.model.lookbook;

import lombok.Data;

@Data public class LookBookDataModel {
    private int id;
    private int type;
    private LookBookPreviewDataModel preview;
    private LookBookDetailDataModel detail;
}
