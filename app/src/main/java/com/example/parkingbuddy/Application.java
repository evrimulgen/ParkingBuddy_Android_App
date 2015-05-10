package com.example.parkingbuddy;

import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by madhavchhura on 4/25/15.
 */
public class Application extends android.app.Application {

    public void onCreate(){
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "0nzHNGC7rAyZhfo6qwjUujceHrNyf7yDOL77zQ8Y", "Wlh8z2BbUYhd1Z9PA68e4nY03dgjtmApTntr09GK");

        //ParseObject.registerSubclass(User.class);
        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }
}
