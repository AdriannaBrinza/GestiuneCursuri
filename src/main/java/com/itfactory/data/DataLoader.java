package com.itfactory.data;

import com.itfactory.model.Course;
import com.itfactory.model.Student;
import com.itfactory.utils.DataUtils;
import com.itfactory.exceptii.*;
import java.io.IOException;
import java.util.*;

public class DataLoader {
    private Map<Course, List<Student>> dataMap;
    private List<Student> students;

    //Constructor
    public DataLoader() {
        dataMap = new HashMap<>();
        students = new ArrayList<>();
    }

    //metoda apelata in momentul inceperii programului. Atunci se incarca datele.
    public void loadData() throws NotUniqueException {
        loadCourses(); //metoda care incarca fisierul cursuri.csv
        loadStudents(); //metoda care incarca fisierul studenti.csv
        mapStudentstoCourses(); //metoda care citeste fisierul de mapari si o sa inceapa sa faca maparile intre studenti si cursuri
    }

    //metoda citeste fisierul de "cursuri.csv" si incarca datele de acolo in "dataMap"
    private void loadCourses() throws NotUniqueException {
        try {
            List<String> courseInputData = DataUtils.readFile(DataUtils.COURSE_FILE_PATH); //punem intr-o lista de Stringuri fisierul citit cursuri.cs
            //parcurgem lista si splituim fiecare linie dupa caracterul virgula ","
            for (String courseInputDatai : courseInputData) {
                String[] data = courseInputDatai.split(","); //fiecare linie din lista reprezinta un curs: o 'spargem' dupa virgula si punem componentele intr-un array de Stringuri
                dataMap.put(createCourse(data), new ArrayList<>()); //adaugam cate un curs in map folosind o metoda care returneaza un obiect de tipul Curs si care primeste ca parametru un array de String-uri
            }
        } catch (IOException e) {
            System.out.println("Data inconsistency: the csv file with courses hasn't been found.");
            e.printStackTrace();
        }
    }

    //metoda care construieste un obiect de tipul Curs, primind ca parametru un array de String-uri
    private Course createCourse(String[] data) throws NotUniqueException {
        int id = Integer.parseInt(data[0]);
        String name = data[1];
        double price = Double.parseDouble(data[2]);
        return new Course(id, name, price);
    }

    //metoda care citeste fisierul "studenti.csv" si incarca datele de acolo in lista "students"
    private void loadStudents() {
        try {
            List<String> courseInputData = DataUtils.readFile(DataUtils.STUDENT_FILE_PATH);
            for (String courseInputDatai : courseInputData) {
                String[] data = courseInputDatai.split(",");
                students.add(createStudent(data));
            }
        } catch (IOException e) {
            System.out.println("Data inconsistency: the csv file with Students hasn't been found.");
            e.printStackTrace();
        }
    }

    //metoda care construieste un obiect de tipul Student, primnind ca parametru un array de Stringuri
    private Student createStudent(String[] data) {
        int studentID = Integer.parseInt(data[0]);
        String studentName = data[1];
        double budget = Double.parseDouble(data[2]);
        return new Student(studentID, studentName, budget);
    }

    //metoda care incarca datele din fisierul "mapari.csv" si adauga fiecare student in lista corespunzatoare din map, in functie de id-ul cursului citit.
    private void mapStudentstoCourses() {
        try {
            List<String> mapInputData = DataUtils.readFile(DataUtils.MAPPING_FILE_PATH);
            for (String mapInputDatum : mapInputData) {
                String[] map = mapInputDatum.split(",");
                int studentID = Integer.parseInt(map[0]);
                int courseID = Integer.parseInt(map[1]);
                Student student = null;
                for (Student student1 : students) {
                    if (student1.getStudentId() == studentID) {
                        student = student1;
                        break;
                    }
                }
                Course course = new Course();
                Set<Course> courseSet = dataMap.keySet();
                for (Course course1 : courseSet) {
                    if (course1.getCourseId() == courseID) {
                        course = course1;
                        break;
                    }
                }
                dataMap.get(course).add(student);
            }
        } catch (IOException e) {
            System.out.println("Data inconsistency: the csv file with mappings hasn't been found.");
            e.printStackTrace();
        }
    }

    public Map<Course, List<Student>> getDataMap() {
        return dataMap;
    }

    public List<Student> getStudents() {
        return students;
    }
}