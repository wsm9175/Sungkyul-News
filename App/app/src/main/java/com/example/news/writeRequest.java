package com.example.news;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class writeRequest extends StringRequest {
    final static private String URL = "http://.dothome.co.kr/BBSWrite.php";
    private Map<String, String> map;

    public writeRequest(String ID, String TITLE, String CONTENT, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", ID);
        map.put("TITLE", TITLE);
        map.put("CONTENT", CONTENT);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
