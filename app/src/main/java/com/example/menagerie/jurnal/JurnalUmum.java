package com.example.menagerie.jurnal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menagerie.Dashboard;
import com.example.menagerie.MainActivity;
import com.example.menagerie.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JurnalUmum extends AppCompatActivity implements adeptJurnal.FDBListerner {
    RecyclerView recViewJurnal;
    adeptJurnal jurnalAdapter;
    ArrayList<dataJurnal> daftarJurnal;
    DatabaseReference refJurnal;
    TextView nomDebit, nomKredit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jurnal_umum);

        nomDebit = findViewById(R.id.nominalDeb);
        nomKredit = findViewById(R.id.nominalKred);

        recViewJurnal = findViewById(R.id.rViewJurnal);
        recViewJurnal.setHasFixedSize(true);
        recViewJurnal.setLayoutManager(new LinearLayoutManager(this));

        // Call Firebase Database disini

        FirebaseApp.initializeApp(this);
        refJurnal = FirebaseDatabase.getInstance().getReference("trx_barang");
        refJurnal.child("belibarang").addValueEventListener(valueEventListener);

    }

    // Retrieve Data dari Tabel Transaksi Barang

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            daftarJurnal = new ArrayList<>();
            if (snapshot.exists()) {
                double total = 0;
                for (DataSnapshot akagami : snapshot.getChildren()) {
                    dataJurnal soujurnertale = akagami.getValue(dataJurnal.class);
                    soujurnertale.setKey(akagami.getKey());
                    daftarJurnal.add(soujurnertale);

                    String nominalD = akagami.child("hargaPembelianBarang").getValue().toString();
                    double xx = Double.parseDouble(nominalD);
                    total += xx;


                    nomDebit.setText(String.valueOf(total));
                    nomKredit.setText(String.valueOf(total));
                }
                jurnalAdapter = new adeptJurnal(JurnalUmum.this, daftarJurnal);
                recViewJurnal.setAdapter(jurnalAdapter);
                jurnalAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };


    @Override
    public void onDataClick(@Nullable dataJurnal daftarJurnal, int position) {

    }
}