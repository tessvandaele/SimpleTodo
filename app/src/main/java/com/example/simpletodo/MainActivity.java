package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.os.Bundle;
import org.apache.commons.io.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List <String> items;

    //referencing each view in xml file
    Button btnAdd;
    EditText etItem;
    RecyclerView rvItems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize the screen: set xml file as screen attributes
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //defining reference variables
        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvItems = findViewById(R.id.rvItems);

        //load in data
        loadItems();

        //setting up the Items Adapter which handles the View Holder
        ItemsAdapter.OnLongClickListener listener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //delete item from list
                items.remove(position);

                //notify adapter at what position the item was deleted
                itemsAdapter.notifyItemRemoved(position);

                //confirm item removal to user
                Toast.makeText(getApplicationContext(), "Item was removed", Toast.LENGTH_SHORT).show();

                //updating file
                saveItems();
            }
        };

        itemsAdapter = new ItemsAdapter(items, listener);
        rvItems.setAdapter(itemsAdapter);
        rvItems.setLayoutManager(new LinearLayoutManager(this));

        //button logic
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = etItem.getText().toString();

                //add item to model
                items.add(todoItem);

                //notify adapter that we added a new item
                itemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");

                //pop up to confirm addition of item to list
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT).show();

                //update file
                saveItems();
            }
        });
    }

    //persistence logic using commons library

    //returns file in which data will be stored
    private File getDataFile() {
        return new File(getFilesDir(), "data.txt");
    }

    //load items by reading every line of the data file
    private void loadItems() {
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading items", e);
            items = new ArrayList<>();
        }
    }

    //saves items into file
    private void saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing items", e);
        }

    }
}