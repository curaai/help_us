package stack.birds.helpus.Item;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dsm2016 on 2017-09-27.
 */

public class Receive extends RealmObject {
    @PrimaryKey
    private String reporter;
    private String title;
    private String content;
    private Date reportDate;
    private Date accidentDate;
    private String[] reportData;

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

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

    public String[] getReportData() {
        return reportData;
    }

    public void setReportData(String[] reportData) {
        this.reportData = reportData;
    }
}
