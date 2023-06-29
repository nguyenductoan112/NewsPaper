package com.example.newspaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword, signupConfirm;
    private Button signupButton;
    private TextView loginReairectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword= findViewById(R.id.signup_password);
        signupButton=findViewById(R.id.signup_button);
        signupConfirm = findViewById(R.id.signup_confirmpassword);
        loginReairectText=findViewById(R.id.logninRedirectText);

        String adminEmail = "admin";
        String adminPassword = "123";

        auth.createUserWithEmailAndPassword(adminEmail, adminPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SignUp.this, "Admin account created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SignUp.this, "Admin account creation failed", Toast.LENGTH_SHORT).show();
                }
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String confirmPass = signupConfirm.getText().toString().trim();

                if (user.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                } else if (pass.isEmpty()) {
                    signupPassword.setError("Password cannot be empty");
                } else if (confirmPass.isEmpty()) {
                    signupConfirm.setError("ConfirmPassword cannot be empty");
                } else if (!confirmPass.equals(pass)) {
                    signupConfirm.setError("password do not match");
                } else if (pass.length() < 8) {
                    signupPassword.setError("Password must contain at least 8 characters");
                } else if (!pass.matches(".*[a-zA-Z].*")) {
                        signupPassword.setError("Password must contain at least one letter");
                    } else {
                    auth.createUserWithEmailAndPassword(user, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUp.this, Login.class));
                            } else {
                                Toast.makeText(SignUp.this, "Sign Up failed " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });
        loginReairectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUp.this, Login.class));
            }
        });
    }
}