package com.quocvusolution.nhanau;

import android.graphics.Bitmap;

import com.quocvusolution.utility.JsonUtility;

import org.json.JSONObject;

public class FoodSave extends BObject {
    public static final String TB_NAME = "FoodSave";
    public static final String USERNAME = "Username";
    public static final String FOOD_ID = "FoodId";

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

    public Food getFood() {
        return Food;
    }

    public void setFood(Food food) {
        Food = food;
    }

    public void setJData(JSONObject jObject) {
        super.setJData(jObject);
        this.setUsername(JsonUtility.getString(jObject, USERNAME));
        this.setFoodId(JsonUtility.getInt(jObject, FOOD_ID));
    }

    private String Username;
    private int FoodId;
    private Food Food;

    public FoodSave() {
        super();
        this.Username = "";
        this.FoodId = 0;
        this.Food = null;
    }
}

