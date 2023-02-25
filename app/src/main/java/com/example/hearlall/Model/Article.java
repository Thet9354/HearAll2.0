package com.example.hearlall.Model;

public class Article {

    private int articlePic;
    private String article;

    public Article(int articlePic, String article) {
        this.articlePic = articlePic;
        this.article = article;
    }

    public Article() {

    }

    public int getArticlePic() {
        return articlePic;
    }

    public void setArticlePic(int articlePic) {
        this.articlePic = articlePic;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }
}
