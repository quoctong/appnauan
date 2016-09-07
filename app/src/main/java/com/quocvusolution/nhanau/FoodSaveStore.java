package com.quocvusolution.nhanau;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;

public class FoodSaveStore {
    public static final String[] COLUMNS = new String[]{
            FoodSave.ROWID,
            FoodSave.ID,
            FoodSave.USERNAME,
            FoodSave.FOOD_ID,
            FoodSave.CREATED_AT,
            FoodSave.CREATED_USER,
            FoodSave.UPDATED_AT,
            FoodSave.UPDATED_USER,
            FoodSave.DELETED_AT,
            FoodSave.DELETED_USER,
            FoodSave.DELETED
    };

    private SQLiteDatabase mDb;
    private Context mContext;
    private FoodStore mFoodStore;

    public FoodSaveStore(Context context, SQLiteDatabase db) {
        this.mContext = context;
        this.mDb = db;
        mFoodStore = new FoodStore(context, db);
    }

    private ContentValues getCData(FoodSave obj) {
        ContentValues values = new ContentValues();
        values.put(FoodSave.ID, obj.getId());
        values.put(FoodSave.USERNAME, obj.getUsername());
        values.put(FoodSave.FOOD_ID, obj.getFoodId());
        values.put(FoodSave.CREATED_AT, obj.getCreatedAt().getTime());
        values.put(FoodSave.CREATED_USER, obj.getCreatedUser());
        values.put(FoodSave.UPDATED_AT, obj.getUpdatedAt().getTime());
        values.put(FoodSave.UPDATED_USER, obj.getUpdatedUser());
        values.put(FoodSave.DELETED_AT, obj.getDeletedAt().getTime());
        values.put(FoodSave.DELETED_USER, obj.getDeletedUser());
        values.put(FoodSave.DELETED, obj.isDeleted());
        return values;
    }

    public void setCData(FoodSave obj, Cursor cursor) {
        obj.setRowId(cursor.getInt(cursor.getColumnIndexOrThrow(FoodSave.ROWID)));
        obj.setId(cursor.getInt(cursor.getColumnIndexOrThrow(FoodSave.ID)));
        obj.setUsername(cursor.getString(cursor.getColumnIndexOrThrow(FoodSave.USERNAME)));
        obj.setFoodId(cursor.getInt(cursor.getColumnIndexOrThrow(FoodSave.FOOD_ID)));
        obj.setCreatedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FoodSave.CREATED_AT))));
        obj.setCreatedUser(cursor.getString(cursor.getColumnIndexOrThrow(FoodSave.CREATED_USER)));
        obj.setUpdatedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FoodSave.UPDATED_AT))));
        obj.setUpdatedUser(cursor.getString(cursor.getColumnIndexOrThrow(FoodSave.UPDATED_USER)));
        obj.setDeletedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(FoodSave.DELETED_AT))));
        obj.setDeletedUser(cursor.getString(cursor.getColumnIndexOrThrow(FoodSave.DELETED_USER)));
        obj.setDeleted(cursor.getInt(cursor.getColumnIndexOrThrow(FoodSave.DELETED)) == 1);
    }

    public FoodSave getByRowId(int rowID) {
        FoodSave rowObj = null;
        Cursor cursor;
        try {
            cursor = mDb.query(FoodSave.TB_NAME, COLUMNS,
                    FoodSave.ROWID + "=" + rowID, null,
                    null, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                rowObj = new FoodSave();
                setCData(rowObj, cursor);
            }
        } catch (SQLException e) {
        }
        return rowObj;
    }

    public FoodSave getByFoodId(int id) {
        FoodSave rowObj = null;
        Cursor cursor;
        try {
            cursor = mDb.query(FoodSave.TB_NAME, COLUMNS,
                    FoodSave.FOOD_ID + "=" + id, null,
                    null, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                rowObj = new FoodSave();
                setCData(rowObj, cursor);
            }
        } catch (SQLException e) {
        }
        return rowObj;
    }

    public ArrayList<FoodSave> getList(int skip, int take) {
        ArrayList<FoodSave> list = new ArrayList<FoodSave>();
        Cursor cursor;
        try {
            cursor = mDb.query(FoodSave.TB_NAME, COLUMNS,
                    null, null,
                    null, null, FoodSave.CREATED_AT + " DESC", Integer.toString(skip) + "," + Integer.toString(take));
            if (cursor.moveToFirst()) {
                do {
                    FoodSave rowObj = new FoodSave();
                    setCData(rowObj, cursor);
                    list.add(rowObj);
                } while(cursor.moveToNext());
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int insert(FoodSave obj) {
        obj.setCreatedAt(new Date());
        obj.setUpdatedAt(new Date());
        ContentValues values = getCData(obj);
        try {
            return (int) mDb.insert(FoodSave.TB_NAME, null, values);
        } catch (Exception e) {
        }
        return -1;
    }

    public void update(FoodSave obj) {
        obj.setUpdatedAt(new Date());
        ContentValues values = getCData(obj);
        String whereClause = FoodSave.ID + "=?";
        String whereArgs[] = new String[]{String.valueOf(obj.getRowId())};
        try {
            mDb.update(FoodSave.TB_NAME, values, whereClause, whereArgs);
        } catch (Exception e) {
        }
    }

    public void delete(int id) {
        ContentValues values = new ContentValues();
        values.put(FoodSave.DELETED, true);
        values.put(FoodSave.UPDATED_AT, (new Date()).getTime());
        String whereClause = FoodSave.ID + "=?";
        String whereArgs[] = new String[]{String.valueOf(id)};
        try {
            mDb.update(FoodSave.TB_NAME, values, whereClause, whereArgs);
        } catch (Exception e) {
        }
    }

    public ArrayList<FoodSave> getFoodSaveList(int skip, int take) {
        ArrayList<FoodSave> list = new ArrayList<FoodSave>();
        Cursor cursor;
        try {
            cursor = mDb.query(FoodSave.TB_NAME, COLUMNS,
                    null, null,
                    null, null, FoodSave.CREATED_AT + " DESC", Integer.toString(skip) + "," + Integer.toString(take));
            if (cursor.moveToFirst()) {
                do {
                    FoodSave rowObj = new FoodSave();
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

