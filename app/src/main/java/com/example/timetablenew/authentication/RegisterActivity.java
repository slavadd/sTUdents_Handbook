package com.example.timetablenew.authentication;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablenew.MainActivity;
import com.example.timetablenew.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextConfirmPassword;
    TextInputEditText fullName, specialty;
    Button buttonRegister;
    ProgressBar progressBar;
    TextView textViewLogin;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        fullName = findViewById(R.id.tv_full_name);
        specialty = findViewById(R.id.specialty);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        editTextConfirmPassword = findViewById(R.id.confirmPassword);
        progressBar = findViewById(R.id.pbRegister);
        textViewLogin = findViewById(R.id.click_login);
        buttonRegister = findViewById(R.id.btn_register);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO CHANGE
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String name, stringSpecialty, email, password, confirmPassword;
                name = String.valueOf(fullName.getText());
                stringSpecialty = String.valueOf(specialty.getText());
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                confirmPassword = String.valueOf(editTextConfirmPassword.getText());

                if (TextUtils.isEmpty(name)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Enter full name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(stringSpecialty)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Enter specialty name", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmPassword)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Enter email and password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(password) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(confirmPassword)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else if (!TextUtils.isEmpty(password) && !TextUtils.isEmpty(email) && TextUtils.isEmpty(confirmPassword)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Enter confirm password", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Email doesn't match structure ", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword)) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this, "Password and confirm password doesn't match", Toast.LENGTH_SHORT).show();
                }


                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(stringSpecialty) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(confirmPassword) && password.equals(confirmPassword) && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressBar.setVisibility(View.GONE);
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "createUserWithEmail:success");
                                        Toast.makeText(RegisterActivity.this, "Successful registration!",
                                                Toast.LENGTH_SHORT).show();


                                        HashMap<String, Object> hashMap = new HashMap<>();
                                        hashMap.put("name", name);
                                        hashMap.put("specialty", stringSpecialty);
                                        hashMap.put("email", email);

                                        String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
//                                        DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
//                                        current_user_db.setValue(true);


                                        databaseReference.child("Users")
                                                .child(user_id)
                                                .setValue(hashMap)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        Toast.makeText(RegisterActivity.this, "Success database import", Toast.LENGTH_SHORT).show();

                                                    }
                                                });

                                        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();

                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}