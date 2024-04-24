package com.example.arimagerecognizer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.ar.core.examples.java.ml.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameInput = findViewById(R.id.editTextUsername);
        final EditText passwordInput = findViewById(R.id.editTextPassword);
        final TextView navigateToSignupPage = findViewById(R.id.textViewSignUp);
        final Button loginButton = findViewById(R.id.button_enter);

        navigateToSignupPage.setOnClickListener(listener -> startActivity(new Intent(this, RegisterActivity.class)));

        loginButton.setOnClickListener(view -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Username and password cannot be empty", Toast.LENGTH_LONG).show();
                return; // Early return to prevent further processing
            }

            if (!validateUsername(username)) {
                Toast.makeText(LoginActivity.this, "Invalid Username, Check and try again", Toast.LENGTH_LONG).show();
            } else if (!validatePassword(password)) {
                Toast.makeText(LoginActivity.this, "Invalid Password, Check and try again", Toast.LENGTH_LONG).show();
            } else {
                loginUser(username, password);
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    public void loginUser(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Login attempt with empty username or password", Toast.LENGTH_LONG).show();
            return; // Prevent login with empty credentials
        }

        mAuth.signInWithEmailAndPassword(username, password)
                .addOnSuccessListener(success -> startActivity(new Intent(this, HomeActivity.class)))
                .addOnFailureListener(failure -> Toast.makeText(LoginActivity.this, "Login Failed: " + failure.getMessage(), Toast.LENGTH_LONG).show());
    }

    public static boolean validateUsername(String username) {
        String EMAIL_REGEX =
                "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(username);

        return matcher.matches();
    }

    public static boolean validatePassword(String password) {
        return password.matches("^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
    }
}
