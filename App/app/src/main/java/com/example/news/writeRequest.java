<<<<<<< HEAD:App/app/src/main/java/com/example/news/writeRequest.java
package com.skuniv.myapplication;
=======
package com.example.news;
>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259:App/app/src/main/java/com/skuniv/myapplication/writeRequest.java

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
<<<<<<< HEAD:App/app/src/main/java/com/example/news/writeRequest.java
=======

>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259:App/app/src/main/java/com/skuniv/myapplication/writeRequest.java
