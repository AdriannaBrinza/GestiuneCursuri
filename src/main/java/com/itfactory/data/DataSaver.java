package com.itfactory.data;

import com.itfactory.model.Course;
import com.itfactory.model.Student;
import com.itfactory.utils.DataUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataSaver {

    public void saveData(Map<Course, List<Student>> map) throws IOException {
        saveCourses(map);
        saveStudents(map);
        saveMapping(map);
    }

    private void saveCourses(Map<Course, List<Student>> map) throws IOException {
        Set<Course> courseSet = map.keySet();
        String courseData = "";
        for (Course course : courseSet) {
            courseData = courseData.concat(course.toString().concat("\n"));
        }
        DataUtils.writeFile(DataUtils.COURSE_FILE_PATH, courseData);
    }

    private void saveStudents(Map<Course, List<Student>> map) throws IOException {
        String studentData = "";
        for (List<Student> valuesOfMap : map.values()) {
            for (Student student : valuesOfMap) {
                //...folosim metoda toString pentru a extrage datele.
                studentData = studentData.concat(student.toString().concat("\n"));
            }
        }
        DataUtils.writeFile(DataUtils.STUDENT_FILE_PATH, studentData);

    }

    private void saveMapping(Map<Course, List<Student>> map) throws IOException {
        String mappings = "";
        for (Map.Entry<Course, List<Student>> entry : map.entrySet()) {
            int courseId = entry.getKey().getCourseId();
            for (Student student : entry.getValue()) {
                int studentId = student.getStudentId();
                //Se va crea o mapare.
                mappings = mappings.concat(String.valueOf(studentId).concat(",").concat(String.valueOf(courseId)).concat("\n"));
            }
        }
        DataUtils.writeFile(DataUtils.MAPPING_FILE_PATH, mappings);
    }
}