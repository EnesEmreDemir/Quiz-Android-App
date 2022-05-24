package quizappmainappforamiusa.example.quizapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    static NotesAdapter adapter ;
    static ArrayList<Fav> courseModalArrayList;

    static SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        chipNavigationBar = findViewById(R.id.bottom_nav_bar);
        chipNavigationBar.setItemSelected(R.id.home,
                true);




        sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);


        Gson gson = new Gson();

        String json = sharedPreferences.getString("courses", null);

        Type type = new TypeToken<ArrayList<Fav>>() {}.getType();


        courseModalArrayList = gson.fromJson(json, type);


        if (courseModalArrayList == null) {
            courseModalArrayList = new ArrayList<>();
        }



        adapter = new NotesAdapter(courseModalArrayList, MainActivity.this);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frag_container_nav,
                        new HomeFragment()).commit();

        chipNavigationBar.setOnItemSelectedListener
                (new ChipNavigationBar.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(int i) {
                        Fragment fragment = null;
                        switch (i){
                            case R.id.home:
                                fragment = new HomeFragment();
                                break;
                            case R.id.stats:
                                fragment = new ResultsFragment();
                                break;
                            case R.id.settings:
                                fragment = new SettingsFragment();
                                break;
                        }
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.frag_container_nav,
                                        fragment).commit();

                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onBackPressed(){
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(MainActivity.this,R.style.AlertDialogTheme);
        builder.setTitle("Confirm Exit");
        builder.setIcon(R.drawable.logo);
        builder.setMessage("Do you really want to exit?");
        builder.setBackground(getResources().getDrawable(R.drawable.input_background , null));
        builder.setCancelable(false);
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(MainActivity.this, "Exit cancelled", Toast.LENGTH_LONG).show();
            }
        });

        builder.show();
    }
}