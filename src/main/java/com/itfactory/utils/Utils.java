package com.itfactory.utils;

import com.itfactory.data.DataSaver;
import com.itfactory.model.*;
import com.itfactory.exceptii.*;
import java.io.IOException;
import java.util.*;

public class Utils {
    private static final Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    //metoda prin care se citesc de la tastatura datele unui curs; optiunea 2 din switch
    public static Course ReadCourseInfo() throws NotUniqueException {
//        try {
        System.out.print("Type the course ID: ");
        int courseId = Integer.parseInt(scanner.nextLine());
        System.out.print("Type the course NAME: ");
        String courseName = scanner.nextLine();
        System.out.print("Type the PRICE of the course: ");
        double price = Double.parseDouble(scanner.nextLine());
        return new Course(courseId, courseName, price);
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        return null;
    }


    //metoda care citeste de la tastatura datele unui student
    public static Student readStudentInfo() {
        System.out.print("Please type the student ID: ");
        int studentId = Integer.parseInt(scanner.nextLine());
        System.out.print("Please type the student name: ");
        String studentName = scanner.nextLine();
        System.out.print("Please type the available budget of the student: ");
        double budget = Double.parseDouble(scanner.nextLine());
        System.out.println("The student's details are: [" + studentId + ", " + studentName + ", " + budget + "]");
        return new Student(studentId, studentName, budget);
    }


    //metoda care afiseaza meniul cu optiunile disponibile pentru utilizator; optiunea 5 din switch
    public static void printMenu() {
        System.out.println("Main menu: \n" +
                "0 - Exit the program\n" +
                "1 - Display the available courses\n" +
                "2 - Add a new course\n" +
                "3 - Add a new student to a course\n" +
                "4 - Display the students enrolled on a certain course\n" +
                "5 - Display the options of the Menu.");
    }


    //metoda care afiseaza cursurile disponibile (optiunea 1 din switch)
    public static void displayCourses(Map<Course, List<Student>> map) {
        Set<Course> courseList = map.keySet();
        List<Course> list = new ArrayList<>(courseList);   //obtinem o lista din set
        list.sort(new CourseIdComparator());
//        Collections.sort(list, new CourseIdComparator());  //sortam lista dupa courseId
        for (Course course : list) {                       //afisam elementele listei sortate
            System.out.println("[" + course + "]");
        }
    }


    //metoda care afiseaza studentii la un anumit curs precizat de utilizator (optiunea 4 din switch)
    public static void displayStudents(Map<Course, List<Student>> map) {
        System.out.println("This is the list of the courses we offer: ");
        displayCourses(map);
        System.out.println("In order to see the list of the students enrolled by now on a certain course, please type the course ID: ");
        int courseID = Integer.parseInt(scanner.nextLine());
        for (Map.Entry<Course, List<Student>> entry : map.entrySet()) {
            if (entry.getKey().getCourseId() == courseID) {
                System.out.println(entry.getValue());
            }
        }
    }


