package com.example.th2.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.th2.model.Baihat;
import com.example.th2.model.Casi;

import java.util.Arrays;
import java.util.List;

public class UpdateDeleteBaihat extends AppCompatActivity {

    Baihat baihat;
    EditText up_de_namebaihat;
    Spinner up_de_spinnerCasi, up_de_spinnerAlbum, up_de_spinnerTheloai;
    CheckBox up_de_yeuthich;
    Button up_de_Update, up_de_Remove, up_de_Cancel;
    SQLiteHelper db;
    private CasiSpinnerAdapter casiSpinnerAdapter;
    List<Casi> listcasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete_baihat);

        db = new SQLiteHelper(this);

        // Khởi tạo các view
        up_de_namebaihat = findViewById(R.id.up_de_namebaihat);
        up_de_spinnerCasi = findViewById(R.id.up_de_spinnerCasi);
        up_de_spinnerAlbum = findViewById(R.id.up_de_spinnerAlbum);
        up_de_spinnerTheloai = findViewById(R.id.up_de_spinnerTheloai);
        up_de_yeuthich = findViewById(R.id.up_de_yeuthich);
        up_de_Update = findViewById(R.id.up_de_Update);
        up_de_Remove = findViewById(R.id.up_de_Remove);
        up_de_Cancel = findViewById(R.id.up_de_Cancel);
        String[] arralbum = getResources().getStringArray(R.array.namealbum);
        String[] arrtheloai = getResources().getStringArray(R.array.nametheloai);
        up_de_spinnerAlbum.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner_casi,arralbum));
        up_de_spinnerTheloai.setAdapter(new ArrayAdapter<String>(this,R.layout.item_spinner_casi,arrtheloai));
        listcasi = db.getAllCasis();
        casiSpinnerAdapter = new CasiSpinnerAdapter(this,listcasi);
        up_de_spinnerCasi.setAdapter(casiSpinnerAdapter);


        Intent intent = getIntent();
        if (intent != null) {
            baihat = (Baihat) intent.getSerializableExtra("baihat");
            if (baihat != null) {
                // Điền thông tin bài hát vào các trường dữ liệu để cập nhật
                up_de_namebaihat.setText(baihat.getNamebaihat());
                up_de_spinnerCasi.setSelection(baihat.getCasi().getId_casi()-1);
                up_de_spinnerAlbum.setSelection(Arrays.asList(arralbum).indexOf(baihat.getAlbum()));
                up_de_spinnerTheloai.setSelection(Arrays.asList(arrtheloai).indexOf(baihat.getTheloai()));
                if(baihat.isYeuthich()==1){
                    up_de_yeuthich.setChecked(true);
                }else{
                    up_de_yeuthich.setChecked(false);
                }
            }
        }

        // Xử lý sự kiện click nút cập nhật
        up_de_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Nhận dữ liệu mới từ các trường nhập liệu
                String newName = up_de_namebaihat.getText().toString();
                Casi selectedCasi = (Casi) up_de_spinnerCasi.getSelectedItem(); // Đối tượng Casi được chọn
                String newAlbum = up_de_spinnerAlbum.getSelectedItem().toString();
                String newTheloai = up_de_spinnerTheloai.getSelectedItem().toString();
                boolean newYeuthich = up_de_yeuthich.isChecked();

                // Kiểm tra xem dữ liệu có hợp lệ không (ví dụ: tên bài hát không được rỗng)
                if (newName.trim().isEmpty()) {
                    // Hiển thị thông báo lỗi
                    Toast.makeText(UpdateDeleteBaihat.this, "Tên bài hát không được để trống", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Tạo một đối tượng Baihat mới với dữ liệu đã cập nhật
                Baihat updatedBaihat = new Baihat();
                updatedBaihat.setId_baihat(baihat.getId_baihat()); // Đặt ID của bài hát cũ
                updatedBaihat.setNamebaihat(newName);
                updatedBaihat.setCasi(selectedCasi);
                updatedBaihat.setAlbum(newAlbum);
                updatedBaihat.setTheloai(newTheloai);
                updatedBaihat.setYeuthich(newYeuthich ? 1 : 0); // Chuyển đổi trạng thái yêu thích thành 0 hoặc 1

                // Gọi phương thức updateBaihat từ đối tượng SQLiteHelper để cập nhật bài hát trong cơ sở dữ liệu
                int result = db.updateBaihat(updatedBaihat);
                if (result > 0) {
                    // Cập nhật thành công
                    Toast.makeText(UpdateDeleteBaihat.this, "Cập nhật bài hát thành công", Toast.LENGTH_SHORT).show();
                    // Đóng activity và quay trở lại activity trước đó (hoặc màn hình chính)
                    finish();
                } else {
                    // Cập nhật không thành công
                    Toast.makeText(UpdateDeleteBaihat.this, "Cập nhật bài hát không thành công", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Xử lý sự kiện click nút xóa
        up_de_Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xác định ID của bài hát cần xóa
                int baihatId = baihat.getId_baihat();

                // Gọi phương thức removeBaihat từ đối tượng SQLiteHelper để xóa bài hát khỏi cơ sở dữ liệu
                db.removeBaihat(baihatId);

                // Hiển thị thông báo hoặc cập nhật giao diện người dùng tùy thuộc vào kết quả của việc xóa
                // Ví dụ: Hiển thị thông báo khi xóa thành công
                Toast.makeText(UpdateDeleteBaihat.this, "Đã xóa bài hát thành công", Toast.LENGTH_SHORT).show();

                // Đóng activity và quay trở lại activity trước đó (hoặc màn hình chính)
                finish();
            }
        });

        // Xử lý sự kiện click nút hủy
        up_de_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UpdateDeleteBaihat.this, MainActivity.class);
                startActivity(i);
            }
        });
    }
}
