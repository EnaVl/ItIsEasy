package com.example.vlad.itiseasy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class KeyboardsSwitcher extends AppCompatActivity {
    Button btnKeyOk;
    RadioGroup rbKey;
    RadioButton rbKey1, rbKey2;
    TextView keyTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.keyboards);
        setContentView(R.layout.activity_keyboards_switcher);

        btnKeyOk = (Button) findViewById(R.id.btnKeyOk);
        rbKey = (RadioGroup) findViewById(R.id.rbKey);
        rbKey1 = (RadioButton) findViewById(R.id.rbKey1);
        rbKey2 = (RadioButton) findViewById(R.id.rbKey2);
        keyTitle = (TextView) findViewById(R.id.keyTitle);

        keyTitle.setTypeface(MainActivity.typeface);
        btnKeyOk.setTypeface(MainActivity.typeface);

        btnKeyOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                switch(checkSelectedRadioBtn()){
                    case 1:
                        intent.putExtra("activity", 1);
                        break;
                    case 2:
                        intent.putExtra("activity", 2);
                        break;
                }
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }

    public int checkSelectedRadioBtn(){
        switch(rbKey.getCheckedRadioButtonId()){
            case R.id.rbKey1:
                return 1;
            case R.id.rb2:
                return 2;
            default: return 2;
        }
    }

}
