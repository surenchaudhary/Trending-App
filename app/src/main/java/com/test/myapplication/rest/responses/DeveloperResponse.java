package com.test.myapplication.rest.responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Surendra Singh on 2/26/2020.
 * Created for Pitney Bowes
 */
public class DeveloperResponse implements Parcelable {

    /**
     * username : brianc
     * name : Brian C
     * url : https://github.com/brianc
     * avatar : https://avatars1.githubusercontent.com/u/50081
     * repo : {"name":"node-postgres","description":"PostgreSQL client for node.js.","url":"https://github.com/brianc/node-postgres"}
     */

    @SerializedName("username")
    private String username;
    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("repo")
    private RepoBean repo;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public RepoBean getRepo() {
        return repo;
    }

    public void setRepo(RepoBean repo) {
        this.repo = repo;
    }

    public static class RepoBean implements Parcelable {
        /**
         * name : node-postgres
         * description : PostgreSQL client for node.js.
         * url : https://github.com/brianc/node-postgres
         */

        @SerializedName("name")
        private String name;
        @SerializedName("description")
        private String description;
        @SerializedName("url")
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeString(this.description);
            dest.writeString(this.url);
        }

        public RepoBean() {
        }

        protected RepoBean(Parcel in) {
            this.name = in.readString();
            this.description = in.readString();
            this.url = in.readString();
        }

        public static final Creator<RepoBean> CREATOR = new Creator<RepoBean>() {
            @Override
            public RepoBean createFromParcel(Parcel source) {
                return new RepoBean(source);
            }

            @Override
            public RepoBean[] newArray(int size) {
                return new RepoBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeString(this.avatar);
        dest.writeParcelable(this.repo, flags);
    }

    public DeveloperResponse() {
    }

    protected DeveloperResponse(Parcel in) {
        this.username = in.readString();
        this.name = in.readString();
        this.url = in.readString();
        this.avatar = in.readString();
        this.repo = in.readParcelable(RepoBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<DeveloperResponse> CREATOR = new Parcelable.Creator<DeveloperResponse>() {
        @Override
        public DeveloperResponse createFromParcel(Parcel source) {
            return new DeveloperResponse(source);
        }

        @Override
        public DeveloperResponse[] newArray(int size) {
            return new DeveloperResponse[size];
        }
    };
}
