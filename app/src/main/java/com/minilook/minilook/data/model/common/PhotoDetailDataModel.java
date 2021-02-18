package com.minilook.minilook.data.model.common;

import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data public class PhotoDetailDataModel implements Serializable {
    private String contents;
    private List<ImageDataModel> photos;
    private int position;
}
