package quizappmainappforamiusa.example.quizapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class QuizDashbaordActivity extends AppCompatActivity {

    static final float END_SCALE = 0.7f;


    Button navToggler_btn, Next_btn;
    LinearLayout  linearLayout1;
    TextView txtQuestions, txtnumberIndicator;
    Dialog dialog;
    private int count = 0;
    private int position = 0;
    String topic="";
    private List<questionsModelClass> list;
    DatabaseReference RootRef,TempRef;
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_dashbaord);


        // Adding this line will prevent taking screenshot in your app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);

        Intent intent = getIntent();
        String string = intent.getStringExtra("Quiz_Set");

        RootRef = FirebaseDatabase.getInstance ().getReference ().child("Quiz").child(string).child("Questions");

        TempRef = FirebaseDatabase.getInstance ().getReference ().child("Quiz").child(string);


        TempRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                topic = snapshot.child("name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        list = new ArrayList<>();

        RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 : snapshot.getChildren()){

                    list.add(snapshot1.getValue(questionsModelClass.class));
                }
                if (list.size()>0){
                    playAnim(txtQuestions, 0, list.get(position).getQuestions());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(QuizDashbaordActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
            }
        });
        



        //All Hooks

        navToggler_btn = findViewById(R.id.action_menu_presenter);
        linearLayout1 = findViewById(R.id.options_layout);
        txtQuestions = findViewById(R.id.question_view);
        txtnumberIndicator = findViewById(R.id.no_of_questions_view);
        Next_btn = findViewById(R.id.next_btn);
        dialog = new Dialog(this, R.style.AnimateDialog);


        Quiz();

    }

    private void Quiz() {

        for (int i = 0; i < 4; i++) {
            linearLayout1.getChildAt(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkAns((Button) v);
                }
            });
        }

        txtnumberIndicator.setText(position + 1 + "/" + list.size());

        Next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ToastShow(list.get(position).getExplanation().toString());

                Next_btn.setEnabled(false);
                Next_btn.setAlpha(0.7f);
                enableOptions(true);
                position++;
                if (position == list.size()) {


                    DataSaveToSharedPref(String.valueOf(score),topic);
                    //Score Activities
                    Intent intent = new Intent(QuizDashbaordActivity.this,ResultShowActivity.class);
                    intent.putExtra("HELLO",String.valueOf(score));
                    startActivity(intent);


                    return;
                }

                count = 0;
                playAnim(txtQuestions, 0, list.get(position).getQuestions());
            }
        });




//        expl_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                expl_btn.setEnabled(true);
//                expl_btn.setAlpha(0.7f);
//                Next_btn.setEnabled(false);
//                Next_btn.setAlpha(0.7f);
//                enableOptions(true);
//
//                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(QuizDashbaordActivity.this,R.style.AlertDialogTheme);
//                builder.setTitle("Explanation");
//                builder.setIcon(R.drawable.logo);
//                builder.setMessage(list.get(position).getExplanation().toString());
//                builder.setBackground(getResources().getDrawable(R.drawable.input_background , null));
//                builder.setCancelable(false);
//                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//
//                    }
//                });
//
//                builder.show();
//            }
//        });

    }

    private void ToastShow(final String questions) {
        Toast.makeText(this, questions, Toast.LENGTH_SHORT).show();
    }


    private void checkAns(Button selectedOptions) {
        enableOptions(false);
        Next_btn.setEnabled(true);
        Next_btn.setAlpha(1);
        if (selectedOptions.getText().toString().equals(list.get(position).getCorrectAnswer())) {
            //correct Answer
            score+=10;
            selectedOptions.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#14E39A")));
        } else {
            //wrong Answer
            selectedOptions.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF2B55")));
            Button correctOption = linearLayout1.findViewWithTag(list.get(position).getCorrectAnswer());
            correctOption.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#14E39A")));
        }
    }

    private void playAnim(final View view, final int value, final String data) {

        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500).setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

                if (value == 0 && count < 4) {
                    String option = "";
                    if (count == 0) {
                        option = list.get(position).getOptionA();
                    } else if (count == 1) {
                        option = list.get(position).getOptionB();
                    } else if (count == 2) {
                        option = list.get(position).getOptionC();
                    } else if (count == 3) {
                        option = list.get(position).getOptionD();
                    }
                    playAnim(linearLayout1.getChildAt(count), 0, option);
                    count++;
                }
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onAnimationEnd(Animator animation) {

                if (value == 0) {

                    try {
                        ((TextView) view).setText(data);
                        txtnumberIndicator.setText(position + 1 + "/" + list.size());
                    } catch (ClassCastException ex) {
                        ((Button) view).setText(data);
                    }
                    view.setTag(data);


                    playAnim(view, 1, data);

                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void enableOptions(boolean enable) {
        for (int i = 0; i < 4; i++) {
            linearLayout1.getChildAt(i).setEnabled(enable);
            if (enable) {
                linearLayout1.getChildAt(i).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#2133A0")));
            }
        }
    }


    private void DataSaveToSharedPref(String data,String topic) {



        String timeStamp = new SimpleDateFormat("yyyy:MM:dd-HH:mm:ss").format(new Date());


        // below line is use to add data to array list.
        MainActivity.courseModalArrayList.add(new Fav(data,timeStamp,topic));
        // notifying adapter when new data added.
        MainActivity.adapter.notifyItemInserted(MainActivity.courseModalArrayList.size());


        // method for saving the data in array list.
        // creating a variable for storing data in
        // shared preferences.
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);

        // creating a variable for editor to
        // store data in shared preferences.
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // creating a new variable for gson.
        Gson gson = new Gson();

        // getting data from gson and storing it in a string.
        String json = gson.toJson(MainActivity.courseModalArrayList);

        // below line is to save data in shared
        // prefs in the form of string.
        editor.putString("courses", json);

        // below line is to apply changes
        // and save data in shared prefs.
        editor.apply();


    }




}