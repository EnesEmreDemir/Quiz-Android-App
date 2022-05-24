package quizappmainappforamiusa.example.quizapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;


public class ResultsFragment extends Fragment {

    TextView textView;
    final private int REQUEST_CODE_PERMISSION = 111;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_results, container, false);
        textView = view.findViewById(R.id.warm_text_show2);
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        textView.setText("Past Results");


        recyclerView = view.findViewById(R.id.results_list_view);


        if (MainActivity.adapter.getItemCount()!=0){
            recyclerView.setVisibility(View.VISIBLE);
        }


        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(manager);


        recyclerView.setAdapter(MainActivity.adapter);

        return view;
    }
}