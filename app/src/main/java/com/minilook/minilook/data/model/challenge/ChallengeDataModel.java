package com.minilook.minilook.data.model.challenge;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.minilook.minilook.data.model.base.BooleanDeserializer;
import java.util.List;
import lombok.Data;

@Data public class ChallengeDataModel {
    @Expose @SerializedName("challengeNo")
    private int challengeNo;
    @Expose @SerializedName("title")
    private String brand;
    @Expose @SerializedName("description")
    private String product;
    @Expose @SerializedName("image")
    private String thumbUrl;
    @Expose @SerializedName("images")
    private List<String> images;
    @Expose @SerializedName("detail_url")
    private String detailUrl;
    @Expose @SerializedName("challenge_url")
    private String challengeUrl;
    @Expose @SerializedName("startDate")
    private long startDate;
    @Expose @SerializedName("startDateName")
    private String startDateName;
    @Expose @SerializedName("endDate")
    private long endDate;
    @Expose @SerializedName("endDateName")
    private String endDateName;
    @Expose @SerializedName("winning")
    private int winnerCount;
    @Expose @SerializedName("applyTotal")
    private int enterCount;
    @Expose @SerializedName("isApplied")
    @JsonAdapter(BooleanDeserializer.class)
    private boolean isEnter;
    @Expose @SerializedName("winningDatetime")
    private long winDate;
    @Expose @SerializedName("reviewLimitDate")
    private long reviewDate;
}
