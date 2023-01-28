package com.example.smartcampus;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartcampus.databinding.ActivityHallBinding;
import com.example.smartcampus.model.Category;
import com.example.smartcampus.model.Course;
import com.example.smartcampus.viewmodel.MainActivityViewModel;

import java.util.ArrayList;
import java.util.List;



public class Hall extends AppCompatActivity {

    private MainActivityViewModel mainActivityViewModel;
    private TextView transport_link;
    private ArrayList<Category> categoriesList;
    private ActivityHallBinding activityMainBinding;
    private MainActivityClickHandlers handlers;//if bug check this (hallclickhandlers)
    private Category selectedCategory;

    // RecyclerView
    private RecyclerView courseRecyclerView;
    private CourseAdapter courseAdapter;
    private ArrayList<Course> coursesList;
    private static final int ADD_COURSE_REQUEST_CODE =1;
    private static final int EDIT_COURSE_REQUEST_CODE =2;
    public int selectedCourseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hall);

        mainActivityViewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);


        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_hall);
        handlers = new MainActivityClickHandlers();
        activityMainBinding.setClickHandlers(handlers);
        transport_link = findViewById(R.id.linkforTransport);

        transport_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://docs.google.com/spreadsheets/d/15xUoX4BVci54TIkHp_AzSnj54CPYvCDMcPf_zs8R_qs/htmlview?fbclid=IwAR3fXd0bSTfCpcDPodXU_cDEaTx-fuJPXhCaQ2cbcOJ7N9Oasu41HFow8Bs");
                startActivity(new Intent(Intent.ACTION_VIEW,uri));
            }
        });







        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoriesList = (ArrayList<Category>) categories;


                for (Category c: categories){

                    Log.i("TAG", c.getCategoryName());
                }


                showOnSpinner();
            }
        });



    }

    private void showOnSpinner() {
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item,
                categoriesList
        );

        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);





    }

    public void LoadCoursesArrayList(int categoryID){
        mainActivityViewModel.getCoursesOfSelectedCategory(categoryID).observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                coursesList = (ArrayList<Course>) courses;
                LoadRecyclerView();
            }
        });
    }

    private void LoadRecyclerView() {
        courseRecyclerView = activityMainBinding.secondaryLayout.recyclerView;
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        courseRecyclerView.setHasFixedSize(true);

        courseAdapter = new CourseAdapter();
        courseRecyclerView.setAdapter(courseAdapter);

        courseAdapter.setCourses(coursesList);


        // EDIT THE COURSE
        courseAdapter.setListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                selectedCourseId = course.getCourseId();

                Intent i = new Intent(Hall.this, AddEditActivity.class);
                i.putExtra(AddEditActivity.COURSE_ID, selectedCourseId);
                i.putExtra(AddEditActivity.COURSE_NAME, course.getCourseName());
                i.putExtra(AddEditActivity.UNIT_PRICE, course.getUnitPrice());

                startActivityForResult(i,EDIT_COURSE_REQUEST_CODE);


            }
        });

        // Delete A COURSE
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Course courseToDelete = coursesList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteCourse(courseToDelete);
            }
        }).attachToRecyclerView(courseRecyclerView);


    }


    public class MainActivityClickHandlers{

        public void onFABClicked(View view){

            // CREATE THE COURSE
            Intent i = new Intent(Hall.this, AddEditActivity.class);
            startActivityForResult(i, ADD_COURSE_REQUEST_CODE);

        }


        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id){
            selectedCategory = (Category) parent.getItemAtPosition(pos);

            String message = "id is: " + selectedCategory.getId() +
                    " \n name is " + selectedCategory.getCategoryName();

            Toast.makeText(parent.getContext(), " "+message, Toast.LENGTH_SHORT).show();

            LoadCoursesArrayList(selectedCategory.getId());

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        int selectedCategoryId = selectedCategory.getId();


        if (requestCode == ADD_COURSE_REQUEST_CODE && resultCode == RESULT_OK){
            Course course = new Course();

            course.setCategoryId(selectedCategoryId);
            course.setCourseName(data.getStringExtra(AddEditActivity.COURSE_NAME));
            course.setUnitPrice(data.getStringExtra(AddEditActivity.UNIT_PRICE));
            mainActivityViewModel.addNewCourse(course);
            Log.v("TAG", "Inserted"+course.getUnitPrice());
        }
        else if(requestCode == EDIT_COURSE_REQUEST_CODE && resultCode == RESULT_OK){

            Course course = new Course();
            course.setCategoryId(selectedCategoryId);
            course.setCourseName(data.getStringExtra(AddEditActivity.COURSE_NAME));
            course.setUnitPrice(data.getStringExtra(AddEditActivity.UNIT_PRICE));

            course.setCourseId(selectedCourseId);

            mainActivityViewModel.updateCourse(course);

        }



    }
}