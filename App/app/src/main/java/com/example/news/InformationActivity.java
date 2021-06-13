


package com.example.news;


import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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


public class InformationActivity extends AppCompatActivity {

    private TextView tv_id,tv_level,tv_name,tv_department;
    private User user;
    private int user_level;
    private TextView txt_articleCount, txt_recommendationCount, txt_commentCount;
    private RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        queue = Volley.newRequestQueue(this);

        tv_id = (TextView)findViewById(R.id.tv_id);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_department = (TextView)findViewById(R.id.tv_department);
        tv_level = (TextView)findViewById(R.id.tv_level);

        txt_articleCount = findViewById(R.id.txt_articleCount);
        txt_recommendationCount = findViewById(R.id.txt_recomendationCount);
        txt_commentCount = findViewById(R.id.txt_commentCount);

        final String user_ID = user.getId();
        final String user_name = user.getName();
        final String user_department = user.getMajor();
        user_level = user.getExp()/2 + 1;

        tv_id.setText(user_ID);
        tv_name.setText(user_name);
        tv_department.setText(user_department);
        tv_level.setText(String.valueOf(user_level));

        //기사작성, 댓글 작성 횟수를 받아오기 위한 코드
        try {
            getUserInfo(user.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        txt_recommendationCount.setText(String.valueOf(user.getExp()));

    }

    public void getUserInfo(String user_id) throws JSONException {

        String url ="http://10.0.2.2:3000/index/users/info"; //URL입력
        // Request a string response from the provided URL.
        JSONObject json = new JSONObject();
        json.put("user_id", user_id);
        String jsonString = json.toString();
        //response - 서버로 부터 받아오는 데이터
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,json,
                new Response.Listener<JSONObject>() {
                    //response - 서버로 부터 받아오는 데이터
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //데이터를 json화
                            JSONObject jsonObject = response;
                            int articleCount = jsonObject.getInt("articleCount");
                            int commentCount = jsonObject.getInt("commentCount");

                            txt_articleCount.setText(String.valueOf(articleCount));
                            txt_commentCount.setText(String.valueOf(commentCount));
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
}

