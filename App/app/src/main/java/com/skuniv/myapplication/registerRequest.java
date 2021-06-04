package com.skuniv.myapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class registerRequest extends StringRequest {

    //서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://.dothome.co.kr/Register.php";
    private Map<String, String> map;


    public registerRequest(String userID, String userPassword, String userName, String userEmail, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userPassword",userPassword);
        map.put("userName",userName);
        map.put("userEmail",userEmail);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
