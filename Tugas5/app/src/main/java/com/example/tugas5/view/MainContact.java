package com.example.tugas5.view;

import android.view.View;

import com.example.tugas5.entity.AppDatabase;
import com.example.tugas5.entity.DataBarang;

import java.util.List;

public interface MainContact {
    interface view extends View.OnClickListener{
        void successAdd();
        void successDelete();
        void resetForm();
        void getData(List<DataBarang> list);
        void editData(DataBarang item);
        void deleteData(DataBarang item);
    }

    interface presenter{
        void insertData(String nama, String harga, int jumlah, AppDatabase database);
        void readData(AppDatabase database);
        void editData(String nama, String harga, int jumlah, int id, AppDatabase database);
        void deleteData(DataBarang dataBarang, AppDatabase database);
    }
}
