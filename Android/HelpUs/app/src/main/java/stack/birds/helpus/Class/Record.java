package stack.birds.helpus.Class;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dsm2016 on 2017-08-01.
 */

public class Record {
    private String fileName;
    private Date modifyDate;

    public Record() {}

    public Record(String fileName, Date modifyDate) {
        this.fileName = fileName;
        this.modifyDate = modifyDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getmodifyDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd HH:mm");
        return format.format(modifyDate);
    }

    public void setmodifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}
