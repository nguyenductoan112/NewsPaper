package com.example.newspaper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText loginEmail, loginPassword;
    private TextView goToSignupActivity, resetPassword;
    private Button loginButton;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        resetPassword = findViewById(R.id.login_resetPassword);
        goToSignupActivity = findViewById(R.id.goToSignupActivity);
        progressDialog = new ProgressDialog(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString().trim();
                String password = loginPassword.getText().toString().trim();
                boolean isValid = true;
                progressDialog.setTitle("Login...");
                progressDialog.show();

                if (email.isEmpty()) {
                    loginEmail.setError("Email cannot be empty");
                    isValid = false;
                }

                if (password.isEmpty()) {
                    loginPassword.setError("Password cannot be empty");
                    isValid = false;
                }

                if (isValid) {
                    if (email.equals("admin@gmail.com") && password.equals("admin12345")) {
                        firebaseAuth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        progressDialog.cancel();
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                                        clearAll();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.cancel();
                                        Toast.makeText(LoginActivity.this, "Invalid email or password" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        firebaseAuth.signInWithEmailAndPassword(email, password)
                                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                    @Override
                                    public void onSuccess(AuthResult authResult) {
                                        progressDialog.cancel();
                                        Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(LoginActivity.this, NewsActivity.class));
                                        clearAll();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.cancel();
                                        Toast.makeText(LoginActivity.this, "Invalid email or password" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                } else progressDialog.cancel();
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = loginEmail.getText().toString();

                if (email.isEmpty()) {
                    loginEmail.setError("Email cannot be empty");
                } else {
                    progressDialog.setTitle("Sending Mail...");
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(email)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    progressDialog.cancel();
                                    Toast.makeText(LoginActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }

            }
        });

        goToSignupActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }
    private void clearAll() {
        loginEmail.setText("");
        loginPassword.setText("");
    }
}