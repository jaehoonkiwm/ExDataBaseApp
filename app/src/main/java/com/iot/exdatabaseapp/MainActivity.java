package com.iot.exdatabaseapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;


public class MainActivity extends Activity {

    public static final String CONTENT_URI = "content://com.iot.database";

    private MyDatabase myDB;
    private ContentResolver mContentResolver;

    TextView tvLogMsg;
    EditText edTableName;

    public static final String TAG = "MainActivity";

    private void LogMsg(String msg){
        Log.i(TAG, msg);
    //    tvLogMsg.append( msg + "\n");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new MyDatabase(this);
        myDB.open();

        mContentResolver = getContentResolver();


        tvLogMsg = (TextView)findViewById(R.id.textView);



        // edTableName = (EditText)findViewById(R.id.editText);
    }

    public void onButtonClicked(View v){
        Uri reUri;
        List<String> reStr;
        String strKey;
        switch (v.getId()) {
            case R.id.buttonInsert :
                ContentValues contentValues = new ContentValues();
                contentValues.put("key", "kikikikey");

                reUri = mContentResolver.insert(Uri.parse(CONTENT_URI + "/setkey"), contentValues);
                reStr = reUri.getPathSegments();
                strKey = reStr.get(0);
                tvLogMsg.append("set key : " + strKey + "\n");
                break;
            case R.id.buttonSelect :
                reUri = mContentResolver.insert(Uri.parse(CONTENT_URI + "/getkey"), new ContentValues());
                reStr = reUri.getPathSegments();
                strKey = reStr.get(0);
                tvLogMsg.append("get key : " + strKey + "\n");
                break;
        }
    }

    public void onButtonInsertClicked(View v) {
        ContentValues values = new ContentValues();
        values.put(MyDatabase.TablePerson.NAME, "junghwa");
        values.put(MyDatabase.TablePerson.AGE, 28);

//        myDB.insert(Uri.parse("content://com.iot.exdatabaseapp"), values);

//        myDB.insertData("Han", 29);
//        myDB.insertData("Park", 28);
//        myDB.insertData("Su", 25);
//        myDB.insertData("Back", 26);
    }

    public void onButtonSelectClicked(View v) {
//        Cursor data = myDB.selectData();
//
//        for (int i = 0; i != data.getCount(); ++i) {
//            data.moveToNext();
//            String name = data.getString(0);
//            int age = data.getInt(1);
//            tvLogMsg.append("\nselect : "+i+" "+name+" "+age);
//        }
//        data.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myDB.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
