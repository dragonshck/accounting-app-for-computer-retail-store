package com.example.menagerie;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.menagerie.jurnal.JurnalUmum;
import com.example.menagerie.jurnal.adeptJurnal;
import com.example.menagerie.jurnal.dataJurnal;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NeracaSaldo extends AppCompatActivity implements adeptNS.FDBXYZ {
    RecyclerView recyclerViewNS;
    adeptNS NSAdapter;
    ArrayList<dataNS> daftarNS;
    DatabaseReference refNS, refSaldoNS;
    TextView saldoDebit, saldoKredit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neraca_saldo);

        recyclerViewNS = findViewById(R.id.rViewNS);
        recyclerViewNS.setHasFixedSize(true);
        recyclerViewNS.setLayoutManager(new LinearLayoutManager(this));

        View view = getLayoutInflater().inflate(R.layout.item_total, null);
        saldoDebit = view.findViewById(R.id.nominalDebit1);
        saldoKredit = view.findViewById(R.id.nominalKredit1);

        FirebaseApp.initializeApp(this);
        refNS = FirebaseDatabase.getInstance().getReference("bagan_akun");
        refNS.child("data_akun").addValueEventListener(valueEventListener);

    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            daftarNS = new ArrayList<>();
            if (snapshot.exists()){
                for (DataSnapshot rayleigh : snapshot.getChildren()){
                    dataNS listNS = rayleigh.getValue(dataNS.class);
                    listNS.setKey(rayleigh.getKey());
                    daftarNS.add(listNS);

                    String koentji = rayleigh.getKey();

                    refSaldoNS = FirebaseDatabase.getInstance().getReference("trx_barang");
                    refSaldoNS.child("belibarang").child(koentji).orderByChild("pilihAkunDebit").equalTo("Peralatan").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.exists()) {
                                double total = 0;
                                for (DataSnapshot shanks : snapshot.getChildren()) {
                                    String nominalD = shanks.child("hargaPembelianBarang").getValue().toString();
                                    double xx = Double.parseDouble(nominalD);
                                    total += xx;

                                    saldoDebit.setText(String.valueOf(total));
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
                NSAdapter = new adeptNS(NeracaSaldo.this, daftarNS);
                recyclerViewNS.setAdapter(NSAdapter);
                NSAdapter.notifyDataSetChanged();
            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    public void onDataClick(@Nullable dataNS daftarNS, int position) {

    }
}