package com.example.firstattemptonchatapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ChatRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Intent i = getIntent();
        String roomName = i.getStringExtra("RoomName");
        String roomID = i.getStringExtra("RoomID");

        TextView textView = findViewById(R.id.RoomInformation);
        textView.setText("The Room you have entered is "+roomName + "\nWith ID of "+roomID);

    }
}
