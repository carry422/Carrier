package com.car.career.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        db.execSQL(CarControl.CREATE_TABLE);

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
    public long insertCarControl(long model,double yakit,double cost){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarControl.COLUMN_ARAC,model);
        contentValues.put(CarControl.COLUMN_YAKIT,yakit);
        contentValues.put(CarControl.COLUMN_COST,cost);
        return db.insert(CarControl.TABLE_NAME, null, contentValues);
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
    public CarControl getCarControl(long id){
        SQLiteDatabase db = this.getReadableDatabase();
        CarControl carControl = null;

        try (Cursor cursor = db.query(CarControl.TABLE_NAME, null, CarControl.COLUMN_ID + " = " + id, null, null, null, null)){
            if (cursor.moveToFirst()) {
                carControl = new CarControl(
                        cursor.getLong(cursor.getColumnIndex(CarControl.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(CarControl.COLUMN_CREATED_AT)),
                        cursor.getLong(cursor.getColumnIndex(CarControl.COLUMN_ARAC)),
                        cursor.getLong(cursor.getColumnIndex(CarControl.COLUMN_YAKIT)),
                        cursor.getLong(cursor.getColumnIndex(CarControl.COLUMN_COST))
                );
            }
        }

        return carControl;
    }

}
