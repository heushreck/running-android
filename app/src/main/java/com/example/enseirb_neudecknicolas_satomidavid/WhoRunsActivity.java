package com.example.enseirb_neudecknicolas_satomidavid;

import android.content.Intent;
import android.os.Bundle;

import com.example.enseirb_neudecknicolas_satomidavid.DataClasses.User;
import com.example.enseirb_neudecknicolas_satomidavid.SQLDatabase.DatabankHandler;
import com.example.enseirb_neudecknicolas_satomidavid.SQLDatabase.getDatabank;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

public class WhoRunsActivity extends AppCompatActivity {

    DatabankHandler db;
    List<User> users;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_who_runs);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        FloatingActionButton add_runner = findViewById(R.id.add_runner);

        add_runner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(WhoRunsActivity.this, AddUserActivity.class);
                startActivity(i);
            }
        });
        recyclerView = findViewById(R.id.who_recycler_view);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {

        try {
            getDatabank gh = new getDatabank(getApplicationContext());
            db = gh.getDb();
            users = db.getAllUsers();
        } catch (Exception e) {
            Log.d("whoRunsActivity", e.getMessage());
        }

        User[] userListData = new User[users.size()];
        userListData = users.toArray(userListData);

        UserItemAdapter adapter = new UserItemAdapter(userListData);
        recyclerView.setAdapter(adapter);
        super.onResume();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}
