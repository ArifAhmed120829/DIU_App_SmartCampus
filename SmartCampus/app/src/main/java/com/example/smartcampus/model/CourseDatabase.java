package com.example.smartcampus.model;
import android.content.Context;


import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Category.class,Course.class}, version = 1)
public abstract class CourseDatabase extends RoomDatabase {
    public abstract CategoryDAO categoryDAO();
    public abstract CourseDAO   courseDAO();

    // Singleton Pattern
    private static CourseDatabase instance;

    public static synchronized CourseDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            CourseDatabase.class, "courses_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    // Callback
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Insert data when db is created...
            InitializeData();


        }
    };

    private static void InitializeData() {

        CourseDAO courseDAO = instance.courseDAO();
        CategoryDAO categoryDAO= instance.categoryDAO();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {

                // Categories
                Category category1 = new Category();
                category1.setCategoryName("Cost");
                category1.setCategoryDescription("Web Development Interface");

                Category category2 = new Category();
                category2.setCategoryName("Time");
                category2.setCategoryDescription("Web Development Database");

                categoryDAO.insert(category1);
                categoryDAO.insert(category2);


                // Courses
                Course course1 = new Course();
                course1.setCourseName("Dhanmondi <> DSC");
                course1.setUnitPrice("25tk");
                course1.setCategoryId(1);

                Course course2 = new Course();
                course2.setCourseName("ECB Chattor <> DSC");
                course2.setUnitPrice("30tk");
                course2.setCategoryId(1);

                Course course3 = new Course();
                course3.setCourseName("Dhanmondi <> DSC");
                course3.setUnitPrice("7:00 A.M-9.30 AM & 12.30 P.M-3.30 P.M");
                course3.setCategoryId(2);

                Course course4 = new Course();
                course4.setCourseName("ECB Chattor <> DSC");
                course4.setUnitPrice("7:00 A.M-9.30 AM & 12.30 P.M-3.30 P.M");
                course4.setCategoryId(2);

                courseDAO.insert(course1);
                courseDAO.insert(course2);
                courseDAO.insert(course3);
                courseDAO.insert(course4);

            }
        });




    }
}
