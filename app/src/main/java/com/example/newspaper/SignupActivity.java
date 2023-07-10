package com.example.newspaper;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.google.firebase.firestore.FirebaseFirestore;

@SuppressWarnings("ALL")
public class SignupActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private EditText signupFullName, signupEmail, signupPhoneNumber, signupPassword, signupConfirmPassword;
    private Button signupButton;
    private TextView goToLoginActivity;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signupFullName = findViewById(R.id.signup_fullName);
        signupEmail = findViewById(R.id.signup_email);
        signupPhoneNumber = findViewById(R.id.signup_phoneNumber);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        signupConfirmPassword = findViewById(R.id.signup_confirmPassword);
        goToLoginActivity = findViewById(R.id.goToLoginActivity);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signupFullName.getText().toString().trim();
                String email = signupEmail.getText().toString().trim();
                String phoneNumber = signupPhoneNumber.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String confirmPassword = signupConfirmPassword.getText().toString().trim();
                boolean isValid = true;
                progressDialog.setTitle("Signup...");
                progressDialog.show();

                if (email.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                    isValid = false;
                }

                if (name.isEmpty()) {
                    signupFullName.setError("Name cannot be empty");
                    isValid = false;
                }

                if (phoneNumber.isEmpty()) {
                    signupPhoneNumber.setError("Phone Number cannot be empty");
                    isValid = false;
                } else if (!phoneNumber.matches("^0\\d{9}$")) {
                    signupPhoneNumber.setError("Phone Number must contain 10 number");
                    isValid = false;
                }

                if (password.isEmpty()) {
                    signupPassword.setError("Password cannot be empty");
                    isValid = false;
                } else if (password.length() < 8) {
                    signupPassword.setError("Password must contain at least 8 characters");
                    isValid = false;
                } else if (!password.matches(".*[a-zA-Z].*") || !password.matches(".*\\d.*")) {
                    signupPassword.setError("Password must contain at least one letter and one number");
                    isValid = false;
                }

                if (confirmPassword.isEmpty()) {
                    signupConfirmPassword.setError("ConfirmPassword cannot be empty");
                    isValid = false;
                } else if (!confirmPassword.equals(password)) {
                    signupConfirmPassword.setError("Password do not match");
                    isValid = false;
                }

                if (isValid) {
                    firebaseAuth.createUserWithEmailAndPassword(email, password)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    progressDialog.cancel();
                                    Toast.makeText(SignupActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    firebaseFirestore.collection("User")
                                            .document(FirebaseAuth.getInstance().getUid())
                                            .set(new UserModel(name, phoneNumber, email));
                                    clearAll();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.cancel();
                                    Toast.makeText(SignupActivity.this, "Sign Up failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                } else progressDialog.cancel();
            }
        });
        goToLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll();
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    private void clearAll() {
        signupFullName.setText("");
        signupEmail.setText("");
        signupPhoneNumber.setText("");
        signupPassword.setText("");
        signupConfirmPassword.setText("");
    }
}