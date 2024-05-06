package com.example.th2.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.Activity.AddBaihat;
import com.example.th2.Activity.UpdateDeleteBaihat;
import com.example.th2.Adapter.BaihatRecycleViewAdapter;
import com.example.th2.R;
import com.example.th2.SQLiteHelper;
import com.example.th2.model.Baihat;

import java.util.List;

public class FragmentDanhsach extends Fragment implements BaihatRecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private BaihatRecycleViewAdapter adapter;
    private SQLiteHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdanhsach,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.fragment_danhsach_recycleview);
        adapter = new BaihatRecycleViewAdapter();
        db = new SQLiteHelper(getContext());
        List<Baihat> listbaihat = db.getAllBaihats();
        adapter.setList(listbaihat);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    @Override
    public void onItemClick(View view, int position) {
        Baihat baihat = adapter.getBaihat(position);
        Intent intent = new Intent(getActivity(), UpdateDeleteBaihat.class);
        intent.putExtra("baihat",baihat);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        List<Baihat> listbaihat = db.getAllBaihats();
        adapter.setList(listbaihat);
    }
}
