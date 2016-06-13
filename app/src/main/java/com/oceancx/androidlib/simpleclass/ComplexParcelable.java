package com.oceancx.androidlib.simpleclass;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by bilibili on 2016/6/13.
 */
public class ComplexParcelable implements Parcelable {
    private String name;

    private List<SubComplexObject> objects;

    /**
     * 添加默认构造方法 for fastjson 解析json字符串
     */
    public ComplexParcelable() {
    }

    protected ComplexParcelable(Parcel in) {
        name = in.readString();
        in.readList(objects, SubComplexObject.class.getClassLoader());
    }

    public static final Creator<ComplexParcelable> CREATOR = new Creator<ComplexParcelable>() {
        @Override
        public ComplexParcelable createFromParcel(Parcel in) {
            return new ComplexParcelable(in);
        }

        @Override
        public ComplexParcelable[] newArray(int size) {
            return new ComplexParcelable[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeList(objects);
    }

    /**
     * 要实现implements Parcelable 这里为了实例 就省略了
     */
    private class SubComplexObject { //implements Parcelable {
    }
}
