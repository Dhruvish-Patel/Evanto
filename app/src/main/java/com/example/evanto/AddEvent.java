package com.example.evanto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddEvent extends AppCompatActivity {
    CardView birthday,marriage,seminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        birthday = findViewById(R.id.birthday_event);
        marriage = findViewById(R.id.marriage_event);
        seminar = findViewById(R.id.seminar);

        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddEvent.this, Birthday.class));
            }
        });


        marriage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddEvent.this, Marriage.class));
            }
        });


        seminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AddEvent.this, Seminar.class));
            }
        });


    }
}