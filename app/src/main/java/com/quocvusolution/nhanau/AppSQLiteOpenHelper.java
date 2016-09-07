package com.quocvusolution.nhanau;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

public class AppSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "nhanau";
    public static final int DB_VERSION = 1;
    public Context mContext;

    public AppSQLiteOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    public String createFoodTableSQL() {
        String sql = "create table "
                + Food.TB_NAME + " ("
                + Food.ROWID
                + " integer primary key autoincrement not null,"
                + Food.ID
                + " integer not null,"
                + Food.TITLE
                + " text not null,"
                + Food.DESCRIPTION
                + " text not null,"
                + Food.PRICE
                + " real not null,"
                + Food.RATING
                + " real not null,"
                + Food.COOKED_TIME
                + " integer not null,"
                + Food.SERVE_COUNT
                + " integer not null,"
                + Food.CALORIES
                + " integer not null,"
                + Food.THUMB_PHOTO
                + " text not null,"
                + Food.PHOTOS
                + " text not null,"
                + Food.MATERIALS
                + " text not null,"
                + Food.COOKING_STEPS
                + " text not null,"
                + Food.TIP
                + " text not null,"
                + Food.LATITUDE
                + " integer not null,"
                + Food.LONGITUDE
                + " integer not null,"
                + Food.ADDRESS
                + " text not null,"
                + Food.CREATED_USER_DISPLAY_NAME
                + " text not null,"
                + Food.CREATED_USER_PHOTO
                + " text not null,"
                + Food.CREATED_USER_LIKED_COUNT
                + " integer not null,"
                + Food.CREATED_USER_COMMENTED_COUNT
                + " integer not null,"
                + Food.LIKED
                + " integer not null,"
                + Food.LIKED_COUNT
                + " integer not null,"
                + Food.COMMENTED_COUNT
                + " integer not null,"
                + Food.VIEW_COUNT
                + " integer not null,"
                + Food.CREATED_AT
                + " integer not null,"
                + Food.CREATED_USER
                + " text not null,"
                + Food.UPDATED_AT
                + " integer not null,"
                + Food.UPDATED_USER
                + " text not null,"
                + Food.DELETED_AT
                + " integer not null,"
                + Food.DELETED_USER
                + " text not null,"
                + Food.DELETED
                + " integer not null,"
                + Food.RATED
                + " integer not null,"
                + Food.TITLE_A
                + " text not null" + ");";
        return sql;
    }

    public String dropFoodTableSQL() {
        String sql = "DROP TABLE IF EXISTS " + Food.TB_NAME;
        return sql;
    }

    public String createFoodCartTableSQL() {
        String sql = "create table "
                + FoodCart.TB_NAME + " ("
                + FoodCart.ROWID
                + " integer primary key autoincrement not null,"
                + FoodCart.ID
                + " integer not null,"
                + FoodCart.USERNAME
                + " text not null,"
                + FoodCart.FOOD_ID
                + " integer not null,"
                + FoodCart.FOOD_COUNT
                + " integer not null,"
                + FoodCart.CREATED_AT
                + " integer not null,"
                + FoodCart.CREATED_USER
                + " text not null,"
                + FoodCart.UPDATED_AT
                + " integer not null,"
                + FoodCart.UPDATED_USER
                + " text not null,"
                + FoodCart.DELETED_AT
                + " integer not null,"
                + FoodCart.DELETED_USER
                + " text not null,"
                + FoodCart.DELETED
                + " integer not null" + ");";
        return sql;
    }

    public String dropFoodCartTableSQL() {
        String sql = "DROP TABLE IF EXISTS " + FoodCart.TB_NAME;
        return sql;
    }

    public String createFoodSaveTableSQL() {
        String sql = "create table "
                + FoodSave.TB_NAME + " ("
                + FoodSave.ROWID
                + " integer primary key autoincrement not null,"
                + FoodSave.ID
                + " integer not null,"
                + FoodSave.USERNAME
                + " text not null,"
                + FoodSave.FOOD_ID
                + " integer not null,"
                + FoodSave.CREATED_AT
                + " integer not null,"
                + FoodSave.CREATED_USER
                + " text not null,"
                + FoodSave.UPDATED_AT
                + " integer not null,"
                + FoodSave.UPDATED_USER
                + " text not null,"
                + FoodSave.DELETED_AT
                + " integer not null,"
                + FoodSave.DELETED_USER
                + " text not null,"
                + FoodSave.DELETED
                + " integer not null" + ");";
        return sql;
    }

    public String dropFoodSaveTableSQL() {
        String sql = "DROP TABLE IF EXISTS " + FoodSave.TB_NAME;
        return sql;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createFoodTableSQL());
        db.execSQL(createFoodCartTableSQL());
        db.execSQL(createFoodSaveTableSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropFoodTableSQL());
        db.execSQL(dropFoodCartTableSQL());
        db.execSQL(dropFoodSaveTableSQL());
        onCreate(db);
    }
}