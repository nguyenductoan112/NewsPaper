package com.example.newspaper.model;

public class NewsModel {
    private String title;
    private String author;
    private String description;
    private String content;
    private String image_url;
    private String pubDate;
    private String category;

    public NewsModel() {
    }

    public NewsModel(String title, String author, String description, String content, String image_url, String pubDate, String category) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.content = content;
        this.image_url = image_url;
        this.pubDate = pubDate;
        this.category = category;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getImage_url() {
        return image_url;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }
}
