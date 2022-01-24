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

public class RegisterActivity extends AppCompatActivity {
    EditText fullnm, usernm, koentji;
    TextView loginkene;
    Button daftarsyek;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://menagerie-8e906-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernm = findViewById(R.id.signupuname);
        fullnm = findViewById(R.id.fullname);
        koentji = findViewById(R.id.signuppass);
        daftarsyek = findViewById(R.id.daftargoh);
        loginkene = findViewById(R.id.loginbtnNow);


        loginkene.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent calus = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(calus);
            }
        });

        daftarsyek.setOnClickListener(view ->{
            createUser();
        });
    }

    private void createUser() {
        String uname = usernm.getText().toString();
        String fname = fullnm.getText().toString();
        String kunci = koentji.getText().toString();

        if(TextUtils.isEmpty(uname)){
            usernm.setError("Username can't be empty man!");
            usernm.requestFocus();
        } else if(TextUtils.isEmpty(kunci)){
            koentji.setError("Password is important, make sure to fill it bro!");
            koentji.requestFocus();
        } else if (TextUtils.isEmpty(fname)){
            fullnm.setError("Fullname can't be empty, you do have a name right?");
            fullnm.requestFocus();
        } else {
            databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(uname)){
                        Toast.makeText(RegisterActivity.this, "Username already registered", Toast.LENGTH_SHORT).show();
                    } else {

                        databaseReference.child("users").child(uname).child("fullname").setValue(fname);
                        databaseReference.child("users").child(uname).child("password").setValue(kunci);

                        Toast.makeText(RegisterActivity.this, "Registered successfully!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }
    }
}