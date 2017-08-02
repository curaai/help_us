package stack.birds.helpus.Helper;

/**
 * Created by sch on 2017-07-31.
 */

public class ContractUserDB {

    private ContractUserDB() {} ;

    public static final String DB_NAME = "helpus_user.db";
    public static final int DB_VERSION = 1;
    public static final String TBL_USER = "USER";
    public static final String COL_USER_ID = "ID";
    public static final String COL_USER_NAME = "NAME";
    public static final String COL_USER_PHONE_NUM = "PHONE_NUM";

    // 유저 목록 생성
    public static final String SQL_CREATE_USER_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_USER + " " +
            "(" +
                COL_USER_ID +           "TEXT NOT NULL" +  ", " +
                COL_USER_NAME +         "TEXT NOT NULL" +  ", " +
                COL_USER_PHONE_NUM +    "TEXT NOT NULL" +
            ")" ;

    public static final String SQL_DROP_USER_TBL = "DROP TABLE IF EXISTS " + TBL_USER;
}
