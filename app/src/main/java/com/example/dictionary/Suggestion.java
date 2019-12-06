package com.example.dictionary;

import android.annotation.SuppressLint;
import android.os.Parcel;

import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;


@SuppressLint("ParcelCreator")
public class Suggestion implements SearchSuggestion {
     String mWord;
     String mType;
     String mPronounce;
     String mMeaning;
     boolean mIsHistory = false;

    public Suggestion( String mWord, String mType, String mPronounce, String mMeaning) {
        this.mWord = mWord;
        this.mType = mType;
        this.mPronounce = mPronounce;
        this.mMeaning = mMeaning;
    }

    public String getmWord() {
        return mWord;
    }

    public void setmWord(String mWord) {
        this.mWord = mWord;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public String getmPronounce() {
        return mPronounce;
    }

    public void setmPronounce(String mPronounce) {
        this.mPronounce = mPronounce;
    }

    public String getmMeaning() {
        return mMeaning;
    }

    public void setmMeaning(String mMeaning) {
        this.mMeaning = mMeaning;
    }

    public boolean ismIsHistory() {
        return mIsHistory;
    }

    public void setmIsHistory(boolean mIsHistory) {
        this.mIsHistory = mIsHistory;
    }

    @Override
    public String getBody()
    {
        return mWord;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}