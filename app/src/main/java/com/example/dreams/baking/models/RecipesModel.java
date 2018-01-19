package com.example.dreams.baking.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipesModel implements Parcelable{


    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("ingredients")
    @Expose
    private List<IngredientsModel> result = null;

    @SerializedName("steps")
    @Expose
    private List<StepsModel> steps=null;

    protected RecipesModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        result = in.createTypedArrayList(IngredientsModel.CREATOR);
        steps = in.createTypedArrayList(StepsModel.CREATOR);
    }

    public static final Creator<RecipesModel> CREATOR = new Creator<RecipesModel>() {
        @Override
        public RecipesModel createFromParcel(Parcel in) {
            return new RecipesModel(in);
        }

        @Override
        public RecipesModel[] newArray(int size) {
            return new RecipesModel[size];
        }
    };

    public int getRecipeId() {
        return id;
    }

    public String getRecipeName() {
        return name;
    }


    public List<IngredientsModel> getIngredientsResult() {
        return result;
    }

    public List<StepsModel> getStepsResult() {
        return steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeTypedList(result);
        dest.writeTypedList(steps);
    }
}
