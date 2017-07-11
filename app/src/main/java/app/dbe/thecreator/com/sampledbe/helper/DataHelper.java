package app.dbe.thecreator.com.sampledbe.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User2 on 21-09-2016.
 */
public class DataHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;

    public static final String DATABASE_NAME = "sampledbe.db";

    public static final int DATABASE_VERSION = 1;

    public static final String USER_TABLE = "User";
    public static final String USER_COLUMN_USER_KEY = "UserKey";
    public static final String USER_COLUMN_MOBILE_NO = "MobileNo";
    public static final String USER_COLUMN_EMAIL_ID = "EmailId";
    public static final String USER_COLUMN_PASSWORD = "Password";
    public static final String USER_COLUMN_NAME = "Name";
    public static final String USER_COLUMN_REF_CH_KEY = "RefcardHolderKey";
    public static final String USER_COLUMN_EMAIL_COUNT = "EmailCount";
    public static final String USER_COLUMN_MOBILE_COUNT = "MobileCount";
    public static final String USER_COLUMN_RESULT = "Result";
    public static final String USER_COLUMN_UPLOAD_PATH = "UploadPath";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(" CREATE TABLE " + USER_TABLE + " (" +
                USER_COLUMN_USER_KEY + " TEXT, " +
                USER_COLUMN_MOBILE_NO + " TEXT, " +
                USER_COLUMN_EMAIL_ID + " TEXT, " +
                USER_COLUMN_PASSWORD + " TEXT, " +
                USER_COLUMN_NAME + " TEXT, " +
                USER_COLUMN_REF_CH_KEY + " TEXT, " +
                USER_COLUMN_EMAIL_COUNT + " TEXT, " +
                USER_COLUMN_MOBILE_COUNT + " TEXT, " +
                USER_COLUMN_RESULT + " TEXT, " +
                USER_COLUMN_UPLOAD_PATH + " TEXT);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int version_old, int current_version) {
        try {

            onCreate(db);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteDb() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from User");
    }

    public Cursor getUserDetails() {
        SQLiteDatabase database = this.getWritableDatabase();
        String selectQuery = "SELECT  * FROM " + USER_TABLE;
        return database.rawQuery(selectQuery, null);
    }

    /*Function to insert user details to database*/
    public String insertUserDetails(String userKey, String mobileNo, String emailId, String password, String name, String refcardHolderKey, String emailCount, String mobileCount, String result, String uploadPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Str_Return_Value = null;
        long query_ret;
        try {
            ContentValues values = new ContentValues();
            values.put(USER_COLUMN_USER_KEY, userKey);
            values.put(USER_COLUMN_MOBILE_NO, mobileNo);
            values.put(USER_COLUMN_EMAIL_ID, emailId);
            values.put(USER_COLUMN_PASSWORD, password);
            values.put(USER_COLUMN_NAME, name);
            values.put(USER_COLUMN_REF_CH_KEY, refcardHolderKey);
            values.put(USER_COLUMN_EMAIL_COUNT, emailCount);
            values.put(USER_COLUMN_MOBILE_COUNT, mobileCount);
            values.put(USER_COLUMN_RESULT, result);
            values.put(USER_COLUMN_UPLOAD_PATH, uploadPath);
            query_ret = db.insertWithOnConflict(USER_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            if (query_ret != -1) {
                Str_Return_Value = "success";
            } else {
                Str_Return_Value = "failure";
            }
        } catch (Exception e) {
        }
        db.close();
        return Str_Return_Value;
    }
    /*public String insertUserDetails(String userKey, String mobileNo, String emailId, String password, String name, String refcardHolderKey, String emailCount, String mobileCount, String result, String uploadPath) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Str_Return_Value = null;
        long query_ret;
        try {
            ContentValues values = new ContentValues();
            values.put(USER_COLUMN_USER_KEY, userKey);
            values.put(USER_COLUMN_MOBILE_NO, mobileNo);
            values.put(USER_COLUMN_EMAIL_ID, emailId);
            values.put(USER_COLUMN_PASSWORD, password);
            values.put(USER_COLUMN_NAME, name);
            values.put(USER_COLUMN_REF_CH_KEY, refcardHolderKey);
            values.put(USER_COLUMN_EMAIL_COUNT, emailCount);
            values.put(USER_COLUMN_MOBILE_COUNT, mobileCount);
            values.put(USER_COLUMN_RESULT, result);
            values.put(USER_COLUMN_UPLOAD_PATH, uploadPath);
            query_ret = db.insertWithOnConflict(USER_TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
            if (query_ret != -1) {
                Str_Return_Value = "success";
            } else {
                Str_Return_Value = "failure";
            }
        } catch (Exception e) {
        }
        db.close();
        return Str_Return_Value;
    }*/
}


