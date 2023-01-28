package com.example.smartcampus.ui.faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartcampus.R;
import com.example.smartcampus.ebook.EbookData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class TeacherAdapter2 extends RecyclerView.Adapter<TeacherAdapter2.TeacherViewAdapter2>{
    private List<TeacherData2> list;
    private Context context;

    public TeacherAdapter2(List<TeacherData2> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherViewAdapter2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout2,parent,false);
        return new TeacherViewAdapter2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter2 holder, int position) {
        TeacherData2 item = list.get(position);
        holder.name.setText(item.getName());
        holder.email.setText(item.getEmail());
        holder.post.setText(item.getPost());
        try {
            Picasso.get().load(item.getImage()).placeholder(R.drawable.images).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public void Filteredlist(ArrayList<TeacherData2> filterlist) {
        list = filterlist;
        notifyDataSetChanged();
    }



    public class TeacherViewAdapter2 extends RecyclerView.ViewHolder {
        private TextView name,email,post;
        private ImageView imageView;

        public TeacherViewAdapter2(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.teacherName);
            email = itemView.findViewById(R.id.teacherEmail);
            post = itemView.findViewById(R.id.teacherPost);
            imageView = itemView.findViewById(R.id.teacherImage);

        }
    }

}


