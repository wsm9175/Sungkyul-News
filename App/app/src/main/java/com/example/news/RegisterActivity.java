package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
public class RegisterActivity extends AppCompatActivity {

    private EditText et_id,et_pass ,et_name ,et_email,et_phonenumber,et_schoolid,et_major;
    private Button btn_register, btn_overlap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar2 = getSupportActionBar();
        actionBar2.setDisplayHomeAsUpEnabled(true);

        // 버튼과 EditText의 키 값 배정
        et_major=findViewById(R.id.et_major);
        et_schoolid=findViewById(R.id.et_schoolid);
        et_phonenumber=findViewById(R.id.et_phonenumber);
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
                String userPhonenumber=et_phonenumber.getText().toString();
                String userSchoolid=et_schoolid.getText().toString();
                String userMajor = et_major.getText().toString();


                requestSignup(userID,userPass,userName,userEmail,userPhonenumber,userSchoolid,userMajor);


            }
            public void requestSignup(String ID, String PW, String Name, String Email, String Phondenumber, String Schoolid, String Major){
                String url = "http://10.0.2.2:3000/index/users";

                //JSON형식으로 데이터 통신을 진행
                JSONObject testjson = new JSONObject();
                try {
                    //입력해둔 edittext의 id와 pw값을 받아와 put : 데이터를 json형식으로 바꿔 넣음.
                    testjson.put("id", ID);
                    testjson.put("password", PW);
                    testjson.put("name", Name);
                    testjson.put("email", Email);
                    testjson.put("phonenumber", Phondenumber);
                    testjson.put("schoolid", Schoolid);
                    testjson.put("major", Major);


                    String jsonString = testjson.toString(); //완성된 json 포맷

                    final RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                    final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                        //데이터 전달을 끝내고 이제 그 응답을 받을 차례
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                //받은 json형식의 응답을 받아
                                JSONObject jsonObject = new JSONObject(response.toString());

                                //key값에 따라 value값을 쪼개 받아옴.
                                String resultId = jsonObject.getString("approve_id");
                                String resultPassword = jsonObject.getString("approve_pw");

                                if(resultId.equals("OK") & resultPassword.equals("OK")){
                                    Toast.makeText(getApplicationContext(),"로그인 성공",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }else{
                                    easyToast("로그인 실패");
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행.
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(jsonObjectRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            void easyToast(String str){
                Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
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
