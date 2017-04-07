package com.example.patryk.shoppinglist;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.content.Context;
import  com.example.patryk.shoppinglist.models.ShoppingListItem;

import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.SQLException;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ShoppingListDbAdapter {
    private SQLiteDatabase db;
    private Context context;
    private DbHelper dbHelper;

    public ShoppingListDbAdapter(Context context) {
        this.context = context;
    }

    public ShoppingListDbAdapter getDbContext() {
        dbHelper = new DbHelper(context, DbContract.DB_SHOPPING_LIST, null, DbContract.DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch(SQLException e) {
            db = dbHelper.getReadableDatabase();
        }

        return this;
    }

    public void closeDbContext() {
        dbHelper.close();
    }
    public List<ShoppingListItem> getShoppingList() {
        String[] columns = {DbContract.Entries._ID,
                DbContract.Entries.KEY_NAME,
                DbContract.Entries.KEY_DESCRIPTION,
                DbContract.Entries.KEY_IMAGE_URL};
        Cursor cursor = db.query(DbContract.Entries.TABLE_NAME, columns, null, null, null, null, null);
        List<ShoppingListItem> shoppingList = new ArrayList<>();
        while(cursor != null && cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(DbContract.Entries._ID));
            String title = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Entries.KEY_NAME));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Entries.KEY_DESCRIPTION));
            String imageUrl = cursor.getString(cursor.getColumnIndexOrThrow(DbContract.Entries.KEY_IMAGE_URL));
            shoppingList.add(new ShoppingListItem(description, title, id, imageUrl));
        }
        if(cursor != null) {
            cursor.close();
        }
        return shoppingList;
    }

    public boolean updateShoppingListItem(ShoppingListItem shoppingListItem) {
        ContentValues updateValues = new ContentValues();

        int id = shoppingListItem.getId();
        String where = DbContract.Entries._ID + "=" + id;
        String name = shoppingListItem.getName();
        String description = shoppingListItem.getDescription();
        String imageUrl = shoppingListItem.getImageUrl();

        updateValues.put(DbContract.Entries.KEY_NAME, name);
        updateValues.put(DbContract.Entries.KEY_DESCRIPTION, description);
        updateValues.put(DbContract.Entries.KEY_IMAGE_URL, imageUrl);

        return db.update(DbContract.Entries.TABLE_NAME, updateValues, where, null) > 0;
    }

    public int insertShoppingListItem(String name, String description, String imageUrl) {
        ContentValues newShoppingListValues = new ContentValues();
        newShoppingListValues.put(DbContract.Entries.KEY_NAME, name);
        newShoppingListValues.put(DbContract.Entries.KEY_DESCRIPTION, description);
        newShoppingListValues.put(DbContract.Entries.KEY_IMAGE_URL, imageUrl);
        return (int)db.insert(DbContract.Entries.TABLE_NAME, null, newShoppingListValues);
    }

    public boolean deleteShoppingListItem(int id){
        String where = DbContract.Entries._ID + "=" + id;
        return db.delete(DbContract.Entries.TABLE_NAME, where, null) > 0;
    }
}
