package com.example.dreams.baking;


import com.example.dreams.baking.models.RecipesModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call <ArrayList<RecipesModel>> RecipeList();







//    @GET("topher/2017/May/59121517_baking/baking.json")
//    Call <ArrayList<IngredientsResultModel>> IngredientList();

}
