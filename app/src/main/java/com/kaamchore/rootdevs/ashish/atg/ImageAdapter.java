package com.kaamchore.rootdevs.ashish.atg;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {
    private List<Photo> il;
    private static Context context;

    public ImageAdapter(List<Photo> il) {
        this.il = il;
    }

    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder viewHolder, int i) {

        Glide.with(context).load(il.get(i).getUrlS()).override(1000,200).into(viewHolder.iv);
        viewHolder.id.setText(il.get(i).getId());


    }

    @Override
    public int getItemCount() {
        return il.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView id;

        public ViewHolder(View view) {
            super(view);

            iv = (ImageView) view.findViewById(R.id.iv);
            id = (TextView)view.findViewById(R.id.imageid);
            context = view.getContext();


        }
    }

}
