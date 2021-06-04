package com.example.tugas5.view;

import android.os.AsyncTask;
import android.util.Log;

import com.example.tugas5.entity.AppDatabase;
import com.example.tugas5.entity.DataBarang;

import java.util.List;

public class MainPresenter implements MainContact.presenter{
    private MainContact.view view;

    public MainPresenter(MainContact.view view) {
        this.view = view;
    }

    class InsertData extends AsyncTask<Void,Void,Long>{
        private AppDatabase appDatabase;
        private DataBarang dataBarang;

        InsertData(AppDatabase appDatabase, DataBarang dataBarang) {
            this.appDatabase = appDatabase;
            this.dataBarang = dataBarang;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            return appDatabase.dao().insertData(dataBarang);
        }

        protected void onPostExecute(Long along){
            super.onPostExecute(along);
            view.successAdd();
        }
    }
    @Override
    public void insertData(String nama, String harga, int jumlah, AppDatabase database) {
        final DataBarang dataBarang =  new DataBarang();
        dataBarang.setNama(nama);
        dataBarang.setHarga(harga);
        dataBarang.setJumlah(jumlah);
        new InsertData(database, dataBarang).execute();
    }

    @Override
    public void readData(AppDatabase database) {
        List<DataBarang> list;
        list = database.dao().getdata();
        view.getData(list);
    }

    class EditData extends AsyncTask<Void,Void,Integer>{
        private AppDatabase appDatabase;
        private DataBarang dataBarang;

        public EditData(AppDatabase appDatabase, DataBarang dataBarang) {
            this.appDatabase = appDatabase;
            this.dataBarang = dataBarang;
        }


        @Override
        protected Integer doInBackground(Void... voids) {
            return appDatabase.dao().updateData(dataBarang);
        }

        protected void onPostExecute(Integer integer){
            super.onPostExecute(integer);
            Log.d("integer db", "onPostExecute"+integer);
            view.successAdd();
        }
    }

    @Override
    public void editData(String nama, String harga, int jumlah, int id, AppDatabase database) {
        final DataBarang dataBarang =  new DataBarang();
        dataBarang.setNama(nama);
        dataBarang.setHarga(harga);
        dataBarang.setJumlah(jumlah);
        dataBarang.setId(id);
        new EditData(database, dataBarang).execute();
    }


    class DeleteData extends AsyncTask<Void,Void,Long>{
        private AppDatabase appDatabase;
        private DataBarang dataBarang;

        public DeleteData(AppDatabase appDatabase, DataBarang dataBarang) {
            this.appDatabase = appDatabase;
            this.dataBarang = dataBarang;
        }

        @Override
        protected Long doInBackground(Void... voids) {
            appDatabase.dao().deleteData(dataBarang);
            return null;
        }

        protected void onPostExecute(Long along){
            super.onPostExecute(along);
            view.successDelete();
        }
    }

    @Override
    public void deleteData(DataBarang dataBarang, AppDatabase database) {
        new DeleteData(database, dataBarang).execute();
    }
}
