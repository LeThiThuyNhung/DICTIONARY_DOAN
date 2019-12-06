package com.example.dictionary;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class UngDungTA extends AppCompatActivity {

    ListView listView;
    String mTitle[] = {"Duolingo: Học Anh văn miễn phí", "Học Tiếng Anh Giao Tiếp", "English Talk – Testuru", "Fun Easy English", "Memrise"};
    String mDescription[] = {"Học tiếng Anh miễn phí với Duolingo, ứng dụng giáo dục ngôn ngữ được tải về hàng đầu trên thế giới.","Học tiếng anh giao tiếp ứng dụng hỗ trợ học giao tiếp tiếp anh trực quan, hiệu quả, và không nhàm chán", "Ứng dụng học tiếng anh online Testuru – Take test, get better là một nền tảng công nghệ hỗ trợ người dùng thi thử IELTS online","Ứng dụng với bộ sưu tập hơn 6000 từ vựng tiếng anh hoàn toàn miễn phí. Bạn có thể học với các hình ảnh minh họa, phiên âm và cách phát âm","Memrise là ứng dụng miễn phí học nhiều ngôn ngữ trên thế giới, không chỉ riêng tiếng anh"};
    int image[] = {R.drawable.d, R.drawable.capture, R.drawable.test, R.drawable.tu, R.drawable.memrise};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ung_dung_t);

        listView = findViewById(R.id.lstView1);
        MyAdapter1 adapter = new MyAdapter1( this, mTitle, image, mDescription);
        listView.setAdapter(adapter);

        ImageButton imbt = (ImageButton) findViewById(R.id.imageButton);
        imbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UngDungTA.this, MainActivity.class);
                startActivity(intent);
            }

        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                if(i==0)
                {
                    String link = "https://play.google.com/store/apps/details?id=com.duolingo";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(link));
                    startActivity(intent);
                }
                if(i==1)
                {
                    String link = "https://play.google.com/store/apps/details?id=com.ndm.tienganh&hl=vi";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(link));
                    startActivity(intent);
                }
                if(i==2)
                {
                    String link = "https://play.google.com/store/apps/details?id=com.boot.ielts";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(link));
                    startActivity(intent);
                }
                if(i==3)
                {
                    String link = "https://play.google.com/store/apps/details?id=com.funeasylearn.english";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(link));
                    startActivity(intent);
                }
                if(i==4)
                {
                    String link = "https://play.google.com/store/apps/details?id=com.memrise.android.memrisecompanion";
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(link));
                    startActivity(intent);
                }
            }
        });

    }


    class MyAdapter1 extends ArrayAdapter<String>
    {
        Context context;
        String rTitle[];
        String rDescription[];
        int rImage[];

        MyAdapter1(Context c, String title[], int image[], String description[]) {
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
            View row = layoutInflater.inflate(R.layout.ungdung, parent, false);
            ImageView imageViewge = row.findViewById(R.id.img);
            TextView textView = row.findViewById(R.id.txtud);
            TextView textView2 = row.findViewById(R.id.txtdetail);

            imageViewge.setImageResource(rImage[position]);
            textView.setText(rTitle[position]);
            textView2.setText(mDescription[position]);

            return row;
        }
    }
}
