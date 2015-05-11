package com.example.parkingbuddy;

import com.parse.Parse;

/**
 * Created by madhavchhura on 4/25/15.
 */
public class Application extends android.app.Application {

    public void onCreate(){
        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "0nzHNGC7rAyZhfo6qwjUujceHrNyf7yDOL77zQ8Y", "Wlh8z2BbUYhd1Z9PA68e4nY03dgjtmApTntr09GK");

        //ParseObject.registerSubclass(User.class);
    }
}
