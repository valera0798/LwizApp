package com.evlishelper.lwizapp.activities.notes;

import android.os.Parcel;
import android.os.Parcelable;

class Note implements Parcelable {
    String title;
    public String text;
    long date;
    long createTime;
    public int color;

    Note(String title, String text, long date, int color, long createTime) {
        this.title = title;
        this.text = text;
        this.date = date;
        this.color = color;
        this.createTime = createTime;
    }

    private Note(Parcel p) {
        date = p.readLong();
        color = p.readInt();
        createTime = p.readLong();
        title = p.readString();
        text = p.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(date);
        dest.writeInt(color);
        dest.writeLong(createTime);
        dest.writeString(title);
        dest.writeString(text);
    }

    public static final Parcelable.Creator<Note> CREATOR
            = new Parcelable.Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
