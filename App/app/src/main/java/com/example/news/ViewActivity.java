package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

public class ViewActivity extends AppCompatActivity {

    private EditText comment;
    private ImageButton comment_addbutton;
    private TextView titleView,writerView,dateView,contentView;
    private RequestQueue queue;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter_comment recyclerAdapter;
    private final List<Comment> comment_list = new ArrayList<Comment>();
    private Board select_board;
    private User user;

    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        //intent 받아오며 데이터에서 userID와 BBS_NO 키값 받아오기
        Intent intent = getIntent();
        select_board = (Board) intent.getSerializableExtra("select_board");
        user = (User) intent.getSerializableExtra("user");

        //게시물의 각 textview 키 값 배정
        titleView = (TextView)findViewById(R.id.titleview);
        writerView = (TextView)findViewById(R.id.writerview);
        dateView = (TextView)findViewById(R.id.dateview);
        contentView = (TextView)findViewById(R.id.contentview);

        titleView.setText(select_board.getName());
        writerView.setText(select_board.getWriter());
        dateView.setText(select_board.getRegistrationDate());
        contentView.setText(select_board.getContent());

        recyclerView = findViewById(R.id.comments_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        queue = Volley.newRequestQueue(this);


        //추천 이미지 버튼
        final ImageButton btn_like = (ImageButton) findViewById(R.id.btn_like);

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Toast.makeText(getApplicationContext(),count+" 좋아요", Toast.LENGTH_SHORT).show();
            }
        });

        //댓글창과 업로드 버튼 키 값 배정
        comment = (EditText)findViewById(R.id.comment);
        comment_addbutton = (ImageButton)findViewById(R.id.add_comment_button);

        //comment_addbutton 클릭 시 수행
        comment_addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newcomment = comment.getText().toString();

            }
        });
    }
    public void getComments(int number) throws JSONException {

        String url ="http://10.0.2.2:3000/index/comments"; //URL입력
        // Request a string response from the provided URL.
        JSONObject testjson = new JSONObject();
        testjson.put("post_number", number);
        String jsonString = testjson.toString();
        //response - 서버로 부터 받아오는 데이터
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson,
                new Response.Listener<JSONObject>() {
                    //response - 서버로 부터 받아오는 데이터
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //데이터를 json화
                            JSONObject jsonObject = response;
                            //데이터안에 배열을 가져옴
                            JSONArray arrayComments = jsonObject.getJSONArray("comments");
                            //배열안에 게시판을 하나씩 빼옴
                            //빼온 게시판을 Board Class에 대입 및 List에 삽입
                            for(int i=0;i<arrayComments.length();i++){
                                JSONObject obj = arrayComments.getJSONObject(i);
                                Comment comment = new Comment();
                                //게시판 number
                                comment.setUser_name(obj.getString("user_name"));
                                comment.setComments(obj.getString("real_comments"));
                                comment.setPost_number(obj.getInt("post_number"));
                                comment.setUser_id(obj.getString("user_id"));

                                comment_list.add(comment);
                            }
                            recyclerAdapter = new RecyclerViewAdapter_comment(comment_list);
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

    public void insertCommet(int number, String user_id,String user_name, String comment) throws JSONException {
        String url = "http://10.0.2.2:3000/index/comments/comment";
        JSONObject testjson = new JSONObject();
        testjson.put("post_number", number);
        testjson.put("user_name",user_name);
        testjson.put("user_id",user_id);
        testjson.put("comment",comment);
        String jsonString = testjson.toString(); //완성된 json 포맷

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson,
                new Response.Listener<JSONObject>() {
                    //response - 서버로 부터 받아오는 데이터
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response.toString());
                            String res = jsonObject.getString("response");
                            if(res.equals("OK")){
                                Toast.makeText(getApplicationContext(), "댓글 작성 성공", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "댓글 작성 실패", Toast.LENGTH_SHORT).show();

                            }

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
    }

    public void getNews(){

        String url ="http://10.0.2.2:3000/index/boards"; //URL입력

        // Request a string response from the provided URL.

        //response - 서버로 부터 받아오는 데이터
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    //response - 서버로 부터 받아오는 데이터
                    @Override
                    public void onResponse(String response) {
                        try {
                            //데이터를 json화
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray arrayArticles = jsonObject.getJSONArray("articles");


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


        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}