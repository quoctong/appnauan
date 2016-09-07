package com.quocvusolution.nhanau;

import android.graphics.Bitmap;

import com.quocvusolution.utility.JsonUtility;

import org.json.JSONObject;

public class FoodCart extends BObject {
    public static final String TB_NAME = "FoodCart";
    public static final String USERNAME = "Username";
    public static final String FOOD_ID = "FoodId";
    public static final String FOOD_COUNT = "FoodCount";

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

    public int getFoodCount() {
        return FoodCount;
    }

    public void setFoodCount(int foodCount) {
        FoodCount = foodCount;
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
        this.setFoodCount(JsonUtility.getInt(jObject, FOOD_COUNT));
    }

    private String Username;
    private int FoodId;
    private int FoodCount;
    private Food Food;

    public FoodCart() {
        super();
        this.Username = "";
        this.FoodId = 0;
        this.FoodCount = 1;
        this.Food = null;
    }
}

