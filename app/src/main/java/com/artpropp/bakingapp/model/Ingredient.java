package com.artpropp.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

class Ingredient implements Parcelable {

    @Json(name = "quantity")
    private float mQuantity;
    @Json(name = "measure")
    private String mMeasure;
    @Json(name = "ingredient")
    private String mIngredient;

    protected Ingredient(Parcel in) {
        mQuantity = in.readFloat();
        mMeasure = in.readString();
        mIngredient = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(mQuantity);
        dest.writeString(mMeasure);
        dest.writeString(mIngredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public float getQuantity() {
        return mQuantity;
    }

    public void setQuantity(float mQuantity) {
        this.mQuantity = mQuantity;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public String getIngredient() {
        return mIngredient;
    }

    public void setIngredient(String mIngredient) {
        this.mIngredient = mIngredient;
    }
}
