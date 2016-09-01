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

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createFoodTableSQL());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropFoodTableSQL());
        onCreate(db);
    }
}