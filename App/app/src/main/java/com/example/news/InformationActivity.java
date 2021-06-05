<<<<<<< HEAD:App/app/src/main/java/com/example/news/InformationActivity.java
package com.skuniv.myapplication;
=======

package com.example.news;
>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259:App/app/src/main/java/com/skuniv/myapplication/InformationActivity.java

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

<<<<<<< HEAD:App/app/src/main/java/com/example/news/InformationActivity.java
import com.skuniv.myapplication.R;

=======
>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259:App/app/src/main/java/com/skuniv/myapplication/InformationActivity.java
public class InformationActivity extends AppCompatActivity {

    private TextView tv_id,tv_pass,tv_name,tv_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);

        tv_id = (TextView)findViewById(R.id.tv_id);
        tv_pass = (TextView)findViewById(R.id.tv_pass);
        tv_name = (TextView)findViewById(R.id.tv_name);
        tv_email = (TextView)findViewById(R.id.tv_email);

        Intent intent = getIntent();
        final String userID = intent.getStringExtra("userID");
        final String userPass = intent.getStringExtra("userPass");
        final String userName = intent.getStringExtra("userName");
        final String userEmail = intent.getStringExtra("userEmail");

        tv_id.setText(userID);
        tv_pass.setText(userPass);
        tv_name.setText(userName);
        tv_email.setText(userEmail);
    }
}
<<<<<<< HEAD:App/app/src/main/java/com/example/news/InformationActivity.java
=======

>>>>>>> f38f4da00ce10501ceb91d688966cf4832b6c259:App/app/src/main/java/com/skuniv/myapplication/InformationActivity.java
