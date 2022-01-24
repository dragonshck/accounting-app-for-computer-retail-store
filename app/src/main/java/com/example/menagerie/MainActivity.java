package com.example.menagerie;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button masok, daftarsek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        masok = findViewById(R.id.btnlogin);
        daftarsek = findViewById(R.id.btnregister);

        masok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calus = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(calus);
            }
        });

        daftarsek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent beloved = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(beloved);
            }
        });
    }
}