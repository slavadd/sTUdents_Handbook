package com.example.timetablenew.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablenew.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {

    TextView profileName, profileSpecialty, profileEmail;
    Button buttonLogout;
    Button buttonEditProfile;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profile_edit_name);
        profileSpecialty = findViewById(R.id.profile_edit_specialty);
        profileEmail = findViewById(R.id.profile_edit_email);
        buttonLogout = findViewById(R.id.btn_logout);
        buttonEditProfile = findViewById(R.id.btn_edit_profile);
        progressBar = findViewById(R.id.pbLoadInformation);

        progressBar.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.profile);

        getUserData();

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        buttonEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void showUserData() {
        Intent intent = getIntent();

        String nameUser = intent.getStringExtra("name");
        String emailUser = intent.getStringExtra("email");
        String specialtyUser = intent.getStringExtra("specialty");

        profileName.setText(nameUser);
        profileSpecialty.setText(specialtyUser);
        profileEmail.setText(emailUser);
    }


    public void getUserData() {
        firebaseUser = mAuth.getCurrentUser();
        String user_id;
        if(firebaseUser != null) {
            user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();


            databaseReference.child("Users").child(user_id).get()
                    .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                if (task.getResult().exists()) {
                                    DataSnapshot dataSnapshot = task.getResult();
                                    String userName = String.valueOf(dataSnapshot.child("name").getValue());
                                    String userSpecialty = String.valueOf(dataSnapshot.child("specialty").getValue());
                                    String userEmail = String.valueOf(dataSnapshot.child("email").getValue());

                                    profileName.setText(userName);
                                    profileSpecialty.setText(userSpecialty);
                                    profileEmail.setText(userEmail);
                                } else {
                                    Toast.makeText(ProfileActivity.this, "Result doesn't exist", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ProfileActivity.this, "Task isn't successful", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

        }
    }


//    public void passUserData(){
//        String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
//        Query checkUserDB = databaseReference.child("Users").child(user_id);
//    }
}