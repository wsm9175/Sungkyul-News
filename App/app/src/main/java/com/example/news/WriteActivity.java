package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WriteActivity extends AppCompatActivity {

    private EditText title, content;
    private ImageButton addbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        Intent intent = getIntent();
        final String userID = intent.getExtras().getString("userID");

        //버튼과 EditText의 키 값 배정
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        addbutton = findViewById(R.id.add_button);

        //add버튼 클릭 시 수행
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //EditText에 현재 입력되어 있는 값을 get해옴.
                String newtitle = title.getText().toString();
                String newcontent = content.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                // 작성 성공
                                Toast.makeText(getApplicationContext(), "작성되었습니다!", Toast.LENGTH_SHORT).show();
                                MainActivity.Getlistdata getlistdata = new MainActivity.Getlistdata();
                                getlistdata.execute();
                                finish();
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

                //서버로 요청
                com.example.news.writeRequest writeRequest = new writeRequest(userID, newtitle, newcontent, responseListener);
                RequestQueue queue = Volley.newRequestQueue(com.example.news.WriteActivity.this);
                queue.add(writeRequest);
            }
        });
    }
}