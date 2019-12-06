package com.example.dictionary;

public class TDT {
    private String Word2;
    private String Meaning2;
    private  String Type2;
    private String Pronounce2;

    public String getType2() {
        return Type2;
    }

    public void setType2(String type2) {
        Type2 = type2;
    }

    public String getPronounce2() {
        return Pronounce2;
    }

    public void setPronounce2(String pronounce2) {
        Pronounce2 = pronounce2;
    }


    public TDT(String word2, String meaning2, String type2, String pronounce2) {
        Word2 = word2;
        Meaning2 = meaning2;
        Type2 = type2;
        Pronounce2 = pronounce2;
    }

    public String getWord2() {
        return Word2;
    }

    public void setWord2(String word2) {
        Word2 = word2;
    }

    public String getMeaning2() {
        return Meaning2;
    }

    public void setMeaning2(String meaning2) {
        Meaning2 = meaning2;
    }
}
