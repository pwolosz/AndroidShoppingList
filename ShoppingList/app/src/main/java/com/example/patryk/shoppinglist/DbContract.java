package com.example.patryk.shoppinglist;

import android.provider.BaseColumns;


public final class DbContract {
    private DbContract() {

    }

    public static final String DB_SHOPPING_LIST = "shoppingDatabase.db";
    public static final int DB_VERSION = 5;

    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entries.TABLE_NAME + " (" +
                    Entries._ID + " INTEGER PRIMARY KEY," +
                    Entries.KEY_NAME + " TEXT," +
                    Entries.KEY_DESCRIPTION + " ,"+
                    Entries.KEY_IMAGE_URL + ")";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entries.TABLE_NAME;

    public static class Entries implements BaseColumns {
    public static final String TABLE_NAME = "shoppingList";
    public static final String KEY_NAME = "name";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE_URL = "imageUrl";
    }

}
