package com.skuniv.myapplication;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class cviewRequest extends StringRequest {

    //서버 URL 설정 (PHP 파일 연동) DB테이블 미완성 phpmyadmin
    final static private String URL = "http://.dothome.co.kr/cpost.php";
    private Map<String, String> map;

    public cviewRequest(int c_BBS_NO, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("c_BBS_NO",c_BBS_NO + "");

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}
