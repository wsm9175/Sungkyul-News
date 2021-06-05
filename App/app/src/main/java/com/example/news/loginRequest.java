<<<<<<< HEAD:App/app/src/main/java/com/example/news/loginRequest.java
package com.skuniv.myapplication;
=======
package com.example.news;
>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259:App/app/src/main/java/com/skuniv/myapplication/loginRequest.java

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class loginRequest extends StringRequest {

<<<<<<< HEAD:App/app/src/main/java/com/example/news/loginRequest.java
    //서버 URL 설정 (PHP 파일 연동) 미완성
=======
    //서버 URL 설정 (노드JS 서버 주소 연동) 미완성
>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259:App/app/src/main/java/com/skuniv/myapplication/loginRequest.java
    final static private String URL = "http://.dothome.co.kr/Login.php";
    private Map<String, String> map;


    public loginRequest(String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
<<<<<<< HEAD:App/app/src/main/java/com/example/news/loginRequest.java

        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userPassword",userPassword);

=======
        map = new HashMap<>();
        map.put("userID",userID);
        map.put("userPassword",userPassword);
>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259:App/app/src/main/java/com/skuniv/myapplication/loginRequest.java
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

<<<<<<< HEAD:App/app/src/main/java/com/example/news/loginRequest.java
}
=======
}
>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259:App/app/src/main/java/com/skuniv/myapplication/loginRequest.java
