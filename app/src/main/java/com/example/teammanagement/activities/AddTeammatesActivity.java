package com.example.teammanagement.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.teammanagement.R;
import com.example.teammanagement.Utils.Constants;
import com.example.teammanagement.Utils.Team;
import com.example.teammanagement.Utils.Teammate;
import com.example.teammanagement.Utils.User;
import com.example.teammanagement.adapters.AddUsersAdapter;
import com.example.teammanagement.adapters.TeammatesCkAdapter;
import com.example.teammanagement.database.JDBCController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddTeammatesActivity extends AppCompatActivity {

    private Button btn_cancel;
    private Button btn_save;
    private ListView lv_users;

    private Intent intent;

    private TeammatesCkAdapter adapter;

    private JDBCController jdbcController;
    private Connection c;

    private Team team;
    private User currentUser;
    private List<Teammate> checkedUsers = new ArrayList<>();
    private List<Teammate> listUsers = new ArrayList<>();
    private Map<Integer,byte[]> listUserPhotos = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teammates);

        initComponents();
    }

    public void initComponents() {

        btn_cancel = findViewById(R.id.add_teammates_btn_cancel);
        btn_save = findViewById(R.id.add_teammates_btn_save);
        lv_users = findViewById(R.id.add_teammates_lv_users);

        btn_save.setOnClickListener(clickSave());
        btn_cancel.setOnClickListener(clickCancel());

        getUser();

        getUsers();

        Thread t1 = new Thread(){
            @Override
            public void run() {
                getUserProfilePicture();
            }
        };
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if (listUsers.size() != 0) {
            adapter = new TeammatesCkAdapter(getApplicationContext(), R.layout.list_item_teammates_ck, listUsers, getLayoutInflater());
            lv_users.setAdapter(adapter);
        }

    }

    public void getUser(){
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.APP_SHAREDPREF,MODE_PRIVATE);
        Gson gson = new Gson();
        String json =  sharedPreferences.getString(Constants.CURRENT_USER,"");
        currentUser= gson.fromJson(json,User.class);
    }

    public void getUserProfilePicture(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        for(Teammate teammate: listUsers) {
            DatabaseReference myRef = database.getReference(String.valueOf(teammate.getUserID()));
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String valoare = dataSnapshot.getValue(String.class);
                    String key=dataSnapshot.getKey();
                    if( valoare != null) {
                        byte[] decodedString = Base64.decode(valoare, Base64.DEFAULT);
                        listUserPhotos.put(Integer.parseInt(key),decodedString);
                        for(Teammate teammate:listUsers ) {
                            if(Integer.parseInt(key) == teammate.getUserID()) {
                                teammate.setUserProfilePicture(decodedString);
                                break;
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
        }
    }

    public void getUsers(){
        try(Statement s = c.createStatement()){
            try(ResultSet r = s.executeQuery("SELECT ID,NUMEUTILIZATOR FROM UTILIZATORI U, ")){

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener clickCancel() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EditTeamActivity.class);
                startActivity(intent);
            }
        };
    }

    private View.OnClickListener clickSave() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),EditTeamActivity.class);
                startActivity(intent);
            }
        };
    }
}
