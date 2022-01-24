package com.example.menagerie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.menagerie.akun.MasterAkun;
import com.example.menagerie.coreactivity.Vendors;
import com.example.menagerie.expenseZ.ExpensesAct;
import com.example.menagerie.jurnal.JurnalUmum;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Dashboard extends AppCompatActivity {
    CardView expenses, reports, account, vendors;
    Button btnMetu;
    DatabaseReference db = FirebaseDatabase.getInstance().getReferenceFromUrl("https://menagerie-8e906-default-rtdb.firebaseio.com/");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        expenses = findViewById(R.id.expenses);
        reports = findViewById(R.id.reportz);
        account = findViewById(R.id.account);
        vendors = findViewById(R.id.vendorz);
        btnMetu = findViewById(R.id.button);

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent katabasis = new Intent(Dashboard.this, MasterAkun.class);
                startActivity(katabasis);
            }
        });

        vendors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent siviks = new Intent(Dashboard.this, Vendors.class);
                startActivity(siviks);
            }
        });

        expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent anarchy = new Intent(Dashboard.this, ExpensesAct.class);
                startActivity(anarchy);
            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pacifista = new Intent(Dashboard.this, DashLaporan.class);
                startActivity(pacifista);
            }
        });

        btnMetu.setOnClickListener(view -> {
            logout();
        });
    }

    private void logout() {
        startActivity(new Intent(this, LoginActivity.class));
        preferences.clearData(this);
        finish();
    }
}