package com.artpropp.bakingapp;

import android.content.Context;

import org.junit.Test;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.assertEquals;

public class AppContextTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = ApplicationProvider.getApplicationContext();

        assertEquals("com.artpropp.bakingapp", appContext.getPackageName());
    }

}
