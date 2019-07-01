package com.car.career.database.model;

public class CarControl {
    public static final String TABLE_NAME = "AracKontrol";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_ARAC = "arac";
    public static final String COLUMN_YAKIT = "yakit";
    public static final String COLUMN_COST = "cost";


    private long id;
    private String createdAt;
    private long car;
    private double yakit;
    private double cost;


    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME + "("
                    + COLUMN_ID + " integer primary key autoincrement,"
                    + COLUMN_CREATED_AT + " datetime default (DATETIME(CURRENT_TIMESTAMP, 'LOCALTIME')),"
                    + COLUMN_ARAC + "  real not null,"
                    + COLUMN_YAKIT + " real not null,"
                    + COLUMN_COST +  " real not null"
                    + ")";

    public CarControl(long id, String createdAt,long model,double yakit,double cost) {
        this.id = id;
        this.createdAt = createdAt;
        this.car = model;
        this.yakit = yakit;
        this.cost=cost;
    }

    public long getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getModel() {
        return this.car;
    }

    public double getYakit() {
        return yakit;
    }

    public double getCost() {
        return cost;
    }

}
