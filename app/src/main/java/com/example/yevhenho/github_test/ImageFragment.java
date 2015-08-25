package com.example.yevhenho.github_test;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;


public class ImageFragment extends Fragment {
    ImageView imageView;
    private DisplayImageOptions options;

    public static ImageFragment newInstance(String url) {
        ImageFragment f = new ImageFragment();
        Bundle args = new Bundle();
        args.putString("url", url);
        f.setArguments(args);
        return f;
    }
    //get image's url
    private String getUrl() {
        return getArguments().getString("url");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        imageView = (ImageView) view.findViewById(R.id.ImageView);
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.ic_menu_add)
                .showImageForEmptyUri(R.drawable.ic_menu_add)
                .showImageOnFail(R.drawable.ic_menu_add)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

        ImageLoader.getInstance().displayImage(getUrl(), imageView, options);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(getFragmentManager().findFragmentById(R.id.container)).commit();
            }
        });
    }
}
