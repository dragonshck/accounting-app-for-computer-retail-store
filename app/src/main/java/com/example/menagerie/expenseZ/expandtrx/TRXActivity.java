package com.example.menagerie.expenseZ.expandtrx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
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
import java.util.Calendar;

public class TRXActivity extends AppCompatActivity implements TRXAdapter.dbListener {
    FloatingActionButton floatfly;
    EditText nmpengeluaran, catatantrx;
    Spinner pendor, accpengeluaran, metodepembayaran;
    ArrayList<String> arrayVendor = new ArrayList<>();
    ArrayList<String> arrayAkunOut = new ArrayList<>();
    ArrayList<String> arrayAkunMethod = new ArrayList<>();
    RecyclerView xReccc;
    TRXAdapter trxAdept;
    ArrayList<DataTransaksi> modeldataTransaksi;
    DatabaseReference refBark, refArabasta, refDreamingCity, refEuropa;
    FirebaseDatabase dbBark;
    DatePickerDialog datePickerDialog;
    Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trxactivity);

        xReccc = findViewById(R.id.recycleViewzTRX);
        xReccc.setHasFixedSize(true);
        xReccc.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        dbBark = FirebaseDatabase.getInstance();
        refBark = dbBark.getReference("trx_barang");
        refBark.child("belibarang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                modeldataTransaksi = new ArrayList<>();
                for (DataSnapshot ohsnap : snapshot.getChildren()) {
                    DataTransaksi trxBarang = ohsnap.getValue(DataTransaksi.class);
                    trxBarang.setKey(ohsnap.getKey());
                    modeldataTransaksi.add(trxBarang);
                }
                trxAdept = new TRXAdapter(TRXActivity.this, modeldataTransaksi);
                xReccc.setAdapter(trxAdept);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(TRXActivity.this, error.getDetails() + " " + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        floatfly = findViewById(R.id.tambahtransaksi);
        floatfly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTambahTRX();
            }
        });
    }

    private void dialogTambahTRX() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tambah Data Transaksi");
        View view = getLayoutInflater().inflate(R.layout.layout_master_transaksi, null);

        nmpengeluaran = view.findViewById(R.id.namaTransaksi);
        catatantrx = view.findViewById(R.id.hargaTransaksi);
        pendor = view.findViewById(R.id.spinnerVendor);
        accpengeluaran = view.findViewById(R.id.spinnerAccPengeluaran);
        metodepembayaran = view.findViewById(R.id.spinnerMethPembayaran);
        dateButton = view.findViewById(R.id.tanggal_transaksi);
        initDatePicker();
        builder.setView(view);

        // Spinner Vendor

        refArabasta = dbBark.getReference("vendors");
        refArabasta.child("data_vendors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayVendor.clear();
                for (DataSnapshot yoink : snapshot.getChildren()) {
                    arrayVendor.add(yoink.child("namaperusahaan").getValue(String.class));
                }
                ArrayAdapter<String> arrayAdept = new ArrayAdapter<>(TRXActivity.this, R.layout.spinner_akun, arrayVendor);
                pendor.setAdapter(arrayAdept);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Spinner Akun Pengeluaran

        refDreamingCity = dbBark.getReference("bagan_akun");
        refDreamingCity.child("data_akun").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayAkunOut.clear();
                for (DataSnapshot yoink1 : snapshot.getChildren()) {
                    arrayAkunOut.add(yoink1.child("kodeakun").getValue(String.class));
                }
                ArrayAdapter<String> arrayAdept1 = new ArrayAdapter<>(TRXActivity.this, R.layout.spinner_akun, arrayAkunOut);
                accpengeluaran.setAdapter(arrayAdept1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Spinner Akun Pembayaran

        refEuropa = dbBark.getReference("bagan_akun");
        refEuropa.child("data_akun").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayAkunMethod.clear();
                for (DataSnapshot yoink4 : snapshot.getChildren()) {
                    arrayAkunMethod.add(yoink4.child("kodeakun").getValue(String.class));
                }
                ArrayAdapter<String> arrayAdept4 = new ArrayAdapter<>(TRXActivity.this, R.layout.spinner_akun, arrayAkunMethod);
                metodepembayaran.setAdapter(arrayAdept4);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        builder.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String tgltransaksi = dateButton.getText().toString();
                String namaPengluar = nmpengeluaran.getText().toString();
                String noteTrx = catatantrx.getText().toString();
                String pendors = pendor.getSelectedItem().toString();
                String akpengeluaran = accpengeluaran.getSelectedItem().toString();
                String akpembayaran = metodepembayaran.getSelectedItem().toString();

                if (!tgltransaksi.isEmpty() && !namaPengluar.isEmpty() && !noteTrx.isEmpty()) {
                    submitDataTransaksi(new DataTransaksi(tgltransaksi, namaPengluar, noteTrx, pendors, akpengeluaran, akpembayaran));
                } else {
                    Toast.makeText(TRXActivity.this, "Data harus di isi!", Toast.LENGTH_LONG).show();
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

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    private void submitDataTransaksi(DataTransaksi dataTRX) {
        refBark.child("belibarang").push().setValue(dataTRX).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(TRXActivity.this, "Data barang berhasil di simpan !", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onDataClick(@Nullable DataTransaksi dataTransaksi, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih Aksi");

        builder.setPositiveButton("N/A", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialogUpdateTransaksi(dataTransaksi);
            }
        });

        builder.setNegativeButton("N/A", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
               hapusDataTransaksi(dataTransaksi);
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

    private void hapusDataTransaksi(DataTransaksi dataTransaksi) {
        if (refBark != null) {
            refBark.child("belibarang").child(dataTransaksi.getKey())
                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void mVoid) {
                    Toast.makeText(TRXActivity.this, "Data berhasil di hapus !", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void dialogUpdateTransaksi(DataTransaksi dataTransaksi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Data ModelBarang");
        View view = getLayoutInflater().inflate(R.layout.layout_master_transaksi, null);
    }
}