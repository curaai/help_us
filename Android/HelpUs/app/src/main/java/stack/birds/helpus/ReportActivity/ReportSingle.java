package stack.birds.helpus.ReportActivity;

import java.util.ArrayList;

/**
 * Created by dsm2016 on 2017-09-28.
 */

public class ReportSingle {
    private static ReportSingle mInstance = null;

    private ArrayList<String> picture;
    private ArrayList<String> record;

    public static ReportSingle getInstance() {
        if (mInstance == null) {
            mInstance = new ReportSingle();
        }
        return mInstance;
    }

    public ArrayList<String> getPicture() {
        return picture;
    }

    public void setPicture(ArrayList<String> picture) {
        this.picture = picture;
    }

    public ArrayList<String> getRecord() {
        return record;
    }

    public void setRecord(ArrayList<String> record) {
        this.record = record;
    }
}