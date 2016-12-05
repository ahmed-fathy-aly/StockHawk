package com.udacity.stockhawk.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import timber.log.Timber;

/**
 * Created by ahmed on 12/4/2016.
 */

public class StockHistory implements Parcelable {
    private String name;
    private List<Entry> history;

    public StockHistory(String name, String historyStr) {
        this.name = name;
        this.history = parseHistory(historyStr);
    }

    protected StockHistory(Parcel in) {
        name = in.readString();
        history = in.createTypedArrayList(Entry.CREATOR);
    }

    public static final Creator<StockHistory> CREATOR = new Creator<StockHistory>() {
        @Override
        public StockHistory createFromParcel(Parcel in) {
            return new StockHistory(in);
        }

        @Override
        public StockHistory[] newArray(int size) {
            return new StockHistory[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entry> getHistory() {
        return history;
    }

    public void setHistory(List<Entry> history) {
        this.history = history;
    }


    public static List<Entry> parseHistory(String str) {
        try {
            StringTokenizer tok = new StringTokenizer(str.replace(",", ""));
            List<Entry> entries = new ArrayList<>();
            while (tok.hasMoreTokens()) {
                long time = Long.parseLong(tok.nextToken());
                double value = Double.parseDouble(tok.nextToken());
                entries.add(new Entry(time, value));
            }
            return entries;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeTypedList(history);
    }


    public static class Entry implements Parcelable, Comparable {
        private long time;
        private double value;

        public Entry(long time, double value) {
            this.time = time;
            this.value = value;
        }

        protected Entry(Parcel in) {
            time = in.readLong();
            value = in.readDouble();
        }

        public static final Creator<Entry> CREATOR = new Creator<Entry>() {
            @Override
            public Entry createFromParcel(Parcel in) {
                return new Entry(in);
            }

            @Override
            public Entry[] newArray(int size) {
                return new Entry[size];
            }
        };

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(time);
            parcel.writeDouble(value);
        }

        @Override
        public int compareTo(Object o) {
            StockHistory.Entry other = (Entry) o;
            return Long.compare(this.time, other.getTime());
        }
    }
}
