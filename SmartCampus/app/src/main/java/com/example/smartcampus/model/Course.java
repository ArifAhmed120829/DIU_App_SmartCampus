package com.example.smartcampus.model;

import static androidx.room.ForeignKey.CASCADE;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "course_table", foreignKeys = @ForeignKey(entity = Category.class,
        parentColumns = "id",childColumns = "category_id", onDelete = CASCADE))

public class Course extends BaseObservable{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    private int courseId;

    @ColumnInfo(name = "course_name")
    private String courseName;

    @ColumnInfo(name = "unit_price")
    private String unitPrice;

    @ColumnInfo(name = "category_id")
    private int categoryId;


    @Ignore
    public Course() {
    }

    public Course(int courseId, String courseName, String unitPrice, int categoryId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.unitPrice = unitPrice;
        this.categoryId = categoryId;
    }


    @Bindable
    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
        notifyPropertyChanged(BR.courseId);

    }

    @Bindable
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
        notifyPropertyChanged(BR.courseName);
    }

    @Bindable
    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
        notifyPropertyChanged(BR.unitPrice);
    }

    @Bindable
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        notifyPropertyChanged(BR.categoryId);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return  courseId == course.courseId
                && categoryId == course.categoryId
                && courseName.equals(course.courseName)
                && unitPrice.equals(course.unitPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, unitPrice, categoryId);
    }
}
