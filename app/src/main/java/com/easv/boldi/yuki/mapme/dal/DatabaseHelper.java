package com.easv.boldi.yuki.mapme.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.easv.boldi.yuki.mapme.entities.Friends;

import java.util.ArrayList;

/**
 * Created by boldi on 2018. 03. 19..
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "friends.db";
    private static final int DATABASE_VERSION = 6;
    private static final String FRIENDS_TABLE = "friend_table";
    private static final String FRIENDS_COLUMN_ID = "ID";
    private static final String FRIENDS_COLUMN_FULLNAME = "FULL_NAME";
    private static final String FRIENDS_COLUMN_PHONE = "PHONE_NUMBER";
    private static final String FRIENDS_COLUMN_ADDRESS = "ADDRESS";
    private static final String FRIENDS_COLUMN_PROFILEIMAGE = "PROFILE_PHOTO";
    private static final String FRIENDS_COLUMN_BIRTHDAY = "BIRTHDAY";
    private static final String FRIENDS_COLUMN_EMAIL = "EMAIL";
    private static final String FRIENDS_COLUMN_WEBSITE = "WEBSITE";
    private static final String FRIENDS_COLUMN_LATITUDE = "LATITUDE";
    private static final String FRIENDS_COLUMN_LONGITUDE = "LONGITUDE";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " +
                FRIENDS_TABLE + " ( " +
                FRIENDS_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FRIENDS_COLUMN_FULLNAME + " TEXT, " +
                FRIENDS_COLUMN_PHONE + " TEXT, " +
                FRIENDS_COLUMN_ADDRESS + " TEXT, " +
                FRIENDS_COLUMN_PROFILEIMAGE + " TEXT, " +
                FRIENDS_COLUMN_BIRTHDAY + " TEXT, " +
                FRIENDS_COLUMN_EMAIL + " TEXT, " +
                FRIENDS_COLUMN_WEBSITE + " TEXT, " +
                FRIENDS_COLUMN_LATITUDE + " TEXT, " +
                FRIENDS_COLUMN_LONGITUDE + " TEXT )";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FRIENDS_TABLE);
        onCreate(db);
    }

//

    // -----------------------------------------------------------------------------
    // MY OWN CREATION
    // -----------------------------------------------------------------------------
    //                  ADD

    /**
     * Insert a new contact into the database
     *
     * @param friend
     * @return
     */
    public boolean addFriend(Friends friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FRIENDS_COLUMN_FULLNAME, friend.getName());
        contentValues.put(FRIENDS_COLUMN_PHONE, friend.getPhone());
        contentValues.put(FRIENDS_COLUMN_ADDRESS, friend.getAddress());
        contentValues.put(FRIENDS_COLUMN_PROFILEIMAGE, friend.getProfileImage());
        contentValues.put(FRIENDS_COLUMN_BIRTHDAY, friend.getBirthday());
        contentValues.put(FRIENDS_COLUMN_EMAIL, friend.getEmail());
        contentValues.put(FRIENDS_COLUMN_WEBSITE, friend.getWebsite());
        contentValues.put(FRIENDS_COLUMN_LATITUDE, friend.getLatitude());
        contentValues.put(FRIENDS_COLUMN_LONGITUDE, friend.getLongitude());

        long result = db.insert(FRIENDS_TABLE, null, contentValues);

        return result != -1;
    }
    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    //                  GET ALL

    /**
     * Retrieve all contacts from database
     *
     * @return
     */
    public ArrayList<Friends> getAllInfo() {
        ArrayList<Friends> list = new ArrayList<Friends>();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + FRIENDS_TABLE, null);
        while (cursor.moveToNext()) {
            list.add(new Friends(
                    cursor.getString(1), // Name
                    cursor.getString(2), // Phone Number
                    cursor.getString(3), // Address
                    cursor.getString(4), // Profile Image
                    cursor.getString(5), // Birthday
                    cursor.getString(6), // Email
                    cursor.getString(7), // Website
                    cursor.getString(8), // Latitude
                    cursor.getString(9)  // Longitude
            ));
        }
        return list;
    }

    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    //                  GET BY ID

    /**
     * Retrieve the contact unique id from the database using @param
     *
     * @param friend
     * @return
     */
    public Cursor getFriendID(Friends friend) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "SELECT * FROM " + FRIENDS_TABLE +
                " WHERE " + FRIENDS_COLUMN_FULLNAME + " = '" + friend.getName() + "'" +
                " AND " + FRIENDS_COLUMN_PHONE + " = '" + friend.getPhone() + "'";
        return db.rawQuery(sql, null);
    }


    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    //                  UPDATE
    public boolean updateLocation(int id, double lat, double lng, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FRIENDS_COLUMN_ADDRESS,address);
        contentValues.put(FRIENDS_COLUMN_LATITUDE, lat);
        contentValues.put(FRIENDS_COLUMN_LONGITUDE, lng);

        int update = db.update(FRIENDS_TABLE, contentValues,
                FRIENDS_COLUMN_ID + " = ? ", new String[]{String.valueOf(id)});

        return update == 1;}
    /**
     * Update a contact where id = @param 'id'
     * Replace the current contact with @param 'contact'
     *
     * @param friend
     * @param id
     * @return
     */

    public boolean updateFriend(Friends friend, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FRIENDS_COLUMN_FULLNAME, friend.getName());
        contentValues.put(FRIENDS_COLUMN_PHONE, friend.getPhone());
        contentValues.put(FRIENDS_COLUMN_ADDRESS, friend.getAddress());
        contentValues.put(FRIENDS_COLUMN_PROFILEIMAGE, friend.getProfileImage());
        contentValues.put(FRIENDS_COLUMN_BIRTHDAY, friend.getBirthday());
        contentValues.put(FRIENDS_COLUMN_EMAIL, friend.getEmail());
        contentValues.put(FRIENDS_COLUMN_WEBSITE, friend.getWebsite());
        contentValues.put(FRIENDS_COLUMN_LATITUDE, friend.getLatitude());
        contentValues.put(FRIENDS_COLUMN_LONGITUDE, friend.getLongitude());

        int update = db.update(FRIENDS_TABLE, contentValues,
                FRIENDS_COLUMN_ID + " = ? ", new String[]{String.valueOf(id)});

        return update == 1;
    }

    // -----------------------------------------------------------------------------
    // -----------------------------------------------------------------------------
    //                  DELETE

    /**
     * Delete the contact by its id
     *
     * @param id
     * @return
     */
    public Integer deleteFriend(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(FRIENDS_TABLE, "ID = ?", new String[]{String.valueOf(id)});
    }



}

