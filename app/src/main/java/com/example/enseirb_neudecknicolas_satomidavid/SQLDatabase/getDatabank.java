package com.example.enseirb_neudecknicolas_satomidavid.SQLDatabase;

import android.content.Context;
import android.util.Log;

import com.example.enseirb_neudecknicolas_satomidavid.DataClasses.Run;
import com.example.enseirb_neudecknicolas_satomidavid.DataClasses.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;


public class getDatabank {
    private DatabankHandler db;
    private Context context;

    public getDatabank(Context context) {
        this.context = context;
        db = new DatabankHandler(context);
        if(db.getUserCount() == 0){
            invoke();
        }
    }

    public void invoke(){
        db.trunkateUsers();
        db.trunkateRuns();
        db.trunkateCurrentRun();

        User nicolas = new User("Nicolas", "Neudeck", "m");
        User david = new User("David", "Satomi", "m");
        db.addUser(david);
        db.addUser(nicolas);

        List<User> users = db.getAllUsers();

        String json = "{\"-35\":\"143\",\"-36\":\"143\", \"-37\":\"143\",\"-38\":\"143\"}";

        JSONObject locationList;
        try {
            locationList =  new JSONObject(json);
        } catch (JSONException e) {
            locationList = null;
        }

        db.addRun(new Run(users.get(0), new Date(), 42000, 120*60 + 70, 21, 25, locationList));
        db.addRun(new Run(users.get(1), new Date(), 5000, 28*60 + 4, 12, 17, locationList));
        Log.d("LocationList","how many 1: " + db.getRunsCount());
    }

    public DatabankHandler getDb(){
        return db;
    }
}

