package com.example.thedogame;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Observable;

public class ListFragment extends Fragment {
    private static ToDoDB todoDB;
    private ArrayList<ToDoItem> localDB;
    private RecyclerView listItems;
    private ItemAdapter mAdapter;


    public void update(Observable observable, Object data) {
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todoDB = ToDoDB.get(getActivity());
        localDB = todoDB.listAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v= inflater.inflate(R.layout.fragment_list, container, false);
        listItems = v.findViewById(R.id.listItems);
        listItems.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter= new ItemAdapter();
        listItems.setAdapter(mAdapter);

        return v;
    }

    // for implementing the recyclerview in order to scroll through the list
    private class ItemHolder extends RecyclerView.ViewHolder {
        private TextView mWhatTextView, mWhereTextView, mNoView;

        public ItemHolder(View itemView) {
            super(itemView);
            mWhatTextView = itemView.findViewById(R.id.item_what);
        }

        public void bind(ToDoItem item){
            mWhatTextView.setText(item.getWhat());
        }
    }

    // for implementing the recyclerview in order to scroll through the list
    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder> {
        public ItemAdapter() { }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            // onerow is a layout defining the design of each row
            View v = layoutInflater.inflate(R.layout.onerow, parent, false);
            return new ItemHolder(v);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            ToDoItem item = localDB.get(position);
            holder.bind(item);
        }

        @Override
        public int getItemCount(){ return localDB.size(); }
    }
}
