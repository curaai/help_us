package stack.birds.helpus.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import stack.birds.helpus.Class.User;

/**
 * Created by sch on 2017-07-31.
 */

public class UserDBHelper extends SQLiteOpenHelper {

    private static SQLiteDatabase write_db, read_db;

    public UserDBHelper(Context context) {
        super(context, ContractHelpUsDB.DB_NAME, null, ContractHelpUsDB.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ContractHelpUsDB.SQL_CREATE_USER_TBL);

        write_db = getWritableDatabase();
        read_db = getReadableDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContractHelpUsDB.SQL_DROP_USER_TBL);

        onCreate(db);
    }

    // insert to user_table
    public void insert(String id, String name, String phoneNum) {
        ContentValues values = new ContentValues();

        values.put(ContractHelpUsDB.COL_USER_ID, id);
        values.put(ContractHelpUsDB.COL_USER_NAME, name);
        values.put(ContractHelpUsDB.COL_USER_PHONE_NUM, phoneNum);

        write_db.insert(ContractHelpUsDB.TBL_USER, null, values);
    }

    // delete from user_table
    public void delete(String ID, String NAME, String PHONE_NUM) {
        read_db.delete(ContractHelpUsDB.TBL_USER,
                        "ID=?, NAME=?, PHONE_NUM=?",
                        new String[] {ID, NAME, PHONE_NUM});
    }

    // select rom user_table
    public ArrayList<User> select() {
        Cursor cursor = read_db.query(ContractHelpUsDB.TBL_USER, null, null, null, null, null, null);

        ArrayList<User> users = new ArrayList<User>();

        while(cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(ContractHelpUsDB.COL_USER_ID));
            String name = cursor.getString(cursor.getColumnIndex(ContractHelpUsDB.COL_USER_NAME));
            String phoneNum = cursor.getString(cursor.getColumnIndex(ContractHelpUsDB.COL_USER_PHONE_NUM));

            User user = new User(id, name, phoneNum);
            users.add(user);
        }

        return users;
    }
}
