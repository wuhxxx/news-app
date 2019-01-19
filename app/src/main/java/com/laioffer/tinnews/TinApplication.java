package com.laioffer.tinnews;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.facebook.stetho.Stetho;
import com.laioffer.tinnews.database.AppDatabase;

public class TinApplication extends Application {

    // Init the Room in the TinApplication
    public static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);

        // Init the Room in the TinApplication
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "tin_db").build();
    }

    // get database
    public static AppDatabase getDataBase() {
        return database;
    }


}

