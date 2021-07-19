package com.example.loginandlogoutusingretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LogOut extends AppCompatActivity {

    TextView textView1, textView2;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_out);

        getSupportActionBar().hide();

        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        btnLogout = findViewById(R.id.btnLogout);

        checkUserExistence();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("username").commit();
                editor.remove("password").commit();
                editor.apply();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }

    public void checkUserExistence() {

        SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
        if (sp.contains("username") && sp.contains("password")) {
            textView1.setText(sp.getString("username", ""));
            textView2.setText(sp.getString("password", ""));
        } else {
            startActivity(new Intent(getApplicationContext(), LogOut.class));
        }
    }
}