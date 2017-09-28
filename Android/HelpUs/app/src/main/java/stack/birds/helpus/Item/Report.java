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
    private String receivers;
    private String music1;
    private String music2;
    private String music3;
    private String image1;
    private String image2;
    private String image3;

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

    public String getReceivers() {
        return receivers;
    }

    public void setReceivers(String receivers) {
        this.receivers = receivers;
    }

    public String getMusic1() {
        return music1;
    }

    public void setMusic1(String music1) {
        this.music1 = music1;
    }

    public String getMusic2() {
        return music2;
    }

    public void setMusic2(String music2) {
        this.music2 = music2;
    }

    public String getMusic3() {
        return music3;
    }

    public void setMusic3(String music3) {
        this.music3 = music3;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = image3;
    }
}