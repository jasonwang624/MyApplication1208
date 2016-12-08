package com.example.user.myapplication1208;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    final String DB_NAME = "TV.sqlite";
    String DB_FILE;
    EditText ed1,ed2,ed3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DB_FILE = getFilesDir() + File.separator + DB_NAME;
        ed1 = (EditText) findViewById(R.id.editText);
        ed2 = (EditText) findViewById(R.id.editText2);
        ed3 = (EditText) findViewById(R.id.editText3);

        copyDataBaseFile();
            }
    private void copyDataBaseFile()
    {
        File f = new File(DB_FILE);
        if(!f.exists())
        {
            InputStream is = null;
            try {
                is =MainActivity.this.getAssets().open(DB_NAME);
                OutputStream os = new FileOutputStream(f);

                byte[] buffer = new byte[1024];
                int length;
                while((length = is.read(buffer))>0)
                {
                    os.write(buffer,0,length);
                }
                os.flush();
                os.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}
    public void clickList(View v)
    {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_FILE, null);
        //Cursor c = db.rawQuery("Select * from TV",null);
        Cursor c = db.query("TV",new String[]{"ID","SName"}, null,null,null,null,null);
        if(c.moveToFirst())
        {
            Log.d("DB",c.getString(1));
        }
        while (c.moveToNext())
        {
            Log.d("DB",c.getString(1));
        }
        c.close();
        db.close();
    }
    public void clickAdd(View v)
    {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_FILE,null);
        String strSName = ed1.getText().toString();
        String strTel = ed2.getText().toString();
        String strAddr = ed3.getText().toString();
       // String sql = String.format("insert into TV (SName, Tel, Address) values ('%s','%s','%s')", strSName, strTel, strAddr);
        //Log.d("DB_SQL",sql);
        //db.execSQL(sql);
        ContentValues cv = new ContentValues();
        cv.put("SName",strSName);
        cv.put("Tel",strTel);
        cv.put("Address",strAddr);
        db.insert("TV",null,cv);
        db.close();
    }

}
