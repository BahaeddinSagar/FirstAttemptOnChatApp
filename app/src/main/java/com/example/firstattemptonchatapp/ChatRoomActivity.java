package com.example.firstattemptonchatapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Timestamp;
import java.util.ArrayList;

public class ChatRoomActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference ref;
    String UID;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        Intent i = getIntent();
        String roomName = i.getStringExtra("RoomName");
        String roomID = i.getStringExtra("RoomID");
        UID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        name = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        database = FirebaseDatabase.getInstance();
        ref = database.getReference().child("Chats").child(roomID);

        ref.orderByChild("TIMESTAMP").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ListView chatListView = findViewById(R.id.ChatList);
                ArrayList<Message> MessageList = new ArrayList<>();
                for (DataSnapshot singleMessage : dataSnapshot.getChildren()) {
                    Message m = singleMessage.getValue(Message.class);
                    MessageList.add(m);
                }
                MessageArrayAdapter adapter = new MessageArrayAdapter(ChatRoomActivity.this,
                        R.layout.message, MessageList);
                chatListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void Send(View view) {
        EditText editText = findViewById(R.id.edittext1);
        String text = editText.getText().toString();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        editText.setText("");
        Message m = new Message(UID, text, String.valueOf(timestamp.getTime()), name);
        String key = ref.push().getKey();

        ref.child(key).setValue(m);

    }
}
