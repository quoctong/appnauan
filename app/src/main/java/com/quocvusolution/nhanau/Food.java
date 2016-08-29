package com.quocvusolution.nhanau;

import android.graphics.Bitmap;

import com.quocvusolution.utility.JsonUtility;

import org.json.JSONObject;

public class Food extends BObject {
    public static final String TB_NAME = "Food";
    public static final String TITLE = "Title";
    public static final String DESCRIPTION = "Description";
    public static final String PRICE = "Price";
    public static final String RATING = "Rating";
    public static final String COOKED_TIME = "CookedTime";
    public static final String SERVE_COUNT = "ServeCount";
    public static final String CALORIES = "Calories";
    public static final String THUMB_PHOTO = "ThumbPhoto";
    public static final String PHOTOS = "Photos";
    public static final String MATERIALS = "Materials";
    public static final String COOKING_STEPS = "CookingSteps";
    public static final String TIP = "Tip";
    public static final String LATITUDE = "Latitude";
    public static final String LONGITUDE = "Longitude";
    public static final String ADDRESS = "Address";
    public static final String CREATED_USER_DISPLAY_NAME = "CreatedUserDisplayName";
    public static final String CREATED_USER_PHOTO = "CreatedUserPhoto";
    public static final String CREATED_USER_LIKED_COUNT = "CreatedUserLikedCount";
    public static final String CREATED_USER_COMMENTED_COUNT = "CreatedUserCommentedCount";
    public static final String LIKED = "Liked";
    public static final String LIKED_COUNT = "LikedCount";
    public static final String COMMENTED_COUNT = "CommentedCount";
    public static final String VIEW_COUNT = "ViewCount";

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public int getCookedTime() {
        return CookedTime;
    }

    public void setCookedTime(int cookedTime) {
        CookedTime = cookedTime;
    }

    public int getServeCount() {
        return ServeCount;
    }

    public void setServeCount(int serveCount) {
        ServeCount = serveCount;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        Calories = calories;
    }

    public String getThumbPhoto() {
        return ThumbPhoto;
    }

    public void setThumbPhoto(String thumbPhoto) {
        ThumbPhoto = thumbPhoto;
    }

    public String[] getPhotos() {
        return Photos;
    }

    public void setPhotos(String[] photos) {
        Photos = photos;
    }

    public String[] getMaterials() {
        return Materials;
    }

    public void setMaterials(String[] materials) {
        Materials = materials;
    }

    public String[] getCookingSteps() {
        return CookingSteps;
    }

    public void setCookingSteps(String[] cookingSteps) {
        CookingSteps = cookingSteps;
    }

    public String getTip() {
        return Tip;
    }

