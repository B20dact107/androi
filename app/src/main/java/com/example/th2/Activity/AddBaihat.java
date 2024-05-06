package com.example.th2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.th2.Adapter.CasiSpinnerAdapter;
import com.example.th2.MainActivity;
import com.example.th2.R;
import com.example.th2.SQLiteHelper;
import com.example.th2.model.Casi;

import java.util.List;

public class AddBaihat extends AppCompatActivity {
    private EditText namebaihat;
    private Spinner spalbum, spcasi, sptheloai;
    private CheckBox yeuthich;
    private Button btadd,btcancel;
    private CasiSpinnerAdapter casiSpinnerAdapter;
    SQLiteHelper db;
    List<Casi> listcasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baihat);
        namebaihat = findViewById(R.id.add_namebaihat);
        spalbum = findViewById(R.id.add_spinnerAlbum);
        spcasi = findViewById(R.id.add_spinnerCasi);
        sptheloai = findViewById(R.id.add_spinnerTheloai);
        yeuthich = findViewById(R.id.add_yeu_thich);
        btadd = findViewById(R.id.add_Add);
        btcancel = findViewById(R.id.add_Cancel);
        db = new SQLiteHelper(this);
        listcasi = db.getAllCasis();
        casiSpinnerAdapter = new CasiSpinnerAdapter(this,listcasi);
        spcasi.setAdapter(casiSpinnerAdapter);
        String[] arralbum = getResources().getStringArray(R.array.namealbum);
        String[] arrtheloai = getResources().getStringArray(R.array.nametheloai);
        spalbum.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner_casi,arralbum));
        sptheloai.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner_casi,arrtheloai));
        btadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenBaiHat = namebaihat.getText().toString().trim();
                Casi casi = (Casi) spcasi.getSelectedItem();
                String album = spalbum.getSelectedItem().toString();
                String theLoai = sptheloai.getSelectedItem().toString();
                int yeuThich = yeuthich.isChecked() ? 1 : 0;

                // Check if any of the required fields are empty or not selected
                if (tenBaiHat.isEmpty()) {
                    // Name of the song is empty
                    Toast.makeText(AddBaihat.this, "Vui lòng nhập tên bài hát", Toast.LENGTH_SHORT).show();
                } else if (casi == null) {
                    // No singer selected
                    Toast.makeText(AddBaihat.this, "Vui lòng chọn ca sĩ", Toast.LENGTH_SHORT).show();
                } else if (album.isEmpty()) {
                    // No album selected
                    Toast.makeText(AddBaihat.this, "Vui lòng chọn album", Toast.LENGTH_SHORT).show();
                } else if (theLoai.isEmpty()) {
                    // No genre selected
                    Toast.makeText(AddBaihat.this, "Vui lòng chọn thể loại", Toast.LENGTH_SHORT).show();
                } else {
                    // All required fields are filled, proceed with insertion
                    long result = db.addBaihat(tenBaiHat, casi.getId_casi(), album, theLoai, yeuThich);
                    if (result != -1) {
                        // Insertion successful
                        Toast.makeText(AddBaihat.this, "Thêm bài hát thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        // Insertion failed
                        Toast.makeText(AddBaihat.this, "Thêm bài hát thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddBaihat.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}