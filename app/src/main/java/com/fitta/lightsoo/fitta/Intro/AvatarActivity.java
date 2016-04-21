package com.fitta.lightsoo.fitta.Intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.fitta.lightsoo.fitta.MainActivity;
import com.fitta.lightsoo.fitta.R;


//만들어진 아바타를  출력해줘
public class AvatarActivity extends AppCompatActivity {
    private Button btn_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar);
        btn_start = (Button)findViewById(R.id.btn_start);
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AvatarActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}
