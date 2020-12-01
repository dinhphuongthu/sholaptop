package com.listview.shoplaptop.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.listview.shoplaptop.MainActivity;
import com.listview.shoplaptop.R;
import com.listview.shoplaptop.adapter.GioHangAdapter;
import com.listview.shoplaptop.util.CheckConnection;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {

    static TextView txttongtien;
    Toolbar toolbargiohang;
    ListView lvgiohang;
    TextView txtthongbao;
    Button btnthanhtoan,btntieptucmua;
    GioHangAdapter listadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        init();
        checkdata();
        ActionBar();

        EventUntil();
        CatchOnItemListView();
        EventButton();
    }
    private void ActionBar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init() {
        toolbargiohang=(Toolbar)findViewById(R.id.toolbargiohang);
        txtthongbao=(TextView)findViewById(R.id.tvthongbao);
        lvgiohang=(ListView)findViewById(R.id.listviewgiohang);
        txttongtien=(TextView)findViewById(R.id.tvtongtien);
        btnthanhtoan=(Button)findViewById(R.id.btnthanhtoangiohang);
        btntieptucmua=(Button)findViewById(R.id.btntieptucmuahang);
        listadapter=new GioHangAdapter(getApplicationContext(),MainActivity.listGioHang);
        lvgiohang.setAdapter(listadapter);
    }

    public static void EventUntil() {
        long tongtien=0;
        for(int i = 0; i< MainActivity.listGioHang.size(); i++){
            tongtien+=MainActivity.listGioHang.get(i).getGiasp();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        txttongtien.setText(""+decimalFormat.format(tongtien)+"Đ");
    }
    private void checkdata() {
        if(MainActivity.listGioHang.size()<=0)
        {   listadapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.VISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE);
        }else{
            listadapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE);
        }
    }
    private void CatchOnItemListView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm không");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(MainActivity.listGioHang.size()<=0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else{
                            MainActivity.listGioHang.remove(position);
                            listadapter.notifyDataSetChanged();
                            EventUntil();
                            if(MainActivity.listGioHang.size()<=0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            }else{
                                txtthongbao.setVisibility(View.INVISIBLE);
                                listadapter.notifyDataSetChanged();
                                EventUntil();
                            }
                        }

                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listadapter.notifyDataSetChanged();
                        EventUntil();

                    }
                });
                builder.show();
                return true;
            }
        });
    }
    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);

            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MainActivity.listGioHang.size()>0)
                {
                    Intent intent = new Intent(getApplicationContext(),ThongTinKhachHang.class);
                    startActivity(intent);
                }else{
                    CheckConnection.showToast_short(getApplicationContext(),"giỏ hàng của bạn chưa có sản phẩm để thanh toán");
                }
            }
        });
    }
}
