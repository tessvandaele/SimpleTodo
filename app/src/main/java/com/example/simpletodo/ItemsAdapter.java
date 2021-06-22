package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

//displays the data from the model into a row in the recycler
public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    //interface to retrieve data from MainActivity class on which item to delete
    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    List<String> items;
    OnLongClickListener listener;

    //constructor
    public ItemsAdapter(List<String> items, OnLongClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    //responsible for creating each view component
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate a view using layout inflater
        View todoView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);

        // wrap it inside a ViewHolder
        return new ViewHolder(todoView);
    }

    @Override
    //responsible for taking data at a specific position and inserting it into a ViewHolder
    public void onBindViewHolder(@NonNull ItemsAdapter.ViewHolder holder, int position) {
        //retrieve item at position
        String item = items.get(position);

        //bind item to specific ViewHolder
        holder.bind(item);

    }

    @Override
    //returns the number of items in the list
    public int getItemCount() {
        return items.size();
    }


    //container that represents each row of the list
    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem;

        //constructor
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(android.R.id.text1);
        }

        //update view inside the ViewHolder with the new data
        public void bind(String item) {
            tvItem.setText(item);

            //item removal logic
            tvItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    //remove item from recycler view
                    listener.onItemLongClicked(getAdapterPosition());
                    return true;
                }
            });
        }
    }
}
