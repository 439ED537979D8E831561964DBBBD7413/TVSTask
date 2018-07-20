package com.sample.marmu.tvstask.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TableModel {

    /*@SerializedName("TABLE_DATA")
    @Expose
    private TABLEDATA tABLEDATA;

    public TABLEDATA getTABLEDATA() {
        return tABLEDATA;
    }

    public void setTABLEDATA(TABLEDATA tABLEDATA) {
        this.tABLEDATA = tABLEDATA;
    }


    public class TABLEDATA {

        @SerializedName("data")
        @Expose
        private List<List<String>> data = null;

        public List<List<String>> getData() {
            return data;
        }

        public void setData(List<List<String>> data) {
            this.data = data;
        }

    }*/


    @SerializedName("TABLE_DATA")
    @Expose
    private String tABLEDATA;

    public String getTABLEDATA() {
        return tABLEDATA;
    }

    public void setTABLEDATA(String tABLEDATA) {
        this.tABLEDATA = tABLEDATA;
    }

}