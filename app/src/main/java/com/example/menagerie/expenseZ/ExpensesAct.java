package com.example.menagerie.expenseZ;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.menagerie.R;
import com.example.menagerie.expenseZ.databarang.masterbarang;
import com.example.menagerie.expenseZ.expandtrx.TRXActivity;
import com.example.menagerie.expenseZ.paymentz.mastertransaksi;

public class ExpensesAct extends AppCompatActivity {
    CardView itemBarang, expand, belibarang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        itemBarang = findViewById(R.id.DataItem);
//        expand = findViewById(R.id.pengeluaran);
        belibarang = findViewById(R.id.pembelianBarang);

        itemBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent dmt = new Intent(ExpensesAct.this, masterbarang.class);
                startActivity(dmt);
            }
        });

//        expand.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent katabasis = new Intent(ExpensesAct.this, TRXActivity.class);
//                startActivity(katabasis);
//            }
//        });

        belibarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notzai3 = new Intent(ExpensesAct.this, mastertransaksi.class);
                startActivity(notzai3);
            }
        });


    }
}