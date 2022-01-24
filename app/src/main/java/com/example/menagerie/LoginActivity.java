package com.example.menagerie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    EditText usrname, passw;
    Button mashok;
    TextView daftar;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://menagerie-8e906-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usrname = findViewById(R.id.loginuname);
        passw = findViewById(R.id.loginpass);
        mashok = findViewById(R.id.logingoh);
        daftar = findViewById(R.id.registerNow);

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calus = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(calus);
            }
        });

        mashok.setOnClickListener(view -> {
            String uname = usrname.getText().toString();
            String passm = passw.getText().toString();

            if(TextUtils.isEmpty(uname)){
                usrname.setError("Username can't be empty!");
                usrname.requestFocus();
            } else if (TextUtils.isEmpty(passm)){
                passw.setError("Password can't be empty!");
                passw.requestFocus();
            } else {
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.hasChild(uname)){
                            String getPass = snapshot.child(uname).child("password").getValue(String.class);
                            if (getPass.equals(passm)){
                                Toast.makeText(LoginActivity.this, "Successfully logged in!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, Dashboard.class));
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "Whoa, wrong password!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
}