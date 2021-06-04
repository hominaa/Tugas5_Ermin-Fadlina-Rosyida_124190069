package com.example.tugas5.view;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tugas5.R;
import com.example.tugas5.entity.AppDatabase;
import com.example.tugas5.entity.DataBarang;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainContact.view{
    private AppDatabase appDatabase;
    private MainPresenter mainPresenter;
    private MainAdapter mainAdapter;

    private Button btnOK;
    private RecyclerView recyclerview;
    private EditText etNama, etHarga, etJumlah;

    private int id = 0;
    boolean edit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnOK = findViewById(R.id.btn_submit);
        etNama = findViewById(R.id.et_nama);
        etHarga = findViewById(R.id.et_harga);
        etJumlah = findViewById(R.id.et_jumlah);

        appDatabase = AppDatabase.inidb(getApplicationContext());
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mainPresenter = new MainPresenter(this);
        mainPresenter.readData(appDatabase);
    }

    @Override
    public void successAdd() {
        Toast.makeText(this, "Berhasil",Toast.LENGTH_SHORT).show();
        mainPresenter.readData(appDatabase);
    }

    @Override
    public void successDelete() {
        Toast.makeText(this, "Berhasil Menghapus Data",Toast.LENGTH_SHORT).show();
        mainPresenter.readData(appDatabase);
    }

    @Override
    public void resetForm() {
        etNama.setText("");
        etHarga.setText("");
        etJumlah.setText("");
        btnOK.setText("Submit");
    }

    @Override
    public void getData(List<DataBarang> list) {
        mainAdapter = new MainAdapter(this,list,this);
        recyclerview.setAdapter(mainAdapter);
    }

    @Override
    public void editData(DataBarang item) {
        etNama.setText(item.getNama());
        etHarga.setText(item.getHarga());
        etJumlah.setText(item.getJumlah());
        id = item.getId();
        edit = true;
        btnOK.setText("Edit Data");
    }

    @Override
    public void deleteData(DataBarang item) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        }else {
            builder = new AlertDialog.Builder(this);
        }
        builder.setTitle("Menghapus Data").setMessage("Apakah Anda yakin ingin menghapus data ini?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetForm();
                        mainPresenter.deleteData(item, appDatabase);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_dialer)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (view==btnOK){
            if(etNama.getText().toString().equals("")||etHarga.getText().toString().equals("")||etJumlah.getText().toString().equals("")){
                Toast.makeText(this, "Harap Isi Semua Data", Toast.LENGTH_SHORT).show();
            } else{
                if (!edit){
                    mainPresenter.insertData(etNama.getText().toString(),etHarga.getText().toString(),
                            Integer.parseInt(etJumlah.getText().toString()),appDatabase);
                } else {
                    mainPresenter.editData(etNama.getText().toString(),etHarga.getText().toString(),
                            Integer.parseInt(etJumlah.getText().toString()),id,appDatabase);
                            edit = false;
                }
                resetForm();
            }
        }
    }
}
