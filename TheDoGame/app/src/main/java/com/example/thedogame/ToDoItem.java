package com.example.thedogame;

public class ToDoItem {
    private String mWhat = null;

    public ToDoItem(String what) {
        mWhat = what;
    }

    @Override
    public String toString() {
        return mWhat;
    }

    public String getWhat() {
        return mWhat;
    }

    public void setWhat(String what) {
        mWhat = what;
    }

}
