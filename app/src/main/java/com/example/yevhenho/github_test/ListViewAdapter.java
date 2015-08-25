package com.example.yevhenho.github_test;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class ListViewAdapter extends ArrayAdapter<User> {

    private Context mContext;
    private int layoutResourceId;
    private ArrayList<User> userList;
    private DisplayImageOptions options;


    public ListViewAdapter(Context mContext, int layoutResourceId, ArrayList<User> userList,
                           DisplayImageOptions options) {
        super(mContext, layoutResourceId, userList);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.userList = userList;
        this.options = options;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;

        if (row == null) {

            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.textView_Link = (TextView) row.findViewById(R.id.linkTextView);
            holder.textView_Login = (TextView) row.findViewById(R.id.loginTextView);
            holder.textView_Id = (TextView) row.findViewById(R.id.idTextView);
            holder.imageView = (ImageView) row.findViewById(R.id.littleImageView);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        User item = userList.get(position);
        holder.textView_Link.setText(item.getLink());
        holder.textView_Login.setText(item.getLogin());
        holder.textView_Id.setText(item.getId());
        holder.imageView.setContentDescription(item.getUrl());
        ImageLoader.getInstance().displayImage(item.getUrl(), holder.imageView, options);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageFragment imageFragment = ImageFragment.newInstance(String.valueOf(v.getContentDescription()));
                ((Activity) mContext).getFragmentManager().beginTransaction().add(R.id.container, imageFragment).commit();
            }
        });
        return row;
    }

    static class ViewHolder {
        TextView textView_Link;
        TextView textView_Login;
        TextView textView_Id;
        ImageView imageView;
    }
}
