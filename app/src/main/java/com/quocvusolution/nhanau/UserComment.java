package com.quocvusolution.nhanau;

import android.graphics.Bitmap;

import com.quocvusolution.utility.JsonUtility;

import org.json.JSONObject;

public class UserComment extends BObject {
    public static final String USERNAME = "Username";
    public static final String FOOD_ID = "FoodId";
    public static final String COMMENT_TO_ID = "CommentToId";
    public static final String CONTENT = "Content";
    public static final String LIKED_COUNT = "LikedCount";
    public static final String COMMENTED_COUNT = "CommentedCount";
    public static final String SHARED_COUNT = "SharedCount";
    public static final String RATING = "Rating";
    public static final String USER_DISPLAY_NAME = "UserDisplayName";
    public static final String USER_PHOTO = "UserPhoto";

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public int getFoodId() {
        return FoodId;
    }

    public void setFoodId(int foodId) {
        FoodId = foodId;
    }

    public int getCommentToId() {
        return CommentToId;
    }

    public void setCommentToId(int commentToId) {
        CommentToId = commentToId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public int getLikedCount() {
        return LikedCount;
    }

    public void setLikedCount(int likedCount) {
        LikedCount = likedCount;
    }

    public int getCommentedCount() {
        return CommentedCount;
    }

    public void setCommentedCount(int commentedCount) {
        CommentedCount = commentedCount;
    }

    public int getSharedCount() {
        return SharedCount;
    }

    public void setSharedCount(int sharedCount) {
        SharedCount = sharedCount;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public String getUserDisplayName() {
        return UserDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        UserDisplayName = userDisplayName;
    }

    public String getUserPhoto() {
        return UserPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        UserPhoto = userPhoto;
    }

    public Bitmap getBitmapUserPhoto() {
        return BitmapUserPhoto;
    }

    public void setBitmapUserPhoto(Bitmap bitmapUserPhoto) {
        BitmapUserPhoto = bitmapUserPhoto;
    }

    public void setJData(JSONObject jObject) {
        super.setJData(jObject);
        this.setUsername(JsonUtility.getString(jObject, USERNAME));
        this.setFoodId(JsonUtility.getInt(jObject, FOOD_ID));
        this.setCommentToId(JsonUtility.getInt(jObject, COMMENT_TO_ID));
        this.setContent(JsonUtility.getString(jObject, CONTENT));
        this.setLikedCount(JsonUtility.getInt(jObject, LIKED_COUNT));
        this.setCommentedCount(JsonUtility.getInt(jObject, COMMENTED_COUNT));
        this.setSharedCount(JsonUtility.getInt(jObject, SHARED_COUNT));
        this.setRating(JsonUtility.getDouble(jObject, RATING));
        this.setUserDisplayName(JsonUtility.getString(jObject, USER_DISPLAY_NAME));
        this.setUserPhoto(JsonUtility.getString(jObject, USER_PHOTO));
    }

    private String Username;
    private int FoodId;
    private int CommentToId;
    private String Content;
    private int LikedCount;
    private int CommentedCount;
    private int SharedCount;
    private double Rating;
    private String UserDisplayName;
    private String UserPhoto;
    private Bitmap BitmapUserPhoto;

    public UserComment() {
        super();
        this.Username = "";
        this.FoodId = 0;
        this.CommentToId = 0;
        this.Content = "";
        this.LikedCount = 0;
        this.CommentedCount = 0;
        this.SharedCount = 0;
        this.Rating = 0;
        this.UserDisplayName = "";
        this.UserPhoto = "";
        this.BitmapUserPhoto = null;
    }
}
