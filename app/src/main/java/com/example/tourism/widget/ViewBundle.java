package com.example.tourism.widget;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * ViewPager的Bundle
 */
public class ViewBundle implements Parcelable {
    private ChildAutoViewPager viewPager;

    protected ViewBundle(Parcel in) {
    }

    public ViewBundle(ChildAutoViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public static final Creator<ViewBundle> CREATOR = new Creator<ViewBundle>() {
        @Override
        public ViewBundle createFromParcel(Parcel in) {
            return new ViewBundle(in);
        }

        @Override
        public ViewBundle[] newArray(int size) {
            return new ViewBundle[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public ChildAutoViewPager getViewPager() {
        return viewPager;
    }

    public void setViewPager(ChildAutoViewPager viewPager) {
        this.viewPager = viewPager;
    }
}
