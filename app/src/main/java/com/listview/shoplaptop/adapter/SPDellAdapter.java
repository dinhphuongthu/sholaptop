package com.listview.shoplaptop.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.listview.shoplaptop.R;
import com.listview.shoplaptop.model.SanPham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SPDellAdapter extends BaseAdapter {
    ArrayList<SanPham> listsp;
    Context context;

    public SPDellAdapter(ArrayList<SanPham> listsp, Context context) {
        this.listsp = listsp;
        this.context = context;
    }


    @Override
    public int getCount() {
        return listsp.size();
    }

    @Override
    public Object getItem(int position) {
        return listsp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        ImageView imgSanPham;
        TextView txttensp,txtgiasp,txtmotasp;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.item_sanpham,null);
            viewHolder.txttensp=(TextView)convertView.findViewById(R.id.txttensp);
            viewHolder.txtgiasp=(TextView)convertView.findViewById(R.id.txtgiasp);
            viewHolder.imgSanPham=(ImageView)convertView.findViewById(R.id.imgsanpham);
            viewHolder.txtmotasp=(TextView)convertView.findViewById(R.id.txtmotasp);
            convertView.setTag(viewHolder);

        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        // binding data to viewProduct
        SanPham sanPham = listsp.get(position);
        viewHolder.txttensp.setText(sanPham.getName());

        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        viewHolder.txtgiasp.setText("Giá:"+""+decimalFormat.format(sanPham.getPrice())+"Đ");

        viewHolder.txtmotasp.setMaxLines(2);
        viewHolder.txtmotasp.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotasp.setText(sanPham.getIntro());
        Picasso.with(context).load(sanPham.getImage()).resize(200,150).centerCrop().into(viewHolder.imgSanPham);
        return convertView;
    }
}
