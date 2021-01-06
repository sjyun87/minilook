package com.minilook.minilook.data.model.gallery;

import lombok.Data;

@Data public class AlbumDataModel {
    private String folder;
    private String name;
    private int count;
    private String recentImage;
}
