package com.example.yevhenho.github_test;

public class User {
    private String mLogin;
    private String mLink;
    private String mUrl;
    private String mId;
    public User( String mLogin, String mLink, String mUrl,  String mId){
        this.mLogin=mLogin;
        this.mLink=mLink;
        this.mUrl=mUrl;
        this.mId=mId;

    }

    public String getId() {
        return mId;
    }

    public String getLink() {
        return mLink;
    }

    public String getLogin() {
        return mLogin;
    }

    public String getUrl() {
        return mUrl;
    }
}
