package com.example.recipeopedia;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Register parse models
        // ParseObject.registerSubclass(Review.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("bnQS6Z8W100oAgAEOjA8eJIKqz0g92SextzULzLH")
                .clientKey("0zSx7RFWsvIzVy6XtImfFyce09q6z6TBrTYT7SFG")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
