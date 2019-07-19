package com.car.career.database.model;

public class Bill {
    public static final String TABLE_NAME = "Bill";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_LIT = "litre";
    public static final String COLUMN_KURUM = "kurum";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_CARID = "carid";
    public static final String COLUMN_COST = "cost";


    private long id;
    private double litre;
    private String kurum;
    private String time;
    private String date;
    private double cost;
    private long carID;


    public static final String CREATE_TABLE =
            "create table " + TABLE_NAME + "("
                    + COLUMN_ID + " integer primary key autoincrement,"
                    + COLUMN_LIT + " real not null,"
                    + COLUMN_KURUM + " text not null,"
                    + COLUMN_TIME + " text not null,"
                    + COLUMN_DATE + " text not null,"
                    + COLUMN_CARID + " real not null,"
                    + COLUMN_COST + " real not null"
                    + ")";

    public Bill(long id, double litre, String kurum,String time,String date,double cost,long carID) {
        this.id = id;
        this.litre = litre;
        this.kurum = kurum;
        this.time = time;
        this.date = date;
        this.cost = cost;
        this.carID = carID;
    }

    public long getId() {
        return id;
    }

    public double getLitre() {
        return litre;
    }
    public String getKurum() {
        return kurum;
    }
    public String getTIME() {
        return time;
    }
    public String getDATE() {
        return date;
    }
    public double getCOST() {
        return cost;
    }
    public long getCarID() {
        return carID;
    }

}
