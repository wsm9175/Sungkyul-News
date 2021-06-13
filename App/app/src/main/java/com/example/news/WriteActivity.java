package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.util.Date;

public class WriteActivity extends AppCompatActivity {

    private EditText title, content;
    private ImageButton addbutton;
    private Spinner spinner_major;
    private  String str;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);


        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        final String userID = user.getId();
        final String userName = user.getName();

        //버튼과 EditText의 키 값 배정
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        addbutton = findViewById(R.id.add_button);
        spinner_major=findViewById(R.id.spinner_major);

        ArrayAdapter<CharSequence> sAdapter = ArrayAdapter.createFromResource(this, R.array.major, android.R.layout.simple_spinner_dropdown_item);

        spinner_major.setAdapter(sAdapter);
        spinner_major.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str= sAdapter.getItem(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //add버튼 클릭 시 수행
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //EditText에 현재 입력되어 있는 값을 get해옴.
                String newtitle = title.getText().toString();
                String newcontent = content.getText().toString();
                long now = System.currentTimeMillis();
                Date date = new Date(now);
                try {
                    requestinsertboard(newtitle, newcontent, date.toString(), userID, str, userName);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        });
    }
    public void requestinsertboard(String title, String contents, String date, String user_id, String board_code, String userName) throws JSONException {
        String url = "http://10.0.2.2:3000/index/boards";

        //JSON형식으로 데이터 통신을 진행
        JSONObject testjson = new JSONObject();
        try {
            //입력해둔 edittext의 id와 pw값을 받아와 put : 데이터를 json형식으로 바꿔 넣음.
            testjson.put("title", title);
            testjson.put("contents", contents);
            testjson.put("date", date);
            testjson.put("user_id", user_id);
            testjson.put("board_code", board_code);
            testjson.put("user_name", userName);


            String jsonString = testjson.toString(); //완성된 json 포맷

            final RequestQueue requestQueue = Volley.newRequestQueue(WriteActivity.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,testjson, new Response.Listener<JSONObject>() {

                //데이터 전달을 끝내고 이제 그 응답을 받을 차례
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //받은 json형식의 응답을 받아
                        JSONObject jsonObject = new JSONObject(response.toString());

                        //key값에 따라 value값을 쪼개 받아옴.
                        String resultres = jsonObject.getString("response");

                        if (resultres.equals("OK")) {
                            Toast.makeText(getApplicationContext(), "게시글 업로드 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("user", user);
                            startActivity(intent);
                            finish();
                        } else {
                            easyToast("업로드 실패");
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

    void easyToast(String str){
        Toast.makeText(getApplicationContext(),str,Toast.LENGTH_SHORT).show();
    }
}