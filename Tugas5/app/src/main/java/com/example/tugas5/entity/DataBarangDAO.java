package com.example.tugas5.entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DataBarangDAO {
    @Insert
    Long insertData(DataBarang dataBarang);

    @Query("Select * from barang_db")
    List<DataBarang> getdata();

    @Update
    int updateData(DataBarang item);

    @Delete
    void deleteData(DataBarang item);
}
