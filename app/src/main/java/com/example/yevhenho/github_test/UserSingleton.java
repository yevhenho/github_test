package com.example.yevhenho.github_test;

import android.content.Context;

import java.util.ArrayList;

public class UserSingleton {


    private ArrayList<User> mUsers;
    private static UserSingleton mUserSingleton;
    private Context mAppContext;
    private int previousTotalItemCount=100;


    private UserSingleton(Context appContext) {
        mAppContext = appContext;
        mUsers = new ArrayList<>();

    }

    public void addUser(User mUser) {
        mUsers.add(mUser);
    }

    public void deleteUser(User mUser) {
        mUsers.remove(mUser);
    }

    public int getPreviousTotalItemCount() {
        return previousTotalItemCount;
    }

    public void setPreviousTotalItemCount(int previousTotalItemCount) {
        this.previousTotalItemCount = previousTotalItemCount;
    }

    public static UserSingleton get(Context c) {
        if (mUserSingleton == null) {
            mUserSingleton = new UserSingleton(c.getApplicationContext());

        }
        return mUserSingleton;
    }

    public User getUser(String login) {
        for (User User : mUsers) {
            if (User.getLogin().equals(login)) {
                return User;
            }
        }
        return null;
    }

    public ArrayList<User> getUsers() {
        return mUsers;
    }
}



