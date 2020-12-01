package com.listview.shoplaptop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.listview.shoplaptop.MainActivity;
import com.listview.shoplaptop.R;
import com.listview.shoplaptop.adapter.SanPhamTheoLoaiadapter;
import com.listview.shoplaptop.adapter.SanPhamadapter;
import com.listview.shoplaptop.model.GioHang;
import com.listview.shoplaptop.model.SanPham;
import com.listview.shoplaptop.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChiTietSanPham extends AppCompatActivity implements SanPhamTheoLoaiadapter.SelectedProduct{

    Toolbar toolbarchitiet;
    ImageView imgChitiet;
    TextView txtten, txtgia, txtmota;
    Spinner spinner;
    Button btndatmua;
    int id;
    int Id = 0;
    String Name = "";
    long Giachitiet = 0;
    String Image = "";
    String Intro = "";

    int Catid = 0;

    ArrayList<SanPham> listSanPham;
    SanPhamTheoLoaiadapter sanPhamadapter;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        AnhXa();
        ActionBar();
        GetInformation();
        CatchEventSpinner();
        CatchEventButton();
        GetSanPhamLienQuan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.giohang,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(), com.listview.shoplaptop.activity.GioHang.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void ActionBar() {
        setSupportActionBar(toolbarchitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarchitiet.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbarchitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void GetInformation() {

        SanPham sp = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        Id = sp.getId();
        Name = sp.getName();
        Giachitiet = sp.getPrice();
        Image = sp.getImage();
        Intro = sp.getIntro();
        Catid = sp.getCatid();
        txtten.setText(Name);
        DecimalFormat decimalFormat = new DecimalFormat("###,###.###");
        txtgia.setText("giá:" + decimalFormat.format(Giachitiet) + "" + "Đ");
        txtmota.setText(Intro);
        Picasso.with(getApplicationContext()).load(Image).resize(150, 100).centerCrop().into(imgChitiet);
    }
    public void GetSanPhamLienQuan(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //public static String urlSanpham="http://"+localhost+"/shop/getsanpham.php?page=";
        String urlSPDell="http://"+Server.localhost+"/shoplaptop/sanphamloaiDell.php?cate="+""+Catid+"&Id="+Id;
       // String duongdan= Server.urlSPDell+""+Id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(urlSPDell, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int Id1 = 0;
                String Name1 = "";
                int Price1 = 0;
                String Image1 = "";
                String Intro1 = "";

                int Catid1 = 0;
                //nếu có json
                if (response != null) {

                    for(int i=0;i<response.length();i++){
                        //JSONObject jsonObject = null;
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Id1=jsonObject.getInt("id");
                            Name1=jsonObject.getString("name");


                            Price1=jsonObject.getInt("price");
                            Image1=jsonObject.getString("image");
                            Intro1=jsonObject.getString("description");

                            Catid1=jsonObject.getInt("catid");
                            listSanPham.add(new SanPham(Id1,Name1, (long) Price1,Image1,Intro1,Catid1));
                            sanPhamadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);

    }
    private void AnhXa() {
        toolbarchitiet = (Toolbar) findViewById(R.id.toolbarchitietsanpham);
        imgChitiet = (ImageView) findViewById(R.id.imgchitietsp);
        txtten = (TextView) findViewById(R.id.tvtensp);
        txtgia = (TextView) findViewById(R.id.tvgiasp);
        txtmota = (TextView) findViewById(R.id.tvmotachitietsp);
        spinner = (Spinner) findViewById(R.id.spinner);
        btndatmua = (Button) findViewById(R.id.btnthemgiohang);

        listSanPham = new ArrayList<>();
        sanPhamadapter = new SanPhamTheoLoaiadapter(listSanPham,this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(sanPhamadapter);
    }

    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void CatchEventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(MainActivity.listGioHang.size()>0){
                    int sl=Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exists=false;

                    for(int i=0;i<MainActivity.listGioHang.size();i++){
                        if(MainActivity.listGioHang.get(i).getIdsp()==Id)
                        {
                            MainActivity.listGioHang.get(i).setSoluongsp(MainActivity.listGioHang.get(i).getSoluongsp() + sl);
                            if (MainActivity.listGioHang.get(i).getSoluongsp() >= 10) {
                                MainActivity.listGioHang.get(i).setSoluongsp(10);
                            }
                            MainActivity.listGioHang.get(i).setGiasp(Giachitiet * MainActivity.listGioHang.get(i).getSoluongsp());
                            exists = true;
                        }
                    }
                    if(exists==false){
                        int soluong =Integer.parseInt(spinner.getSelectedItem().toString());
                        long giamoi=soluong*Giachitiet;
                        MainActivity.listGioHang.add(new GioHang(Id,Name,giamoi,Image,soluong));
                    }
                }else{
                    int soluong =Integer.parseInt(spinner.getSelectedItem().toString());
                    long giamoi=soluong * Giachitiet;
                    MainActivity.listGioHang.add(new GioHang(Id,Name,giamoi,Image,soluong));
                }
                Intent intent = new Intent(getApplicationContext(), com.listview.shoplaptop.activity.GioHang.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void selectedProduct(SanPham sanpham) {
        startActivity(new Intent(this,ChiTietSanPham.class).putExtra("data",sanpham));
    }
}
