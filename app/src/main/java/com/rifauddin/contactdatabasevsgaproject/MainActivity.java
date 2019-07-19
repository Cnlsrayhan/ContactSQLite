package com.rifauddin.contactdatabasevsgaproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper db;
    ListView listView;
    EditText edFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listView);
        edFilter = findViewById(R.id.edFilter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> mapObject =
                    (Map<String, Object>)
                        parent.getAdapter().getItem(position);
                Intent i = new Intent(MainActivity.this, AddDataActivity.class);
                i.putExtra("id", (int) mapObject.get("id"));
                i.putExtra("nama", (String) mapObject.get("nama"));
                i.putExtra("alamat", (String) mapObject.get("alamat"));
                i.putExtra("no", (String) mapObject.get("no"));
                startActivity(i);
            }
        });



       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               Map<String, Object> mapObject =
                       (Map<String, Object>)
                               parent.getAdapter().getItem(position);

               int idData =  (int) mapObject.get("id");
               konfirmasiHapus(idData);
               return true;


           }
       });

    }


    void konfirmasiHapus(final int idData){
        new AlertDialog.Builder(this)
                .setTitle("Hapus Data ini?")
                .setMessage("Apakah Anda yakin ingin menghapus data ini?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton("Hapus",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                db.delete(idData);
                                loadData("");
                            }
                        })
                .setNegativeButton("Jangan", null)
                .show();


    }


    //https://github.com/rifauddint/SQLiteAppVSGA
    //https://github.com/rifauddint/SQLiteContactAppsVSGA

    @Override
    protected void onResume() {
        super.onResume();
        loadData("");
    }

    void loadData(String filter) {
        ArrayList<Map<String, Object>> arrData = db.getAllKontak(filter);
        if(arrData.size()>0){
            SimpleAdapter simpleAdapter =
                    new SimpleAdapter(this, arrData ,
                            android.R.layout.simple_list_item_2,
                            new String[]{"nama", "alamat"},
                            new int[]{ android.R.id.text1,
                                    android.R.id.text2}
                            );

            listView.setAdapter(simpleAdapter);
            simpleAdapter.notifyDataSetChanged();
        }

    }


    public void onClickTambah(View view) {
        Intent i  = new Intent(this, AddDataActivity.class);
        startActivity(i);
    }

    public void onClickFilter(View view) {
        Log.i("DLOG2", edFilter.getText().toString());
        if(edFilter.getText().toString().isEmpty()) {

            Log.i("DLOG", "kosong");
            loadData("");
        }else{
            loadData(edFilter.getText().toString());
        }

    }
}
