package com.example.menagerie.coreactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.menagerie.R;
import com.example.menagerie.coreactivity.VendorAdaptor;
import com.example.menagerie.coreactivity.InfoVendor;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Vendors extends AppCompatActivity implements VendorAdaptor.FirebaseDataListener {

    EditText perusahaan, emailvendors, alamat;
    RecyclerView xRecycle;
    ArrayList<InfoVendor> daftarPerusahaan;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    VendorAdaptor adaptor;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendors);

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                getWindow().getDecorView()
                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }

        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        xRecycle = findViewById(R.id.recycleView);
        xRecycle.setHasFixedSize(true);
        xRecycle.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("vendors");
        databaseReference.child("data_vendors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daftarPerusahaan = new ArrayList<>();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    InfoVendor vendor = snapshot1.getValue(InfoVendor.class);
                    vendor.setKey(snapshot1.getKey());
                    daftarPerusahaan.add(vendor);
                }
                adaptor = new VendorAdaptor(Vendors.this, daftarPerusahaan);
                xRecycle.setAdapter(adaptor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Vendors.this, error.getDetails() + " " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton = findViewById(R.id.tambahdata);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialogTambahVendor();
            }
        });

    }

    private void setWindowFlag(Vendors vendors, int flagTranslucentStatus, boolean b) {
        Window win = vendors.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (b) {
            winParams.flags |= flagTranslucentStatus;
        } else {
            winParams.flags &= ~flagTranslucentStatus;
        }
        win.setAttributes(winParams);
    }

    private void dialogTambahVendor() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Vendors");
        View view = getLayoutInflater().inflate(R.layout.layout_tambah_vendors, null);

        perusahaan = view.findViewById(R.id.nama_perusahaan);
        emailvendors = view.findViewById(R.id.email_vendor);
        alamat = view.findViewById(R.id.alamat);
        builder.setView(view);

        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String namaPerusahaan = perusahaan.getText().toString();
                String emailPerusahaan = emailvendors.getText().toString();
                String alamatPerusahaan = alamat.getText().toString();

                if(!namaPerusahaan.isEmpty() && !alamatPerusahaan.isEmpty() && !emailPerusahaan.isEmpty()) {
                    submitDataVendor(new InfoVendor(namaPerusahaan, emailPerusahaan, alamatPerusahaan));
                } else {
                    Toast.makeText(Vendors.this, "Data harus diisi!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        Dialog dialog = builder.create();
        dialog.show();
    }

    private void submitDataVendor(InfoVendor infoVendor) {
        databaseReference.child("data_vendors").push()
                .setValue(infoVendor).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(Vendors.this, "Data barang berhasil di simpan !", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDataClick(@Nullable InfoVendor vendor, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi");

        builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialogUpdateBarang(vendor);
            }
        });

        builder.setNegativeButton("HAPUS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                hapusDataBarang(vendor);
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

    private void hapusDataBarang(InfoVendor vendor) {
        if (databaseReference != null) {
            databaseReference.child("data_vendors").child(vendor.getKey())
                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void mVoid) {
                    Toast.makeText(Vendors.this, "Data berhasil di hapus !", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void dialogUpdateBarang(InfoVendor vendor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data Akun");
        View view = getLayoutInflater().inflate(R.layout.layout_tambah_vendors, null);

        perusahaan = view.findViewById(R.id.nama_perusahaan);
        emailvendors = view.findViewById(R.id.email_vendor);
        alamat = view.findViewById(R.id.alamat);

        perusahaan.setText(vendor.getNamaperusahaan());
        emailvendors.setText(vendor.getEmail());
        alamat.setText(vendor.getAlamat());
        builder.setView(view);

        if (vendor != null) {
            builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    vendor.setNamaperusahaan(perusahaan.getText().toString());
                    vendor.setEmail(emailvendors.getText().toString());
                    vendor.setAlamat(alamat.getText().toString());
                    updateDataBarang(vendor);
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

    private void updateDataBarang (InfoVendor vendor) {
        databaseReference.child("data_vendors").child(vendor.getKey())
                .setValue(vendor).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(Vendors.this, "Data berhasil di update !", Toast.LENGTH_LONG).show();
            }
        });
    }
}