package com.example.arimagerecognizer;

import android.os.Bundle;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import androidx.appcompat.app.AppCompatActivity;
import com.google.ar.core.examples.java.ml.R;


public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        TextView usernameTextView = findViewById(R.id.textViewUsername);
        Button logoutButton = findViewById(R.id.buttonLogout);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            usernameTextView.setText(user.getEmail());
        } else {
            // Log out the user or show an error message
            Log.d("UserProfileActivity", "No user is currently signed in.");
            Toast.makeText(this, "No user is logged in. Redirecting to login screen.", Toast.LENGTH_LONG).show();

        }

        // Set up the logout button
        logoutButton.setOnClickListener(v -> {
            logoutUser();  // Call the logoutUser method when the button is clicked
        });

        findViewById(R.id.buttonChangePassword).setOnClickListener(view -> showChangePasswordDialog());



    }



    private void logoutUser() {
        // Sign out the current user
        FirebaseAuth.getInstance().signOut();

        // Redirect to the LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_change_password, null);
        final EditText editTextCurrentPassword = view.findViewById(R.id.editTextCurrentPassword);
        final EditText editTextNewPassword = view.findViewById(R.id.editTextNewPassword);
        final EditText editTextConfirmNewPassword = view.findViewById(R.id.editTextConfirmNewPassword);

        builder.setView(view);
        builder.setPositiveButton("Change", (dialog, which) -> {
            String currentPassword = editTextCurrentPassword.getText().toString().trim();
            String newPassword = editTextNewPassword.getText().toString().trim();
            String confirmPassword = editTextConfirmNewPassword.getText().toString().trim();

            if (!newPassword.isEmpty() && newPassword.equals(confirmPassword)) {
                reAuthenticateAndChangePassword(currentPassword, newPassword);
            } else {
                Toast.makeText(UserProfileActivity.this, "Passwords do not match or are empty", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    private void reAuthenticateAndChangePassword(String currentPassword, String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && user.getEmail() != null) {
            AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), currentPassword);
            user.reauthenticate(credential).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    updatePassword(newPassword);
                } else {
                    if (task.getException() != null) {
                        Toast.makeText(UserProfileActivity.this, "Re-authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(UserProfileActivity.this, "Re-authentication failed, unknown error occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "No authenticated user found, please log in", Toast.LENGTH_SHORT).show();
        }
    }

    private void updatePassword(String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.updatePassword(newPassword).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(UserProfileActivity.this, "Password successfully updated", Toast.LENGTH_SHORT).show();
                } else {
                    // Log the error or display a more detailed error message
                    if (task.getException() != null) {
                        Log.e("UserProfileActivity", "Password update failed: " + task.getException().getMessage());
                        Toast.makeText(UserProfileActivity.this, "Failed to update password: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(UserProfileActivity.this, "Failed to update password, unknown error occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "No authenticated user found, please log in", Toast.LENGTH_SHORT).show();
        }
    }


}
