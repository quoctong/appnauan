package com.quocvusolution.nhanau;

public class FoodPair {
    public Food getFoodLeft() {
        return FoodLeft;
    }

    public void setFoodLeft(Food foodLeft) {
        FoodLeft = foodLeft;
    }

    public Food getFoodRight() {
        return FoodRight;
    }

    public void setFoodRight(Food foodRight) {
        FoodRight = foodRight;
    }

    private Food FoodLeft;
    private Food FoodRight;
}
