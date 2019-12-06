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

public class TDTAdapter extends BaseAdapter implements Filterable {
    private TuDaTra context;
    private List<TDT> tdtList;
    private LayoutInflater inflater;
    private int layout;
    List<TDT> mValue;
    ValueFilter valueFilter;

    public TDTAdapter(TuDaTra context, List<TDT> tdtList, int layout) {
        this.context = context;
        this.tdtList = tdtList;
        this.layout = layout;
        mValue = tdtList;
    }

    @Override
    public int getCount() {
        return tdtList.size();
    }

    @Override
    public Object getItem(int position) {
        return tdtList.get(position);
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
        final TextView txtWord2 = convertView.findViewById(R.id.txttu);
        TextView txtMeaning2 = convertView.findViewById(R.id.txtnghia);
        ImageView imv1 = convertView.findViewById(R.id.imgloa);

        final TDT tdt = tdtList.get(position);
        final String word2 = tdt.getWord2();
        final String meaning2 = tdt.getMeaning2();

        txtWord2.setText(word2);
        txtMeaning2.setText(meaning2);

        imv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String nd = txtWord2.getText().toString();
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

        ImageView imageView = convertView.findViewById(R.id.imgsao);
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                context.InsertWord(txtWord2.getText().toString());
            }
        });

        return convertView;
    }




    public void XacNhanXoa(final int id1)
    {
        final String tu = tdtList.get(id1).getWord2();
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
                ArrayList<TDT> filterList = new ArrayList<TDT>();
                for( int i = 0; i < mValue.size(); i++){
                    if((mValue.get(i).getWord2().toUpperCase()).contains(constraint.toString().toUpperCase())){
                        TDT yourWord = new TDT(mValue.get(i).getWord2(), mValue.get(i).getMeaning2(),mValue.get(i).getType2(), mValue.get(i).getPronounce2());
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
            tdtList = (ArrayList<TDT>) results.values;
            notifyDataSetChanged();
        }
    }
}
