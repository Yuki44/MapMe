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

//    private static Context context;
//    private static DatabaseHelper m_instance;
//    private SQLiteDatabase db;


    public DatabaseHelper(Context context) {
//        DatabaseHelper.context = context;
//        OpenHelper openHelper = new OpenHelper(DatabaseHelper.context);
//        this.db = openHelper.getWritableDatabase();
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

        //        db.execSQL("CREATE TABLE " + FRIENDS_TABLE
        //                + " (id INTEGER PRIMARY KEY, fullName TEXT,address TEXT, email TEXT, website TEXT, birthday TEXT,"
        //                + " phoneNumb TEXT, profileImage TEXT,latitude TEXT,longitude TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FRIENDS_TABLE);
        onCreate(db);
    }

//    public static void setContext(Context c)
//    {
//        context = c;
//    }
//
//    public static DatabaseHelper getInstance(){
//
//        if (m_instance == null) m_instance = new DatabaseHelper(context);
//        return m_instance;
//
//    }
//
//    public void deleteAll() {
//
//        this.db.delete(FRIENDS_TABLE, null, null);
//    }
//
//    public void deleteById(long id)
//    {
//        this.db.delete(FRIENDS_TABLE, "id = " + id, null);
//    }
//
//    public Friends getByIndex(int index)
//    {
//        return getAllInfo().get(index);
//    }
//
//    public boolean insert(String fullName, String email, String website, String address, String birthday, String phone, String profileImage,double lat,double lng)
//    {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("fullName",fullName);
//        contentValues.put("email",email);
//        contentValues.put("website",website);
//        contentValues.put("address",address);
//        contentValues.put("birthday",birthday);
//        contentValues.put("phoneNumb",phone);
//        contentValues.put("profileImage", profileImage);
//        contentValues.put("latitude",lat);
//        contentValues.put("longitude",lng);
//        db.insert("friend_table", null,contentValues);
//        return true;
//    }
//
//    public boolean updatesniFriend(Integer id, String fullName, String email, String website, String address, String birthday, String phone, String profileImage,double lat,double lng)
//    {
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("fullName",fullName);
//        contentValues.put("email",email);
//        contentValues.put("website",website);
//        contentValues.put("address",address);
//        contentValues.put("birthday",birthday);
//        contentValues.put("phoneNumb",phone);
//        contentValues.put("profileImage", profileImage);
//        contentValues.put("latitude",lat);
//        contentValues.put("longitude",lng);
//        db.update("friends_table",contentValues,"id = ? ", new String[] { Integer.toString(id) } );
//        return true;
//    }
//    public ArrayList<Friends> getAllInfo() {
//        ArrayList<Friends> list = new ArrayList<Friends>();
//        Cursor cursor = this.db.query(FRIENDS_TABLE,
//                new String[]{"fullName", "address", "email", "website", "birthday", "phoneNumb", "profileImage", "latitude", "longitude"},
//                null, null, null, null, "fullName desc");
//        if (cursor.moveToFirst()) {
//            do {
//                list.add(new Friends(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3),
//                        cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getDouble(7), cursor.getDouble(8)));
//            } while (cursor.moveToNext());
//        }
//        if (!cursor.isClosed()) {
//            cursor.close();
//        }
//        return list;
//    }
//
//    private static class OpenHelper extends SQLiteOpenHelper {
//
//        OpenHelper(Context context) {
//            super(context, DATABASE_NAME, null, DATABASE_VERSION);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            db.execSQL("CREATE TABLE " + FRIENDS_TABLE + " (id INTEGER PRIMARY KEY, fullName TEXT,address TEXT, email TEXT, website TEXT, birthday TEXT," +
//                    " phoneNumb TEXT, profileImage TEXT,latitude TEXT,longitude TEXT)");
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db,
//                              int oldVersion, int newVersion) {
//
//            db.execSQL("DROP TABLE IF EXISTS " + FRIENDS_TABLE);
//            onCreate(db);
//        }
//    }

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

