package com.example.th2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.th2.model.Baihat;
import com.example.th2.model.Casi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="TH.db";
    private static int DATABASE_VERSION =1;
    public SQLiteHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE casis("+"id_casi INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name TEXT,img TEXT)";
        db.execSQL(sql);
        sql = "CREATE TABLE baihats("+"id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "namebaihat TEXT,id_casi INTEGER,album TEXT, theloai TEXT, yeuthich INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }


    // Thêm mới một Ca sĩ vào bảng casis
    public long addCasi(String name, String img) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("img", img);
        long result = db.insert("casis", null, values);
        db.close();
        return result;
    }

    // Thêm mới một Bài hát vào bảng baihats
    public long addBaihat(String namebaihat, int id_casi, String album, String theloai, int yeuthich) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("namebaihat", namebaihat);
        values.put("id_casi", id_casi);
        values.put("album", album);
        values.put("theloai", theloai);
        values.put("yeuthich", yeuthich);
        long result = db.insert("baihats", null, values);
        db.close();
        return result;
    }
    // Lấy ra danh sách các Ca sĩ từ bảng casis
    public List<Casi> getAllCasis() {
        List<Casi> casiList = new ArrayList<>();
        String selectQuery = "SELECT * FROM casis";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Casi casi = new Casi();
                casi.setId_casi(cursor.getInt(0));
                casi.setName(cursor.getString(1));
                casi.setImg(cursor.getString(2));
                casiList.add(casi);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return casiList;
    }
    // Lấy thông tin của một Ca sĩ dựa trên id
    public Casi getCasiById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("casis", new String[]{"id_casi", "name", "img"},
                "id_casi=?", new String[]{String.valueOf(id)}, null, null, null, null);
        Casi casi = null;
        if (cursor != null && cursor.moveToFirst()) {
            casi = new Casi();
            casi.setId_casi(cursor.getInt(0));
            casi.setName(cursor.getString(1));
            casi.setImg(cursor.getString(2));
            cursor.close();
        }
        db.close();
        return casi;
    }

    // Lấy ra danh sách các Bài hát từ bảng baihats
    public List<Baihat> getAllBaihats() {
        List<Baihat> baihatList = new ArrayList<>();
        String selectQuery = "SELECT * FROM baihats";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Baihat baihat = new Baihat();
                baihat.setId_baihat(cursor.getInt(0));
                baihat.setNamebaihat(cursor.getString(1));

                // Tạo một đối tượng Casi từ id của casi và gán vào Baihat
                int casiId = cursor.getInt(2);
                Casi casi = getCasiById(casiId);
                baihat.setCasi(casi);

                baihat.setAlbum(cursor.getString(3));
                baihat.setTheloai(cursor.getString(4));
                baihat.setYeuthich(cursor.getInt(5));
                baihatList.add(baihat);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return baihatList;
    }
    // Cập nhật một Bài hát trong bảng baihats
    public int updateBaihat(Baihat baihat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("namebaihat", baihat.getNamebaihat());
        values.put("id_casi", baihat.getCasi().getId_casi());
        values.put("album", baihat.getAlbum());
        values.put("theloai", baihat.getTheloai());
        values.put("yeuthich", baihat.isYeuthich());
        int result = db.update("baihats", values, "id=?", new String[]{String.valueOf(baihat.getId_baihat())});
        db.close();
        return result;
    }

    // Xóa một Bài hát từ bảng baihats
    public void removeBaihat(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("baihats", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Baihat> getBaihatByCasi(int casiId) {
        List<Baihat> baihatList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        // Câu truy vấn để lấy danh sách các bài hát của một ca sĩ dựa vào id_casi
        String selectQuery = "SELECT * FROM baihats WHERE id_casi = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(casiId)});

        // Lặp qua các hàng kết quả và thêm vào danh sách bài hát
        if (cursor.moveToFirst()) {
            do {
                Baihat baihat = new Baihat();
                baihat.setId_baihat(cursor.getInt(0));
                baihat.setNamebaihat(cursor.getString(1));

                // Lấy thông tin của ca sĩ từ id_casi và gán vào bài hát
                int casiIdFromCursor = cursor.getInt(2);
                Casi casi = getCasiById(casiIdFromCursor);
                baihat.setCasi(casi);

                baihat.setAlbum(cursor.getString(3));
                baihat.setTheloai(cursor.getString(4));
                baihat.setYeuthich(cursor.getInt(5));

                // Thêm bài hát vào danh sách
                baihatList.add(baihat);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return baihatList;
    }

    public List<Baihat> getBaihatByAlbum(String albumName) {
        List<Baihat> baihatList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        // Câu truy vấn để lấy danh sách các bài hát của một album
        String selectQuery = "SELECT * FROM baihats WHERE album = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{albumName});

        // Lặp qua các hàng kết quả và thêm vào danh sách bài hát
        if (cursor.moveToFirst()) {
            do {
                Baihat baihat = new Baihat();
                baihat.setId_baihat(cursor.getInt(0));
                baihat.setNamebaihat(cursor.getString(1));

                // Lấy thông tin của ca sĩ từ id_casi và gán vào bài hát
                int casiIdFromCursor = cursor.getInt(2);
                Casi casi = getCasiById(casiIdFromCursor);
                baihat.setCasi(casi);

                baihat.setAlbum(cursor.getString(3));
                baihat.setTheloai(cursor.getString(4));
                baihat.setYeuthich(cursor.getInt(5));

                // Thêm bài hát vào danh sách
                baihatList.add(baihat);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return baihatList;
    }
    public List<Baihat> getBaihatByAlbumAndSortByTheloai(String albumName) {
        List<Baihat> baihatList = getBaihatByAlbum(albumName);

        // Sắp xếp danh sách bài hát theo thể loại
        Collections.sort(baihatList, new Comparator<Baihat>() {
            @Override
            public int compare(Baihat baihat1, Baihat baihat2) {
                return baihat1.getTheloai().compareTo(baihat2.getTheloai());
            }
        });

        return baihatList;
    }



}
