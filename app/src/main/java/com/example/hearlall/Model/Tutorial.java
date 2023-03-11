package com.example.hearlall.Model;

public class Tutorial {

    private int tutorialPic;
    private String tutorialName;

    public Tutorial(int tutorialPic, String tutorialName) {
        this.tutorialPic = tutorialPic;
        this.tutorialName = tutorialName;
    }

    public Tutorial() {

    }

    public int getTutorialPic() {
        return tutorialPic;
    }

    public void setTutorialPic(int tutorialPic) {
        this.tutorialPic = tutorialPic;
    }

    public String getTutorialName() {
        return tutorialName;
    }

    public void setTutorialName(String tutorialName) {
        this.tutorialName = tutorialName;
    }
}
