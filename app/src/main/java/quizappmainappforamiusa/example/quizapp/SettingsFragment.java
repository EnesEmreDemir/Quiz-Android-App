package quizappmainappforamiusa.example.quizapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class SettingsFragment extends Fragment {


    TextView textView;
    TextView rate,contact,share;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_settings, container, false);


        rate = view.findViewById(R.id.rateus);
        contact = view.findViewById(R.id.feedback);
        share = view.findViewById(R.id.share);


        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=quizappmainappforamiusa.example.quizapp"));
                startActivity(browserIntent);
            }
        });
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=quizappmainappforamiusa.example.quizapp"));
                startActivity(browserIntent);
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=quizappmainappforamiusa.example.quizapp"));
                startActivity(browserIntent);
            }
        });


        textView = view.findViewById(R.id.warm_text_show3);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            textView.setText("Good Morning");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            textView.setText("Good Afternoon");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            textView.setText("Good Evening");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            textView.setText("Good Night");
        }

        return view;
    }
}