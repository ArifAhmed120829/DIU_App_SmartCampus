package com.example.smartcampus.ui2;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartcampus.R;
import com.example.smartcampus.model2.student_model;

import java.util.List;

public class IssueRecyclerAdapter extends RecyclerView.Adapter<IssueRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<student_model> issueList;

    public IssueRecyclerAdapter(Context context, List<student_model> issueList) {
        this.context = context;
        this.issueList = issueList;
    }

    @NonNull
    @Override
    public IssueRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.issue_row, viewGroup,false);

        return new ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull IssueRecyclerAdapter.ViewHolder holder, int position) {
        student_model model = issueList.get(position);
        String imageUrl;
        holder.title.setText(model.getTitle());
        holder.thoughts.setText(model.getThoughts());
        holder.name.setText(model.getUserName());
        imageUrl = model.getImageUrl();
        String timeAgo = (String) DateUtils.getRelativeTimeSpanString(
                model.getTimeAdded().getSeconds()*1000);
        holder.dateAdded.setText(timeAgo);
        /**
         * Using Glide library to display the images
         **/
        Glide.with(context)
                .load(imageUrl)
                .fitCenter()
                .into(holder.image);


    }

    @Override
    public int getItemCount() {
        return issueList.size();
    }

    //View Holder
    public class ViewHolder extends  RecyclerView.ViewHolder{
        public TextView title, thoughts, dateAdded, name;
        public ImageView image;
        public ImageView shareButton;
        String userId;
        String username;

        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);
            context = ctx;
            title = itemView.findViewById(R.id.issue_title_list);
            thoughts = itemView.findViewById(R.id.issue_thought_list);
            dateAdded = itemView.findViewById(R.id.issue_timestamp_list);
            image = itemView.findViewById(R.id.issue_image_list);
            name = itemView.findViewById(R.id.issue_row_username);
            shareButton = itemView.findViewById(R.id.issue_row_sharebutton);
            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Sharing the post
                }
            });
        }
    }
}
