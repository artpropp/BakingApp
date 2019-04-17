package com.artpropp.bakingapp.util;

import android.content.Context;

import com.artpropp.bakingapp.model.Recipe;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecipeReaderUtil {

    public static List<Recipe> getRecipes(Context context) {
        List<Recipe> list = new ArrayList<>();

        try {
            InputStream stream = context.getAssets().open("baking.json");
            byte[] bytes = new byte[stream.available()];
            if (stream.read(bytes, 0, bytes.length) > 0) {
                String json = new String(bytes);
                list = getRecipesFromJson(json);
            }
        } catch (IOException ignored) { /* ignore */ }

        return list;
    }

    private static List<Recipe> getRecipesFromJson(String json) throws IOException {
        Moshi moshi = new Moshi.Builder().build();
        Type type = Types.newParameterizedType(List.class, Recipe.class);
        JsonAdapter<List<Recipe>> adapter = moshi.adapter(type);
        return adapter.fromJson(json);
    }
}
