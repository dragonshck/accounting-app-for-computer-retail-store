package com.example.menagerie.akun;

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
import android.widget.EditText;
import android.widget.Toast;

import com.example.menagerie.R;
import com.example.menagerie.coreactivity.Vendors;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MasterAkun extends AppCompatActivity implements AccAdapt.dbFireListener {
    EditText namaakun, kodeakun, deskripsiakun;
    RecyclerView RView;
    DatabaseReference dbRef;
    FirebaseDatabase dbFire;
    ArrayList<ItemAkun> itemAkuns;
    AccAdapt adapterz;
    FloatingActionButton floatz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_master_akun);

        RView = findViewById(R.id.recycleViewz);
        RView.setHasFixedSize(true);
        RView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        dbFire = FirebaseDatabase.getInstance();
        dbRef = dbFire.getReference("bagan_akun");
        dbRef.child("data_akun").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemAkuns = new ArrayList<>();
                for (DataSnapshot tsmSnapshot : snapshot.getChildren()) {
                    ItemAkun akun = tsmSnapshot.getValue(ItemAkun.class);
                    akun.setKey(tsmSnapshot.getKey());
                    itemAkuns.add(akun);
                }
                adapterz = new AccAdapt(MasterAkun.this, itemAkuns);
                RView.setAdapter(adapterz);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MasterAkun.this, error.getDetails() + "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        floatz = findViewById(R.id.tambahakun);
        floatz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTambahAkun();
            }
        });

    }

    private void dialogTambahAkun() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Akun");
        View view = getLayoutInflater().inflate(R.layout.layout_tambah_akun, null);

        namaakun = view.findViewById(R.id.nama_akun);
        kodeakun = view.findViewById(R.id.kode_akun);
        deskripsiakun = view.findViewById(R.id.deskripsiakun);
        builder.setView(view);

        builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                String namaAkun = namaakun.getText().toString();
                String kodeAkun = kodeakun.getText().toString();
                String descAkun = deskripsiakun.getText().toString();

                if (!namaAkun.isEmpty() && !kodeAkun.isEmpty() && !descAkun.isEmpty()) {
                    submitDataAkun(new ItemAkun(namaAkun, kodeAkun, descAkun));
                } else {
                    Toast.makeText(MasterAkun.this, "Data harus di isi!", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void submitDataAkun(ItemAkun itemAkun) {
        dbRef.child("data_akun").push().setValue(itemAkun).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(MasterAkun.this, "Data akun berhasil di simpan !", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDataClick(@Nullable ItemAkun akun, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi");

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialogUpdateBarang(akun);
            }
        });

        builder.setNegativeButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                hapusDataBarang(akun);
            }
        });

        builder.setNeutralButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void hapusDataBarang(ItemAkun akun) {
        if (dbRef != null) {
            dbRef.child("data_akun").child(akun.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void mVoid) {
                    Toast.makeText(MasterAkun.this, "Data berhasil di hapus !", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void dialogUpdateBarang(ItemAkun akun) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data Akun");
        View view = getLayoutInflater().inflate(R.layout.layout_tambah_akun, null);

        namaakun = view.findViewById(R.id.nama_akun);
        kodeakun = view.findViewById(R.id.kode_akun);
        deskripsiakun = view.findViewById(R.id.deskripsiakun);

        namaakun.setText(akun.getNamaakun());
        kodeakun.setText(akun.getKodeakun());
        deskripsiakun.setText(akun.getDeskripsi());
        builder.setView(view);

        if (akun != null) {
            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {

                    akun.setNamaakun(namaakun.getText().toString());
                    akun.setKodeakun(kodeakun.getText().toString());
                    akun.setDeskripsi(deskripsiakun.getText().toString());
                    updateDataAkun(akun);
                }
            });
        }

        builder.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        Dialog dialog = builder.create();
        dialog.show();
    }

    private void updateDataAkun(ItemAkun akun) {
        dbRef.child("data_akun").child(akun.getKey())
                .setValue(akun).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(MasterAkun.this, "Data berhasil di update !", Toast.LENGTH_LONG).show();
            }
        });
    }
}