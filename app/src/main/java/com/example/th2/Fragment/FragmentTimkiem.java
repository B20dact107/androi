package com.example.th2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.Adapter.BaihatRecycleViewAdapter;
import com.example.th2.R;
import com.example.th2.SQLiteHelper;
import com.example.th2.model.Baihat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentTimkiem extends Fragment {
    Spinner sp;
    TextView tv;
    SQLiteHelper db;
    RecyclerView recyclerView;
    BaihatRecycleViewAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmenttimkiemvathongke,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sp = view.findViewById(R.id.fragment_timkiem_spinner);
        tv = view.findViewById(R.id.fragment_timkiem_theloai);
        recyclerView = view.findViewById(R.id.fragment_timkiem_recycleview);
        adapter = new BaihatRecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        String[] arralbum = getResources().getStringArray(R.array.namealbum);
        String[] arrtheloai = getResources().getStringArray(R.array.nametheloai);
        sp.setAdapter(new ArrayAdapter<String>(getContext(),R.layout.item_spinner_casi,arralbum));
        String album = arralbum[1].toString();
        List<Baihat> listbaihattheoalbum = db.getBaihatByAlbum(album);
        adapter.setList(listbaihattheoalbum);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String album = sp.getItemAtPosition(position).toString();
                List<Baihat> listbaihattheoalbum = db.getBaihatByAlbum(album);
                List<Baihat> sortedBaihatList = db.getBaihatByAlbumAndSortByTheloai(album);
                adapter.setList(sortedBaihatList);
                adapter.notifyDataSetChanged();
                // Tạo một Map để lưu số lượng bài hát của mỗi thể loại
                Map<String, Integer> countByTheloai = new HashMap<>();
                for (Baihat baihat : listbaihattheoalbum) {
                    String theloai = baihat.getTheloai();
                    if (countByTheloai.containsKey(theloai)) {
                        countByTheloai.put(theloai, countByTheloai.get(theloai) + 1);
                    } else {
                        countByTheloai.put(theloai, 1);
                    }
                }

                // Chuyển Map thành List để sắp xếp
                List<Map.Entry<String, Integer>> sortedCountList = new ArrayList<>(countByTheloai.entrySet());

                // Sắp xếp List theo số lượng bài hát giảm dần
                Collections.sort(sortedCountList, new Comparator<Map.Entry<String, Integer>>() {
                    @Override
                    public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                        return entry2.getValue().compareTo(entry1.getValue());
                    }
                });

                // Hiển thị kết quả
                StringBuilder resultText = new StringBuilder();
                for (Map.Entry<String, Integer> entry : sortedCountList) {
                    resultText.append(entry.getKey()).append(": ").append(entry.getValue()).append(" bài hát\n");
                }
                tv.setText(resultText.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
