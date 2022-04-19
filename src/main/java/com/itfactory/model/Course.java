package com.itfactory.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import com.itfactory.exceptii.*;

public class Course {
    private int courseId;
    private String courseName;
    private double price;
    private static Set<Integer> assignedIds = new HashSet<>();

    public Course() {
    }

    public Course(int courseId, String courseName, double price) throws NotUniqueException {
        //punem conditia ca ID-ul cursului sa fie unic pentru fiecare instanta (not OK in multi-threading environment!)
        if (this.courseId != courseId) {
            if (assignedIds.contains(courseId)) {
                throw new NotUniqueException("This ID " + courseId + " is already assigned. Please choose a different ID.");
            }
            assignedIds.add(courseId);
            this.courseId = courseId;
        }

        this.courseName = courseName;
        this.price = price;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return courseId == course.courseId && Double.compare(course.price, price) == 0 && Objects.equals(courseName, course.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, courseName, price);
    }

    @Override
    public String toString() {
        return courseId + "," + courseName + "," + price;
    }
}