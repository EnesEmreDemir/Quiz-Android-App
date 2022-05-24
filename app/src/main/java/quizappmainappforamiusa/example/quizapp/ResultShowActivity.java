package quizappmainappforamiusa.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ResultShowActivity extends AppCompatActivity {




    LinearLayout linearLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_show);


        Intent intent = getIntent();
        String string = intent.getExtras().getString("HELLO");

        textView = findViewById(R.id.score_show);
        textView.setText(string+" % Score");


    }

    public void BackGoing(View view) {

        Intent intent = new Intent(ResultShowActivity.this,MainActivity.class);
        intent.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ResultShowActivity.this,MainActivity.class);
        intent.addFlags ( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
        startActivity(intent);
        finish();
    }
}