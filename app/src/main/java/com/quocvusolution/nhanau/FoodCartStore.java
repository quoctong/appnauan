package com.quocvusolution.nhanau;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

public class FoodCartStore {
    public static final String[] COLUMNS = new String[]{
            FoodCart.ROWID,
            FoodCart.ID,
            FoodCart.USERNAME,
            FoodCart.FOOD_ID,
            FoodCart.FOOD_COUNT,
            FoodCart.CREATED_AT,
            FoodCart.CREATED_USER,
            FoodCart.UPDATED_AT,
            FoodCart.UPDATED_USER,
            FoodCart.DELETED_AT,
            FoodCart.DELETED_USER,
            FoodCart.DELETED
    };

    private SQLiteDatabase mDb;
    private Context mContext;
    private FoodStore mFoodStore;

    public FoodCartStore(Context context, SQLiteDatabase db) {
        this.mContext = context;
        this.mDb = db;
        mFoodStore = new FoodStore(context, db);
    }

    private ContentValues getCData(FoodCart obj) {
        ContentValues values = new ContentValues();
        values.put(FoodCart.ID, obj.getId());
        values.put(FoodCart.USERNAME, obj.getUsername());
        values.put(FoodCart.FOOD_ID, obj.getFoodId());
        values.put(FoodCart.FOOD_COUNT, obj.getFoodCount());
        values.put(FoodCart.CREATED_AT, obj.getCreatedAt().getTime());
        values.put(FoodCart.CREATED_USER, obj.getCreatedUser());
        values.put(FoodCart.UPDATED_AT, obj.getUpdatedAt().getTime());
        values.put(FoodCart.UPDATED_USER, obj.getUpdatedUser());
        values.put(FoodCart.DELETED_AT, obj.getDeletedAt().getTime());
        values.put(FoodCart.DELETED_USER, obj.getDeletedUser());
        values.put(FoodCart.DELETED, obj.isDeleted());
        return values;
    }

    public void setCData(FoodCart obj, Cursor cursor) {
        obj.setRowId(cursor.getInt(cursor.getColumnIndexOrThrow(FoodCart.ROWID)));
        obj.setId(cursor.getInt(cursor.getColumnIndexOrThrow(FoodCart.ID)));
        obj.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(FoodCart.USERNAME)));
        obj.setFoodId(cursor.getInt(cursor.getColumnIndexOrThrow(FoodCart.FOOD_ID)));
        obj.setFoodCount(cursor.getInt(cursor.getColumnIndexOrThrow(FoodCart.FOOD_COUNT)));
        obj.setCreatedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FoodCart.CREATED_AT))));
        obj.setCreatedUser(cursor.getString(cursor.getColumnIndexOrThrow(FoodCart.CREATED_USER)));
        obj.setUpdatedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FoodCart.UPDATED_AT))));
        obj.setUpdatedUser(cursor.getString(cursor.getColumnIndexOrThrow(FoodCart.UPDATED_USER)));
        obj.setDeletedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FoodCart.DELETED_AT))));
        obj.setDeletedUser(cursor.getString(cursor.getColumnIndexOrThrow(FoodCart.DELETED_USER)));
        obj.setDeleted(cursor.getInt(cursor.getColumnIndexOrThrow(FoodCart.DELETED)) == 1);
    }

    public FoodCart getByRowId(int rowID) {
        FoodCart rowObj = null;
        Cursor cursor;
        try {
            cursor = mDb.query(FoodCart.TB_NAME, COLUMNS,
                    FoodCart.ROWID + "=" + rowID, null,
                    null, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                rowObj = new FoodCart();
                setCData(rowObj, cursor);
            }
        } catch (SQLException e) {
        }
        return rowObj;
    }

    public FoodCart getByFoodId(int id) {
        FoodCart rowObj = null;
        Cursor cursor;
        try {
            cursor = mDb.query(FoodCart.TB_NAME, COLUMNS,
                    FoodCart.FOOD_ID + "=" + id, null,
                    null, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                rowObj = new FoodCart();
                setCData(rowObj, cursor);
            }
        } catch (SQLException e) {
        }
        return rowObj;
    }

    public ArrayList<FoodCart> getList(int skip, int take) {
        ArrayList<FoodCart> list = new ArrayList<FoodCart>();
        Cursor cursor;
        try {
            cursor = mDb.query(FoodCart.TB_NAME, COLUMNS,
                    null, null,
                    null, null, FoodCart.CREATED_AT + " DESC", Integer.toString(skip) + "," + Integer.toString(take));
            if (cursor.moveToFirst()) {
                do {
                    FoodCart rowObj = new FoodCart();
                    setCData(rowObj, cursor);
                    list.add(rowObj);
                } while(cursor.moveToNext());
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int insert(FoodCart obj) {
        obj.setCreatedAt(new Date());
        obj.setUpdatedAt(new Date());
        ContentValues values = getCData(obj);
        try {
            return (int) mDb.insert(FoodCart.TB_NAME, null, values);
        } catch (Exception e) {
        }
        return -1;
    }

    public void update(FoodCart obj) {
        obj.setUpdatedAt(new Date());
        ContentValues values = getCData(obj);
        String whereClause = FoodCart.FOOD_ID + "=?";
        String whereArgs[] = new String[]{String.valueOf(obj.getFoodId())};
        try {
            mDb.update(FoodCart.TB_NAME, values, whereClause, whereArgs);
        } catch (Exception e) {
        }
    }

    public void delete(int id) {
        String whereClause = FoodCart.FOOD_ID + "=?";
        String whereArgs[] = new String[]{String.valueOf(id)};
        try {
            mDb.delete(FoodCart.TB_NAME, whereClause, whereArgs);
        } catch (Exception e) {
        }
    }

    public ArrayList<FoodCart> getFoodCartList(int skip, int take) {
        ArrayList<FoodCart> list = new ArrayList<FoodCart>();
        Cursor cursor;
        try {
            cursor = mDb.query(FoodCart.TB_NAME, COLUMNS,
                    null, null,
                    null, null, FoodCart.CREATED_AT + " DESC", Integer.toString(skip) + "," + Integer.toString(take));
            if (cursor.moveToFirst()) {
                do {
                    FoodCart rowObj = new FoodCart();
                    setCData(rowObj, cursor);
                    Food obj = mFoodStore.getById(rowObj.getFoodId());
                    if (obj != null) {
                        rowObj.setFood(obj);
                        list.add(rowObj);
                    }
                } while(cursor.moveToNext());
            }
        } catch (SQLException e) {
        }
        return list;
    }
}
