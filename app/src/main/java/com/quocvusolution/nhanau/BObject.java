package com.quocvusolution.nhanau;

import com.quocvusolution.utility.JsonUtility;

import org.json.JSONObject;

import java.util.Date;

public class BObject {
    public static final String ROWID = "RowId";
    public static final String ID = "Id";
    public static final String CREATED_AT = "CreatedAt";
    public static final String CREATED_USER = "CreatedUser";
    public static final String UPDATED_AT = "UpdatedAt";
    public static final String UPDATED_USER = "UpdatedUser";
    public static final String DELETED_AT = "DeletedAt";
    public static final String DELETED_USER = "DeletedUser";
    public static final String DELETED = "Deleted";

    public int getRowId() {
        return RowId;
    }

    public void setRowId(int rowId) {
        RowId = rowId;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Date getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        CreatedAt = createdAt;
    }

    public String getCreatedUser() {
        return CreatedUser;
    }

    public void setCreatedUser(String createdUser) {
        CreatedUser = createdUser;
    }

    public Date getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        UpdatedAt = updatedAt;
    }

    public String getUpdatedUser() {
        return UpdatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        UpdatedUser = updatedUser;
    }

    public Date getDeletedAt() {
        return DeletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        DeletedAt = deletedAt;
    }

    public String getDeletedUser() {
        return DeletedUser;
    }

    public void setDeletedUser(String deletedUser) {
        DeletedUser = deletedUser;
    }

    public boolean isDeleted() {
        return Deleted;
    }

    public void setDeleted(boolean deleted) {
        Deleted = deleted;
    }

    protected int RowId;
    protected int Id;
    protected Date CreatedAt;
    protected String CreatedUser;
    protected Date UpdatedAt;
    protected String UpdatedUser;
    protected Date DeletedAt;
    protected String DeletedUser;
    protected boolean Deleted;

    protected BObject() {
        this.RowId = 0;
        this.Id = 0;
        this.CreatedAt = new Date();
        this.CreatedUser = "";
        this.UpdatedAt = new Date();
        this.UpdatedUser = "";
        this.DeletedAt = new Date();
        this.DeletedUser = "";
    }

    protected void setJData(JSONObject jObject) {
        this.setId(JsonUtility.getInt(jObject, ID));
        this.setRowId(JsonUtility.getInt(jObject, ROWID));
        this.setCreatedUser(JsonUtility.getString(jObject, CREATED_USER));
        this.setUpdatedUser(JsonUtility.getString(jObject, UPDATED_USER));
        this.setDeletedUser(JsonUtility.getString(jObject, DELETED_USER));
        this.setDeleted(JsonUtility.getBoolean(jObject, DELETED));
        this.setCreatedAt(JsonUtility.getDate(jObject, CREATED_AT));
        this.setUpdatedAt(JsonUtility.getDate(jObject, UPDATED_AT));
        this.setDeletedAt(JsonUtility.getDate(jObject, DELETED_AT));
    }
}

