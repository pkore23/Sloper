package com.sloper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout parent = (LinearLayout) findViewById(R.id.featured_scroll);
        View child;
        LayoutInflater inflater = getLayoutInflater();
        child = inflater.inflate(R.layout.product_card,null);
        parent.addView(child);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),DetailsActivity.class);
                startActivity(i);
            }
        });
        parent = (LinearLayout) findViewById(R.id.new_scroll);
        child = inflater.inflate(R.layout.product_card,null);
        parent.addView(child);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),DetailsActivity.class);
                startActivity(i);
            }
        });
        parent = (LinearLayout) findViewById(R.id.deals_scroll);
        child = inflater.inflate(R.layout.product_card,null);
        parent.addView(child);
        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),DetailsActivity.class);
                startActivity(i);
            }
        });
    }
}
