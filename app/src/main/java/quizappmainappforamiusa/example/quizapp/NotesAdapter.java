package quizappmainappforamiusa.example.quizapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    // creating a variable for array list and context.
    private ArrayList<Fav> courseModalArrayList = new ArrayList<>();
    private Context context;

    // creating a constructor for our variables.
    public NotesAdapter(ArrayList<Fav> courseModalArrayList, Context context) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // below line is to inflate our layout.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_results, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        // setting data to our views of recycler view.
        Fav modal = courseModalArrayList.get(position);
        holder.score.setText(modal.getName()+"%");
        holder.date.setText(modal.getDate());
        holder.topic.setText(modal.getTopic());



    }

    @Override
    public int getItemCount() {
        // returning the size of array list.
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        // creating variables for our views.
        private TextView date,score,topic;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // initializing our views with their ids.
            score = itemView.findViewById(R.id.results_score);
            date = itemView.findViewById(R.id.results_date);
            topic = itemView.findViewById(R.id.history_topic_name);

        }
    }
}
