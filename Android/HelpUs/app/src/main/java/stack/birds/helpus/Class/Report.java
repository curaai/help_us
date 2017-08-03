package stack.birds.helpus.Class;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dsm2016 on 2017-08-02.
 */

public class Report {
    private String title;
    private String content;
    private String filePath;
    private String firstPlace;
    private String lastPlace;
    private String[] receivers;
    private Date reportDate;
    private int ANONYMOUS;

    public Report(String title, String content, String filePath, String[] receivers,
                  String firstPlace, String lastPlace, Date reportDate, int ANONYMOUS) {
        this.title = title;
        this.content = content;
        this.filePath = filePath;
        this.receivers = receivers;
        this.firstPlace = firstPlace;
        this.lastPlace = lastPlace;
        this.reportDate = reportDate;
        this.ANONYMOUS = ANONYMOUS;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getFirstPlace() {
        return firstPlace;
    }

    public String getReceivers() {
        return TextUtils.join(", ", receivers);
    }

    public String getLastPlace() {
        return lastPlace;
    }

    public String getReportDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return format.format(reportDate);
    }

    public int getANONYMOUS() {
        return ANONYMOUS;
    }
}
