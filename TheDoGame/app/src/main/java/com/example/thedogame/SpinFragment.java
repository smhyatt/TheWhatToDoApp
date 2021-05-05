package com.example.thedogame;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class SpinFragment extends Fragment {
    //GUI variables
    private Button try_again_button, see_all_button;
    TextView result, spin_title;
    private static ToDoDB todoDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todoDB = ToDoDB.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_spin_result, container, false);

        // animation for spinning the title of the result page
        spin_title = (TextView)v.findViewById(R.id.spin_title);
        spin_title.setText("YOU SHOULD");

        // rotate is a anim layout which designs the rotation
        Animation rotate = AnimationUtils.loadAnimation(this.getContext(),R.anim.rotate);
        spin_title.setAnimation(rotate);

        // getting a random thing to do
        result = v.findViewById(R.id.result);
        String todo = todoDB.getRandom();

        // delaying the time to display the thing to do
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                result.setText(todo);
            }
        }, 1000);

        // the try again button uses recreate to restore the activity
        try_again_button = v.findViewById(R.id.try_again_button);
        try_again_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().recreate();
            }
        });

        // we only want to see the list items button in portrait mode
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            see_all_button = v.findViewById(R.id.see_all_button);
            see_all_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // start intent to show the list activity
                    Intent intent = new Intent(getActivity(), ListActivity.class);
                    startActivity(intent);
                }
            });
        }

        return v;
    }
}

