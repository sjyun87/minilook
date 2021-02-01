package com.minilook.minilook.data.model.gallery;

import lombok.Data;

@Data public class GalleryDataModel {
    private String name;
    private String path;
    private boolean isSelect;
    private int selectPosition;
}
