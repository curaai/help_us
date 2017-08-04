package stack.birds.helpus.Helper;

/**
 * Created by sch on 2017-07-31.
 */

public class ContractHelpUsDB {

    private ContractHelpUsDB() {} ;

    public static final String DB_NAME = "helpus_user.db";
    public static final int DB_VERSION = 1;
    public static final String TBL_USER = "USER";
    public static final String COL_USER_ID = "ID";
    public static final String COL_USER_NAME = "NAME";
    public static final String COL_USER_PHONE_NUM = "PHONE_NUM";

    public static final String TBL_REPORT = "REPORT";
    public static final String COL_REPORT_TITLE = "TITLE";
    public static final String COL_REPORT_CONTENT = "CONTENT";
    public static final String COL_REPORT_FILE = "FILE_PATH";
    public static final String COL_REPORT_FIRST_PLACE = "FIRST_PLACE";
    public static final String COL_REPORT_LAST_PLACE = "LAST_PLACE";
    public static final String COL_REPORT_DATE = "DATE";
    public static final String COL_REPORT_ACCIDENT_DATE = "ACCIDENT_DATE";
    public static final String COL_REPORT_RECEIVCERS = "RECEIVERS";
    public static final String COL_REPORT_ANONYMOUS = "ANONYMOUS";

    // 유저 목록 생성
    public static final String SQL_CREATE_USER_TBL = "CREATE TABLE IF NOT EXISTS " + TBL_USER + " " +
            "(" +
                COL_USER_ID +           "TEXT NOT NULL" +  ", " +
                COL_USER_NAME +         "TEXT NOT NULL" +  ", " +
                COL_USER_PHONE_NUM +    "TEXT NOT NULL" +
            ")" ;

    public static final String SQL_DROP_USER_TBL = "DROP TABLE IF EXISTS " + TBL_USER;

    public static final String SQL_CREATE_REPORT_TBL = "CREATE TABLE IF NOT EXIST " + TBL_REPORT + " " +
            "(" +
                COL_REPORT_TITLE +          "TEXT NOT NULL"     +  ", " +
                COL_REPORT_CONTENT +        "TEXT NOT NULL"     +  ", " +
                COL_REPORT_FILE +           "TEXT NOT NULL"     +  ", " +
                COL_REPORT_FIRST_PLACE +    "TEXT NOT NULL"     +  ", " +
                COL_REPORT_LAST_PLACE +     "TEXT NOT NULL"     +  ", " +
                COL_REPORT_DATE +           "TEXT NOT NULL"     +  ", " +
                COL_REPORT_ACCIDENT_DATE +  "TEXT NOT NULL"     +  ", " +
                COL_REPORT_RECEIVCERS +     "TEXT NOT NULL"     +  ", " +
                COL_REPORT_ANONYMOUS +      "INTEGER NOT NULL " +
            ")" ;

    public static final String SQL_DROP_REPORT_TBL = "DROP TABLE IF EXISTS " + TBL_REPORT;
}
