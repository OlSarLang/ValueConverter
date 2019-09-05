package com.example.left4candy.cryptoexchange;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.ArrayList;

public class DBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "currencies.db";
    public static final String TABLE_CURRENCIES = "currencies";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "_name";
    public static final String COLUMN_VALUE = "_value";
    public static final String COLUMN_TYPE = "_type";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CURRENCIES + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_VALUE + " REAL, " +
                COLUMN_TYPE + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CURRENCIES);
        onCreate(db);
    }

    //Add a new row to the database
    public void addCurrency(Currency c){
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, c.getName());
        values.put(COLUMN_VALUE, c.getValue());
        values.put(COLUMN_TYPE, c.getType());
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CURRENCIES + " WHERE " + COLUMN_NAME + " = \"" + c.getName()+ "\"";
        Cursor curs = db.rawQuery(query, null);
        if(curs.getCount() > 0){
            curs.moveToFirst();
            int id = curs.getInt(curs.getColumnIndex(COLUMN_ID));
            db.delete(TABLE_CURRENCIES,  COLUMN_ID + " = ? ", new String[]{""+id});
        }
        db.insert(TABLE_CURRENCIES, null, values);
        db.close();
        System.out.println("Currency added");
    }

    //Delete currency from database
    public void deleteCurrency(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_CURRENCIES + " WHERE " + COLUMN_ID + "=\"" + id + "\";");
        System.out.println("Currency deleted");
    }

    //Print out the database as a string
    public ArrayList<Currency> getCurrencies(){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CURRENCIES + " WHERE 1";

        //Cursor point to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();
        ArrayList<Currency> currencies = new ArrayList<Currency>();
        while(!c.isAfterLast()){
            int id = -1;
            double value = 1;
            String name = "unknown";
            String type = "unknown";
            if(c.getString(c.getColumnIndex(COLUMN_NAME)) != null){
                name = c.getString(c.getColumnIndex(COLUMN_NAME));
                value = c.getDouble(c.getColumnIndex(COLUMN_VALUE));
                type =  c.getString(c.getColumnIndex(COLUMN_TYPE));
                id = c.getInt(c.getColumnIndex(COLUMN_ID));
            }
            if(type.equals("Fiat")){
                currencies.add(new Fiat(value, name, id));

            }else if(type.equals("Crypto")){
                currencies.add(new Crypto(value, name, id));
            }else{
                currencies.add(new Currency(value, name, id));
            }
            c.moveToNext();
        }
        db.close();
        return currencies;
    }
}
