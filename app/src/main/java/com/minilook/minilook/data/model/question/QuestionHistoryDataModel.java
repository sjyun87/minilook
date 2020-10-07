package com.minilook.minilook.data.model.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import lombok.Data;

@Data public class QuestionHistoryDataModel {
    @Expose @SerializedName("inquiryCount")
    private int questionCount;
    @Expose @SerializedName("inquiries")
    private List<QuestionDataModel> questions;
}
