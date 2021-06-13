package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyBoard extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter_board recyclerViewAdapter_board;
    private RecyclerViewAdapter_board recyclerAdapter;
    private List<Board> board_list = new ArrayList<Board>();
    private User user;

    private RequestQueue queue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myboard);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");

        recyclerView = findViewById(R.id.myBoard_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        queue = Volley.newRequestQueue(this);

        try {
            getNews(user.getName());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void getNews(String user_id) throws JSONException {
        String url = "http://10.0.2.2:3000/index/boards/selectmyboard"; //URL입력
        JSONObject json = new JSONObject();
        json.put("user_id", user_id);
        String jsonString = json.toString();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,json,
                new Response.Listener<JSONObject>() {
                    //response - 서버로 부터 받아오는 데이터
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            //데이터안에 배열을 가져옴
                            JSONArray arrayArticles = jsonObject.getJSONArray("articles");
                            //배열안에 게시판을 하나씩 빼옴
                            //빼온 게시판을 Board Class에 대입 및 List에 삽입
                            for(int i=0;i<arrayArticles.length();i++){
                                JSONObject obj = arrayArticles.getJSONObject(i);
                                Board board = new Board();
                                //게시판 number
                                board.setNumber( obj.getInt("id"));
                                //게시판 name
                                board.setName( obj.getString("title"));
                                //게시판 content
                                board.setContent( obj.getString("contents"));
                                //게시판 registrationDate;
                                board.setRegistrationDate( obj.getString("date"));
                                //게시판 View
                                board.setView( obj.getInt("view"));
                                //게시판 Comment
                                board.setComment( obj.getInt("comment_number"));
                                //게시판 recommendation
                                board.setRecommendation( obj.getInt("recommends"));
                                //게시판 writer;
                                board.setWriter( obj.getString("user_id"));
                                //게시판 board_id;
                                board.setBoard_id( obj.getString("board_code"));
                                board.setWriter_name(obj.getString("user_name"));

                                board_list.add(board);
                            }
                            recyclerAdapter = new RecyclerViewAdapter_board(user, board_list);
                            recyclerView.setAdapter(recyclerAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "통신오류", Toast.LENGTH_SHORT);
            }

        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }
}
