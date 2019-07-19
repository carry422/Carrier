package com.car.career.database;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;
import android.util.Log;

import com.car.career.database.model.Bill;
import com.car.career.database.model.Car;
import com.car.career.database.model.CarControl;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static DBHelper sInstance;
    private static final String DATABASE_NAME = "career.db";
    private static Context mainContext;



    public static synchronized DBHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DBHelper(context.getApplicationContext());
            mainContext=context;
        }
        return sInstance;
    }

    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Car.CREATE_TABLE);
        db.execSQL(Bill.CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onCreate(db);
    }

    public void insertCar(String title,String model,long year){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(Car.COLUMN_TITLE, title);
            values.put(Car.COLUMN_MODEL,model);
            values.put(Car.COLUMN_YEAR,year);

            db.insert(Car.TABLE_NAME, null, values);
            db.close();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    public long insertBill(double litre, String kurum,String time,String date,double cost){
        SQLiteDatabase db = this.getWritableDatabase();
        SharedPreferences sharedPreferences = mainContext.getSharedPreferences("settings", 0);

        Long d=sharedPreferences.getLong("currentCar",0);
        ContentValues contentValues = new ContentValues();
        contentValues.put(Bill.COLUMN_CARID,d);
        contentValues.put(Bill.COLUMN_LIT,litre);
        contentValues.put(Bill.COLUMN_COST,cost);
        contentValues.put(Bill.COLUMN_TIME,time);
        contentValues.put(Bill.COLUMN_KURUM,kurum);
        contentValues.put(Bill.COLUMN_DATE,date);
        return db.insert(Bill.TABLE_NAME, null, contentValues);
    }
    public Car getCar(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Car car = null;

        try (Cursor cursor = db.query(Car.TABLE_NAME, null, Car.COLUMN_ID + " = " + id, null, null, null, null)){
            if (cursor.moveToFirst()) {
                    car = new Car(
                        cursor.getLong(cursor.getColumnIndex(Car.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Car.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(Car.COLUMN_CREATED_AT)),
                            cursor.getString(cursor.getColumnIndex(Car.COLUMN_MODEL)),
                            cursor.getLong(cursor.getColumnIndex(Car.COLUMN_YEAR))
                            );
            }
        }

        return car;
    }
    public ArrayList<Car> getCarList(){
        SQLiteDatabase db = this.getReadableDatabase();
        Car car;

        ArrayList<Car> arrayList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Car.TABLE_NAME, new String[]{});
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                car = new Car(
                        cursor.getLong(cursor.getColumnIndex(Car.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(Car.COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndex(Car.COLUMN_CREATED_AT)),
                        cursor.getString(cursor.getColumnIndex(Car.COLUMN_MODEL)),
                        cursor.getLong(cursor.getColumnIndex(Car.COLUMN_YEAR))
                );

                arrayList.add(car);

                cursor.moveToNext();
            }
        }
        cursor.close();

        return arrayList;
    }
    public Bill getBill(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        Bill bill = null;

        try (Cursor cursor = db.query(Bill.TABLE_NAME, null, Bill.COLUMN_ID + " = " + id, null, null, null, null)){
            if (cursor.moveToFirst()) {
                bill = new Bill(
                        cursor.getLong(cursor.getColumnIndex(Bill.COLUMN_ID)),
                        cursor.getDouble(cursor.getColumnIndex(Bill.COLUMN_LIT)),
                        cursor.getString(cursor.getColumnIndex(Bill.COLUMN_KURUM)),
                        cursor.getString(cursor.getColumnIndex(Bill.COLUMN_TIME)),
                        cursor.getString(cursor.getColumnIndex(Bill.COLUMN_DATE)),
                        cursor.getDouble(cursor.getColumnIndex(Bill.COLUMN_COST)),
                        cursor.getLong(cursor.getColumnIndex(Bill.COLUMN_CARID))
                );
            }
        }

        return bill;
    }
    public ArrayList<Bill> getBills(long carid){
        SQLiteDatabase db = this.getReadableDatabase();
        Bill bill = null;
        ArrayList<Bill> arrayList = new ArrayList<Bill>();

        try (Cursor cursor = db.query(Bill.TABLE_NAME, null, Bill.COLUMN_CARID + " = " + carid, null, null, null, null)){
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    bill = new Bill(
                            cursor.getLong(cursor.getColumnIndex(Bill.COLUMN_ID)),
                            cursor.getDouble(cursor.getColumnIndex(Bill.COLUMN_LIT)),
                            cursor.getString(cursor.getColumnIndex(Bill.COLUMN_KURUM)),
                            cursor.getString(cursor.getColumnIndex(Bill.COLUMN_TIME)),
                            cursor.getString(cursor.getColumnIndex(Bill.COLUMN_DATE)),
                            cursor.getDouble(cursor.getColumnIndex(Bill.COLUMN_COST)),
                            cursor.getLong(cursor.getColumnIndex(Bill.COLUMN_CARID))
                    );
                    arrayList.add(bill);
                    cursor.moveToNext();

                }
            }
        }

        return arrayList;
    }
    public ArrayList<Bill> getBillList(){
        SQLiteDatabase db = this.getReadableDatabase();
        Bill bill = null;
        ArrayList<Bill> arrayList = new ArrayList<Bill>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Bill.TABLE_NAME, new String[]{});
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                bill = new Bill(
                        cursor.getLong(cursor.getColumnIndex(Bill.COLUMN_ID)),
                        cursor.getDouble(cursor.getColumnIndex(Bill.COLUMN_LIT)),
                        cursor.getString(cursor.getColumnIndex(Bill.COLUMN_KURUM)),
                        cursor.getString(cursor.getColumnIndex(Bill.COLUMN_TIME)),
                        cursor.getString(cursor.getColumnIndex(Bill.COLUMN_DATE)),
                        cursor.getDouble(cursor.getColumnIndex(Bill.COLUMN_COST)),
                        cursor.getLong(cursor.getColumnIndex(Bill.COLUMN_CARID))
                        );
                arrayList.add(bill);
                cursor.moveToNext();

            }
        }

        return arrayList;
    }


}
