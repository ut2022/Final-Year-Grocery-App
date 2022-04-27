package com.smartechindia.grocery.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "product_table";

    public DatabaseHelper(@Nullable Context context) {
        super(context,TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " +TABLE_NAME+ " (id TEXT,valuee TEXT)");
    }

    @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(String.format("DROP IF TABLE EXISTS %s", TABLE_NAME));
        onCreate(sqLiteDatabase);
    }

    public void addData(String data,String valuee)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("id",data);
        contentValues.put("valuee",valuee);
        Log.d("add data", String.valueOf(data));
        db.insert(TABLE_NAME,null,contentValues);
    }

    public int getDataRow()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor datas = db.rawQuery(query, null);
        return datas.getCount();


    }
    public int getValue(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int p;
        String idsp;
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id ='"+id+"'";
        Cursor data = db.rawQuery(query, null);
        if (!(data.moveToFirst()) || data.getCount() ==0)
        {
            idsp="0";
        }else{
            idsp = data.getString(data.getColumnIndex("valuee"));
         }
        data.close();
        Log.d("id and value is", String.valueOf(id)+" And "+idsp);
        return Integer.parseInt(idsp);
    }

    public void ClearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
            String query;
              query = "DELETE FROM " + TABLE_NAME + "";
            db.execSQL(query);
    }

    public void UpdateData(String id,int val) {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            ContentValues content =new ContentValues();
            content.put("valuee", val);
        String query;
        if(val==0){  query = "DELETE FROM " + TABLE_NAME + " WHERE id='" + id + "'"; }else{
           query = "UPDATE " + TABLE_NAME + " SET valuee ='" +val+ "' WHERE id='" + id + "'";
      }
        Log.d("query",query);
            db.execSQL(query);
        } catch(Exception e){
            System.out.println("NO UPDATE IN APPSTATUS"+e);
        }
    }
}
