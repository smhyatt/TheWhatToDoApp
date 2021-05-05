package com.example.thedogame;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

public class UIFragment extends Fragment {
    // GUI variables
    private Button todo_button;

    private static ToDoDB todoDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todoDB = ToDoDB.get(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_ui, container, false);

        todo_button = (Button) v.findViewById(R.id.try_button);
        todo_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), SpinActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }
}
