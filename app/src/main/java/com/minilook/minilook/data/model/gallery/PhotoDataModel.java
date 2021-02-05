package com.minilook.minilook.data.model.gallery;

import android.os.Parcel;
import android.os.Parcelable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor public class PhotoDataModel implements Parcelable {
    private String name;
    private String uriPath;
    private String filePath;
    private boolean isSelect;
    private int selectPosition;

    protected PhotoDataModel(Parcel in) {
        name = in.readString();
        uriPath = in.readString();
        filePath = in.readString();
        isSelect = in.readByte() != 0;
        selectPosition = in.readInt();
    }

    public static final Creator<PhotoDataModel> CREATOR = new Creator<PhotoDataModel>() {
        @Override
        public PhotoDataModel createFromParcel(Parcel in) {
            return new PhotoDataModel(in);
        }

        @Override
        public PhotoDataModel[] newArray(int size) {
            return new PhotoDataModel[size];
        }
    };

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(uriPath);
        parcel.writeString(filePath);
        parcel.writeByte((byte) (isSelect ? 1 : 0));
        parcel.writeInt(selectPosition);
    }
}
