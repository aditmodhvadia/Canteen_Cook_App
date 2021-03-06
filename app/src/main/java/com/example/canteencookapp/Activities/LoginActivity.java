package com.example.canteencookapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.canteencookapp.R;
import com.example.canteencookapp.Service.MyNotificationService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

//    Layout Views
    private EditText idEditText;
    private Button loginButton;
    private ProgressBar progressBar;
//    Variables
    private String CATEGORY = "Category";
    private String MyPREFERENCES = "Login Data";
    SharedPreferences pref;
//    Firebase Variables
    DatabaseReference cookRoot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        idEditText = findViewById(R.id.idEditText);
        loginButton = findViewById(R.id.loginButton);
        progressBar = findViewById(R.id.progressBar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm =
                        (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null &&
                        activeNetwork.isConnectedOrConnecting();
                if(isConnected){
                    progressBar.setVisibility(View.VISIBLE);
//                    Validate the entries by the user
                    validateID();
                }
                else
                    Toast.makeText(LoginActivity.this, "No Internet Connectivity", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void validateID(){

        final String ID = idEditText.getText().toString();

        if(ID.isEmpty()){
            idEditText.setError("Enter ID first");
            idEditText.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        if(ID.length()<5){
            idEditText.setError("Enter ID first");
            idEditText.requestFocus();
            progressBar.setVisibility(View.GONE);
            return;
        }

        cookRoot = FirebaseDatabase.getInstance().getReference().child("CookData");
        cookRoot.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(ID).exists()){
//                    send category through intent
                    String category = dataSnapshot.child(ID).child(CATEGORY).getValue().toString();
                    Intent i = new Intent(LoginActivity.this, OrderListActivity.class);
                    i.putExtra(CATEGORY,category);
                    progressBar.setVisibility(View.GONE);
                    idEditText.setText("");
//                    Save value of code in Shared preferences
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString(CATEGORY, category);
                    editor.apply();
                    startActivity(i);
                }
                else{
//                    hide progress dialog
                    progressBar.setVisibility(View.GONE);
                    idEditText.setError("Invalid ID");
                    idEditText.setText("");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(OrderListActivity.flag)
            stopService(OrderListActivity.service);

        pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        if(pref.contains(CATEGORY)){
            Intent i = new Intent(LoginActivity.this, OrderListActivity.class);
            i.putExtra(CATEGORY,pref.getString(CATEGORY, null));
            startActivity(i);
        }



    }
}
