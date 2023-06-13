package com.example.timetablenew.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timetablenew.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

public class EditProfileActivity extends AppCompatActivity {

    EditText editName, editSpecialty;
    TextView editEmail;
    ProgressBar pbLoad;

    Button editSave, editCancel;
    FirebaseAuth mAuth;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editName = findViewById(R.id.liquidationString);
        editSpecialty = findViewById(R.id.tv_profile_edit_specialty);
        editEmail = findViewById(R.id.tv_profile_edit_email);
        pbLoad = findViewById(R.id.pbLoadEdit);
        editCancel = findViewById(R.id.btn_edit_cancel);
        editSave = findViewById(R.id.btn_edit_save);
        pbLoad.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.edit_profile);

        getUserData();

        editSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        editCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }


    public void getUserData() {
        String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        databaseReference.child("Users").child(user_id).get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        pbLoad.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            if (task.getResult().exists()) {
                                DataSnapshot dataSnapshot = task.getResult();
                                String userName = String.valueOf(dataSnapshot.child("name").getValue());
                                String userSpecialty = String.valueOf(dataSnapshot.child("specialty").getValue());
                                String userEmail = String.valueOf(dataSnapshot.child("email").getValue());

                                editName.setText(userName);
                                editSpecialty.setText(userSpecialty);
                                editEmail.setText(userEmail);
                            } else {
                                Toast.makeText(EditProfileActivity.this, "Result doesn't exist", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(EditProfileActivity.this, "Task isn't successful", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void updateData() {
        String name, stringSpecialty, email;
        name = String.valueOf(editName.getText());
        stringSpecialty = String.valueOf(editSpecialty.getText());
        email = String.valueOf(editEmail.getText());


        if (name.isEmpty() || stringSpecialty.isEmpty()) {
            Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        }

        if (!name.isEmpty() && !stringSpecialty.isEmpty()) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("email", email);
            hashMap.put("name", name);
            hashMap.put("specialty", stringSpecialty);
            String user_id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

            databaseReference.child("Users")
                    .child(user_id)
                    .updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(EditProfileActivity.this, "Success update of information!", Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    }
}