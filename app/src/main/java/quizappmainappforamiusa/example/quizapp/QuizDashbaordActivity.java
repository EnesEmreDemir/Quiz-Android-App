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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class QuizDashbaordActivity extends AppCompatActivity {

    static final float END_SCALE = 0.7f;


    Button navToggler_btn, expl_btn , Next_btn;
    LinearLayout  linearLayout1;
    TextView txtQuestions, txtnumberIndicator;
    Dialog dialog;
    private int count = 0;
    private int position = 0;
    private List<questionsModelClass> list;
    DatabaseReference RootRef;
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
        expl_btn = findViewById(R.id.expl_btn);
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



                Next_btn.setEnabled(false);
                Next_btn.setAlpha(0.7f);
                enableOptions(true);
                position++;
                if (position == list.size()) {
                    //Score Activities
                    if (score <= 30) {

                        Button try_again, share;
                        dialog.setContentView(R.layout.activity_fail_20_layout);
                        try_again = dialog.findViewById(R.id.try_again_btn);
                        share = dialog.findViewById(R.id.share_btn);


                        try_again.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), QuizDashbaordActivity.class); //If User get 20% let him or her play again
                                startActivity(BG);
                                finish();
                            }
                        });

                        ///Share Quiz Box to friends
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Hey! I just played Quiz Box and it's WONDERFUL!";
                                String shareSub = "Your subject here";
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                            }
                        });

                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    } else if (score <= 50) {

                        Button try_again, share;
                        dialog.setContentView(R.layout.activity_pass_50_layout);
                        try_again = dialog.findViewById(R.id.try_again_btn);
                        share = dialog.findViewById(R.id.share_btn);


                        try_again.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), QuizDashbaordActivity.class); ///If User get 50% let him or her play again
                                startActivity(BG);
                                finish();
                            }
                        });

                        ///Share Quiz Box to friends
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Hey! I just played Quiz Box and it's WONDERFUL!";
                                String shareSub = "Your subject here";
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                            }
                        });

                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    } else if (score <= 69) {

                        Button promote_btn, share;
                        dialog.setContentView(R.layout.activity_pass_70_layout);
                        promote_btn = dialog.findViewById(R.id.nl_btn);
                        share = dialog.findViewById(R.id.share_btn);



                        promote_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), MainActivity.class); ///If User get 70% let him to next category
                                startActivity(BG);
                                finish();
                            }
                        });

                        ///Share Quiz Box to friends
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Hey! I just played Quiz Box and it's WONDERFUL!";
                                String shareSub = "Your subject here";
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                            }
                        });

                        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    } else if ( score >= 70) {

                        Button promote_btn, share;
                        dialog.setContentView(R.layout.activity_pass_100_layout);
                        promote_btn = dialog.findViewById(R.id.nl_btn);
                        share = dialog.findViewById(R.id.share_btn);



                        promote_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent BG = new Intent(getApplicationContext(), MainActivity.class); ///If User get 100% promote him or her to next category
                                startActivity(BG);
                            }
                        });

                        ///Share Quiz Box to friends
                        share.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                                sharingIntent.setType("text/plain");
                                String shareBody = "Hey! I just played Quiz Box and it's WONDERFUL!";
                                String shareSub = "Your subject here";
                                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                            }
                        });


                        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                        dialog.show();

                    }
                    return;
                }

                count = 0;
                playAnim(txtQuestions, 0, list.get(position).getQuestions());
            }
        });




        expl_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expl_btn.setEnabled(true);
                expl_btn.setAlpha(0.7f);
                Next_btn.setEnabled(false);
                Next_btn.setAlpha(0.7f);
                enableOptions(true);

                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(QuizDashbaordActivity.this,R.style.AlertDialogTheme);
                builder.setTitle("Explanation");
                builder.setIcon(R.drawable.logo);
                builder.setMessage(list.get(position).getExplanation().toString());
                builder.setBackground(getResources().getDrawable(R.drawable.input_background , null));
                builder.setCancelable(false);
                builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    }

    private void ToastShow(final String questions) {
        Toast.makeText(this, questions, Toast.LENGTH_SHORT).show();
    }


    private void checkAns(Button selectedOptions) {
        enableOptions(false);
        Next_btn.setEnabled(true);
        expl_btn.setEnabled(true);
        expl_btn.setAlpha(1);
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
}