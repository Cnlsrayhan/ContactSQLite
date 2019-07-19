package com.rifauddin.contactdatabasevsgaproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddDataActivity extends AppCompatActivity {

    EditText edNama, edNo, edAlamat;
    DatabaseHelper db;

    int id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        edNama = findViewById(R.id.edNama);
        edNo = findViewById(R.id.edNo);
        edAlamat = findViewById(R.id.edAlamat);
        db = new DatabaseHelper(this);

        if(getIntent().hasExtra("id")){
            id = getIntent().getIntExtra("id", -1);
            edNama.setText(getIntent().getStringExtra("nama"));
            edNo.setText(getIntent().getStringExtra("no"));
            edAlamat.setText(getIntent().getStringExtra("alamat"));
        }
    }


    public void onClickSimpan(View view) {

        //Jika tidak ada id maka buat baru
        if(id == -1) {
            db.addDataKontak(
                    edNama.getText().toString(),
                    edAlamat.getText().toString(),
                    edNo.getText().toString()
            );
        }else{
            db.update(id,
                    edNama.getText().toString(),
                    edAlamat.getText().toString(),
                    edNo.getText().toString()
            );
        }

        finish();
    }
}
