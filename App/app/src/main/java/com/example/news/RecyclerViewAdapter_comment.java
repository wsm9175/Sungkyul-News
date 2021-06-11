package com.example.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_comment extends RecyclerView.Adapter<RecyclerViewAdapter_comment.MyViewHolder> {
    List<Comment> comment_list = new ArrayList<Comment>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView txt_username;
        private TextView txt_comment;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_username = itemView.findViewById(R.id.txt_username);
            txt_comment =itemView.findViewById(R.id.txt_comment);
        }
    }

    public RecyclerViewAdapter_comment(List<Comment> comment) {
        comment_list = comment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewAdapter_comment.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View holderView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.comment, viewGroup, false);
        RecyclerViewAdapter_comment.MyViewHolder myViewHolder = new MyViewHolder(holderView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter_comment.MyViewHolder viewholder, int position) {
        Comment comment = comment_list.get(position);
        viewholder.txt_username.setText(comment.getUser_name());
        viewholder.txt_comment.setText(comment.getComments());
    }

    //데이터 세팅

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return comment_list.size();
    }
}