    public static void addStudent(Map<Course, List<Student>> map) throws CourseException {
        System.out.println("You chose to add a new student for registration.");
        Student student = Utils.readStudentInfo();
        System.out.println("Write down the name of the course in which you would like the student [" + student + "] to enroll: ");
        Course soughtCourse = searchCourseInMap(map);
        if (soughtCourse != null) {
            //verificam daca lungimea listei de studenti depaseste 7 (deci, daca are 6 studenti pentru ca studentul nostru sa fie al 7-lea
            if (map.get(soughtCourse).size() <= 6) {
                if (student.getBudget() >= soughtCourse.getPrice()) {
                    map.get(soughtCourse).add(student);
                    System.out.println("Congratulations! You have successfully added the student: [" + student + "] to this course [" + soughtCourse + "].");
                    student.setBudget(student.getBudget() - soughtCourse.getPrice());
                    System.out.println("The new budget of [" + student + "] is [" + student.getBudget() + " euros]");

                } else {
                    System.out.println("The student [" + student + "] hasn't enough money to cover this course :(");
                }
            }
            //if list >= 6 (deci, in lista sunt deja 7 studenti inscrisi 0, 1, 2, 3, 4, 5, 6
            else {
                System.out.println("For this course [" + soughtCourse + "] there are already 7 students enrolled. We will search for other available courses " +
                        "for you. See below the results: ");
                int availableCourses = 0;
                for (Map.Entry<Course, List<Student>> entry : map.entrySet()) {
                    if (entry.getValue().size() <= 6) {
                        availableCourses++;
                        System.out.println("Available course: " + entry.getKey());
                    }
                }
                if (availableCourses == 0) {
                    System.out.println("There's no available course at the moment.");
                } else {
                    System.out.print("Would you like to enroll the student on a different course now? Type \"yes\" if you do, or any other key if otherwise: ");
                    String userAnswer = scanner.nextLine();
                    if (userAnswer.equals("yes")) {
                        System.out.println("Write down the name of the course in which you would like the student [" + student + "] to enroll: ");
                        enrollStudentOnACourse(map, student);
                    } else {
                        System.out.println("You'll get back to the main Menu.");
                    }
                }
            }
        } else {
            System.out.println("The course hasn't been found in our list. " +
                    "Please make sure that the name of the course you're typing is among these below: ");
            Utils.displayCourses(map);
            throw new CourseException("Course name incorrect.");
        }

    }


    private static Course searchCourseInMap(Map<Course, List<Student>> map) {
        String courseName = scanner.nextLine();
        Course soughtCourse = null;
        Set<Course> courseSet = map.keySet();
        for (Course course : courseSet) {
            if (courseName.equals(course.getCourseName())) {
                soughtCourse = course;
                break;
            }
        }
        return soughtCourse;
    }


    private static void enrollStudentOnACourse(Map<Course, List<Student>> map, Student student) {
        Course course = searchCourseInMap(map);
        if (course != null) {
            if (student.getBudget() >= course.getPrice()) {
                map.get(course).add(student);
                System.out.println("Congratulations! You have successfully added the student: [" + student + "] to this course [" + course + "].");
                student.setBudget(student.getBudget() - course.getPrice());
                System.out.println("The new budget of [" + student + "] is [" + student.getBudget() + " euros]");
            }
        }
    }


    public static void parseOptions(Map<Course, List<Student>> map) {
        System.out.print("\nType one of the options of the main Menu: ");
        String optionString = scanner.nextLine();
        int option;
        try {
            option = Integer.parseInt(optionString);  //daca optionString este alt caracter decat o numar, atunci se duce pe catch
            switch (option) {
                case 0: {
                    try {
                        System.out.println("You chose to exit the program.");
                        DataSaver saveDataNow = new DataSaver();
                        saveDataNow.saveData(map);
                        System.exit(0);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case 1: {
                    System.out.println("You chose to display the available courses. The result of your query is: ");
                    if (map.isEmpty()) {
                        System.out.println("No course added until now.");
                    } else {
                        Utils.displayCourses(map);
                    }
                }
                break;
                case 2: {
                    try {
                        System.out.println("You chose to add a new course. Please fill in the following details: ");
                        Course course = Utils.ReadCourseInfo();
                        map.put(course, new ArrayList<>());
                        System.out.println("You have successfully added: [" + course + "]");
                    } catch (NotUniqueException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case 3: {
                    try {
                        Utils.addStudent(map);
                    } catch (CourseException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case 4: {
                    System.out.println("Students enrolled on a certain course");
                    Utils.displayStudents(map);
                }
                break;
                case 5: {
                    Utils.printMenu();
                }
                break;
                default: {
                    System.out.println("This option doesn't exist.");
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("The value of the field has to be numerical.\nIn order to see the main Menu, please type 5.");
        }
    }
}