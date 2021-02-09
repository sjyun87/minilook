package com.minilook.minilook.data.model.review;

import java.util.List;
import lombok.Data;

@Data public class ReviewWriteDataModel {
    private String orderNo;
    private int productNo;
    private int optionNo;
    private String satisfactionCode;
    private String sizeRatingCode;
    private String genderCode;
    private int age;
    private int height;
    private int weight;
    private List<String> photos;
    private String review;
}
