package com.example.dictionary;

public class YourWord {

    private String Word1;
    private String Meaning;
    private String Type;
    private  String Pronounce;

    public YourWord(String word1, String meaning, String type, String pronounce) {
        Word1 = word1;
        Meaning = meaning;
        Type = type;
        Pronounce = pronounce;
    }

    public String getWord1() {
        return Word1;
    }

    public void setWord1(String word1) {
        Word1 = word1;
    }

    public String getMeaning() {
        return Meaning;
    }

    public void setMeaning(String meaning) {
        Meaning = meaning;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getPronounce() {
        return Pronounce;
    }

    public void setPronounce(String pronounce) {
        Pronounce = pronounce;
    }
}
