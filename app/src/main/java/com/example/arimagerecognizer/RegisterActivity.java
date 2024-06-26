package com.example.arimagerecognizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.examples.java.ml.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize UI components
        final EditText usernameEditText = findViewById(R.id.editTextNewUsername);
        final EditText passwordEditText = findViewById(R.id.editTextNewPassword);
        final EditText confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        Button registerButton = findViewById(R.id.button_register_enter);

        // Set click listener for the register button
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();


                if (!LoginActivity.validateUsername(username)) {
                    // Show error if any field is empty
                    Toast.makeText(RegisterActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!LoginActivity.validatePassword(password)) {
                    // Show error if any field is empty
                    Toast.makeText(RegisterActivity.this, "Invalid password. Must be 8+ letters and numbers only.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!RegisterActivity.passwordValidation(password, confirmPassword)) {
                    // Show error if any field is empty
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }




                registerNewUser(username, password);
            }
        });

        mAuth = FirebaseAuth.getInstance();


    }

    public void registerNewUser(String username, String password) {
        mAuth.createUserWithEmailAndPassword(username, password).addOnSuccessListener(success -> {
            startActivity(new Intent(this, HomeActivity.class));
        }).addOnFailureListener(failure -> {
            Toast.makeText(this, failure.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    public void onSignInClick(View view) {
        // Intent to open the login activity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish(); // Optional: if you want to close the registration screen after going to the login
    }

    public static boolean passwordValidation (String password, String confirmPassword){
        return Objects.equals(password, confirmPassword);
    }
}




