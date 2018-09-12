package com.example.canteencookapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canteencookapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText idEditText;
    Button loginButton;

    DatabaseReference cookRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        idEditText = findViewById(R.id.idEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateID();
            }
        });

    }

    private void validateID(){

        final String ID = idEditText.getText().toString();

        if(ID.isEmpty()){
            idEditText.setError("Enter ID first");
            idEditText.requestFocus();
            return;
        }

        if(ID.length()<5){
            idEditText.setError("Enter ID first");
            idEditText.requestFocus();
            return;
        }

        cookRoot = FirebaseDatabase.getInstance().getReference().child("CookData");
        cookRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(ID).exists()){
                    Toast.makeText(LoginActivity.this, "Valid ID", Toast.LENGTH_LONG).show();
//                    send category through intent
                    Intent i = new Intent(LoginActivity.this, OrderListActivity.class);
                    startActivity(i);
                }
                else{
//                    hide progress dialog
                    Toast.makeText(LoginActivity.this, "Invalid ID", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
