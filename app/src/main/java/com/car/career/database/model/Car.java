package com.car.career.database.model;

public class Car {
    public static final String TABLE_NAME = "Arac";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_MODEL = "model";
    public static final String COLUMN_YEAR = "year";


    private long id;
    private String title;
    private String createdAt;
    private String model;
    private long year;


    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME + "("
                    + COLUMN_ID + " integer primary key autoincrement,"
                    + COLUMN_TITLE + " text not null,"
                    + COLUMN_CREATED_AT + " datetime default (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')),"
                    + COLUMN_MODEL + " text not null,"
                    + COLUMN_YEAR + " real not null"
                    + ")";

    public Car(long id, String title, String createdAt,String model,long year) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
        this.model = model;
        this.year = year;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModel() {
        return model;
    }

    public long getYear() {
        return year;
    }

    public String toString() {
        return title;
    }
}
