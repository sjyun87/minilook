package com.minilook.minilook.data.model.question;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data public class QuestionDataModel {
    @Expose @SerializedName("inquiryNo")
    private int questionNo;
    @Expose @SerializedName("memberNo")
    private int memberNo;
    @Expose @SerializedName("nickname")
    private String nick;
    @Expose @SerializedName("type")
    private String type;
    @Expose @SerializedName("regDate")
    private String registDate;
    @Expose @SerializedName("question")
    private String question;
    @Expose @SerializedName("isAnswer")
    private boolean isAnswer;
    @Expose @SerializedName("answer")
    private String answer;
    @Expose @SerializedName("updateDate")
    private String answerDate;
    @Expose @SerializedName("isSecret")
    private boolean isSecret;
}
