package com.example.news;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter_board extends RecyclerView.Adapter<RecyclerViewAdapter_board.MyViewHolder>{
    ArrayList<Board> board_list = new ArrayList<Board>();

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_board;
        private TextView txt_boardName;
        private TextView txt_boardContents;
        private TextView txt_recommendationNum;
        private TextView txt_commentsNum;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                img_board = itemView.findViewById(R.id.img_board);
                txt_boardName = itemView.findViewById(R.id.txt_boardName);
                txt_boardContents = itemView.findViewById(R.id.txt_boardContents);
                txt_recommendationNum = itemView.findViewById(R.id.txt_recomendationNum);
                txt_commentsNum = itemView.findViewById(R.id.txt_commentsNum);
            }
        }

    public RecyclerViewAdapter_board(ArrayList<Board> board) {
        board_list = board;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        LinearLayout v = (LinearLayout) LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.board, viewGroup, false);

        return new MyViewHolder(v);
    }
    //데이터 세팅
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        //이름 세팅
        Board board = board_list.get(position);
        viewHolder.txt_boardName.setText(board.getName());
        viewHolder.txt_boardContents.setText(board.getContent());
        viewHolder.txt_commentsNum.setText(String.valueOf(board.getComment()));
        viewHolder.txt_recommendationNum.setText(String.valueOf(board.getRecommendation()));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return board_list.size();
    }

}
