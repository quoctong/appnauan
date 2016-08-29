package com.quocvusolution.nhanau;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Date;

public class FoodStore{
    private static final String[] COLUMNS = new String[]{
            Food.ROWID,
            Food.ID,
            Food.TITLE,
            Food.DESCRIPTION,
            Food.PRICE,
            Food.RATING,
            Food.COOKED_TIME,
            Food.SERVE_COUNT,
            Food.CALORIES,
            Food.THUMB_PHOTO,
            Food.PHOTOS,
            Food.MATERIALS,
            Food.COOKING_STEPS,
            Food.TIP,
            Food.LATITUDE,
            Food.LONGITUDE,
            Food.ADDRESS,
            Food.CREATED_USER_DISPLAY_NAME,
            Food.CREATED_USER_PHOTO,
            Food.CREATED_USER_LIKED_COUNT,
            Food.CREATED_USER_COMMENTED_COUNT,
            Food.LIKED,
            Food.LIKED_COUNT,
            Food.COMMENTED_COUNT,
            Food.VIEW_COUNT,
            Food.CREATED_AT,
            Food.CREATED_USER,
            Food.UPDATED_AT,
            Food.UPDATED_USER,
            Food.DELETED_AT,
            Food.DELETED_USER,
            Food.DELETED
    };
    private static String SPLIT_PATTERN = "-@-";

    private SQLiteDatabase mDb;
    private Context mContext;

    public FoodStore(Context context, SQLiteDatabase db) {
        this.mContext = context;
        this.mDb = db;
    }

    private ContentValues getCData(Food obj) {
        ContentValues values = new ContentValues();
        values.put(Food.ID, obj.getId());
        values.put(Food.TITLE, obj.getTitle());
        values.put(Food.DESCRIPTION, obj.getDescription());
        values.put(Food.PRICE, obj.getPrice());
        values.put(Food.RATING, obj.getRating());
        values.put(Food.COOKED_TIME, obj.getCookedTime());
        values.put(Food.SERVE_COUNT, obj.getServeCount());
        values.put(Food.CALORIES, obj.getCalories());
        values.put(Food.THUMB_PHOTO, obj.getThumbPhoto());
        values.put(Food.PHOTOS, TextUtils.join(SPLIT_PATTERN, obj.getPhotos()));
        values.put(Food.MATERIALS, TextUtils.join(SPLIT_PATTERN, obj.getMaterials()));
        values.put(Food.COOKING_STEPS, TextUtils.join(SPLIT_PATTERN, obj.getCookingSteps()));
        values.put(Food.TIP, obj.getTip());
        values.put(Food.LATITUDE, obj.getLatitude());
        values.put(Food.LONGITUDE, obj.getLongitude());
        values.put(Food.ADDRESS, obj.getAddress());
        values.put(Food.CREATED_USER_DISPLAY_NAME, obj.getCreatedUserDisplayName());
        values.put(Food.CREATED_USER_PHOTO, obj.getCreatedUserPhoto());
        values.put(Food.CREATED_USER_LIKED_COUNT, obj.getCreatedUserLikedCount());
        values.put(Food.CREATED_USER_COMMENTED_COUNT, obj.getCreatedUserCommentedCount());
        values.put(Food.LIKED, obj.isLiked());
        values.put(Food.LIKED_COUNT, obj.getLikedCount());
        values.put(Food.COMMENTED_COUNT, obj.getCommentedCount());
        values.put(Food.VIEW_COUNT, obj.getViewCount());
        values.put(Food.CREATED_AT, obj.getCreatedAt().getTime());
        values.put(Food.CREATED_USER, obj.getCreatedUser());
        values.put(Food.UPDATED_AT, obj.getUpdatedAt().getTime());
        values.put(Food.UPDATED_USER, obj.getUpdatedUser());
        values.put(Food.DELETED_AT, obj.getDeletedAt().getTime());
        values.put(Food.DELETED_USER, obj.getDeletedUser());
        values.put(Food.DELETED, obj.isDeleted());
        return values;
    }

