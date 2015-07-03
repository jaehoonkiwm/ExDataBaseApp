package com.iot.exdatabaseapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by iot10 on 2015-05-27.
 */
public class MyDatabase extends ContentProvider{
    public static final String TAG = "MyDatabase";

    public static final String AUTHOLITY = "com.iot.database";
    public static final int GET_KEY_PATH = 1;
    public static final int SET_KEY_PATH = 2;

    private static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        mUriMatcher.addURI(AUTHOLITY, "getkey", GET_KEY_PATH);
        mUriMatcher.addURI(AUTHOLITY, "setkey", SET_KEY_PATH);
    }

    public static String key = "monkey";

    public static final String DATABASE_NAME = "myDB";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "iot";
    public static final String DROP_TABLE_SQL = "drop table if exists " + TABLE_NAME;
    public static final String CREATE_SQL = "create table "+TABLE_NAME
            + "("
            + TablePerson._ID   + " integer PRIMARY KEY autoincrement,"
            + TablePerson.NAME  + " text , "
            + TablePerson.AGE   + " integer "
            +");";

    private MyDataBaseHelper myDataBaseHelper;
    private SQLiteDatabase myDatabase;
    private Context context;


    public interface TablePerson extends BaseColumns {
        public static final String NAME = "name";
        public static final String AGE = "age";
    }

    public MyDatabase() {
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    private boolean doInsert(ContentValues values) {
        String name = values.getAsString(TablePerson.NAME);
        int age = values.getAsInteger(TablePerson.AGE);

        String query = "insert into "+TABLE_NAME
                + "(name, age) values('"
                + name +"',"+ age
                + ");";
        myDatabase.execSQL(query);
        LogMsg("MyDataBaseHelper : " + query);

        return true;
    }
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri reUri = null;
        switch (mUriMatcher.match(uri)) {
            case GET_KEY_PATH :
                Log.i(TAG, "get key : " + key + "\n");
                reUri = Uri.parse(key);
                break;
            case SET_KEY_PATH :
                String newKey = values.getAsString("key");
                key = newKey;
                Log.i(TAG, "set key : " + key + "\n");
                reUri = Uri.parse(key);
                break;
        }
        return reUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    public void LogMsg(String msg) {
        Log.i(TAG, msg);
    }

    public MyDatabase(Context context) {
        this.context = context;
    }

    public boolean open(){
        myDataBaseHelper = new MyDataBaseHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
        myDatabase = myDataBaseHelper.getWritableDatabase();
        return true;
    }

    public Cursor selectData() {
        Cursor data;
        String selectSql = "select name, age from "+ TABLE_NAME;

        data = myDatabase.rawQuery(selectSql, null);
        int count = data.getCount();

        LogMsg("selectData :" + ((Integer)count).toString());

        return data;
    }

//    public boolean insertData(String name, int age) {
//        String query = "insert into "+TABLE_NAME
//                + "(name, age) values('"
//                + name +"',"+((Integer)age).toString()
//                + ");";
//        myDatabase.execSQL(query);
//        LogMsg("MyDataBaseHelper : " + query);
//        return true;
//    }

    public boolean close() {
        myDatabase.close();
        myDatabase = null;
        return true;
    }

    private class MyDataBaseHelper extends SQLiteOpenHelper {
        public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, DATABASE_NAME, factory, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            // drop table
            db.execSQL(DROP_TABLE_SQL);
            LogMsg("MyDataBaseHelper : "+DROP_TABLE_SQL);
            // create table
            db.execSQL(CREATE_SQL);
            LogMsg("MyDataBaseHelper : " + CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if(newVersion > 1){
                db.execSQL(DROP_TABLE_SQL);
            }
        }
    }
}
