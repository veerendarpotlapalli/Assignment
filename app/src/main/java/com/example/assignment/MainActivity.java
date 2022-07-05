package com.example.assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText eduser,edpass;
    Button button;
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_USER = "user";
    private static final String KEY_PASS = "pass";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eduser = findViewById(R.id.ed_user);
        edpass = findViewById(R.id.ed_pass);
        button = findViewById(R.id.button);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);

        String user = sharedPreferences.getString(KEY_USER,null);
        if(user != null){
            Intent intent = new Intent(MainActivity.this,maps.class);
            startActivity(intent);
            finish();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (eduser.getText().toString().isEmpty() || edpass.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivity.this, "username and password should not be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        int a = 123;
                        int b = Integer.parseInt(eduser.getText().toString().trim());
                        int c = Integer.parseInt(edpass.getText().toString().trim());
                        if (a == b && a == c) {

                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString(KEY_USER,eduser.getText().toString());
                            editor.putString(KEY_PASS,edpass.getText().toString());
                            editor.apply();

                            Intent intent = new Intent(MainActivity.this,maps.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(MainActivity.this, "username and password should be 123", Toast.LENGTH_SHORT).show();
                        }//else
                    }//else
            }//onClick
        });//setOnClickListener

    }//oncreate

}//main