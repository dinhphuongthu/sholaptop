package com.listview.shoplaptop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.listview.shoplaptop.R;
import com.listview.shoplaptop.model.LoaiSP;
import com.listview.shoplaptop.model.SanPham;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TheLoaiadapter extends RecyclerView.Adapter<TheLoaiadapter.ItemHolder> {
    ArrayList<LoaiSP> listloaisp;
    Context context;

    public TheLoaiadapter(ArrayList<LoaiSP> listloaisp, Context context) {
        this.listloaisp = listloaisp;
        this.context=context;
    }
    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theloai,null);
        ItemHolder itemHolder=new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        LoaiSP loai = listloaisp.get(position);
        Picasso.with(context).load(loai.getImagesp()).resize(110,80).centerCrop().into(holder.imgtheloai);
        Log.d("kiemtra","ok");
    }

    @Override
    public int getItemCount() {
        return listloaisp.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {
        public ImageView imgtheloai;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgtheloai = (ImageView) itemView.findViewById(R.id.imgtheloai);
        }
    }
}
