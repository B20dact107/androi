package com.example.th2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.th2.R;
import com.example.th2.model.Baihat;

import java.util.ArrayList;
import java.util.List;

public class BaihatRecycleViewAdapter extends RecyclerView.Adapter<BaihatRecycleViewAdapter.BaihatViewHolder>{

    private ItemListener itemListener;
    List<Baihat> list;

    public BaihatRecycleViewAdapter() {
        list = new ArrayList<>();
    }

    public void setItemListener(ItemListener itemListener) {
        this.itemListener = itemListener;
    }
    public Baihat getBaihat(int position){
        return list.get(position);
    }

    public void setList(List<Baihat> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BaihatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itembaihat,parent,false);
        return new BaihatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaihatViewHolder holder, int position) {
        Baihat baihat = list.get(position);
        holder.namebaihat.setText(baihat.getNamebaihat());
        holder.namecasi.setText(baihat.getCasi().getName());
        holder.namealbum.setText(baihat.getAlbum());
        holder.nametheloai.setText(baihat.getTheloai());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class BaihatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView namebaihat, namecasi,namealbum,nametheloai;

        public BaihatViewHolder(@NonNull View itemView) {
            super(itemView);
            namebaihat = itemView.findViewById(R.id.item_baihat_namebaihat);
            namecasi = itemView.findViewById(R.id.item_baihat_namecasi);
            namealbum = itemView.findViewById(R.id.item_baihat_album);
            nametheloai = itemView.findViewById(R.id.item_baihat_theloai);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemListener!=null){
                itemListener.onItemClick(v,getAdapterPosition());
            }
        }
    }
    public interface ItemListener{
        void onItemClick(View view, int position);
    }
}
