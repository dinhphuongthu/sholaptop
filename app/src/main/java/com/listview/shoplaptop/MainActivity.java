package com.listview.shoplaptop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.listview.shoplaptop.activity.Asus;
import com.listview.shoplaptop.activity.ChiTietSanPham;
import com.listview.shoplaptop.activity.ChiTietSanPhamMoiNhat;
import com.listview.shoplaptop.activity.Dell;
import com.listview.shoplaptop.activity.Hp;
import com.listview.shoplaptop.activity.Lenovo;
import com.listview.shoplaptop.activity.LienHe;
import com.listview.shoplaptop.activity.Macbook;
import com.listview.shoplaptop.adapter.LoaiSPadapter;
import com.listview.shoplaptop.adapter.SanPhamadapter;
import com.listview.shoplaptop.adapter.TheLoaiadapter;
import com.listview.shoplaptop.model.GioHang;
import com.listview.shoplaptop.model.LoaiSP;
import com.listview.shoplaptop.model.SanPham;
import com.listview.shoplaptop.util.CheckConnection;
import com.listview.shoplaptop.util.Server;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SanPhamadapter.SelectedProduct {
    androidx.appcompat.widget.Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerViewmanhinhchinh,recycleViewTheLoai;
    NavigationView navigationView;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;
    ArrayList<LoaiSP> listtheloai;
    TheLoaiadapter theLoaiadapter;

    ArrayList<LoaiSP> listloaisp;
    LoaiSPadapter listloaispadapter;
    int id=0;
    String tenloaisp="";
    String hinhanhsp="";

    ArrayList<SanPham> listsanpham;
    SanPhamadapter listspadapter;

    public static ArrayList<GioHang> listGioHang;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        ActionBar();
        ActionViewFlipper();
        getdulieuloaisp();
        getLoaiSP();
        getdulieusanphammoinhat();
        CatchOnItemListView();

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
    private void CatchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, Dell.class);
                            intent.putExtra("idloaisanpham", listloaisp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, Hp.class);
                            intent.putExtra("idloaisanpham", listloaisp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 3:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, Asus.class);
                            intent.putExtra("idloaisanpham", listloaisp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 4:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, Lenovo.class);
                            intent.putExtra("idloaisanpham", listloaisp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case 5:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, Macbook.class);
                            intent.putExtra("idloaisanpham", listloaisp.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

//                    case 6:
//                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
//                            Intent intent = new Intent(MainActivity.this, LienHe.class);
//                            startActivity(intent);
//                        } else {
//                            CheckConnection.showToast_short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
//                        }
//                        drawerLayout.closeDrawer(GravityCompat.START);
//                        break;
                }
            }
        }
        );}

    private void getLoaiSP(){
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null)//neu du lieu json ton tai
                {
                    for(int i=0;i<response.length();i++)  {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            tenloaisp=jsonObject.getString("name");

                            hinhanhsp=jsonObject.getString("image");
                            listtheloai.add(new LoaiSP(id,tenloaisp,hinhanhsp));
                            theLoaiadapter.notifyDataSetChanged();
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
    private void getdulieuloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null)//neu du lieu json ton tai
                {
                    for(int i=0;i<response.length();i++)  {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            tenloaisp=jsonObject.getString("name");

                            hinhanhsp=jsonObject.getString("image");
                            listloaisp.add(new LoaiSP(id,tenloaisp,hinhanhsp));
                            listloaispadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listloaisp.add(6,new LoaiSP(0,"Liên hệ","https://icon-library.net/images/android-contacts-icon-png/android-contacts-icon-png-11.jpg"));
                    listloaisp.add(7,new LoaiSP(0,"Tài khoản","https://icon-library.net/images/android-contacts-icon-png/android-contacts-icon-png-26.jpg"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);
    }
    private void getdulieusanphammoinhat() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlsanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response!=null)//neu du lieu json ton tai
                {   int id1=0;
                    String name="";
                    long price=0;
                    String image="";
                    String intro="";
                    int catid=0;
                    for(int i=0;i<response.length();i++)  {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id1=jsonObject.getInt("id");
                           // Log.d("kiemtra2",String.valueOf(id1));
                            name=jsonObject.getString("name");

                            price=jsonObject.getLong("price");
                            image=jsonObject.getString("image");
                            intro=jsonObject.getString("description");

                            catid=jsonObject.getInt("catid");
                            listsanpham.add(new SanPham(id1,name,price,image,intro,catid));

                            listspadapter.notifyDataSetChanged();
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
    private void ActionViewFlipper(){
        ArrayList<String> mangquangcao=new ArrayList<>();//khai bao va cap phat bo nho
        mangquangcao.add("https://images.fpt.shop/unsafe/fit-in/1200x300/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/9/22/637363819762175576_B2S-C1-2x.png");
        mangquangcao.add("https://images.fpt.shop/unsafe/fit-in/1200x300/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/9/1/637345475462076999_LenovoT9-C1.png");
        mangquangcao.add("https://images.fpt.shop/unsafe/fit-in/1200x300/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2020/8/31/637345132362643573_AcerThang9-C1-2x.png");

        for(int i=0;i<mangquangcao.size();i++)
        {
            ImageView imageView = new ImageView(getApplicationContext());
            //Load ảnh từ url trong mangquangcao về imageview
            Picasso.with(getApplicationContext()).load(mangquangcao.get(i)).into(imageView);
            //chỉnh kích thước để imageview vừa đủ với ViewFlipper
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        //bắt sự kiện cho viewflipper chạy.
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_slide_out= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setAnimation(animation_slide_in);
        viewFlipper.setAnimation(animation_slide_out);
    }
    private void ActionBar() {
        setSupportActionBar(toolbar);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_menu_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void Anhxa() {
        toolbar=( androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbarmanhinhchinh);
        viewFlipper=(ViewFlipper) findViewById(R.id.viewfliper);
        recyclerViewmanhinhchinh=(RecyclerView)findViewById(R.id.recyclerview);
        recycleViewTheLoai = (RecyclerView) findViewById(R.id.recyclerViewTheLoai);
        navigationView=(NavigationView)findViewById(R.id.navigationview);
        listViewmanhinhchinh=(ListView)findViewById(R.id.listviewmanhinhchinh);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawerlayout);
        listloaisp=new ArrayList<>();
        listloaisp.add(0,new LoaiSP(0,"Trang Chính","https://icons.iconarchive.com/icons/double-j-design/origami-colored-pencil/256/blue-home-icon.png"));
        listloaispadapter=new LoaiSPadapter(listloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(listloaispadapter);

        listtheloai = new ArrayList<>();
        theLoaiadapter = new TheLoaiadapter(listtheloai,getApplicationContext());

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recycleViewTheLoai.setLayoutManager(mLayoutManager);
        recycleViewTheLoai.setAdapter(theLoaiadapter);

        listsanpham=new ArrayList<>();
        listspadapter=new SanPhamadapter(listsanpham,this);

        recyclerViewmanhinhchinh.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        recyclerViewmanhinhchinh.setAdapter(listspadapter);

        recyclerViewmanhinhchinh.setLayoutManager(gridLayoutManager);
        if(listGioHang!=null){
            Toast.makeText(this, "Có "+ listGioHang.size()+ " sản phẩm trong giỏ hàng!", Toast.LENGTH_SHORT).show();
        }else{
        listGioHang = new ArrayList<>();
        }
    }
    @Override
    public void selectedProduct(SanPham sanpham){
        startActivity(new Intent(MainActivity.this, ChiTietSanPhamMoiNhat.class).putExtra("data",sanpham));
    }


}



