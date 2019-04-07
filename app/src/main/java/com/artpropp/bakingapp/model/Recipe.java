package com.artpropp.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.util.List;

public class Recipe implements Parcelable {

    @Json(name = "id")
    private int mId;
    @Json(name = "name")
    private String mName;
    @Json(name = "ingredients")
    private List<Ingredient> mIngredients;
    @Json(name = "steps")
    private List<Step> mSteps;
    @Json(name = "serving")
    private int mServings;
    @Json(name = "image")
    private String mImage;

    protected Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mIngredients = in.createTypedArrayList(Ingredient.CREATOR);
        mSteps = in.createTypedArrayList(Step.CREATOR);
        mServings = in.readInt();
        mImage = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
        dest.writeTypedList(mIngredients);
        dest.writeTypedList(mSteps);
        dest.writeInt(mServings);
        dest.writeString(mImage);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(List<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public List<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(List<Step> mSteps) {
        this.mSteps = mSteps;
    }

    public int getServings() {
        return mServings;
    }

    public void setServings(int mServings) {
        this.mServings = mServings;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }
}
