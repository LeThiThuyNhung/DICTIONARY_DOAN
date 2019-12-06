package com.example.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

public class TraTu extends AppCompatActivity {

    TextView anhviet, trainghia, nghiacuaban, txttt;
    ViewPager viewPager;
    TTAdapter adapter;
    ImageView search, back, sao;

    String urlInsert = "http://192.168.1.11:8080/androidwebservice/insertYourWord.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tra_tu);
        viewPager = (ViewPager)findViewById(R.id.fragment_container);
        txttt = (TextView)findViewById(R.id.txtTT) ;
        search = findViewById(R.id.search_tt);
        adapter = new TTAdapter(getSupportFragmentManager());

        viewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tbdemo);
        tabLayout.setupWithViewPager(viewPager);
        final Intent intent = getIntent();
        String tu = intent.getStringExtra("Word");
        txttt.setText(tu);

        back = findViewById(R.id.imgmuiten);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TraTu.this, Search.class);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(TraTu.this, MainActivity.class);
                startActivity(intent1);
            }
        });

        sao = findViewById(R.id.save_tt);
        sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InsertWord(txttt.getText().toString());
            }
        });
    }

    public  void InsertWord(final String word)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("D"))
                {
                    Toast.makeText(TraTu.this, "Đã thêm['"+word+"']vào Từ Của Bạn", Toast.LENGTH_SHORT).show();
                }
                else if(response.trim().equals("success"))
                {
                    Toast.makeText(TraTu.this, "Đã thêm['"+word+"']vào Từ Của Bạn", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(TraTu.this, response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TraTu.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
                        Log.d("AAA", "Lỗi"+error.toString());
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("WORD1", word);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}
