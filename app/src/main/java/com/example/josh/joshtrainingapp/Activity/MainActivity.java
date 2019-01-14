package com.example.josh.joshtrainingapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.josh.joshtrainingapp.R;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String username = "aniket";
        final String password = "aniket";
        Intent intent = new Intent(MainActivity.this, NavigationHome.class);
        startActivity(intent);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editText = findViewById(R.id.editTextUsername);
                if(editText.getText().toString().equals(username)) {

                    editText = findViewById(R.id.editTextPassword);
                    if(editText.getText().toString().equals(password)) {

                        Intent intent = new Intent(MainActivity.this, NavigationHome.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }
}
