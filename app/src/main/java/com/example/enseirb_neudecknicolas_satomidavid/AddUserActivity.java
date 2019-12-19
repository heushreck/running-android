package com.example.enseirb_neudecknicolas_satomidavid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.enseirb_neudecknicolas_satomidavid.DataClasses.User;
import com.example.enseirb_neudecknicolas_satomidavid.SQLDatabase.DatabankHandler;
import com.example.enseirb_neudecknicolas_satomidavid.SQLDatabase.getDatabank;

public class AddUserActivity extends AppCompatActivity {

    DatabankHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Spinner spinner = findViewById(R.id.add_user_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final EditText firstname = findViewById(R.id.add_user_firstName);
        final EditText lastname = findViewById(R.id.add_user_lastName);

        try {
            getDatabank gh = new getDatabank(getApplicationContext());
            db = gh.getDb();
        } catch (Exception e) {
            firstname.setText(e.getMessage());
        }

        Button button = findViewById(R.id.add_user_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!firstname.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty()){
                    db.addUser(new User(firstname.getText().toString(), lastname.getText().toString(), spinner.getSelectedItem().toString()));
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}
