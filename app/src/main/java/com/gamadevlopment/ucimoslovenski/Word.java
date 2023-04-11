package com.gamadevlopment.ucimoslovenski;

public class Word {

    private String Forgein_word;
    private String Translated_word;
    private String audio_path;

    public Word(String forgein_word, String translated_word, String audio_path) {
        this.Forgein_word = forgein_word;
        this.Translated_word = translated_word;
        this.audio_path = audio_path;
    }

    public String getForgein_word() {
        return Forgein_word;
    }

    public void setForgein_word(String forgein_word) {
        Forgein_word = forgein_word;
    }

    public String getTranslated_word() {
        return Translated_word;
    }

    public void setTranslated_word(String translated_word) {
        Translated_word = translated_word;
    }

    public String getAudio_path() {
        return audio_path;
    }

    public void setAudio_path(String audio_path) {
        this.audio_path = audio_path;
    }
}
