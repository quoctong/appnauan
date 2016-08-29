package com.quocvusolution.nhanau;

import android.graphics.Bitmap;

import com.quocvusolution.utility.JsonUtility;

import org.json.JSONObject;

public class UserAccount extends BObject {
    public static final String USERNAME = "Username";
    public static final String PASSWORD = "Password";
    public static final String NAME = "Name";
    public static final String RATING = "Rating";
    public static final String PHONE = "Phone";
    public static final String EMAIL = "Email";
    public static final String GENDER = "Gender";
    public static final String ADDRESS = "Address";
    public static final String PHOTO = "Photo";

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public Bitmap getBitmapPhoto() {
        return BitmapPhoto;
    }

    public void setBitmapPhoto(Bitmap bitmapPhoto) {
        BitmapPhoto = bitmapPhoto;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public void setJData(JSONObject jObject) {
        super.setJData(jObject);
        this.setUsername(JsonUtility.getString(jObject, USERNAME));
        this.setPassword(JsonUtility.getString(jObject, PASSWORD));
        this.setName(JsonUtility.getString(jObject, NAME));
        this.setRating(JsonUtility.getDouble(jObject, RATING));
        this.setPhone(JsonUtility.getString(jObject, PHONE));
        this.setEmail(JsonUtility.getString(jObject, EMAIL));
        this.setEmail(JsonUtility.getString(jObject, GENDER));
        this.setAddress(JsonUtility.getString(jObject, ADDRESS));
        this.setPhoto(JsonUtility.getString(jObject, PHOTO));
    }

    private String Username;
    private String Password;
    private String Name;
    private double Rating;
    private String Email;
    private String Phone;
    private String Address;
    private String Gender;
    private String Photo;
    private Bitmap BitmapPhoto;
    private String Token;

    public UserAccount() {
        super();
        this.Username = "";
        this.Password = "";
        this.Name = "";
        this.Rating = 0;
        this.Phone = "";
        this.Email = "";
        this.Address = "";
        this.Gender = "";
        this.Photo = "";
        this.BitmapPhoto = null;
        this.Token = "";
    }
}
