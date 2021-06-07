package com.example.news;

public class AlgorithmCheck{

    public static void mergeSort_comment(int start, int end, Board[] board_list, Board[] tmp){
        if(start < end){
            int mid = (start + end) / 2;
            mergeSort_comment(start, mid, board_list, tmp);
            mergeSort_comment(mid+1, end, board_list, tmp);

            int p = start;
            int q = mid+1;
            int idx = p;
            while(p<=mid || q<=end){
                if(q>end || (p<=mid && board_list[p].getComment() > board_list[q].getComment())){
                    tmp[idx++] = board_list[p++];
                }else{
                    tmp[idx++] = board_list[q++];
                }
            }
            for(int i=start;i<=end;i++){
                board_list[i] = tmp[i];
            }
        }
    }

    public static void mergeSort_recommendation(int start, int end, Board[] board_list, Board[] tmp){
        if(start < end){
            int mid = (start + end) / 2;
            mergeSort_recommendation(start, mid, board_list, tmp);
            mergeSort_recommendation(mid+1, end, board_list, tmp);

            int p = start;
            int q = mid+1;
            int idx = p;
            while(p<=mid || q<=end){
                if(q>end || (p<=mid && board_list[p].getRecommendation() > board_list[q].getRecommendation())){
                    tmp[idx++] = board_list[p++];
                }else{
                    tmp[idx++] = board_list[q++];
                }
            }
            for(int i=start;i<=end;i++){
                board_list[i] = tmp[i];
            }
        }
    }

    public static void quickSort_comment(Board[] board_list,int left, int right){
        int pl = left;
        int pr = right;
        int x = board_list[(pl + pr) / 2].getComment();
        do{
            while(board_list[pl].getComment() > x) pl++;
            while(board_list[pr].getComment() < x ) pr--;
            if(pl <= pr) {
                swap(board_list, pl++, pr--);
            }
        }while(pl <= pr);

        if (left < pr) quickSort_comment(board_list, left, pr);
        if (pl <right) quickSort_comment(board_list, pl, right);
    }

    public static void quickSort_recommendation(Board[] board_list,int left, int right){
        int pl = left;
        int pr = right;
        int x = board_list[(pl + pr) / 2].getRecommendation();
        do{
            while(board_list[pl].getRecommendation() > x) pl++;
            while(board_list[pr].getRecommendation() < x ) pr--;
            if(pl <= pr) {
                swap(board_list, pl++, pr--);
            }
        }while(pl <= pr);

        if (left < pr) quickSort_recommendation(board_list, left, pr);
        if (pl <right) quickSort_recommendation(board_list, pl, right);
    }

    public static void shellSort_comment(Board[] board_list){
        for(int h = board_list.length;h>0;h/=2){
            for(int i=h;i<board_list.length;i++){
                int j;
                Board tmp = board_list[i];
                for(j=i-h;j>=0 && board_list[j].getComment()<tmp.getComment();j-=h){
                    board_list[j+h] = board_list[j];
                }
                board_list[j+h] = tmp;
            }
        }
    }

    public static void shellSort_recommendation(Board[] board_list){
        for(int h = board_list.length;h>0;h/=2){
            for(int i=h;i<board_list.length;i++){
                int j;
                Board tmp = board_list[i];
                for(j=i-h;j>=0 && board_list[j].getRecommendation()<tmp.getRecommendation();j-=h){
                    board_list[j+h] = board_list[j];
                }
                board_list[j+h] = tmp;
            }
        }
    }

    public static void swap(Board[] board_list, int idx1, int idx2) {
        Board board = board_list[idx1];
        board_list[idx1] = board_list[idx2];
        board_list[idx2] = board;
    }


}