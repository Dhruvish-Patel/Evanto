package com.example.evanto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class ConfirmBooking extends AppCompatActivity implements PaymentResultListener {
    int n;
    TextView name ,email ,phone ,guests ,date ,location, pay, type;
    Button submit;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseDatabase root;
    DatabaseReference refrence;
    String data[] = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_booking);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        guests = findViewById(R.id.guests);
        date = findViewById(R.id.date);
        location = findViewById(R.id.location);
        pay = findViewById(R.id.payment);
        type = findViewById(R.id.type);
        submit = findViewById(R.id.submit);
        Intent intent = getIntent();
        String str = intent.getStringExtra("Message");

        data = str.split(" ");

        name.setText("Name : "+data[0].toString());
        email.setText("Email Id : "+data[1].toString());
        phone.setText("Phone No. : "+data[2].toString());
        guests.setText("No. of Guests : "+data[5].toString());
        date.setText("Date : "+data[3].toString());
        location.setText("Location : "+data[4].toString());
        type.setText("Type of event : "+data[6].toString());
        n = Integer.parseInt(data[5]);
        n = n * 150;
        n= n+2000;
        pay.setText("Total Cost : Rs.2000 (Venue) + Rs.150/guest--------------------Rs."+ n);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment();
            }
        });

    }


    public void startPayment() {

        /**
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        /**
         * Set your logo here
         */
        //checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Events Now");
            options.put("description", "Reference No. #123456");
            //options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
//            options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
//            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", n*100);//pass amount in currency subunits
//            options.put("prefill.email", "gaurav.kumar@example.com");
//            options.put("prefill.contact","9988776655");
//            JSONObject retryObj = new JSONObject();
//            retryObj.put("enabled", true);
//            retryObj.put("max_count", 4);
//            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
           // Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }
    @Override
    public void onPaymentSuccess(String s) {


        Toast.makeText(ConfirmBooking.this,"Payment Successful",Toast.LENGTH_LONG).show();
        FirebaseUser user = mAuth.getCurrentUser();
        root = FirebaseDatabase.getInstance();
        refrence = root.getReference("Users/"+user.getUid().toString()+"/Events");
        EventDetails details = new EventDetails(data[0],data[1],data[5],data[2],data[3],data[4]);
        refrence.child(data[6]).setValue(details);
        Toast.makeText(ConfirmBooking.this,"SUCCESSFUL",Toast.LENGTH_LONG).show();
        startActivity(new Intent(ConfirmBooking.this, YourEvents.class));
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(ConfirmBooking.this,"Error in Payment",Toast.LENGTH_LONG).show();
    }
}