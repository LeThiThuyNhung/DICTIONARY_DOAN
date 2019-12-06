package com.example.dictionary;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class WordAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Word> wordList;

    public WordAdapter(Context context, int layout, List<Word> wordList) {
        this.context = context;
        this.layout = layout;
        this.wordList = wordList;
    }

    @Override
    public int getCount() {
        return wordList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private  class  ViewHolder{
        TextView txtWord, txtType, txtPronounce, txtMeaning;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            holder.txtWord = (TextView) convertView.findViewById(R.id.txtWord);
            holder.txtType= (TextView) convertView.findViewById(R.id.txtType);
            holder.txtPronounce = (TextView) convertView.findViewById(R.id.txtPronounce);
            holder.txtMeaning = (TextView) convertView.findViewById(R.id.txtMeaning);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        Word word = wordList.get(position);
        holder.txtWord.setText(word.getWord());
        holder.txtType.setText(word.getType());
        holder.txtPronounce.setText((word.getPronounce()));
        holder.txtMeaning.setText(word.getMeaning());

        return convertView;

    }
}
