package com.example.enseirb_neudecknicolas_satomidavid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.enseirb_neudecknicolas_satomidavid.DataClasses.Run;
import com.example.enseirb_neudecknicolas_satomidavid.SQLDatabase.DatabankHandler;
import com.example.enseirb_neudecknicolas_satomidavid.SQLDatabase.getDatabank;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class RunsActivity extends AppCompatActivity {

    DatabankHandler db;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runs);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getDatabank gH = new getDatabank(getApplicationContext());
        db = gH.getDb();

        List<Run> runs = db.getAllRuns();
        Log.d("LocationList", "how many runs: "+ db.getRunsCount());

        recyclerView = findViewById(R.id.main_recycler_view);
        RunItemAdapter adapter = new RunItemAdapter(runs, db);
        DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_filter){
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "Filter comming in future", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        if (item.getItemId() == R.id.action_delete){
            Snackbar snackbar = Snackbar
                    .make(findViewById(android.R.id.content), "To delete, long click on the run", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
