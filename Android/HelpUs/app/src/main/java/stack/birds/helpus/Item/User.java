package stack.birds.helpus.Item;

/**
 * Created by sch on 2017-07-31.
 */

public class User {
    private String name, id, phoneNum;

    public User(String name, String id, String phoneNum) {
        this.name = name;
        this.id = id;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
