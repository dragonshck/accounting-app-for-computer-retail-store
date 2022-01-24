package com.example.menagerie.expenseZ.databarang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.menagerie.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class masterbarang extends AppCompatActivity implements DataBarangAdapter.bmthListener{
    FloatingActionButton xFloatBtn;
    EditText namaBrg, descBrg;
    Spinner tipeBrg;
    RecyclerView dropRecycle;
    DataBarangAdapter barangAdapter;
    ArrayList<ModelDataBarang> daftarBarang;
    ArrayList<String> listAkun = new ArrayList<>();
    DatabaseReference dbRefBrg, dbRefSpinAkun;
    FirebaseDatabase dbBrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_masterbarang);

        dropRecycle = findViewById(R.id.recycleViewBarang);
        dropRecycle.setHasFixedSize(true);
        dropRecycle.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        dbBrg = FirebaseDatabase.getInstance();
        dbRefBrg = dbBrg.getReference("barang");
        dbRefBrg.child("data_barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarBarang = new ArrayList<>();
                for (DataSnapshot snapBrg : snapshot.getChildren()) {
                    ModelDataBarang brg = snapBrg.getValue(ModelDataBarang.class);
                    brg.setKey(snapBrg.getKey());
                    daftarBarang.add(brg);
                }

                barangAdapter = new DataBarangAdapter(masterbarang.this, daftarBarang);
                dropRecycle.setAdapter(barangAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(masterbarang.this, error.getDetails() + " " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        xFloatBtn = findViewById(R.id.tambahItemBarang);
        xFloatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTambahBarang();
            }
        });

    }

    private void dialogTambahBarang() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Barang");
        View view = getLayoutInflater().inflate(R.layout.layout_master_item, null);

        namaBrg = view.findViewById(R.id.nama_item);
        tipeBrg = view.findViewById(R.id.spinnerTipeAkun);
        descBrg = view.findViewById(R.id.descItem);
        builder.setView(view);

        // Dropdown Memilih Akun Pengeluaran untuk Pembelian Barang
        dbRefSpinAkun = dbBrg.getReference("bagan_akun");
        dbRefSpinAkun.child("data_akun").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAkun.clear();
                for (DataSnapshot snapAk : snapshot.getChildren()) {
                    listAkun.add(snapAk.child("kodeakun").getValue(String.class));
                }
                ArrayAdapter<String> adeptAkun = new ArrayAdapter<>(masterbarang.this, R.layout.spinner_akun, listAkun);
                tipeBrg.setAdapter(adeptAkun);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(masterbarang.this, error.getDetails() + " " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String nmBrg = namaBrg.getText().toString();
                String tpBrg = tipeBrg.getSelectedItem().toString();
                String dcBrg = descBrg.getText().toString();

                if (!nmBrg.isEmpty() && !dcBrg.isEmpty()) {
                    pushDataBarang(new ModelDataBarang(nmBrg, tpBrg, dcBrg));
                } else {
                    Toast.makeText(masterbarang.this, "Data harus diisi!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void pushDataBarang(ModelDataBarang modelDataBarang) {
        dbRefBrg.child("data_barang").push().setValue(modelDataBarang).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(masterbarang.this, "Data barang berhasil ditambahkan!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDataClick(@Nullable ModelDataBarang dataBarang, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Acton");

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogUpdateBarang(dataBarang);
            }
        });

        builder.setNegativeButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                hapusDataBarang(dataBarang);
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void hapusDataBarang(ModelDataBarang dataBarang) {
        if (dbRefBrg != null) {
            dbRefBrg.child("data_barang").child(dataBarang.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(masterbarang.this, "Data barang berhasil dihapus!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void dialogUpdateBarang(ModelDataBarang modelDataBarang) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data Barang");
        View view = getLayoutInflater().inflate(R.layout.layout_master_item, null);

        namaBrg = view.findViewById(R.id.nama_item);
        tipeBrg = view.findViewById(R.id.spinnerTipeAkun);
        descBrg = view.findViewById(R.id.descItem);

        // Dropdown Memilih Akun Pengeluaran untuk Pembelian Barang
        dbRefSpinAkun = dbBrg.getReference("bagan_akun");
        dbRefSpinAkun.child("data_akun").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listAkun.clear();
                for (DataSnapshot snapAk : snapshot.getChildren()) {
                    listAkun.add(snapAk.child("kodeakun").getValue(String.class));
                }
                ArrayAdapter<String> adeptAkun = new ArrayAdapter<>(masterbarang.this, R.layout.spinner_akun, listAkun);
                tipeBrg.setAdapter(adeptAkun);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(masterbarang.this, error.getDetails() + " " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        namaBrg.setText(modelDataBarang.getNamaBarang());
        descBrg.setText(modelDataBarang.getDeskripsiBarang());
        builder.setView(view);

        if (modelDataBarang != null) {
            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    modelDataBarang.setNamaBarang(namaBrg.getText().toString());
                    modelDataBarang.setTipeAkunBarang(tipeBrg.getSelectedItem().toString());
                    modelDataBarang.setDeskripsiBarang(descBrg.getText().toString());
                    builder.setView(view);
                }
            });
        }

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();

    }
}