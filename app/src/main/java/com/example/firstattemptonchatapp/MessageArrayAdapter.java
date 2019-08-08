package com.example.firstattemptonchatapp;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class MessageArrayAdapter extends ArrayAdapter<Message> {
    public MessageArrayAdapter(Context context, int resource, ArrayList objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout itemView = (LinearLayout) convertView;
        if (itemView == null){
            itemView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.message,parent,false);
        }
        Message message = getItem(position);
        String CUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        TextView uidView = itemView.findViewById(R.id.UID);
        TextView messageView = itemView.findViewById(R.id.message);
        TextView stampView = itemView.findViewById(R.id.stamp);

        uidView.setText(message.name);
        messageView.setText(message.message);
        stampView.setText(message.timeStamp);

        if(CUID.equals(message.UID)){
            itemView.setGravity(Gravity.RIGHT);
        } else {
            itemView.setGravity(Gravity.LEFT);
        }

        return itemView;
    }
}
