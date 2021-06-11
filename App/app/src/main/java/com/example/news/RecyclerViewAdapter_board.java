package com.example.news;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter_board extends RecyclerView.Adapter<RecyclerViewAdapter_board.MyViewHolder>{
    List<Board> board_list = new ArrayList<Board>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
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

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = getAdapterPosition();
                        Board select_board = board_list.get(pos);

                        v.setBackgroundColor(Color.BLUE);

                        Intent intent = new Intent(v.getContext(), ViewActivity.class);
                        intent.putExtra("select_board", select_board);
                        v.getContext().startActivity(intent);
                    }
                });
            }


        }
    public RecyclerViewAdapter_board(List<Board> board) {
        board_list = board;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View holderView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.board, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(holderView);
        return myViewHolder;
    }
    //데이터 세팅
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {
        //이름 세팅
        System.out.println(board_list.size());
        Board board = board_list.get(position);
        viewHolder.txt_boardName.setText(board.getName());
        viewHolder.txt_boardContents.setText(board.getContent());
        viewHolder.txt_commentsNum.setText("댓글수 : "+String.valueOf(board.getComment()));
        viewHolder.txt_recommendationNum.setText("추천수 : "+String.valueOf(board.getRecommendation()));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return board_list.size();
    }

}
