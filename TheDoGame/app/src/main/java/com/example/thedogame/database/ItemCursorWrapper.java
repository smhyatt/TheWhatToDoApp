package com.example.thedogame.database;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.example.thedogame.ToDoItem;
import com.example.thedogame.database.ItemDBSchema.ItemTable;

public class ItemCursorWrapper extends CursorWrapper {
    public ItemCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public ToDoItem getItem() {
        String what = getString(getColumnIndex(ItemTable.Cols.WHAT));
        return new ToDoItem(what);
    }

}