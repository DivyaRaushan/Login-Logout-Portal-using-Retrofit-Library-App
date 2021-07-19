package com.example.loginandlogoutusingretrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText emailEdittext, passwordEditText;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        textView = findViewById(R.id.textView);
        emailEdittext = findViewById(R.id.emailEdittext);
        passwordEditText = findViewById(R.id.passwordEdittext);
        btn = findViewById(R.id.btn);

        checkUserExistence();


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEdittext.getText().toString();
                String password = passwordEditText.getText().toString();
                process(email, password);
            }
        });

    }

    public void process(String email, String password) {

        Call<ResponseModel> call = ApiController.getInstance()
                .getApi()
                .userverify(email, password);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                ResponseModel obj = response.body();
                String output = obj.getMessage();

                if (output.equals("failed")) {
                    emailEdittext.setText("");
                    passwordEditText.setText("");
                    textView.setText("Invalid Username or Password");
                    textView.setTextColor(Color.RED);
                }
                if (output.equals("exist")) {
                    SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("username", email);
                    editor.putString("password", password);
                    editor.commit();
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), LogOut.class));
                    finish();
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                textView.setText(t.toString());
                textView.setTextColor(Color.RED);
            }
        });

    }

    public void checkUserExistence() {
        SharedPreferences sp = getSharedPreferences("credentials", MODE_PRIVATE);
        if (sp.contains("username") && sp.contains("password")) {
            startActivity(new Intent(getApplicationContext(), LogOut.class));
        } else {
            textView.setText("Please Login");
            textView.setTextColor(Color.RED);
        }

    }
}