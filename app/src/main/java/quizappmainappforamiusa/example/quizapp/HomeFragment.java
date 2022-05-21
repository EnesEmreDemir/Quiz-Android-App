package quizappmainappforamiusa.example.quizapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {


    TextView textView;

    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    DatabaseReference RootRef;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        textView = view.findViewById(R.id.warm_text_show1);
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


        RootRef = FirebaseDatabase.getInstance ().getReference ().child("Quiz");

        recyclerView = view.findViewById(R.id.quiz_recylerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));





        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        showProgressDialog();
        recyclerView.setVisibility(View.GONE);


        FirebaseRecyclerOptions<QuizSetMode> options =
                new FirebaseRecyclerOptions.Builder<QuizSetMode> ()
                        .setQuery ( RootRef,QuizSetMode.class )
                        .build ();


        FirebaseRecyclerAdapter<QuizSetMode, StudentViewHolder2> adapter =
                new FirebaseRecyclerAdapter<QuizSetMode, StudentViewHolder2>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull final StudentViewHolder2 holder, final int position, @NonNull final QuizSetMode model) {

                        String listPostKey = getRef(position).getKey();


                        holder.quiz_name.setText(model.getName());
                        Picasso.get().load(model.getImage()).placeholder(R.drawable.logo).error(R.drawable.logo).into(holder.circleImageView);

                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent intent = new Intent(getContext(),QuizDashbaordActivity.class);
                                intent.putExtra("Quiz_Set",listPostKey);
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public StudentViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                        View view  = LayoutInflater.from ( viewGroup.getContext () ).inflate ( R.layout.quiz_topics,viewGroup,false );
                        StudentViewHolder2 viewHolder  = new StudentViewHolder2(  view);
                        return viewHolder;

                    }

                    @Override
                    public void onDataChanged() {
                        super.onDataChanged();

                        recyclerView.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();


                    }


                };
        recyclerView.setAdapter ( adapter );
        adapter.startListening ();

    }

    public static class StudentViewHolder2 extends  RecyclerView.ViewHolder
    {

        TextView quiz_name;
        CircleImageView circleImageView;
        public StudentViewHolder2(@NonNull View itemView) {
            super ( itemView );
            quiz_name = itemView.findViewById ( R.id.quiz_set_name);
            circleImageView  = itemView.findViewById(R.id.quiz_set_image);
        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_diaglog);
        progressDialog.setCanceledOnTouchOutside(false);
        Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);

    }



}