package com.example.thedogame;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class SpinActivity extends AppCompatActivity {
    private FragmentManager fm;
    Fragment fragmentSpin, fragmentList;

    private static ToDoDB todoDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_result);
        todoDB = ToDoDB.get(this);

        fm = getSupportFragmentManager();
        setUpFragments();
    }

    private void setUpFragments() {
        // Orientation landscape including the result page and the list of all things to do
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            fragmentSpin = fm.findFragmentById(R.id.container_spin_result_landscape);
            fragmentList = fm.findFragmentById(R.id.container_list);
            if ((fragmentSpin == null) && (fragmentList == null)) {
                fragmentSpin = new SpinFragment();
                fragmentList = new ListFragment();
                fm.beginTransaction()
                        .add(R.id.container_spin_result_landscape, fragmentSpin)
                        .add(R.id.container_list, fragmentList)
                        .commit();
            }
        } else {
            //Orientation portrait including the result page
            fragmentSpin = fm.findFragmentById(R.id.container_spin_result_portrait);
            if (fragmentSpin == null) {
                fragmentSpin = new SpinFragment();
                fm.beginTransaction()
                        .add(R.id.container_spin_result_portrait, fragmentSpin)
                        .commit();
            }
        }
    }

}





