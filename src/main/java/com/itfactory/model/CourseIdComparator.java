package com.itfactory.model;

import java.util.Comparator;

/*Clasa care implementeaza interfata Comparator<>, si care va ajuta la sortarea obiectelor de tip "Curs", dupa ID-ul cursului */

public class CourseIdComparator implements Comparator<Course> {
    @Override
    public int compare(Course course1, Course course2) {
        return Integer.compare(course1.getCourseId(), course2.getCourseId());
    }
}
