package com.test.myapplication.rest.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Surendra Singh on 2/26/2020.
 * Created for Pitney Bowes
 */
public class RepositoryResponse implements Parcelable {

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

    public static class BuiltByBean implements Parcelable {
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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.username);
            dest.writeString(this.href);
            dest.writeString(this.avatar);
        }

        public BuiltByBean() {
        }

        protected BuiltByBean(Parcel in) {
            this.username = in.readString();
            this.href = in.readString();
            this.avatar = in.readString();
        }

        public static final Parcelable.Creator<BuiltByBean> CREATOR = new Parcelable.Creator<BuiltByBean>() {
            @Override
            public BuiltByBean createFromParcel(Parcel source) {
                return new BuiltByBean(source);
            }

            @Override
            public BuiltByBean[] newArray(int size) {
                return new BuiltByBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.author);
        dest.writeString(this.name);
        dest.writeString(this.avatar);
        dest.writeString(this.url);
        dest.writeString(this.description);
        dest.writeInt(this.stars);
        dest.writeInt(this.forks);
        dest.writeInt(this.currentPeriodStars);
        dest.writeString(this.language);
        dest.writeString(this.languageColor);
        dest.writeTypedList(this.builtBy);
    }

    public RepositoryResponse() {
    }

    protected RepositoryResponse(Parcel in) {
        this.author = in.readString();
        this.name = in.readString();
        this.avatar = in.readString();
        this.url = in.readString();
        this.description = in.readString();
        this.stars = in.readInt();
        this.forks = in.readInt();
        this.currentPeriodStars = in.readInt();
        this.language = in.readString();
        this.languageColor = in.readString();
        this.builtBy = in.createTypedArrayList(BuiltByBean.CREATOR);
    }

    public static final Parcelable.Creator<RepositoryResponse> CREATOR = new Parcelable.Creator<RepositoryResponse>() {
        @Override
        public RepositoryResponse createFromParcel(Parcel source) {
            return new RepositoryResponse(source);
        }

        @Override
        public RepositoryResponse[] newArray(int size) {
            return new RepositoryResponse[size];
        }
    };
}
