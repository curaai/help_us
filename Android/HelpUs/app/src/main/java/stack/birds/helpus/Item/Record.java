package stack.birds.helpus.Item;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dsm2016 on 2017-08-01.
 */

public class Record extends RealmObject {
    @PrimaryKey
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

    public String getStringFileDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy mm dd");
        return format.format(fileDate);
    }

}

