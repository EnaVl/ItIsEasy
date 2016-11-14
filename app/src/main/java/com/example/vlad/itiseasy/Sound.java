package com.example.vlad.itiseasy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Sound extends AppCompatActivity {

    Button btnKeyOk;
    RadioButton rb_sOff, rb_sOn;
    RadioGroup rbSound;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound);

        intent = new Intent();

        btnKeyOk = (Button) findViewById(R.id.btnKeyOk);
        rb_sOff = (RadioButton) findViewById(R.id.rb_sOff);
        rb_sOn = (RadioButton) findViewById(R.id.rb_sOn);
        rbSound = (RadioGroup) findViewById(R.id.rbSound);

        btnKeyOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch(rbSound.getCheckedRadioButtonId()){
                    case R.id.rb_sOff:
                        intent.putExtra("sound_state", false);
                        break;
                    case R.id.rb_sOn:
                        intent.putExtra("sound_state", true);
                        break;
                }
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }


}
