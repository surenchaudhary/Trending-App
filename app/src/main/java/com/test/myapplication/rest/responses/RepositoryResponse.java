package com.test.myapplication.rest.responses;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Surendra Singh on 2/26/2020.
 * Created for Pitney Bowes
 */
public class RepositoryResponse {

    /**
     * author : jwasham
     * name : coding-interview-university
     * avatar : https://github.com/jwasham.png
     * url : https://github.com/jwasham/coding-interview-university
     * description : A complete computer science study plan to become a software engineer.
     * stars : 99175
     * forks : 29196
     * currentPeriodStars : 799
     * builtBy : [{"username":"jwasham","href":"https://github.com/jwasham","avatar":"https://avatars0.githubusercontent.com/u/3771963"},{"username":"avizmarlon","href":"https://github.com/avizmarlon","avatar":"https://avatars3.githubusercontent.com/u/24354489"},{"username":"YoSaucedo","href":"https://github.com/YoSaucedo","avatar":"https://avatars0.githubusercontent.com/u/13459501"},{"username":"aleen42","href":"https://github.com/aleen42","avatar":"https://avatars3.githubusercontent.com/u/9573300"},{"username":"strollkim","href":"https://github.com/strollkim","avatar":"https://avatars2.githubusercontent.com/u/14087177"}]
     * language : Python
     * languageColor : #3572A5
     */

    @SerializedName("author")
    private String author;
    @SerializedName("name")
    private String name;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("url")
    private String url;
    @SerializedName("description")
    private String description;
    @SerializedName("stars")
    private int stars;
    @SerializedName("forks")
    private int forks;
    @SerializedName("currentPeriodStars")
    private int currentPeriodStars;
    @SerializedName("language")
    private String language;
    @SerializedName("languageColor")
    private String languageColor;
    @SerializedName("builtBy")
    private List<BuiltByBean> builtBy;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getCurrentPeriodStars() {
        return currentPeriodStars;
    }

    public void setCurrentPeriodStars(int currentPeriodStars) {
        this.currentPeriodStars = currentPeriodStars;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageColor() {
        return languageColor;
    }

    public void setLanguageColor(String languageColor) {
        this.languageColor = languageColor;
    }

    public List<BuiltByBean> getBuiltBy() {
        return builtBy;
    }

    public void setBuiltBy(List<BuiltByBean> builtBy) {
        this.builtBy = builtBy;
    }

    public static class BuiltByBean {
        /**
         * username : jwasham
         * href : https://github.com/jwasham
         * avatar : https://avatars0.githubusercontent.com/u/3771963
         */

        @SerializedName("username")
        private String username;
        @SerializedName("href")
        private String href;
        @SerializedName("avatar")
        private String avatar;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }
}
