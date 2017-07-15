package com.example.toni.tictactoe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by TONI on 7/4/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="loja.db";
    public static final String TABLE_NAME="loja_table";
    public static final String COL_1="id";
    public static final String COL_2="player_1";
    public static final String COL_3="player_2";
    public static final String COL_4="result";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //krijojm databazen
        db.execSQL("create table "+TABLE_NAME+" ("+ COL_1+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_2+" TEXT,"+COL_3+" TEXT,"+COL_4+" TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME );
        onCreate(db);

    }
    public boolean insertData(String player_1,String player_2,String result)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValue=new ContentValues();
        contentValue.put(COL_2,player_1);
        contentValue.put(COL_3,player_2);
        contentValue.put(COL_4,result);
        long uKrye=db.insert(TABLE_NAME,null,contentValue);
        if(uKrye==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public Cursor getAllData()
    {
        SQLiteDatabase db=getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

}
