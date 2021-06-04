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

public class LoginActivity extends AppCompatActivity {

    private long time= 0;
    private EditText et_id,et_pass;
    private Button btn_login, btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar ab = getSupportActionBar();
        ab.setLogo(R.drawable.ii);
        ab.setDisplayUseLogoEnabled(true) ;
        ab.setDisplayShowHomeEnabled(true) ;

        et_id = findViewById(R.id.et_id);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() { //회원가입 버튼 클릭 시 수행
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.news.LoginActivity.this, com.example.news.RegisterActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

                //서버로 부터 응답왔을때 실행
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Json형식으로 데이터 통신을 진행.
                            JSONObject jsonObject = new JSONObject(response);
                            //server로 부터 받는 메시지가 success인지 판별
                            boolean success = jsonObject.getBoolean("success");
                            if(success) {
                                // 로그인 성공
                                String userID = jsonObject.getString("userID");
                                String userPass = jsonObject.getString("userPassword");
                                String userName = jsonObject.getString("userName");
                                String userEmail = jsonObject.getString("userEmail");
                                Toast.makeText(getApplicationContext(),"로그인이 성공적으로 이뤄졌습니다!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(com.example.news.LoginActivity.this, com.example.news.MainActivity.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("userPass",userPass);
                                intent.putExtra("userName",userName);
                                intent.putExtra("userEmail",userEmail);
                                startActivity(intent);
                            }
                            else {
                                // 로그인 실패
                                Toast.makeText(getApplicationContext(),"로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                com.example.news.loginRequest loginRequest = new loginRequest(userID,userPass,responseListener);
                RequestQueue queue = Volley.newRequestQueue(com.example.news.LoginActivity.this);
                queue.add(loginRequest);
            }
        });
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