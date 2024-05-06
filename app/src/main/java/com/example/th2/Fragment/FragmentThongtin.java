package com.example.th2.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.th2.R;
import com.example.th2.SQLiteHelper;
import com.example.th2.model.Baihat;
import com.example.th2.model.Casi;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FragmentThongtin extends Fragment {
    ImageView imageView;
    TextView name,listbaihat;
    SQLiteHelper db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentthongtin,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.fragmentthongtin_img);
        name = view.findViewById(R.id.fragmentthongtin_namecasi);
        listbaihat = view.findViewById(R.id.fragmentthongtin_listbaihat);
        db = new SQLiteHelper(getContext());
        Casi casi = db.getCasiById(1);
        List<Baihat> dsbhcuacasi = db.getBaihatByCasi(1);
        String dsbh="";
        for(Baihat i : dsbhcuacasi){
            dsbh +=i.getNamebaihat()+", ";
        }
        name.setText(casi.getName());
        listbaihat.setText(dsbh);
        Picasso.get().load(casi.getImg()).into(imageView);
    }
}
