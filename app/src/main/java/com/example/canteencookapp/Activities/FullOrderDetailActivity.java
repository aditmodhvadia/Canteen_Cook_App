package com.example.canteencookapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.canteencookapp.R;

public class FullOrderDetailActivity extends AppCompatActivity {

    TextView textView, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_order_detail);

        textView = findViewById(R.id.textView);
        textView2 = findViewById(R.id.textView2);

        Intent data = getIntent();

        textView.setText(data.getStringExtra("OrderID"));
        textView2.setText(data.getStringExtra("Category"));

    }
}
