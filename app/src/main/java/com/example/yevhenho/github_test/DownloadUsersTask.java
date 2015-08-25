package com.example.yevhenho.github_test;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public final class DownloadUsersTask extends AsyncTask<String, Void, Boolean> {


    private int perPage;
    private Context context;
    private ListViewAdapter adapter;


    public DownloadUsersTask(Context context, int perPage, ListViewAdapter adapter ) {
        this.context = context;
        this.adapter = adapter;
        this.perPage=perPage;
    }


    @Override
    protected Boolean doInBackground(String... arg0) {
        try {
            loadUsers(context, perPage);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        if(result){
        adapter.notifyDataSetChanged();}


    }

    void loadUsers(Context context, int perPage) {
        String result = "";
        int size =UserSingleton.get(context).getUsers().size();

        try {
                String webPage = (size!=0)?"https://api.github.com/users?since=" + UserSingleton.get(context).getUsers().get(size- 1).getId() + "&per_page=" + perPage
                        :"https://api.github.com/users?since=0" + "&per_page=" + perPage;
            //make coonection and get result
            URL url = new URL(webPage);
            URLConnection urlConnection = url.openConnection();
            InputStream is = urlConnection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            int numCharsRead;
            char[] charArray = new char[1024];
            StringBuffer sb = new StringBuffer();
            while ((numCharsRead = isr.read(charArray)) > 0) {
                sb.append(charArray, 0, numCharsRead);
            }
            result = sb.toString();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // parse JSON
        try {
            JSONArray array = new JSONArray(result);
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                User user = new User(object.getString("login"),
                        object.getString("html_url"),
                        object.getString("avatar_url"),
                        object.getString("id"));
                UserSingleton.get(context).addUser(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}