<<<<<<< HEAD
package com.skuniv.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.skuniv.myapplication.R;
=======
package com.example.news;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259

import java.util.ArrayList;
import java.util.List;

public class CViewActivity extends AppCompatActivity {
<<<<<<< HEAD

    //받아올 data(사용자가 쓴 글)
    List<String> cdata = new ArrayList<>();
=======
    //기사 목록을 담아 놓는 리스트
    private RecyclerView recyclerView;
    private RecyclerViewAdapter_board recyclerAdapter;
    private RequestQueue queue;
>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cview);

<<<<<<< HEAD
        //intent 받아오기
        Intent cintent = getIntent();
        //xml에서 담아온 listview 정의
        final ListView listView = (ListView) findViewById(R.id.comment_list);
        //adapter 선언: 리스트 방식과 cdata의 정보를 adapter에
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cdata);
        //listview와 adapter를 연결
        listView.setAdapter(adapter);

        //listview 테스트
        cdata.add("comm1 - Paul - 2020/6/6");
        cdata.add("comm2 - Mike - 2020/6/7");
        cdata.add("comm3 - Sarah - 2020/6/8");
        cdata.add("comm4 - alpha - 2020/6/9");
        cdata.add("comm5 - bravo - 2020/6/9");
        cdata.add("comm6 - charlie - 2020/6/9");
        cdata.add("comm7 - delta - 2020/6/20");
        cdata.add("comm8 - echo - 2020/6/20");
        cdata.add("comm9 - foxtrot - 2020/6/20");
        cdata.add("comm10 - golf - 2020/6/20");
        cdata.add("comm11 - hotel - 2020/6/20");
        cdata.add("comm12 - india - 2020/6/20");
        cdata.add("comm13 - juliet - 2020/6/20");
        cdata.add("comm14 - kilo - 2020/6/20");
        cdata.add("comm15 - lima - 2020/6/20");

        //상태 저장
        adapter.notifyDataSetChanged();
    }
}
=======
        recyclerView = findViewById(R.id.articles_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        Volley.newRequestQueue(this);
        getNews();
    }
    public void getNews(){

        String url ="http://www.google.com"; //URL입력

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    //response - 서버로 부터 받아오는 데이터
                    @Override
                    public void onResponse(String response) {

                        try {
                            //데이터를 json화
                            JSONObject jsonObject = new JSONObject(response);
                            //데이터안에 배열을 가져옴
                            JSONArray arrayArticles = jsonObject.getJSONArray("article");
                            //배열안에 게시판을 하나씩 빼옴
                            ArrayList<Board> board_list = new ArrayList<Board>();
                            for(int i=0, j=arrayArticles.length();i<j;i++){
                                JSONObject obj = arrayArticles.getJSONObject(i);
                                //게시판 number
                                //obj.getString();
                                //게시판 name
                                //게시판 content
                                //게시판 registrationDate;
                                //게시판 View
                                //게시판 Comment
                                //게시판 recommendation
                                //게시판 writer;
                                //게시판 board_id;

                                Board board = new Board();
                                //위에 가져온 내용을 board에 setting
                                //board.setNumber();

                                board_list.add(board);
                            }
                            //response ->> Board Class 분류
                            recyclerAdapter = new RecyclerViewAdapter_board(board_list);
                            recyclerView.setAdapter(recyclerAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "통신오류", Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}



>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259
