package com.example.topgmeals.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.topgmeals.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * This class is an Activity that allows the user to enter their email to receive an email from
 * Firebase to reset their password.
 */
public class MainForgotPassword extends AppCompatActivity {
    private EditText editTextEmail;
    private Button btnResetPassword;
    private FirebaseAuth mAuth;

    /**
     * This method handles the layout and logic of the Activity. Called on Activity creation.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_forgot_password);

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.forgotPwdEmailAddress);

        btnResetPassword = findViewById(R.id.forgotPwdResetPassword);
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });
    }

    /**
     * This method validates if the user has entered an appropriate email address and either sends
     * the user an email containing a reset password link or shows failed to send email message.
     */
    private void resetPassword() {
        String email = editTextEmail.getText().toString();

        if (email.isEmpty()) {
            editTextEmail.setError("Email address is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid email address!");
            editTextEmail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Email is sent
                    Toast.makeText(MainForgotPassword.this,
                            "Please check your email mailbox to reset your password!",
                            Toast.LENGTH_LONG).show();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            finish();
                        }
                    }, 3000); // 3 seconds
                } else {
                    Toast.makeText(MainForgotPassword.this,
                            "Failed to send email! Please try again!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}