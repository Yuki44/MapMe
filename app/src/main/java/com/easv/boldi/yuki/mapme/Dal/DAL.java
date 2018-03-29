package com.easv.boldi.yuki.mapme.Dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.easv.boldi.yuki.mapme.Entities.Friends;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by boldi on 2018. 03. 19..
 */

public class DAL {
    private static final String DATABASE_NAME = "Friends.db";
    private static final int DATABASE_VERSION = 1;
    private static final String FRIENDS_TABLE = "friend_table";
    private static final String FRIENDS_COLUMN_ID = "id";
    private static final String FRIENDS_COLUMN_FULLNAME = "fullName";
    private static final String FRIENDS_COLUMN_EMAIL = "email";
    private static final String FRIENDS_COLUMN_WEBSITE = "website";
    private static final String FRIENDS_COLUMN_ADDRESS = "address";
    private static final String FRIENDS_COLUMN_BIRTHDAY = "birthday";
    private static final String FRIENDS_COLUMN_PHONE = "phoneNumb";
    private static final String FRIENDS_COLUMN_PROFILEIMAGE = "profileImage";

    private static Context context;
    private static DAL m_instance;
    private SQLiteDatabase db;


    public DAL(Context context) {
        DAL.context = context;
        OpenHelper openHelper = new OpenHelper(DAL.context);
        this.db = openHelper.getWritableDatabase();
    }

    public static void setContext(Context c)
    {
        context = c;
    }

    public static DAL getInstance(){

        if (m_instance == null) m_instance = new DAL(context);
        return m_instance;

    }

    public void deleteAll() {

        this.db.delete(FRIENDS_TABLE, null, null);
    }

    public void deleteById(long id)
    {
        this.db.delete(FRIENDS_TABLE, "id = " + id, null);
    }

    public Friends getByIndex(int index)
    {
        return getAllInfo().get(index);
    }

    public boolean insert(String fullName, String email, String website, String address, String birthday, String phone, String profileImage)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullName",fullName);
        contentValues.put("email",email);
        contentValues.put("website",website);
        contentValues.put("address",address);
        contentValues.put("birthday",birthday);
        contentValues.put("phoneNumb",phone);
        contentValues.put("profileImage", profileImage);
        db.insert("friend_table", null,contentValues);
        return true;
    }

    public boolean updateFriend(Integer id, String fullName, String email, String website, String address, String birthday, String phone, String profileImage)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullName",fullName);
        contentValues.put("email",email);
        contentValues.put("website",website);
        contentValues.put("address",address);
        contentValues.put("birthday",birthday);
        contentValues.put("phoneNumb",phone);
        contentValues.put("profileImage", profileImage);
        db.update("friends_table",contentValues,"id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public List<Friends> getAllInfo() {
        List<Friends> list = new ArrayList<Friends>();
        Cursor cursor = this.db.query(FRIENDS_TABLE,
                new String[]{"id", "fullName", "email", "website", "address", "birthday", "phoneNumb", "profileImage"},
                null, null, null, null, "fullName desc");
        if (cursor.moveToFirst()) {
            do {
                list.add(new Friends(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
            } while (cursor.moveToNext());
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        return list;
    }

    private static class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + FRIENDS_TABLE + " (id INTEGER PRIMARY KEY, fullName TEXT, email TEXT, website TEXT, address TEXT, birthday TEXT, phoneNumb TEXT, profileImage TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + FRIENDS_TABLE);
            onCreate(db);
        }
    }
}

