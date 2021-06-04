
package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
public class RegisterActivity extends AppCompatActivity {

    private EditText et_id,et_pass ,et_name ,et_email;
    private Button btn_register, btn_overlap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar2 = getSupportActionBar();
        actionBar2.setDisplayHomeAsUpEnabled(true);

        // 버튼과 EditText의 키 값 배정
        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        btn_register = findViewById(R.id.btn_register);
        btn_overlap = findViewById(R.id.btn_overlap);

        // 회원가입 버튼 클릭시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //EditText에 현재 입력되어 있는 값을 get 해옴.
                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();
                String userName = et_name.getText().toString();
                String userEmail = et_email.getText().toString();


                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) {
                                // 회원가입 성공
                                Toast.makeText(getApplicationContext(),"회원 가입이 성공적으로 이뤄졌습니다!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(com.example.news.RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                // 회원가입 실패
                                Toast.makeText(getApplicationContext(),"회원 가입이 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };

                //서버로 요청
                registerRequest registerRequest = new registerRequest(userID,userPass,userName,userEmail,responseListener);
                RequestQueue queue = Volley.newRequestQueue(com.example.news.RegisterActivity.this);
                queue.add(registerRequest);

            }
        });

        btn_overlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = et_id.getText().toString();

                Response.Listener<String> responseListener2 = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if(success) {

                                Toast.makeText(getApplicationContext(),"중복된 아이디입니다.", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"사용할 수 있는아이디입니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                };

                //서버로 요청
                com.example.news.overlapRequest overlapRequest = new com.example.news.overlapRequest(userID,responseListener2);
                RequestQueue queue2 = Volley.newRequestQueue(com.example.news.RegisterActivity.this);
                queue2.add(overlapRequest);

            }
        });





    }
}

