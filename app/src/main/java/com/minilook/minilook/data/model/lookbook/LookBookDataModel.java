package com.minilook.minilook.data.model.lookbook;

import lombok.Data;

@Data public class LookBookDataModel {
    private String id;
    private String type;
    private LookBookPreviewDataModel preview;
    private LookBookDetailDataModel detail;
}
