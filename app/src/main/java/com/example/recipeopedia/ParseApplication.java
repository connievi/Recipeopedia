package com.example.recipeopedia;

import android.app.Application;

import com.example.recipeopedia.models.FavoriteRecipe;
import com.example.recipeopedia.models.Review;
import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(FavoriteRecipe.class);
        ParseObject.registerSubclass(Review.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("bnQS6Z8W100oAgAEOjA8eJIKqz0g92SextzULzLH")
                .clientKey("0zSx7RFWsvIzVy6XtImfFyce09q6z6TBrTYT7SFG")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
