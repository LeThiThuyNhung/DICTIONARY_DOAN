package com.example.dictionary;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
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

public class TuCuaBan extends AppCompatActivity {

    SearchView searchView;
    ListView listView;
    List<YourWord> yourWords;
    YourWordAdapter adapter;
    ImageView search, tv;
    TextToSpeech toSpeech;
    List<Word> words;

    String urlGetData = "http://192.168.1.11:8080/androidwebservice/getdataTCB.php";
    String urlDelete = "http://192.168.1.11:8080/androidwebservice/deleteWord.php";
    String urlDeleteAll = "http://192.168.1.11:8080/androidwebservice/deleteyourWord.php";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tu_cua_ban);

        searchView = findViewById(R.id.search);
        listView = findViewById(R.id.lvTCB);
        search = findViewById(R.id.imgTK);
        tv = findViewById(R.id.imgTV);

        registerForContextMenu(listView);

        yourWords = new ArrayList<YourWord>();
        adapter = new YourWordAdapter(this, yourWords, R.layout.tucuaban);
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


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                YourWord yourWord = (YourWord) adapter.getItem(position);
                Intent intent = new Intent(TuCuaBan.this, TraTu.class);

                intent.putExtra("Word", yourWord.getWord1());
                intent.putExtra("Type", yourWord.getType());
                intent.putExtra("Pronounce", yourWord.getPronounce());
                intent.putExtra("Meaning", yourWord.getMeaning());
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TuCuaBan.this, Search.class);
                startActivity(intent);
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TuCuaBan.this, MainActivity.class);
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
//                        Toast.makeText(TuDaTra.this, response.toString(), Toast.LENGTH_LONG).show();
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                yourWords.add(new YourWord(
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
                        Toast.makeText(TuCuaBan.this, error.toString(), Toast.LENGTH_LONG).show();
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
                            Toast.makeText(TuCuaBan.this, "Success", Toast.LENGTH_SHORT).show();
                            GetData(urlGetData);
                        } else {
                            Toast.makeText(TuCuaBan.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TuCuaBan.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ){};
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
                            Toast.makeText(TuCuaBan.this, "Success", Toast.LENGTH_SHORT).show();
                            GetData(urlGetData);
                        }
                        else {
                            Toast.makeText(TuCuaBan.this, response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TuCuaBan.this, error.toString(), Toast.LENGTH_SHORT).show();
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
