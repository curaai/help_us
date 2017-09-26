package stack.birds.helpus.Item;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dsm2016 on 2017-08-02.
 */

public class Report extends RealmObject {
    @PrimaryKey
    private String title;
    private String content;
    private String[] fileList;
    private String[] receivers;
    private Date reportDate;
    private Date accidentDate;
    private boolean ANONYMOUS;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getFileList() {
        return fileList;
    }

    public void setFileList(String[] fileList) {
        this.fileList = fileList;
    }

    public String[] getReceivers() {
        return receivers;
    }

    public void setReceivers(String[] receivers) {
        this.receivers = receivers;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Date getAccidentDate() {
        return accidentDate;
    }

    public void setAccidentDate(Date accidentDate) {
        this.accidentDate = accidentDate;
    }

    public boolean isANONYMOUS() {
        return ANONYMOUS;
    }

    public void setANONYMOUS(boolean ANONYMOUS) {
        this.ANONYMOUS = ANONYMOUS;
    }
}