package com.example.menagerie.expenseZ.paymentz;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menagerie.R;
import com.example.menagerie.expenseZ.expandtrx.DataTransaksi;
import com.example.menagerie.expenseZ.expandtrx.TRXActivity;
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

public class mastertransaksi extends AppCompatActivity implements AdapterTransaksiBarang.firebaseListener {
    FloatingActionButton floatAddBarang;
    EditText jumlahBrg, catatanBrg, hargaSatuan;
    Spinner pilihBarang, pilihDebit, pilihKredit, pilihVendor;
    DatabaseReference dbRefTrxBarang, refPilihVendor, refPilihBarang, refPilihDebit, refPilihKredit;
    ArrayList<String> listVendor = new ArrayList<>();
    ArrayList<String> listBarang = new ArrayList<>();
    ArrayList<String> listDebit = new ArrayList<>();
    ArrayList<String> listKredit = new ArrayList<>();
    FirebaseDatabase dbTrxBarang;
    RecyclerView recyclerViewTrxBarang;
    Button BtnDate;
    AdapterTransaksiBarang adeptTrxBarang;
    ArrayList<datatransaksibarang> dataTrxBarang;
    DatePickerDialog datePickerz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mastertransaksi);

        recyclerViewTrxBarang = findViewById(R.id.recycleViewTransaksiBarang);
        recyclerViewTrxBarang.setHasFixedSize(true);
        recyclerViewTrxBarang.setLayoutManager(new LinearLayoutManager(this));

        FirebaseApp.initializeApp(this);
        dbTrxBarang = FirebaseDatabase.getInstance();
        dbRefTrxBarang = dbTrxBarang.getReference("trx_barang");
        dbRefTrxBarang.child("belibarang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataTrxBarang = new ArrayList<>();
                for (DataSnapshot SLTS : snapshot.getChildren()) {
                    datatransaksibarang saint14 = SLTS.getValue(datatransaksibarang.class);
                    saint14.setKey(SLTS.getKey());
                    dataTrxBarang.add(saint14);
                }
                adeptTrxBarang = new AdapterTransaksiBarang(mastertransaksi.this, dataTrxBarang);
                recyclerViewTrxBarang.setAdapter(adeptTrxBarang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(mastertransaksi.this, error.getDetails() + " " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        floatAddBarang = findViewById(R.id.tambahTransaksiItemBarang);
        floatAddBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogTambahDataTransaksiBarang();
            }
        });


    }

    private void dialogTambahDataTransaksiBarang() {
        AlertDialog.Builder lego = new AlertDialog.Builder(this);
        lego.setTitle("Tambah Data Transaksi Barang");
        View view = getLayoutInflater().inflate(R.layout.layout_pembelian_barang, null);

        BtnDate = view.findViewById(R.id.tanggal_transaksi);
        pilihVendor = view.findViewById(R.id.spinnerPilihVendor);
        pilihBarang = view.findViewById(R.id.spinnerPilihBarang);
        pilihDebit = view.findViewById(R.id.spinnerAccPengeluaran);
        pilihKredit = view.findViewById(R.id.spinnerMethPembayaran);
        jumlahBrg = view.findViewById(R.id.QuantityBarang);
        hargaSatuan = view.findViewById(R.id.HargaSatuanBarang);

        initializeDate();
        lego.setView(view);

        //Dropdown Memilih Barang
        refPilihBarang = dbTrxBarang.getReference("barang");
        refPilihBarang.child("data_barang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listBarang.clear();
                for (DataSnapshot tbark : snapshot.getChildren()) {
                    listBarang.add(tbark.child("namaBarang").getValue(String.class));
                }
                ArrayAdapter<String> adeptBarang = new ArrayAdapter<>(mastertransaksi.this, R.layout.spinner_akun, listBarang);
                pilihBarang.setAdapter(adeptBarang);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Dropdown Memilih Vendor
        refPilihVendor = dbTrxBarang.getReference("vendors");
        refPilihVendor.child("data_vendors").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listVendor.clear();
                for (DataSnapshot arpark : snapshot.getChildren()) {
                    listVendor.add(arpark.child("namaperusahaan").getValue(String.class));
                }
                ArrayAdapter<String> adeptVendor = new ArrayAdapter<>(mastertransaksi.this, R.layout.spinner_akun, listVendor);
                pilihVendor.setAdapter(adeptVendor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Dropdown Debit
        refPilihDebit = dbTrxBarang.getReference("bagan_akun");
        refPilihDebit.child("data_akun").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listDebit.clear();
                for (DataSnapshot sunnygo : snapshot.getChildren()) {
                    listDebit.add(sunnygo.child("kodeakun").getValue(String.class));
                }
                ArrayAdapter<String> adeptDebt = new ArrayAdapter<>(mastertransaksi.this, R.layout.spinner_akun, listDebit);
                pilihDebit.setAdapter(adeptDebt);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Dropdown Kredit
        refPilihKredit = dbTrxBarang.getReference("bagan_akun");
        refPilihKredit.child("data_akun").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listKredit.clear();
                for (DataSnapshot sunnygorun : snapshot.getChildren()) {
                    listKredit.add(sunnygorun.child("kodeakun").getValue(String.class));
                }
                ArrayAdapter<String> adeptCred = new ArrayAdapter<>(mastertransaksi.this, R.layout.spinner_akun, listKredit);
                pilihKredit.setAdapter(adeptCred);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lego.setPositiveButton("SIMPAN", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String tgltransaksibarang = BtnDate.getText().toString();
                String pilihbarang = pilihBarang.getSelectedItem().toString();
                String pilihvendor = pilihVendor.getSelectedItem().toString();
                String pilihdebit = pilihDebit.getSelectedItem().toString();
                String pilihkredit = pilihKredit.getSelectedItem().toString();
                String jumlahbarang = jumlahBrg.getText().toString();
                String hargabijian= hargaSatuan.getText().toString();

                if (!tgltransaksibarang.isEmpty() && !pilihbarang.isEmpty() && !pilihvendor.isEmpty() && !pilihdebit.isEmpty() && !pilihkredit.isEmpty() && !jumlahbarang.isEmpty() && !hargabijian.isEmpty()) {
                    pushDataTransaksi(new datatransaksibarang(tgltransaksibarang, pilihbarang, pilihvendor, pilihdebit, pilihkredit, jumlahbarang, hargabijian));
                } else {
                    Toast.makeText(mastertransaksi.this, "Data harus di isi!", Toast.LENGTH_LONG).show();
                }
            }
        });

        lego.setNegativeButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        Dialog dialog = lego.create();
        dialog.show();
    }

    private void pushDataTransaksi(datatransaksibarang dataTransaksi) {
        dbRefTrxBarang.child("belibarang").push().setValue(dataTransaksi).addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void mVoid) {
                Toast.makeText(mastertransaksi.this, "Data transaksi barang berhasil di simpan !", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initializeDate() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                BtnDate.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerz = new DatePickerDialog(this, dateSetListener, year, month, day);
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

    public void  openDatePicker (View view) {
        datePickerz.show();
    }

    @Override
    public void onDataClick(@Nullable datatransaksibarang datatransaksiBarang, int position) {
        AlertDialog.Builder lego = new AlertDialog.Builder(this);
        lego.setTitle("Pilih Aksi");

        lego.setPositiveButton("N/A", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        lego.setNegativeButton("N/A", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                hapusDataTransaksiBarang(datatransaksiBarang);
            }
        });

        lego.setNeutralButton("BATAL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });

        Dialog dialog = lego.create();
        dialog.show();

    }

    private void hapusDataTransaksiBarang(datatransaksibarang datatransaksiBarang) {
        if (dbRefTrxBarang != null) {
            dbRefTrxBarang.child("belibarang").child(datatransaksiBarang.getKey()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void mVoid) {
                    Toast.makeText(mastertransaksi.this, "Data berhasil di hapus !", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


}