package com.mrright.inkarestaurant.room_repository;

import android.content.Context;

import androidx.room.Room;

public class DataBaseClient {

    private static DataBaseClient dataBaseClient;
    private static DataBaseInka dataBaseInka;

    public DataBaseClient(Context context) {
        try {
            dataBaseInka = Room.databaseBuilder(context, DataBaseInka.class, "dbinka.db").build();
        } catch (Exception e) {
            dataBaseInka.clearAllTables();
            e.printStackTrace();
        }
    }

    public static synchronized DataBaseClient getInstance(Context context) {
        if (dataBaseClient == null) {
            dataBaseClient = new DataBaseClient(context);
        }
        return dataBaseClient;
    }

    public DataBaseInka getDataBaseInka() {
        return dataBaseInka;
    }

}
