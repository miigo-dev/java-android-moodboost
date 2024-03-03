package com.example.moodboostio;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    EditText editTextEmail, editTextPassword, editTextUsername;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        editTextUsername = findViewById(R.id.etUsername);
        editTextEmail = findViewById(R.id.etEmail);
        editTextPassword = findViewById(R.id.etPassword);
        ImageView btnCreate = findViewById(R.id.imgRegister);
        CheckBox cbAgree = findViewById(R.id.cbTerms);

        btnCreate.setOnClickListener(view -> {
            String username, email, password;
            username = String.valueOf(editTextUsername.getText());
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextPassword.getText());

            StringBuilder errorMessage = new StringBuilder();

            if (TextUtils.isEmpty(username)) {
                errorMessage.append("Enter Username\n");
            }

            if (TextUtils.isEmpty(email)) {
                errorMessage.append("Enter Email\n");
            }

            if (TextUtils.isEmpty(password)) {
                errorMessage.append("Enter Password\n");
            }

            if (password.length() < 6) {
                errorMessage.append(getString(R.string.passLength));
            }

            if (!cbAgree.isChecked()) {
                errorMessage.append("Agree to Terms");

            }

            if (errorMessage.length() > 0) {
                Toast.makeText(Register.this, errorMessage.toString(), Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "Account Created",
                                    Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(Register.this, MainActivity.class));
                        } else {
                            Toast.makeText(Register.this, "Account creation failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}