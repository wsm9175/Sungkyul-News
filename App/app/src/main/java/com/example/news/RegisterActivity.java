
package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
public class RegisterActivity extends AppCompatActivity {

    private EditText et_id,et_pass ,et_name ,et_email, et_phonenumber, et_schoolid, et_emailok;
    private Button btn_register, btn_overlap, btn_email, btn_emailok;
    private Spinner spinner_register_major;
    private String major;
    private int authNum;
    private boolean email_flag = false,overlap_flag=false;

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
        et_emailok=findViewById(R.id.et_emailok);
        btn_emailok=findViewById(R.id.btn_emailok);
        btn_email=findViewById(R.id.btn_email);
        et_phonenumber = findViewById(R.id.et_phonenumber);
        spinner_register_major = findViewById(R.id.spinner_register_major);
        et_schoolid = findViewById(R.id.et_schoolid);
        btn_register = findViewById(R.id.btn_register);
        btn_overlap = findViewById(R.id.btn_overlap);


        ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);

        spinner_register_major.setAdapter(sAdapter);
        spinner_register_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                major = sAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = et_email.getText().toString();
                if (email.contains("@sungkyul.ac.kr") == true) {
                    String url = "http://10.0.2.2:3000/index/mail";


                    //JSON형식으로 데이터 통신을 진행
                    JSONObject testjson = new JSONObject();
                    try {
                        //입력해둔 edittext의 id와 pw값을 받아와 put : 데이터를 json형식으로 바꿔 넣음.
                        testjson.put("email", email);


                        String jsonString = testjson.toString(); //완성된 json 포맷

                        final RequestQueue requestQueue = Volley.newRequestQueue(RegisterActivity.this);
                        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, testjson, new Response.Listener<JSONObject>() {

                            //데이터 전달을 끝내고 이제 그 응답을 받을 차례
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //받은 json형식의 응답을 받아
                                    JSONObject jsonObject = new JSONObject(response.toString());

                                    //key값에 따라 value값을 쪼개 받아옴.
                                    authNum = jsonObject.getInt("authNum");


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                            }
                            //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
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
                    Toast.makeText(getApplicationContext(), "이메일을 확인하세요", Toast.LENGTH_SHORT).show();

                }
                else{
                    Toast.makeText(getApplicationContext(), "성결대 메일로만 가입가능합니다.", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btn_emailok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num= Integer.parseInt(et_emailok.getText().toString());

                if(authNum==num){
                    email_flag=true;
                    Toast.makeText(getApplicationContext(), "이메일 인증 성공", Toast.LENGTH_SHORT).show();

                    if(email_flag==true && overlap_flag==true){
                        btn_register.setEnabled(true);
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), "이메일 인증 실패", Toast.LENGTH_SHORT).show();

                }
            }
        });

        // 회원가입 버튼 클릭시 수행
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_register.isEnabled()==false){
                    Toast.makeText(getApplicationContext(), "위를 먼저 작성해주세요", Toast.LENGTH_SHORT).show();

                }
                else {
                    String userID = et_id.getText().toString();
                    String userPass = et_pass.getText().toString();
                    String userName = et_name.getText().toString();
                    String userEmail = et_email.getText().toString();
                    String phonenumber = et_phonenumber.getText().toString();
                    String Schoolid = et_schoolid.getText().toString();


                    try {
                        requestSignup(userID, userPass, userName, userEmail, phonenumber, Schoolid, major);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void requestSignup(String ID, String PW, String Name, String Email, String Phondenumber, String Schoolid, String Major) throws JSONException {
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
                    final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, testjson, new Response.Listener<JSONObject>() {

                        //데이터 전달을 끝내고 이제 그 응답을 받을 차례
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                //받은 json형식의 응답을 받아
                                JSONObject jsonObject = response;
                                //key값에 따라 value값을 쪼개 받아옴.
                                String res = jsonObject.getString("response");
                                if (res.equals("OK")) {
                                    Toast.makeText(getApplicationContext(), "회원가입 성공", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    easyToast("회원가입 실패");
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        }
                        //서버로 데이터 전달 및 응답 받기에 실패한 경우 아래 코드가 실행됩니다.
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

            void easyToast(String str) {
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }

            ;


        });



        btn_overlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userID = et_id.getText().toString();

                String url ="http://10.0.2.2:3000/index/users/IDcheck"; //URL입력
                // Request a string response from the provided URL.
                JSONObject testjson = new JSONObject();
                try {
                    testjson.put("id", userID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String jsonString = testjson.toString();
                //response - 서버로 부터 받아오는 데이터
                final RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson,
                        new Response.Listener<JSONObject>() {
                            //response - 서버로 부터 받아오는 데이터
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    //데이터를 json화
                                    JSONObject jsonObject = response;
                                    String res = jsonObject.getString("res");
                                    if(res.equals("Retry")){
                                        et_id.setText("");
                                    }
                                    else if(res.equals("OK")){
                                        Toast.makeText(getApplicationContext(), "사용가능한아이디", Toast.LENGTH_SHORT).show();
                                        overlap_flag=true;

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
                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                // Add the request to the RequestQueue.
                queue.add(jsonObjectRequest);


            }
        });

    }
    }