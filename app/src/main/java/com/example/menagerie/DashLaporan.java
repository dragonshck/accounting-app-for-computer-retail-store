package com.example.menagerie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.menagerie.jurnal.JurnalUmum;

public class DashLaporan extends AppCompatActivity {
    CardView btnJU, btnNS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_laporan);

        btnJU = findViewById(R.id.DataJU);
        btnNS = findViewById(R.id.DataNS);

        btnJU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sabaody = new Intent(DashLaporan.this, JurnalUmum.class);
                startActivity(sabaody);
            }
        });

        btnNS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent amazonlily = new Intent(DashLaporan.this, NeracaSaldo.class);
                startActivity(amazonlily);
            }
        });
    }
}