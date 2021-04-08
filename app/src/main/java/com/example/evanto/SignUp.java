package com.example.evanto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {

    TextInputLayout name, email, phone, password, confirm;
    Button signUp, logIn;
    private int flag = 0;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    FirebaseDatabase root;
    DatabaseReference refrence;

    final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    Matcher matcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        password = findViewById(R.id.password);
        confirm = findViewById(R.id.confirm_password);
        signUp = findViewById(R.id.signup);
        logIn = findViewById(R.id.login);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = name.getEditText().getText().toString();
                String emailId = email.getEditText().getText().toString();
                String pass1 = password.getEditText().getText().toString();
                String pass2 = confirm.getEditText().getText().toString();
                String phoneNumber = phone.getEditText().getText().toString();

                if(fullname.isEmpty())
                {
                    name.setError("Please enter your name");
                    flag = 1;
                }
                if(emailId.isEmpty())
                {
                    email.setError("Please enter your EmailId");
                    flag = 1;
                }
                if(pass1.isEmpty())
                {
                    password.setError("Please enter a Password");
                    flag = 1;
                }
                if(pass2.isEmpty())
                {
                    confirm.setError("Please confirm your password");
                    flag = 1;
                }
                if(phoneNumber.isEmpty())
                {
                    phone.setError("Please enter your phone number");
                    flag = 1;
                }
                if(!fullname.isEmpty() && !emailId.isEmpty() && !pass1.isEmpty() && !pass2.isEmpty() && !phoneNumber.isEmpty())
                {
                    flag = 0;
                    Pattern pattern = Patterns.EMAIL_ADDRESS;
                    if(!pattern.matcher(emailId).matches())
                    {
                        email.setError("Enter a valid email");
                        flag=1;
                    }

                }
                if(flag==0)
                {
                    if(pass1.equals(pass2))
                    {

                        mAuth.createUserWithEmailAndPassword(emailId,pass1)
                                .addOnCompleteListener(SignUp.this,new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {

                                            FirebaseUser user = mAuth.getCurrentUser();
                                            root = FirebaseDatabase.getInstance();
                                            refrence = root.getReference("Users");

                                            UserHelperClass newUser = new UserHelperClass(fullname,emailId,phoneNumber,pass1);
                                            refrence.child(user.getUid().toString()).setValue(newUser);
                                            Intent intent = new Intent(SignUp.this, Dashboard.class);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Toast.makeText(SignUp.this, "Sorry couldn't add.", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

                    }
                    else
                    {
                        confirm.setError("Confirm password doesn't match the password.");
                    }
                }

            }
        });
    }
}