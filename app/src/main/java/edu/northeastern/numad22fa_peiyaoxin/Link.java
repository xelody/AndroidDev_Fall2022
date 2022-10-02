package edu.northeastern.numad22fa_peiyaoxin;

import android.os.Parcel;
import android.os.Parcelable;

public class Link implements Parcelable {
    private String name;
    private String URL;

    public Link(String name, String URL) {
        this.name = name;
        this.URL = URL;
    }

    public String getName() {
        return this.name;
    }

    public String getURL() {
        return this.URL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(URL);
    }
}
