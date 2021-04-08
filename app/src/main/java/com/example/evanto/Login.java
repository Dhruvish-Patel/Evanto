package com.example.evanto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {
    private int flag = 0;
    TextInputLayout uname , password;
    Button forgot_password, logIn, signUp;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        uname = findViewById(R.id.uname);
        password = findViewById(R.id.password);
        forgot_password = findViewById(R.id.forgotPassword);
        logIn = findViewById(R.id.login);
        signUp = findViewById(R.id.signup);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,SignUp.class);
                startActivity(intent);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(uname.getEditText().getText().toString().isEmpty())
                {
                    uname.setError("Please enter the User Name");
                    flag=1;
                }
                if(password.getEditText().getText().toString().isEmpty())
                {
                    password.setError("Please enter the Password");
                    flag=1;
                }
                if(!password.getEditText().getText().toString().isEmpty() && !uname.getEditText().getText().toString().isEmpty())
                {
                    flag=0;
                }


                if(flag == 0)
                {
                    mAuth.signInWithEmailAndPassword(uname.getEditText().getText().toString(),password.getEditText().getText().toString())
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful())
                                    {
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        Intent intent = new Intent(Login.this, Dashboard.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(Login.this,"Authentication Failure",Toast.LENGTH_LONG);
                                    }
                                }
                            });

                }
            }
        });
    }
}