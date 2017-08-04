package stack.birds.helpus.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import stack.birds.helpus.Class.Report;

/**
 * Created by dsm2016 on 2017-08-02.
 */

public class ReportDBHelper extends SQLiteOpenHelper {

    private static SQLiteDatabase write_db, read_db;

    public ReportDBHelper(Context context) {
        super(context, ContractHelpUsDB.DB_NAME, null, ContractHelpUsDB.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContractHelpUsDB.SQL_CREATE_REPORT_TBL);

        write_db = getWritableDatabase();
        read_db = getReadableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContractHelpUsDB.SQL_DROP_REPORT_TBL);

        onCreate(db);
    }

    // insert to report_table
    public void insert(String title, String content, String filePath, String[] receivers, String accidentDate,
                       String firstPlace, String lastPlace, String reportDate, int isAnonymous) {

        String str_receiver = TextUtils.join(",", receivers);

        ContentValues values = new ContentValues();

        values.put(ContractHelpUsDB.COL_REPORT_TITLE, title);
        values.put(ContractHelpUsDB.COL_REPORT_CONTENT, content);
        values.put(ContractHelpUsDB.COL_REPORT_FILE, filePath);
        values.put(ContractHelpUsDB.COL_REPORT_RECEIVCERS, str_receiver);
        values.put(ContractHelpUsDB.COL_REPORT_FIRST_PLACE, firstPlace);
        values.put(ContractHelpUsDB.COL_REPORT_LAST_PLACE, lastPlace);
        values.put(ContractHelpUsDB.COL_REPORT_DATE, reportDate);
        values.put(ContractHelpUsDB.COL_REPORT_ACCIDENT_DATE, accidentDate);
        values.put(ContractHelpUsDB.COL_REPORT_ANONYMOUS, isAnonymous);

        write_db.insert(ContractHelpUsDB.TBL_REPORT, null, values);
    }

    // delete from report_table
    public void delete(String title, String filePath, String date) {

        write_db.delete(ContractHelpUsDB.TBL_REPORT,
                        ContractHelpUsDB.COL_REPORT_TITLE       + "=?, " +
                        ContractHelpUsDB.COL_REPORT_DATE        + "=?, " +
                        ContractHelpUsDB.COL_REPORT_FILE        + "=?",
                        new String[] {title, filePath, date});
    }

    public List<Report> select() {
        List<Report> reportList = new ArrayList<Report>();
        String title, content, filePath, firstPlace, lastPlace;
        String[] receivers;
        Date reportDate = null, accidentDate = null;
        int anonymous;

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");

        Cursor cursor = read_db.query(ContractHelpUsDB.TBL_REPORT, null, null, null, null, null, null);

        while(cursor.moveToNext()) {
            title = cursor.getString(cursor.getColumnIndex(ContractHelpUsDB.COL_REPORT_TITLE));
            content = cursor.getString(cursor.getColumnIndex(ContractHelpUsDB.COL_REPORT_CONTENT));
            filePath = cursor.getString(cursor.getColumnIndex(ContractHelpUsDB.COL_REPORT_FILE));
            receivers = cursor.getString(cursor.getColumnIndex(ContractHelpUsDB.COL_REPORT_FILE)).split(",");
            firstPlace = cursor.getString(cursor.getColumnIndex(ContractHelpUsDB.COL_REPORT_FIRST_PLACE));
            lastPlace = cursor.getString(cursor.getColumnIndex(ContractHelpUsDB.COL_REPORT_LAST_PLACE));
            anonymous = cursor.getInt(cursor.getColumnIndex(ContractHelpUsDB.COL_REPORT_ANONYMOUS));

            try {
                reportDate = format.parse(cursor.getString(cursor.getColumnIndex(ContractHelpUsDB.COL_REPORT_DATE)));
                accidentDate = format.parse(cursor.getString(cursor.getColumnIndex(ContractHelpUsDB.COL_REPORT_ACCIDENT_DATE)));

            } catch (ParseException e) {
                e.printStackTrace();
            }

            Report report = new Report(title, content, filePath, receivers, firstPlace, lastPlace, reportDate, accidentDate, anonymous);
            reportList.add(report);
        }

        return reportList;
    }
}