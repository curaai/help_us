package stack.birds.helpus.Item;

import java.util.Date;

/**
 * Created by dsm2016 on 2017-08-01.
 */

public class Record {
    private String fileName;
    private String filePath;
    private Date fileDate;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getFileDate() {
        return fileDate;
    }

    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

}

