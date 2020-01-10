package com.example.enseirb_neudecknicolas_satomidavid.SQLDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

import com.example.enseirb_neudecknicolas_satomidavid.DataClasses.CurrentRun;
import com.example.enseirb_neudecknicolas_satomidavid.DataClasses.Run;
import com.example.enseirb_neudecknicolas_satomidavid.DataClasses.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class DatabankHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Database";

    // Contacts table name
    private static final String TABLE_USERS = "users";
    private static final String TABLE_RUNS = "runs";
    private static final String TABLE_CURRENT_RUN = "current_run";

    // User Table Columns names
    private static final String KEY_USERS_FIRST_NAME = "first_name";
    private static final String KEY_USERS_LAST_NAME = "last_name";
    private static final String KEY_USERS_GENDER = "gender";
    private static final String KEY_USERS_UNIQUE_ID = "id";

    // Runs Table Columns names
    private static final String KEY_RUNS_RUNNER = "runner";
    private static final String KEY_RUNS_DATE = "date";
    private static final String KEY_RUNS_LENGTH = "length";
    private static final String KEY_RUNS_SECONDS = "seconds";
    private static final String KEY_RUNS_AVG_SPEED = "avg_speed";
    private static final String KEY_RUNS_TOP_SPEED = "top_speed";
    private static final String KEY_RUNS_LOCATION = "location";

    // Current Run Table Columns names
    private static final String KEY_CURRENT_RUN_TIME = "time";
    private static final String KEY_CURRENT_RUN_LANG = "lang";
    private static final String KEY_CURRENT_RUN_LONG = "long";
    private static final String KEY_CURRENT_RUN_SPEED = "speed";


    public DatabankHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "";

        CREATE_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_USERS_FIRST_NAME + " TEXT,"
                + KEY_USERS_LAST_NAME + " TEXT,"
                + KEY_USERS_GENDER + " TEXT,"
                + KEY_USERS_UNIQUE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
                + ")";
        db.execSQL(CREATE_TABLE);

        Log.d("LocationList","wurde aufgerufen");
        CREATE_TABLE = "CREATE TABLE " + TABLE_RUNS + "("
                + KEY_RUNS_RUNNER + " integer,"
                + KEY_RUNS_DATE + " TEXT,"
                + KEY_RUNS_LENGTH + " integer,"
                + KEY_RUNS_SECONDS + " integer,"
                + KEY_RUNS_AVG_SPEED + " integer,"
                + KEY_RUNS_TOP_SPEED + " integer,"
                + KEY_RUNS_LOCATION + " TEXT"
                + ")";
        db.execSQL(CREATE_TABLE);

        CREATE_TABLE = "CREATE TABLE " + TABLE_CURRENT_RUN + "("
                + KEY_CURRENT_RUN_TIME + " TEXT,"
                + KEY_CURRENT_RUN_LANG + " REAL,"
                + KEY_CURRENT_RUN_LONG + " REAL,"
                + KEY_CURRENT_RUN_SPEED + " REAL"
                + ")";
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RUNS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENT_RUN);
        onCreate(db);
    }

    //ADDING ROWS

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_USERS_FIRST_NAME, user.getFirst_name());
        values.put(KEY_USERS_LAST_NAME, user.getLast_name());
        values.put(KEY_USERS_GENDER, user.getGender());

        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }


    public void addRun(Run run) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_RUNS_RUNNER, run.getUser().getId());
        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = iso8601Format.format(run.getDate());
        values.put(KEY_RUNS_DATE, date);
        values.put(KEY_RUNS_LENGTH, run.getLength());
        values.put(KEY_RUNS_SECONDS, run.getSeconds());
        values.put(KEY_RUNS_AVG_SPEED, run.getAverageSpeed());
        values.put(KEY_RUNS_TOP_SPEED, run.getTopSpeed());

        values.put(KEY_RUNS_LOCATION, run.getLocationList().toString());

        // Inserting Row
        db.insert(TABLE_RUNS, null, values);
        db.close(); // Closing database connection
    }

    public void addCurrentRun(CurrentRun currentRun) {
        SQLiteDatabase db = this.getWritableDatabase();
        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String date = iso8601Format.format(currentRun.getTime());
        ContentValues values = new ContentValues();
        values.put(KEY_CURRENT_RUN_TIME, date);
        values.put(KEY_CURRENT_RUN_LANG, currentRun.getLocation().getLatitude());
        values.put(KEY_CURRENT_RUN_LONG, currentRun.getLocation().getLongitude());
        if(currentRun.getLocation().hasSpeed()){
            values.put(KEY_CURRENT_RUN_SPEED, currentRun.getLocation().getSpeed());
        } else {
            values.put(KEY_CURRENT_RUN_SPEED, 0);
        }

        // Inserting Row
        db.insert(TABLE_CURRENT_RUN, null, values);
        db.close(); // Closing database connection
    }


    //TRUNKATE

    public void trunkateUsers(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS,null,null);
        db.close();
    }

    public void trunkateRuns(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RUNS,null,null);
        db.close();
    }

    public void trunkateCurrentRun(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CURRENT_RUN,null,null);
        db.close();
    }

    //GETTING
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
                user.setId(cursor.getInt(3));
                // Adding contact to list
                list.add(user);
            } while (cursor.moveToNext());
        }

        // return contact list
        return list;
    }

    public User getUserById(int id){
        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE " + KEY_USERS_UNIQUE_ID + " == " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        User user = null;

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                user = new User(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
                user.setId(cursor.getInt(3));
                // Adding contact to list
            } while (cursor.moveToNext());
        }

        // return contact list
        return user;
    }

    public List<Run> getAllRuns() {
        List<Run> list = new ArrayList<Run>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_RUNS + " ORDER BY " + KEY_RUNS_DATE + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                try {
                    date = format.parse(cursor.getString(1));
                } catch (Exception e) {
                    date = new Date();
                }

                JSONObject locationList = null;
                try {
                    locationList = new JSONObject(cursor.getString(6));
                } catch (JSONException e) {
                    Log.d("DataBankHandler",e.getMessage());
                }

                Run r = new Run(
                        getUserById(cursor.getInt(0)),
                        date,
                        cursor.getInt(2),
                        cursor.getInt(3),
                        cursor.getInt(4),
                        cursor.getInt(5),
                        locationList
                );
                // Adding contact to list
                list.add(r);
            } while (cursor.moveToNext());
        }

        // return contact list
        return list;
    }

    public List<CurrentRun> getAllCurrentRun() {
        List<CurrentRun> list = new ArrayList<CurrentRun>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_CURRENT_RUN + " ORDER BY " + KEY_CURRENT_RUN_TIME + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Location location = new Location("dummystring");
                location.setLatitude(cursor.getFloat(1));
                location.setLongitude(cursor.getFloat(2));
                location.setSpeed(cursor.getFloat(3));
                SimpleDateFormat format = new SimpleDateFormat(
                        "yyyy-MM-dd HH:mm:ss");
                Date date = new Date();
                try {
                    date = format.parse(cursor.getString(0));
                } catch (Exception e) {
                    date = new Date();
                }
                CurrentRun currentRun = new CurrentRun(
                        date,
                        location,
                        cursor.getFloat(3)
                );
                // Adding contact to list
                list.add(currentRun);
            } while (cursor.moveToNext());
        }

        // return contact list
        return list;
    }

    //DELETE

    public int removeSingleRun(Date date, User user){
        SimpleDateFormat iso8601Format = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String dateString = iso8601Format.format(date);
        String deleteQuery = "DELETE FROM " + TABLE_RUNS +
                " WHERE " + KEY_RUNS_DATE + " == '" + dateString + "' AND " +
        KEY_RUNS_RUNNER + " == " + user.getId();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(deleteQuery, null);
        return cursor.getCount();
    }



    //COUNTS

    public int getUserCount() {
        String countQuery = "SELECT * FROM " + TABLE_USERS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }

    public int getRunsCount() {
        String countQuery = "SELECT * FROM " + TABLE_RUNS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }

    public int getCurrentRunCount() {
        String countQuery = "SELECT * FROM " + TABLE_CURRENT_RUN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        // return count
        return cursor.getCount();
    }

}