    public void setCData(Food obj, Cursor cursor) {
        obj.setRowId(cursor.getInt(cursor.getColumnIndexOrThrow(Food.ROWID)));
        obj.setId(cursor.getInt(cursor.getColumnIndexOrThrow(Food.ID)));
        obj.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(Food.TITLE)));
        obj.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(Food.DESCRIPTION)));
        obj.setPrice(cursor.getDouble(cursor.getColumnIndexOrThrow(Food.PRICE)));
        obj.setRating(cursor.getDouble(cursor.getColumnIndexOrThrow(Food.RATING)));
        obj.setCookedTime(cursor.getInt(cursor.getColumnIndexOrThrow(Food.COOKED_TIME)));
        obj.setServeCount(cursor.getInt(cursor.getColumnIndexOrThrow(Food.SERVE_COUNT)));
        obj.setCalories(cursor.getInt(cursor.getColumnIndexOrThrow(Food.CALORIES)));
        obj.setThumbPhoto(cursor.getString(cursor.getColumnIndexOrThrow(Food.THUMB_PHOTO)));
        obj.setPhotos(TextUtils.split(cursor.getString(cursor.getColumnIndexOrThrow(Food.PHOTOS)), SPLIT_PATTERN));
        obj.setMaterials(TextUtils.split(cursor.getString(cursor.getColumnIndexOrThrow(Food.MATERIALS)), SPLIT_PATTERN));
        obj.setCookingSteps(TextUtils.split(cursor.getString(cursor.getColumnIndexOrThrow(Food.COOKING_STEPS)), SPLIT_PATTERN));
        obj.setTip(cursor.getString(cursor.getColumnIndexOrThrow(Food.TIP)));
        obj.setLatitude(cursor.getInt(cursor.getColumnIndexOrThrow(Food.LATITUDE)));
        obj.setLongitude(cursor.getInt(cursor.getColumnIndexOrThrow(Food.LONGITUDE)));
        obj.setAddress(cursor.getString(cursor.getColumnIndexOrThrow(Food.ADDRESS)));
        obj.setCreatedUserDisplayName(cursor.getString(cursor.getColumnIndexOrThrow(Food.CREATED_USER_DISPLAY_NAME)));
        obj.setCreatedUserPhoto(cursor.getString(cursor.getColumnIndexOrThrow(Food.CREATED_USER_PHOTO)));
        obj.setCreatedUserLikedCount(cursor.getInt(cursor.getColumnIndexOrThrow(Food.CREATED_USER_LIKED_COUNT)));
        obj.setCreatedUserCommentedCount(cursor.getInt(cursor.getColumnIndexOrThrow(Food.CREATED_USER_COMMENTED_COUNT)));
        obj.setLiked(cursor.getInt(cursor.getColumnIndexOrThrow(Food.LIKED)) == 1);
        obj.setLikedCount(cursor.getInt(cursor.getColumnIndexOrThrow(Food.LIKED_COUNT)));
        obj.setCommentedCount(cursor.getInt(cursor.getColumnIndexOrThrow(Food.COMMENTED_COUNT)));
        obj.setViewCount(cursor.getInt(cursor.getColumnIndexOrThrow(Food.VIEW_COUNT)));
        obj.setCreatedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Food.CREATED_AT))));
        obj.setCreatedUser(cursor.getString(cursor.getColumnIndexOrThrow(Food.CREATED_USER)));
        obj.setUpdatedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Food.UPDATED_AT))));
        obj.setUpdatedUser(cursor.getString(cursor.getColumnIndexOrThrow(Food.UPDATED_USER)));
        obj.setDeletedAt(new Date(cursor.getLong(cursor.getColumnIndexOrThrow(Food.DELETED_AT))));
        obj.setDeletedUser(cursor.getString(cursor.getColumnIndexOrThrow(Food.DELETED_USER)));
        obj.setDeleted(cursor.getInt(cursor.getColumnIndexOrThrow(Food.DELETED)) == 1);
    }

    public Food getByRowId(int rowID) {
        Food rowObj = null;
        Cursor cursor;
        try {
            cursor = mDb.query(Food.TB_NAME, COLUMNS,
                    Food.ROWID + "=" + rowID, null,
                    null, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                rowObj = new Food();
                setCData(rowObj, cursor);
            }
        } catch (SQLException e) {
        }
        return rowObj;
    }

    public Food getById(int id) {
        Food rowObj = null;
        Cursor cursor;
        try {
            cursor = mDb.query(Food.TB_NAME, COLUMNS,
                    Food.ID + "=" + id, null,
                    null, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                rowObj = new Food();
                setCData(rowObj, cursor);
            }
        } catch (SQLException e) {
        }
        return rowObj;
    }

    public ArrayList<Food> getList(int skip, int take) {
        ArrayList<Food> list = new ArrayList<Food>();
        Cursor cursor;
        try {
            cursor = mDb.query(Food.TB_NAME, COLUMNS,
                    null, null,
                    null, null, Food.CREATED_AT + " DESC", Integer.toString(skip) + "," + Integer.toString(take));
            if (cursor.moveToFirst()) {
                do {
                    Food rowObj = new Food();
                    setCData(rowObj, cursor);
                    list.add(rowObj);
                } while(cursor.moveToNext());
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int insert(Food obj) {
        obj.setCreatedAt(new Date());
        obj.setUpdatedAt(new Date());
        ContentValues values = getCData(obj);
        try {
            return (int) mDb.insert(Food.TB_NAME, null, values);
        } catch (Exception e) {
        }
        return -1;
    }

    public void update(Food obj) {
        obj.setUpdatedAt(new Date());
        ContentValues values = getCData(obj);
        String whereClause = Food.ID + "=?";
        String whereArgs[] = new String[]{String.valueOf(obj.getRowId())};
        try {
            mDb.update(Food.TB_NAME, values, whereClause, whereArgs);
        } catch (Exception e) {
        }
    }

    public void delete(int id) {
        ContentValues values = new ContentValues();
        values.put(Food.DELETED, true);
        values.put(Food.UPDATED_AT, (new Date()).getTime());
        String whereClause = Food.ID + "=?";
        String whereArgs[] = new String[]{String.valueOf(id)};
        try {
            mDb.update(Food.TB_NAME, values, whereClause, whereArgs);
        } catch (Exception e) {
        }
    }
}
