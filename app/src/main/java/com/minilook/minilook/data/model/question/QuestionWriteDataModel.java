package com.minilook.minilook.data.model.question;

import java.util.List;
import lombok.Data;

@Data public class QuestionWriteDataModel {
    private int productNo;
    private String type;
    private String question;
    private List<String> photos;
    private boolean isSecret;
}
