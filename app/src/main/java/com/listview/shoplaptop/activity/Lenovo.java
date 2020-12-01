package com.listview.shoplaptop.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.listview.shoplaptop.R;
import com.listview.shoplaptop.adapter.SPDellAdapter;
import com.listview.shoplaptop.model.SanPham;
import com.listview.shoplaptop.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Lenovo extends AppCompatActivity {



    Toolbar toolbar;
    ListView listview;

    ArrayList<SanPham> listSanPham;
    SPDellAdapter spDellAdapter;

    int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lenovo);

        AnhXa();
        ActionBar();

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
                Intent intent=new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void AnhXa() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        listview = (ListView) findViewById(R.id.listview);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",listSanPham.get(position));
                startActivity(intent);
            }
        });

        listSanPham = new ArrayList<>();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null) {
            id=bundle.getInt("idloaisanpham");
            //Toast.makeText(Lenovo.this,id+"",Toast.LENGTH_SHORT).show();
        }

        getData();

        spDellAdapter = new SPDellAdapter(listSanPham,Lenovo.this);
        listview.setAdapter(spDellAdapter);

    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //public static String urlSanpham="http://"+localhost+"/shop/getsanpham.php?page=";
        String duongdan= Server.urlsanpham+""+id;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(duongdan, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                int Id = 0;
                String Name = "";
                int Price = 0;
                String Image = "";
                String Intro = "";

                int Catid = 0;
                //nếu có json
                if (response != null) {

                    for(int i=0;i<response.length();i++){
                        //JSONObject jsonObject = null;
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            Id=jsonObject.getInt("id");
                            Name=jsonObject.getString("name");


                            Price=jsonObject.getInt("price");
                            Image=jsonObject.getString("image");
                            Intro=jsonObject.getString("description");

                            Catid=jsonObject.getInt("catid");
                            listSanPham.add(new SanPham(Id,Name, (long) Price,Image,Intro,Catid));
                            spDellAdapter.notifyDataSetChanged();
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
}
