package com.example.evanto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class YourEvents extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    CardView add;
    ListView events,marriageEvents,corporateEvents;
    ArrayList<String> yourEvents,marriage_events,corporate_events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_events);
        FirebaseUser user = mAuth.getCurrentUser();
        add = findViewById(R.id.add_event);
        events = findViewById(R.id.data);
        marriageEvents = findViewById(R.id.marriage_data);
        corporateEvents = findViewById(R.id.seminar_data);
        DatabaseReference ref,ref1,ref2;
        yourEvents = new ArrayList<String>();
        marriage_events = new ArrayList<String>();
        corporate_events = new ArrayList<String>();

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, yourEvents);
        final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, marriage_events);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, corporate_events);


        ref = FirebaseDatabase.getInstance().getReference().child("Users/"+user.getUid().toString()+"/Events/Birthday");
        ref1 = FirebaseDatabase.getInstance().getReference().child("Users/"+user.getUid().toString()+"/Events/Marriage");
        ref2 = FirebaseDatabase.getInstance().getReference().child("Users/"+user.getUid().toString()+"/Events/Corporate");

        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                yourEvents.add(snapshot.getValue(String.class));
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                yourEvents.remove(snapshot.getValue(String.class));

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }

        });

        ref1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                marriage_events.add(snapshot.getValue(String.class));
                adapter1.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                marriage_events.remove(snapshot.getValue(String.class));

                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }

        });

        ref2.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                corporate_events.add(snapshot.getValue(String.class));
                adapter2.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                corporate_events.remove(snapshot.getValue(String.class));

                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }

        });
    events.setAdapter(adapter);
    marriageEvents.setAdapter(adapter1);
    corporateEvents.setAdapter(adapter2);

    add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(YourEvents.this,AddEvent.class));
        }
    });
    }

}