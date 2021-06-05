package com.skuniv.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.skuniv.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewActivity extends AppCompatActivity {

    private EditText comment;
    private ImageButton comment_addbutton;
    private TextView titleView,writerView,dateView,contentView;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        //intent 받아오며 데이터에서 userID와 BBS_NO 키값 받아오기
        Intent intent = getIntent();
        final String userID = intent.getExtras().getString("userID");
        final String BBS_s = intent.getExtras().getString("BBS_NO");
        int BBS_NO = Integer.parseInt(BBS_s);

        //게시물의 각 textview 키 값 배정
        titleView = (TextView)findViewById(R.id.titleview);
        writerView = (TextView)findViewById(R.id.writerview);
        dateView = (TextView)findViewById(R.id.dateview);
        contentView = (TextView)findViewById(R.id.contentview);

        //서버로부터 응답 받고, json으로부터 데이터 가져오기
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if(success) {
                        String TITLE = jsonObject.getString("TITLE");
                        String userID = jsonObject.getString("userID");
                        String CONTENT = jsonObject.getString("CONTENT");
                        String REG_DATE = jsonObject.getString("REG_DATE");

                        titleView.setText(TITLE);
                        writerView.setText(userID);
                        contentView.setText(CONTENT);
                        dateView.setText(REG_DATE);

                    }
                    else {
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //서버로부터 요청
        viewRequest viewRequest = new viewRequest(BBS_NO,responseListener);
        RequestQueue queue = Volley.newRequestQueue(com.skuniv.myapplication.ViewActivity.this);
        queue.add(viewRequest);



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
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                // 작성 성공
                                Toast.makeText(getApplicationContext(), "작성되었습니다!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(com.skuniv.myapplication.ViewActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // 작성 실패
                                Toast.makeText(getApplicationContext(), "작성이 실패되었습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

//                //서버로부터 요청
//                cviewRequest cviewRequest = new cviewRequest(userID, newcomment, responseListener);
//                RequestQueue queue = Volley.newRequestQueue(ViewActivity.this);
//                queue.add(cviewRequest);
            }
        });


        //comment text 클릭시 intent->CViewActivity로 화면전환
        TextView text2 = (TextView) findViewById(R.id.comments);
        text2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //text클릭시 수행
                //댓글 토스트 메세지
                //댓글 listview로 화면 전
                Toast.makeText(getApplicationContext(), "댓글", Toast.LENGTH_SHORT).show();
                Intent cintent = new Intent(com.skuniv.myapplication.ViewActivity.this, CViewActivity.class);
//                intent.putExtra("c_userID",c_userID);
                startActivity(cintent);
            }
        });
    }
}
