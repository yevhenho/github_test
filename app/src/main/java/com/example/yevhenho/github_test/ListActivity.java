package com.example.yevhenho.github_test;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

public class ListActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_activity);
    }
    @Override
    public void onBackPressed() {
        //when image 400x400 is on screen
        if(getFragmentManager().findFragmentById(R.id.container)!=null){
           getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.container)).commit();
        }else{
            super.onBackPressed();
        }
    }
}