    public void setTip(String tip) {
        Tip = tip;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getLatitude() {
        return Latitude;
    }

    public void setLatitude(int latitude) {
        Latitude = latitude;
    }

    public int getLongitude() {
        return Longitude;
    }

    public void setLongitude(int longitude) {
        Longitude = longitude;
    }

    public Bitmap getBitmapThumbPhoto() {
        return BitmapThumbPhoto;
    }

    public void setBitmapThumbPhoto(Bitmap bitmapThumbPhoto) {
        BitmapThumbPhoto = bitmapThumbPhoto;
    }

    public Bitmap[] getBitmapPhotos() {
        return BitmapPhotos;
    }

    public void setBitmapPhotos(Bitmap[] bitmapPhotos) {
        BitmapPhotos = bitmapPhotos;
    }

    public String getCreatedUserDisplayName() {
        return CreatedUserDisplayName;
    }

    public void setCreatedUserDisplayName(String createdUserDisplayName) {
        CreatedUserDisplayName = createdUserDisplayName;
    }

    public String getCreatedUserPhoto() {
        return CreatedUserPhoto;
    }

    public void setCreatedUserPhoto(String createdUserPhoto) {
        CreatedUserPhoto = createdUserPhoto;
    }

    public Bitmap getBimapCreatedUserPhoto() {
        return BimapCreatedUserPhoto;
    }

    public void setBimapCreatedUserPhoto(Bitmap bimapCreatedUserPhoto) {
        BimapCreatedUserPhoto = bimapCreatedUserPhoto;
    }

    public int getCreatedUserLikedCount() {
        return CreatedUserLikedCount;
    }

    public void setCreatedUserLikedCount(int createdUserLikedCount) {
        CreatedUserLikedCount = createdUserLikedCount;
    }

    public int getCreatedUserCommentedCount() {
        return CreatedUserCommentedCount;
    }

    public void setCreatedUserCommentedCount(int createdUserCommentedCount) {
        CreatedUserCommentedCount = createdUserCommentedCount;
    }

    public boolean isLiked() {
        return Liked;
    }

    public void setLiked(boolean liked) {
        Liked = liked;
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

    public int getViewCount() {
        return ViewCount;
    }

    public void setViewCount(int viewCount) {
        ViewCount = viewCount;
    }

    public void setJData(JSONObject jObject) {
        super.setJData(jObject);
        this.setTitle(JsonUtility.getString(jObject, TITLE));
        this.setDescription(JsonUtility.getString(jObject, DESCRIPTION));
        this.setPrice(JsonUtility.getDouble(jObject, PRICE));
        this.setRating(JsonUtility.getDouble(jObject, RATING));
        this.setCookedTime(JsonUtility.getInt(jObject, COOKED_TIME));
        this.setServeCount(JsonUtility.getInt(jObject, SERVE_COUNT));
        this.setCalories(JsonUtility.getInt(jObject, CALORIES));
        this.setThumbPhoto(JsonUtility.getString(jObject, THUMB_PHOTO));
        this.setPhotos(JsonUtility.getStrings(jObject, PHOTOS));
        this.setMaterials(JsonUtility.getStrings(jObject, MATERIALS));
        this.setCookingSteps(JsonUtility.getStrings(jObject, COOKING_STEPS));
        this.setTip(JsonUtility.getString(jObject, TIP));
        this.setAddress(JsonUtility.getString(jObject, ADDRESS));
        this.setLatitude(JsonUtility.getInt(jObject, LATITUDE));
        this.setLongitude(JsonUtility.getInt(jObject, LONGITUDE));
        this.setCreatedUserDisplayName(JsonUtility.getString(jObject, CREATED_USER_DISPLAY_NAME));
        this.setCreatedUserPhoto(JsonUtility.getString(jObject, CREATED_USER_PHOTO));
        this.setCreatedUserLikedCount(JsonUtility.getInt(jObject, CREATED_USER_LIKED_COUNT));
        this.setCreatedUserCommentedCount(JsonUtility.getInt(jObject, CREATED_USER_COMMENTED_COUNT));
        this.setLiked(JsonUtility.getBoolean(jObject, LIKED));
        this.setLikedCount(JsonUtility.getInt(jObject, LIKED_COUNT));
        this.setCommentedCount(JsonUtility.getInt(jObject, COMMENTED_COUNT));
        this.setViewCount(JsonUtility.getInt(jObject, VIEW_COUNT));
    }

    private String Title;
    private String Description;
    private double Price;
    private double Rating;
    private int CookedTime;
    private int ServeCount;
    private int Calories;
    private String ThumbPhoto;
    private String[] Photos;
    private String[] Materials;
    private String[] CookingSteps;
    private String Tip;
    private String Address;
    private int Latitude;
    private int Longitude;
    private Bitmap BitmapThumbPhoto;
    private Bitmap[] BitmapPhotos;
    private String CreatedUserDisplayName;
    private String CreatedUserPhoto;
    private Bitmap BimapCreatedUserPhoto;
    private int CreatedUserLikedCount;
    private int CreatedUserCommentedCount;
    private boolean Liked;
    private int LikedCount;
    private int CommentedCount;
    private int ViewCount;

    public Food() {
        super();
        this.Title = "";
        this.Description = "";
        this.Price = 0;
        this.Rating = 0;
        this.CookedTime = 0;
        this.ServeCount = 0;
        this.Calories = 0;
        this.ThumbPhoto ="";
        this.Photos = new String[0];
        this.Materials = new String[0];
        this.CookingSteps = new String[0];
        this.Tip = "";
        this.Address = "";
        this.Latitude = 0;
        this.Longitude = 0;
        this.BitmapThumbPhoto = null;
        this.BitmapPhotos = new Bitmap[0];
        this.CreatedUserPhoto = "";
        this.CreatedUserDisplayName = "";
        this.BimapCreatedUserPhoto = null;
        this.CreatedUserLikedCount = 0;
        this.CreatedUserCommentedCount = 0;
        this.Liked = false;
        this.LikedCount = 0;
        this.CommentedCount = 0;
        this.ViewCount = 0;
    }
}
