package com.softricsolutions.videoplayapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.softricsolutions.videoplayapplication.R;
import com.softricsolutions.videoplayapplication.model.Videos;

import java.util.ArrayList;

/**
 * Created by jyoti on 08-Aug-17.
 */

public class Videoviewadapter extends RecyclerView.Adapter<Videoviewadapter.MyViewHolder> {

    public ArrayList<Videos> arrayList;
    Context context;
    Videos videos;

    public Videoviewadapter(Context context,ArrayList<Videos> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title,url;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txttitle);
            url = (TextView) view.findViewById(R.id.txturl);


        }
    }
    @Override
    public Videoviewadapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.videolist, parent, false);
        return new Videoviewadapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Videoviewadapter.MyViewHolder holder, int position) {


        videos=arrayList.get(position);
        holder.title.setText(arrayList.get(position).getTitle());
        holder.url.setText(arrayList.get(position).getVideo_url());

    }

    @Override
    public int getItemCount() {
        Log.e("SIZE",""+arrayList.size());
        return arrayList.size();
    }
}
