package com.example.smartcampus.ui.about;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.smartcampus.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {
    private ViewPager viewPager;
    private BranchAdapter adapter;
    private List<BranchModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_about, container, false);
        list = new ArrayList<>();
        addBranch();
        adapter = new BranchAdapter(getContext(),list);
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        ImageView imageView = view.findViewById(R.id.college_image);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/smartcampus-efff3.appspot.com/o/gallery%2F%5BB%40bc4a9d8jpg?alt=media&token=b36909e6-6ba5-40b2-9a4a-09a3bab0d863")
                .into(imageView);
        return view;


    }

    private void addBranch() {
        list.add(new BranchModel(R.drawable.ic_baseline_computer_24,"Computer Science And Engineering","Computer Science and Engineering Started in year 2002. It is the best department in diu. Most of the student in diu are CSE based student"));
        list.add(new BranchModel(R.drawable.ic_settings,"Software Engineering","In the era of rapid integration & innovation of technology in every field of life, becoming an " +
                "ENGINEERING graduate will give you the opportunity of high potential career growth.."));
        list.add(new BranchModel(R.drawable.ic_baseline_create_24,"Computing and Information System","The department of Computing and Information System (CIS) " +
                " since its inception has been continuously fostering academic excellence through industry academy collaboration especially integrating industry demands of ICT skills in the core curriculum of the CIS program."));
        list.add(new BranchModel(R.drawable.ic_baseline_display_settings_24,"Information Technology and Management (ITM)","The Department of Information Technology and Management (ITM) provides you a unique opportunity to have BSc. in Information Technology and Management. In the field of Information Technology and Management, the job possibilities are almost endless." +
                " \"The major goal of the discipline, which is now unique in our nation, is to integrate information technology with business intelligence. We also intend to secure financial systems on cloud.."));
        list.add(new BranchModel(R.drawable.ic_baseline_draw_24,"Physical Education and Sports Science","The department of Physical Education and Sports Science aims to produce high-quality" +
                " graduates aspiring to become environmental scientists and make positive impacts to save the world and its population"));
        list.add(new BranchModel(R.drawable.ic_baseline_energy_savings_leaf_24,"General Educational Development","The Department of General Educational Development is an interdisciplinary department with majors in a number of science and education subjects." +
                " At this moment the department provides the basic knowledge of basic sciences to the science and technology students in various departments."));
        list.add(new BranchModel(R.drawable.ic_baseline_energy_savings_leaf_24,"Environmental Science and Disaster Management (ESDM)","The department of Environmental Science and Disaster Management (ESDM) aims to produce high-quality graduates aspiring to become" +
                " environmental scientists and make positive impacts to save the world and its population from natural disasters."));
        list.add(new BranchModel(R.drawable.ic_baseline_computer_24,"Multimedia and Creative Technology (MCT)","In the Department of Multimedia and Creative Technology (MCT), we nurture both students, technical skills," +
                " and their creativity, allowing them to apply their imagination, their dreams, and their artistic talents in terms of state-of-art technology."));
    }
}