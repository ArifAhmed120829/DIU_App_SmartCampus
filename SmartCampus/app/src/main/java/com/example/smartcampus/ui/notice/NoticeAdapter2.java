package com.example.smartcampus.ui.notice;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.FullImageView;
import com.example.smartcampus.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoticeAdapter2 extends RecyclerView.Adapter<NoticeAdapter2.NoticeViewAdapter2> {
    private Context context;
    private ArrayList<NoticeData2> list;

    public NoticeAdapter2(Context context, ArrayList<NoticeData2> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsfeed_item_layout2, parent, false);
        return new NoticeViewAdapter2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter2 holder, int position) {
        final NoticeData2 currentItem = list.get(position);
        holder.userNoticeTitle.setText(currentItem.getTitle());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());
        try {
            if (currentItem.getImage() != null)
                Picasso.get().load(currentItem.getImage()).into(holder.userNoticeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.userNoticeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FullImageView.class);
                intent.putExtra("image",currentItem.getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter2 extends RecyclerView.ViewHolder {
        private TextView userNoticeTitle, date, time;
        private ImageView userNoticeImage;

        public NoticeViewAdapter2(@NonNull View itemView) {
            super(itemView);
            userNoticeTitle = itemView.findViewById(R.id.userNoticeTitle);
            userNoticeImage = itemView.findViewById(R.id.userNoticeImage);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}

