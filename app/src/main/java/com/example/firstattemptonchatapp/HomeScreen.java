package com.example.firstattemptonchatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {
    // declaring variables
    FirebaseAuth auth;
    TextView welcomeView;
    FirebaseDatabase mDatabase;
    DatabaseReference refrence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        // initilizing Variables
        auth = FirebaseAuth.getInstance();
        welcomeView = findViewById(R.id.WelcomeView);
        FirebaseUser user = auth.getCurrentUser();
        welcomeView.setText("Welcome " + user.getDisplayName());

        mDatabase = FirebaseDatabase.getInstance();
        // refrencing Rooms to get Rooms data only
        refrence = mDatabase.getReference().child("Rooms");
        final ListView listView = findViewById(R.id.roomList);
        // reading the Rooms Data
        refrence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Arraylist to store Room names
                ArrayList<String> roomNames = new ArrayList<>();
                // ArrayList to store Rooms full data
                final ArrayList<Room> rooms = new ArrayList<>();
                // iterate through rooms
                for (DataSnapshot childData : dataSnapshot.getChildren()) {
                    Room r = childData.getValue(Room.class);
                    rooms.add(r);
                    roomNames.add(r.roomName);
                }
                // populating the required data
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeScreen.this,
                        android.R.layout.simple_list_item_1, roomNames);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(HomeScreen.this,ChatRoomActivity.class);
                        intent.putExtra("RoomName", rooms.get(i).roomName);
                        intent.putExtra("RoomID", rooms.get(i).roomID);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void addRoom(View view) {
        EditText RoomNameEdit = findViewById(R.id.RoomName);
        String key = refrence.push().getKey();
        String RoomName = RoomNameEdit.getText().toString();
        Room newRoom = new Room(RoomName,key);

        refrence.child(key).setValue(newRoom);
    }
}
