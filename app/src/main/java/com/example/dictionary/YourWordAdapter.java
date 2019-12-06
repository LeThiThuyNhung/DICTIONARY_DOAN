package com.example.dictionary;

import android.content.Context;
import android.content.DialogInterface;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class YourWordAdapter extends BaseAdapter implements Filterable {
    private TuCuaBan context;
    private List<YourWord> yourWordList;
    private LayoutInflater inflater;
    private int layout;
    List<YourWord> mValue;
    ValueFilter valueFilter;

    public YourWordAdapter(TuCuaBan context, List<YourWord> yourWordList, int layout) {
        this.context = context;
        this.yourWordList = yourWordList;
        this.layout = layout;
        mValue = yourWordList;
    }

    @Override
    public int getCount() {
        return yourWordList.size();
    }

    @Override
    public Object getItem(int position) {
        return yourWordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater == null){
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(convertView == null){
            convertView = inflater.inflate(layout, null);
        }
        final TextView txtWord1 = convertView.findViewById(R.id.txttu);
        TextView txtMeaning1 = convertView.findViewById(R.id.txtnghia);
        ImageView imv1 = convertView.findViewById(R.id.imgloa);

        final YourWord yourWord = yourWordList.get(position);
        String word1 = yourWord.getWord1();
        String meaning1 = yourWord.getMeaning();

        txtWord1.setText(word1);
        txtMeaning1.setText(meaning1);

        //context.registerForContextMenu(imv2);
        imv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nd = txtWord1.getText().toString();
                context.toSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if (i != TextToSpeech.ERROR) {
                            context.toSpeech.setLanguage(Locale.ENGLISH);
                            context.toSpeech.speak(nd, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                });
            }
        });

        return convertView;
    }

    public void XacNhanXoa(final int id1)
    {
        final String tu = yourWordList.get(id1).getWord1();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to delete "+tu+"?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.DeleteWord(tu);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    public void XacNhanXoaHet()
    {
//        final int id = yourWordList.get(id1).getID1();
//        String tu = yourWordList.get(id1).getWord1();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to delete all?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.DeleteAllWord();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public Filter getFilter() {
        if(valueFilter == null){
            valueFilter = new ValueFilter();
        }
        return valueFilter;
    }

    private class ValueFilter extends  Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            if(constraint != null && constraint.length() > 0){
                ArrayList<YourWord> filterList = new ArrayList<YourWord>();
                for( int i = 0; i < mValue.size(); i++){
                    if((mValue.get(i).getWord1().toUpperCase()).contains(constraint.toString().toUpperCase())){
                        YourWord yourWord = new YourWord(mValue.get(i).getWord1(), mValue.get(i).getMeaning(), mValue.get(i).getType(), mValue.get(i).getPronounce());
                        filterList.add(yourWord);
                    }
                }
                results.count = filterList.size();
                results.values = filterList;
            }else{
                results.count = mValue.size();
                results.values = mValue;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            yourWordList = (ArrayList<YourWord>) results.values;
            notifyDataSetChanged();
        }
    }
}