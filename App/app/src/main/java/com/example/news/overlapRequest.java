<<<<<<< HEAD:App/app/src/main/java/com/example/news/overlapRequest.java
package com.skuniv.myapplication;
=======
package com.example.news;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259:App/app/src/main/java/com/skuniv/myapplication/overlapRequest.java

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class overlapRequest extends StringRequest {

    //서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://.dothome.co.kr/overlap.php";
    private Map<String, String> map;


    public overlapRequest(String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID",userID);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
