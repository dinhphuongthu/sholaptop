package com.listview.shoplaptop.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.listview.shoplaptop.R;
import com.listview.shoplaptop.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamTheoLoaiadapter extends RecyclerView.Adapter<SanPhamTheoLoaiadapter.ItemHolder> {
    ArrayList<SanPham> listsanpham;
    Context context;
    private SelectedProduct selectedProduct;

    public SanPhamTheoLoaiadapter(ArrayList<SanPham> listsanpham, SelectedProduct selectedProduct) {
        this.listsanpham = listsanpham;
        this.selectedProduct=selectedProduct;
    }

    @NonNull
    @Override
    public SanPhamTheoLoaiadapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanpham_lienquan,null);
        ItemHolder itemHolder=new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamTheoLoaiadapter.ItemHolder holder, int position) {
        SanPham sanPham=listsanpham.get(position);
        holder.txttensp.setText(sanPham.getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        holder.txtgiasp.setText("" + "giá: " + decimalFormat.format(sanPham.getPrice()) + " Đ");
        Picasso.with(context).load(sanPham.getImage()).resize(200,180).centerCrop().into(holder.imgsanpham);

    }
    public interface SelectedProduct{
        void selectedProduct(SanPham sanpham);
    }
    @Override
    public int getItemCount() {
        return listsanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imgsanpham;
        public TextView txttensp,txtgiasp;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgsanpham=(ImageView)itemView.findViewById(R.id.imgsanpham);
            txttensp=(TextView)itemView.findViewById(R.id.txttensp);
            txtgiasp=(TextView)itemView.findViewById(R.id.txtgiasp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedProduct.selectedProduct(listsanpham.get(getAdapterPosition()));
                }
            });
        }
    }

}
