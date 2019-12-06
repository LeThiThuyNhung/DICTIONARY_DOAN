package com.example.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TuDaTra extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    List<TDT> tdt;
    TDTAdapter adapter;
    ImageView search, tv;
    TextToSpeech toSpeech;

    String urlGetData = "http://192.168.1.11:8080/androidwebservice/GetDataTDT.php";
    String urlDelete = "http://192.168.1.11.:8080/androidwebservice/deleteTDT";
    String urlDeleteAll = "http://192.168.1.11:8080/androidwebservice/deleteTDTAll.php";
    String urlInsert = "http://192.168.1.11:8080/androidwebservice/insertYourWord.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu_da_tra);

        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.lvTDT);
        search = findViewById(R.id.imgsearch);
        tv = findViewById(R.id.imgCCel);
        registerForContextMenu(listView);

        tdt = new ArrayList<TDT>();
        adapter = new TDTAdapter(this, tdt, R.layout.tudatra);
        listView.setAdapter(adapter);

        GetData(urlGetData);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TuDaTra.this, Search.class);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TDT tdt = (TDT) adapter.getItem(position);
                Intent intent = new Intent(TuDaTra.this, TraTu.class);

                intent.putExtra("Word", tdt.getWord2());
                intent.putExtra("Type", tdt.getType2());
                intent.putExtra("Pronounce", tdt.getPronounce2());
                intent.putExtra("Meaning", tdt.getMeaning2());
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TuDaTra.this, Search.class);
                startActivity(intent);
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TuDaTra.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(item.getItemId() == R.id.xoa){
            int pos = adapterContextMenuInfo.position;
            adapter.XacNhanXoa(pos);
        }
        if(item.getItemId() == R.id.xoah){
//            int pos = adapterContextMenuInfo.position;
            adapter.XacNhanXoaHet();
        }
        return super.onContextItemSelected(item);
    }

    private void GetData(String url)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                tdt.add(new TDT(
                                        object.getString("Word"),
                                        object.getString("Meaning"),
                                        object.getString("Type"),
                                        object.getString("Pronounce")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TuDaTra.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 5000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 5000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });

    }

    public void DeleteAllWord()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest( urlDeleteAll,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")) {
                            Toast.makeText(TuDaTra.this, "Success", Toast.LENGTH_SHORT).show();
                            GetData(urlGetData);
                        } else {
                            Toast.makeText(TuDaTra.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TuDaTra.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){};
        requestQueue.add(stringRequest);
    }

    public  void InsertWord(final String word)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlInsert, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.trim().equals("D"))
                {
                    Toast.makeText(TuDaTra.this, "Đã thêm['"+word+"']vào Từ Của Bạn", Toast.LENGTH_SHORT).show();
                }
                else if(response.trim().equals("success"))
                {
                    Toast.makeText(TuDaTra.this, "Đã thêm['"+word+"']vào Từ Của Bạn", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(TuDaTra.this, response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TuDaTra.this, "Xảy ra lỗi", Toast.LENGTH_SHORT).show();
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

    public void DeleteWord(final String word)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlDelete,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("success")){
                            Toast.makeText(TuDaTra.this, "Success", Toast.LENGTH_SHORT).show();
                            GetData(urlGetData);
                        }
                        else {
                            Toast.makeText(TuDaTra.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TuDaTra.this, error.toString(), Toast.LENGTH_SHORT).show();
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
