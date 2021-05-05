package com.example.thedogame;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class ListActivity extends AppCompatActivity {
    private FragmentManager fm;
    Fragment fragmentList;

    private static ToDoDB todoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        todoDB = ToDoDB.get(this);

        fm= getSupportFragmentManager();
        //Orientation portrait
        fragmentList = fm.findFragmentById(R.id.container_list);
        if (fragmentList == null) {
            fragmentList = new ListFragment();
            fm.beginTransaction()
                    .add(R.id.container_list, fragmentList)
                    .commit();
        }
    }
}



