package com.example.dictionary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.Locale;

public class Fragment_AnhViet extends Fragment {
    private View mRootView;
    TextView txttt, txtpa, txtlt, txtnghia;
    TextToSpeech toSpeech;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_anh_viet, container, false);

        txttt = (TextView)  mRootView.findViewById(R.id.txtTu);
        txtpa = (TextView)  mRootView.findViewById(R.id.txtPA);
        txtlt = (TextView)  mRootView.findViewById(R.id.txtLT);
        txtnghia = (TextView)  mRootView.findViewById(R.id.txtNT);

        Activity activity = (Activity) getActivity();
        final Intent intent = activity.getIntent();
        String tu = intent.getStringExtra("Word");
        String pa = intent.getStringExtra("Pronounce");
        String tl = intent.getStringExtra("Type");
        String nghia = intent.getStringExtra("Meaning");
        txttt.setText(tu);
        txtpa.setText(pa);
        txtlt.setText(tl);
        txtnghia.setText(nghia);

        ImageView imv1 = mRootView.findViewById(R.id.imv2);
        imv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nd = txttt.getText().toString();
                toSpeech = new TextToSpeech(getActivity(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if(i != TextToSpeech.ERROR)
                        {
                            toSpeech.setLanguage(Locale.ENGLISH);
                            toSpeech.speak(nd, TextToSpeech.QUEUE_FLUSH,null);

                        }
                    }
                });
            }
        });
        return mRootView;
    }
}
