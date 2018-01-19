package com.example.dreams.baking.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IngredientsResultModel {
    @SerializedName("ingredients")
    @Expose
    private ArrayList<IngredientsModel> result = null;

    public ArrayList<IngredientsModel> getIngredientsResult() {
        return result;
    }
}
