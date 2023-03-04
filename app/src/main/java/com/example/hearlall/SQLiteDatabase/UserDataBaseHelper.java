package com.example.hearlall.SQLiteDatabase;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserDataBaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USER_USERNAME = "USER_USERNAME";
    public static final String COLUMN_USER_EMAIL = "USER_EMAIL";
    public static final String COLUMN_USER_MOBILENUMBER = "USER_MOBILENUMBER";
    public static final String COLUMN_USER_PASSWORD = "USER_PASSWORD";
    public static final String COLUMN_USER_FULLNAME = "USER_FULLNAME";
    public static final String COLUMN_USER_COUNTRY = "USER_COUNTRY";
    public static final String COLUMN_USER_DATEOFBIRTH = "USER_DATEOFBIRTH";


    public UserDataBaseHelper(@Nullable Context context) {
        super(context, "name", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_USERNAME + " TEXT, " + COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_MOBILENUMBER + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT, " + COLUMN_USER_FULLNAME + " TEXT, " + COLUMN_USER_COUNTRY + " TEXT, " + COLUMN_USER_DATEOFBIRTH + " TEXT)";

        db.execSQL(createUserTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOneUser(UserModel userModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USER_USERNAME, userModel.getUsername());
        cv.put(COLUMN_USER_EMAIL, userModel.getEmail());
        cv.put(COLUMN_USER_MOBILENUMBER, userModel.getMobileNumber());
        cv.put(COLUMN_USER_PASSWORD, userModel.getPassword());
        cv.put(COLUMN_USER_FULLNAME, userModel.getFullName());
        cv.put(COLUMN_USER_COUNTRY, userModel.getCountry());
        cv.put(COLUMN_USER_DATEOFBIRTH, userModel.getDateOfBirth());


        long insert = db.insert(USER_TABLE, null, cv);
        return insert != -1;
    }

    public boolean DeleteOneUser(UserModel userModel)
    {
        // find customerModel in the database. if it found, delete it and return true.
        // if it is not found, return false.

        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "DELETE FROM" + USER_TABLE + "WHERE" + COLUMN_ID + " = " + userModel.getId();

        Cursor cursor = db.rawQuery(queryString, null);

        return cursor.moveToFirst();
    }

    public List<UserModel> getEveryUser()
    {
        List<UserModel> returnList = new ArrayList<>();

        // get data from the database

        String queryString = "SELECT * FROM " + USER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new customer objects. Put them into the return list.
            do {
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                String userEmail = cursor.getString(2);
                String userMobileNumber = cursor.getString(3);
                String userPassword = cursor.getString(4);
                String userFullName = cursor.getString(5);
                String userCountry = cursor.getString(6);
                String userDateOfBirth = cursor.getString(7);

                UserModel newUser = new UserModel(userID, userName, userEmail, userMobileNumber, userPassword, userFullName, userCountry, userDateOfBirth);
                returnList.add(newUser);
            } while (cursor.moveToNext());
        }
        else {
            // failure. do not add anything to the list.
        }

        // close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }

    public void updateData(UserModel userModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ID, userModel.getId());
        cv.put(COLUMN_USER_USERNAME, userModel.getUsername());
        cv.put(COLUMN_USER_EMAIL, userModel.getEmail());
        cv.put(COLUMN_USER_MOBILENUMBER, userModel.getMobileNumber());
        cv.put(COLUMN_USER_PASSWORD, userModel.getPassword());
        cv.put(COLUMN_USER_FULLNAME, userModel.getFullName());
        cv.put(COLUMN_USER_COUNTRY, userModel.getCountry());
        cv.put(COLUMN_USER_DATEOFBIRTH, userModel.getDateOfBirth());
        int myTable = db.update(USER_TABLE, cv, "ID = '" + userModel.getId() + "'", null);
        Log.e(TAG, "updateData: "+myTable);
    }
}
