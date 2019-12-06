package com.example.dictionary;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.arlib.floatingsearchview.FloatingSearchView;


public class MainActivity extends AppCompatActivity  {


    public TextToSpeech toSpeech;
    private FloatingSearchView mSearchView;

    ListView listView;
    String mTitle[] = {"Từ đã tra", "Từ của bạn", "Cửa sổ tra nhanh", "Kênh học tiếng anh", "Ứng dụng học tiếng anh khác","Thông tin"};
    String mDescription[] = {"","", "","","","Phiên bản 1.1, phát triển bởi TNH"};
    int image[] = {R.drawable.accept_icon, R.drawable.star_icon, R.drawable.window_icon, R.drawable.youtube_icon, R.drawable.play_playstore_icon, R.drawable.get_info_icon};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.lstView);

        mSearchView = findViewById(R.id.floating_search_view);

        final MenuItem mActionVoice = findViewById(R.id.action_voice_rec);
        mSearchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);

                mSearchView.hideProgress();

            }

            @Override
            public void onFocusCleared() {

            }
        });

        MyAdapter adapter = new MyAdapter( this, mTitle, image, mDescription);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==4)
                {
                    Intent intent = new Intent(MainActivity.this, UngDungTA.class);
                    startActivity(intent);
                }
                if(i==1)
                {
                    Intent intent = new Intent(MainActivity.this, TuCuaBan.class);
                    startActivity(intent);
                }

                if(i==0)
                {
                    Intent intent = new Intent(MainActivity.this, TuDaTra.class);
                    startActivity(intent);
                }

                if(i==3)
                {
                    String link = "https://www.youtube.com/results?search_query=c%C3%A1c+k%C3%AAnh+h%E1%BB%8Dc+ti%E1%BA%BFng+anh+hay+tr%C3%AAn+youtube";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(link));
                    startActivity(intent);
                }

                if(i==2)
                {
                    Intent intent = new Intent(MainActivity.this, TraTu.class);
                    startActivity(intent);
                }

            }
        });

    }



    class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        String rTitle[];
        String rDescription[];
        int rImage[];

        MyAdapter(Context c, String title[], int image[], String description[]) {
            super(c, R.layout.row, R.id.tvView);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImage = image;
        }

        @Override
        public int getCount() {
            return rTitle.length;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView imageViewge = row.findViewById(R.id.imgView);
            TextView textView = row.findViewById(R.id.tvView);
            TextView textView2 = row.findViewById(R.id.tvView2);
            imageViewge.setImageResource(rImage[position]);
            textView.setText(rTitle[position]);
            textView2.setText(mDescription[position]);
            return row;
        }
    }
}
