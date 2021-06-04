package com.skuniv.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.toolbox.Volley;
//import com.skuniv.myapplication.MainActivity;
import com.example.news.R;
//import com.skuniv.myapplication.RegisterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

                Intent intent = new Intent(com.skuniv.myapplication.LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = et_id.getText().toString();
                String userPass = et_pass.getText().toString();

//                Response.Listener<String> responseListener = new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            JSONObject jsonObject = new JSONObject(response);
//                            boolean success = jsonObject.getBoolean("success");
//                            if(success) {
//                                // 로그인 성공
//                                String userID = jsonObject.getString("userID");
//                                String userPass = jsonObject.getString("userPassword");
//                                String userName = jsonObject.getString("userName");
//                                String userEmail = jsonObject.getString("userEmail");
//                                Toast.makeText(getApplicationContext(),"로그인이 성공적으로 이뤄졌습니다!", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(com.example.news.LoginActivity.this, MainActivity.class);
//                                intent.putExtra("userID",userID);
//                                intent.putExtra("userPass",userPass);
//                                intent.putExtra("userName",userName);
//                                intent.putExtra("userEmail",userEmail);
//                                startActivity(intent);
//                            }
//                            else {
//                                // 로그인 실패
//                                Toast.makeText(getApplicationContext(),"로그인에 실패하셨습니다.", Toast.LENGTH_SHORT).show();
//                                return;
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                };
//                loginRequest loginRequest = new loginRequest(userID,userPass,responseListener);
//                RequestQueue queue = Volley.newRequestQueue(com.example.news.LoginActivity.this);
//                queue.add(loginRequest);
            }
        });
    }

    public class JSONTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            JSONObject jsonObject =new JSONObject();
            try {
                jsonObject.accumulate("user_id",et_id.getText().toString());
                jsonObject.accumulate("user_password",et_pass.getText().toString());

                HttpURLConnection con=null;
                BufferedReader reader = null;

                try{
                    URL url = new URL(strings[0]);
                    con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST"); //POST방식으로 보냄
                    con.setRequestProperty("Cache-Control","no-cache");//캐시 설정
                    con.setRequestProperty("Content-Type","application/json"); //json 형식으로 전송
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.connect();

                    OutputStream outStream = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();

                    InputStream stream = con.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(stream));

                    StringBuffer buffer = new StringBuffer();

                    String line="";
                    while((line = reader.readLine())!=null){
                        buffer.append(line);
                    }
                    return buffer.toString();// 서버로부터 받은 값을 리턴

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    if(con != null){
                        con.disconnect();
                    }try{
                        if(reader!=null){
                            reader.close();//버퍼를 닫아줌
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
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
