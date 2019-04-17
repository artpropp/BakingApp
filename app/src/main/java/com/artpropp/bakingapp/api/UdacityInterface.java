package com.artpropp.bakingapp.api;

import com.artpropp.bakingapp.model.Recipe;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UdacityInterface {

    @GET("android-baking-app-json")
    Observable<List<Recipe>> getRecipes();

}
