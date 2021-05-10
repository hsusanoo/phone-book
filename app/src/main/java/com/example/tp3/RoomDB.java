package com.example.tp3;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tp3.dao.ContactDAO;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)

public abstract class RoomDB extends RoomDatabase {

    private static final String DATABASE_NAME = "database";
    private static RoomDB database;

    public synchronized static RoomDB getInstance(Context context) {

        if (database == null) {

            database = Room
                    .databaseBuilder(context.getApplicationContext(), RoomDB.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return database;
    }

    public abstract ContactDAO contactDAO();
}
