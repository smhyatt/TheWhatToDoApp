package com.example.thedogame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.thedogame.database.ItemBaseHelper;
import com.example.thedogame.database.ItemCursorWrapper;
import com.example.thedogame.database.ItemDBSchema;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Observable;


public class ToDoDB extends Observable {
    private static ToDoDB sItemsDB;
    private static SQLiteDatabase mDatabase;
    private static Context sContext;


    // setting up the database with the things to do
    private ToDoDB(Context context)  {
        if (listAll().size() == 0) fillItemsDBFromFile("thingsToDo");
    }

    // Singleton method to initialise the database
    public static ToDoDB get(Context context) {
        if (sItemsDB == null) {
            sContext = context;
            mDatabase= new ItemBaseHelper(context.getApplicationContext()).getWritableDatabase();
            sItemsDB = new ToDoDB(context);
        }
        return sItemsDB;
    }

    // returns all the things to do listed in the database
    public ArrayList<ToDoItem> listAll() {
        ArrayList<ToDoItem> items= new ArrayList<ToDoItem>();
        ItemCursorWrapper cursor= queryItems(null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            items.add(cursor.getItem());
            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }

    // selects a random thing to do from the database
    public String getRandom() {
        ToDoItem item = new ToDoItem("");
        ItemCursorWrapper cursor = queryRandom();
        cursor.moveToFirst();
        item = cursor.getItem();
        cursor.close();
        return item.toString();
    }

    // for querying randomly, this should be eliminated and instead use a modified generic queryItems
    static private ItemCursorWrapper queryRandom() {
        Cursor cursor= mDatabase.rawQuery(
                "SELECT * FROM " + ItemDBSchema.ItemTable.TABLE_NAME +
                        " ORDER BY RANDOM() LIMIT 1", null);
        return new ItemCursorWrapper(cursor);
    }

    // adding a thing to do to the database
    public void addToDo(String what){
        ToDoItem newItem = new ToDoItem(what);
        ContentValues values= getContentValues(newItem);
        mDatabase.insert(ItemDBSchema.ItemTable.TABLE_NAME, null, values);
        this.setChanged(); notifyObservers();
    }

    // a database helper methods to convert between ToDoItems and database rows
    private static ContentValues getContentValues(ToDoItem item) {
        ContentValues values=  new ContentValues();
        values.put(ItemDBSchema.ItemTable.Cols.WHAT, item.getWhat());
        return values;
    }

    // generic querying
    static private ItemCursorWrapper queryItems(String whereClause, String[] whereArgs) {
        Cursor cursor= mDatabase.query(
                ItemDBSchema.ItemTable.TABLE_NAME,
                null, // Columns - null selects all columns
                whereClause, whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new ItemCursorWrapper(cursor);
    }


    public void close() {
        mDatabase.close();
    }


    // method for reading a file from the Assets folder and loading its content into the database
    private void fillItemsDBFromFile(String filename) {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(sContext.getAssets().open(filename)));
            String line = reader.readLine();

            while (line != null) {
                addToDo(line);
                line = reader.readLine();
            }
        } catch (IOException e) {
            Log.e(filename, "fillItemsDB: error reading file", e);
        }
    }
}


