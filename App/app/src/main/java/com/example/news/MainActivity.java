
package com.example.news;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private long time= 0;
    private TextView tv_id;
    private DrawerLayout drawerLayout;
    private View drawerView;
    private Spinner spinner;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter_board recyclerAdapter;
    private RequestQueue queue;
    private ImageButton btn_write;
    private final List<Board> board_list = new ArrayList<Board>();
    private final List<Board> board_list_comment = new ArrayList<>();
    private final List<Board> board_list_recommendation = new ArrayList<>();

    //게시판을 담아놓기 위한 배열 선언
    Board[] board_comment;
    Board[] board_recommendation;

    //새로고침 구현
    private void refreshListView(){
        getNews();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //actionbar에 logo 및 ui개선
        ActionBar ab = getSupportActionBar();
        ab.setLogo(R.drawable.ii);
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        //회원 정보를 보여주게끔 구현
        tv_id = findViewById(R.id.tv_id);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawerView = (View)findViewById(R.id.drawer);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String userPass = intent.getStringExtra("userPass");
        final String userName = intent.getStringExtra("user_name");
        final String userEmail = intent.getStringExtra("user_email");

        //회원 이름을 text에
        tv_id.setText(userName);

        //회원 정보 창을 여는 버튼 구현
        Button btn_infor = (Button)findViewById(R.id.btn_infor);
        btn_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.news.MainActivity.this, com.example.news.InformationActivity.class);
                intent.putExtra("userID",userID);
                intent.putExtra("userPass",userPass);
                intent.putExtra("userName",userName);
                intent.putExtra("userEmail",userEmail);
                startActivity(intent);
            }
        });

        //메뉴창 슬라이드로 열리게끔 구현
        drawerLayout.setDrawerListener(listener);
        drawerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        //메뉴창 닫기 구현
        ImageButton close = (ImageButton)findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() { //메뉴 닫기 버튼 누를 때 닫기
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        //기사 목록 받아오기
        recyclerView = findViewById(R.id.articles_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        queue = Volley.newRequestQueue(this);

        getNews();

        //등록순, 댓글순, 추천순 정렬
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.my_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sort_Adapter(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


       //작성 버튼 선언, xml에서 가져오기
        btn_write = (ImageButton) findViewById(R.id.btn_write);
        btn_write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //버튼을 눌렀을 때 화면 전환 -> 작성페이지로
                //fill blank -> Activity name.class
                Intent intent = new Intent(com.example.news.MainActivity.this, com.example.news.WriteActivity.class);
                intent.putExtra("userID",userID);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"새 게시글 작성", Toast.LENGTH_SHORT).show();
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
                                board_list.add(board);
                            }
                            recyclerAdapter = new RecyclerViewAdapter_board(board_list);
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


        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    //어댑터 정렬을 위한 메소드
    public void sort_Adapter(int position){

        switch (position){
            case 0:
                //등록일자 기준 정렬
                Toast.makeText(this, "등록순 정렬", Toast.LENGTH_SHORT).show();
                sort_view();
                break;
            case 1:
                //댓글수 기준 정렬
                Toast.makeText(this, "댓글순 정렬", Toast.LENGTH_SHORT).show();
                sort_comment();
                break;
            case 2:
                //추천수 기준 정렬
                Toast.makeText(this, "추천순 정렬", Toast.LENGTH_SHORT).show();
                sort_recommend();
                break;
        }
    }

    public void sort_view(){
        recyclerAdapter = new RecyclerViewAdapter_board(board_list);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void sort_comment(){
        if(board_list_comment.size() == 0){
            board_comment = new Board[board_list.size()];
            //정렬을 위해 기존 어댑터의 data 리스트에 들어있는 게시판 객체를 배열로 옮김
            for(int i=0;i<board_list.size();i++){
                board_comment[i] = board_list.get(i);
            }
            //board_comment를 정렬
//          quick 정렬
            quickSort_comment(board_comment, 0, board_comment.length-1);
            board_list_comment.addAll(Arrays.asList(board_comment));
        }
        recyclerAdapter = new RecyclerViewAdapter_board(board_list_comment);
        recyclerView.setAdapter(recyclerAdapter);
    }

    public void sort_recommend(){
        if(board_list_recommendation.size() == 0){
            board_recommendation = new Board[board_list.size()];
            //정렬을 위해 기존 어댑터의 data 리스트에 들어있는 게시판 객체를 배열로 옮김
            for(int i=0;i<board_list.size();i++){
                board_recommendation[i] = board_list.get(i);
            }
            //quick 정렬
            quickSort_recommendaton(board_recommendation, 0, board_recommendation.length-1);

            board_list_recommendation.addAll(Arrays.asList(board_recommendation));
        }
        recyclerAdapter = new RecyclerViewAdapter_board(board_list_recommendation);
        recyclerView.setAdapter(recyclerAdapter);
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

    public static void quickSort_recommendaton(Board[] board_list,int left, int right){
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

        if (left < pr) quickSort_recommendaton(board_list, left, pr);
        if (pl <right) quickSort_recommendaton(board_list, pl, right);
    }

    public static void swap(Board[] board_list, int idx1, int idx2) {
        Board board = board_list[idx1];
        board_list[idx1] = board_list[idx2];
        board_list[idx2] = board;
    }


    //메뉴창
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {

        }

        @Override
        public void onDrawerStateChanged(int newState) {

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_btn1:
                drawerLayout.openDrawer(drawerView);
                return true;
            case R.id.refresh:
                refreshListView();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //뒤로가기 버튼을 두번누르면 종료
    @Override
    public void onBackPressed(){
        if (System.currentTimeMillis() - time >= 2000) {
            time = System.currentTimeMillis();
            Toast.makeText(getApplicationContext(), "뒤로가기 버튼을 한번 더 누르면 종료합니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() - time < 2000) {
            finish();
        }
    }

}

