package com.gamadevlopment.ucimoslovenski;

public class Exercise {

    private String title ;
    private String csvFileName;

    private String numberOfWordsInExcerCise;

    private String Excercise_image;


    public Exercise(String title, String csvFileName, String numberOfWordsInExcerCise, String excercise_image) {
        this.title = title;
        this.csvFileName = csvFileName;
        this.numberOfWordsInExcerCise = numberOfWordsInExcerCise;
        Excercise_image = excercise_image;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCsvFileName() {
        return csvFileName;
    }

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }

    public String getNumberOfWordsInExcerCise() {
        return numberOfWordsInExcerCise;
    }

    public void setNumberOfWordsInExcerCise(String numberOfWordsInExcerCise) {
        this.numberOfWordsInExcerCise = numberOfWordsInExcerCise;
    }

    public String getExcercise_image() {
        return Excercise_image;
    }

    public void setExcercise_image(String excercise_image) {
        Excercise_image = excercise_image;
    }
}
