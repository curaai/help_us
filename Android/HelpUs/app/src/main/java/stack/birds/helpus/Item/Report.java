package stack.birds.helpus.Item;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by dsm2016 on 2017-08-02.
 */

public class Report extends RealmObject {
    @PrimaryKey
    private String title;
    private String content;
    private String fileList;
    private String receivers;
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

    public String getFileList() {
        return fileList;
    }

    public void setFileList(String fileList) {
        this.fileList = fileList;
    }

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public boolean isANONYMOUS() {
        return ANONYMOUS;
    }

    public void setANONYMOUS(boolean ANONYMOUS) {
        this.ANONYMOUS = ANONYMOUS;
    }
}