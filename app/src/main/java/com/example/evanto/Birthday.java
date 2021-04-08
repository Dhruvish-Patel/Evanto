package com.example.evanto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Birthday extends AppCompatActivity {
    TextInputLayout name, emailId, phone, guests;
    TextInputEditText dateSet;
    Button submit;
    int flag = 0;
    String str;
    AutoCompleteTextView location;
    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase root;
    DatabaseReference refrence;

    DatePickerDialog.OnDateSetListener setListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday);

        name = findViewById(R.id.fullname);
        emailId = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        guests = findViewById(R.id.guests);
        submit = findViewById(R.id.submit);
        dateSet = findViewById(R.id.date);

        location = findViewById(R.id.location);
        String [] options = {"Surat_Marriot_Hotel-Umra","CoutYard_by_Marriot-Hazira","Hotel_Orange_International-RailwayStation_Road","Hotel_Amisha_International-Una_Pani_Road","Lords_Plaza-DelhiGate_RingRoad", "Hotel_Sadbhav_Villa-Marvela_Business_Hub_RTO"};
        ArrayAdapter adapter = new ArrayAdapter(Birthday.this,R.layout.dropdown_item,options);
        location.setAdapter(adapter);


        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        dateSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Birthday.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        month = month+1;
                        String date = day +"/"+month+"/"+year;
                        dateSet.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = name.getEditText().getText().toString();
                String email = emailId.getEditText().getText().toString();
                String phoneNumber = phone.getEditText().getText().toString();
                String guestsCount = guests.getEditText().getText().toString();
                String date = dateSet.getText().toString();
                String loc = location.getEditableText().toString();

                if(fullname.isEmpty())
                {
                    name.setError("Please enter your name");
                    flag = 1;
                }
                if(email.isEmpty())
                {
                    emailId.setError("Please enter your EmailId");
                    flag = 1;
                }
                if(guestsCount.isEmpty())
                {
                    guests.setError("Please enter number of guests");
                    flag = 1;
                }
                if(date.isEmpty())
                {
                    dateSet.setError("Please enter a date");
                    flag = 1;
                }
                if(phoneNumber.isEmpty())
                {
                    phone.setError("Please enter your phone number");
                    flag = 1;
                }
                if(loc.equals("Location"))
                {
                    location.setError("Please select a location");
                    flag = 1;
                }

                if(!fullname.isEmpty() && !email.isEmpty() && !date.isEmpty() && !phoneNumber.isEmpty() && !loc.isEmpty() && !guestsCount.isEmpty())
                {
                    flag = 0;

                    Pattern pattern = Patterns.EMAIL_ADDRESS;
                    if(!pattern.matcher(email).matches())
                    {
                        emailId.setError("Enter a valid email");
                        flag=1;
                    }

                }

                if(flag==0)
                {
                    fullname = fullname.replace(' ','_');
                    str = fullname;
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    root = FirebaseDatabase.getInstance();
//                    refrence = root.getReference("Users/"+user.getUid().toString()+"/Events");
//
//                    EventDetails details = new EventDetails(fullname,email,guestsCount,phoneNumber,date,loc);
//                    refrence.child("Birthday").setValue(details);
//                    Toast.makeText(Birthday.this,"SUCCESSFUL",Toast.LENGTH_LONG).show();
                    openDialogue();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            str = str+ " "+email+" "+phoneNumber+" "+date+" "+loc+" "+guestsCount+" Birthday";
                            Intent intent = new Intent(Birthday.this,ConfirmBooking.class);
                            intent.putExtra("Message",str);
                            startActivity(intent);
                        }
                    }, 7000);
                }

            }
        });


    }
    public void openDialogue()
    {
        AlertDialogueEx dialogueEx = new AlertDialogueEx();
        dialogueEx.show(getSupportFragmentManager(),"Alert");

    }
}