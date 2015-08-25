package com.example.yevhenho.github_test;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.io.IOException;

public class ListFragment extends android.app.ListFragment {
    public static final int NEXT_ITEMS_COUNT = 30;
    public static final int PER_PAGE = 100;
    private DisplayImageOptions options;
    private ListViewAdapter adapter;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
        // imageloader's options
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_menu_add)
                .showImageForEmptyUri(R.drawable.ic_menu_add)
                .showImageOnFail(R.drawable.ic_menu_add)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        final ListView listView = getListView();

        //empty list
        if (listView.getCount() == 0) {
            View empty = getActivity().findViewById(R.id.emptyList);
            empty.setVisibility(View.VISIBLE);
            listView.setEmptyView(empty);
        }

        listView.setDividerHeight(4);
        listView.setSelected(false);
        //set adapter
        adapter = new ListViewAdapter(getActivity(), R.layout.list_item,
                UserSingleton.get(getActivity()).getUsers(), options);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            public void onScroll(AbsListView lw, final int firstVisibleItem,
                                 final int visibleItemCount, final int totalItemCount) {

                if ((firstVisibleItem + NEXT_ITEMS_COUNT > UserSingleton.get(getActivity()).getPreviousTotalItemCount())) {
                    // set new total count
                    UserSingleton.get(getActivity()).setPreviousTotalItemCount((UserSingleton.get(getActivity()).getPreviousTotalItemCount() + PER_PAGE));
                    //get new users
                    if (isOnline()) {
                        new DownloadUsersTask(getActivity(), PER_PAGE, adapter).execute();
                        Log.d(getActivity().getClass().getSimpleName(), String.valueOf(UserSingleton.get(getActivity()).getPreviousTotalItemCount()));
                    } else {
                        Toast.makeText(getActivity(), "Internet is not available!", Toast.LENGTH_LONG).show();
                    }
                }
                Log.d(getActivity().getClass().getSimpleName(), "firstVisibleItem" + firstVisibleItem +
                        "visibleItemCount" + visibleItemCount + "totalItemCount" + totalItemCount);
            }

            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }
        });

        getFirstUsers();
        setListAdapter(adapter);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.list_fragment, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_reload:
                    getFirstUsers();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void getFirstUsers() {
        // if (list is empty)
        if (UserSingleton.get(getActivity()).getUsers().size() == 0) {
            //check internet
            if (isOnline()) {
                new DownloadUsersTask(getActivity(), PER_PAGE, adapter).execute();
                Log.d(getActivity().getClass().getSimpleName(), "size()==0");
            } else {
                Toast.makeText(getActivity(), "Internet is not available!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean isOnline() {

        Runtime runtime = Runtime.getRuntime();
        try {

            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


}
