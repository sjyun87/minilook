package com.minilook.minilook.data.model.image;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor public class ImageDataModel implements Parcelable {
    @Expose @SerializedName("sumb")
    private String thumbUrl;
    @Expose @SerializedName("origin")
    private String originUrl;

    protected ImageDataModel(Parcel in) {
        thumbUrl = in.readString();
        originUrl = in.readString();
    }

    public static final Creator<ImageDataModel> CREATOR = new Creator<ImageDataModel>() {
        @Override
        public ImageDataModel createFromParcel(Parcel in) {
            return new ImageDataModel(in);
        }

        @Override
        public ImageDataModel[] newArray(int size) {
            return new ImageDataModel[size];
        }
    };

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(thumbUrl);
        parcel.writeString(originUrl);
    }
}
