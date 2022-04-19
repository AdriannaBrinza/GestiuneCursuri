package com.itfactory.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class DataUtils {
    public static final String COURSE_FILE_PATH = "cursuri.csv";
    public static final String STUDENT_FILE_PATH = "studenti.csv";
    public static final String MAPPING_FILE_PATH = "mapari.csv";

    //metoda care citeste un fisier intreg.
    public static List<String> readFile(String filePathStr) throws IOException {
        Path path = Paths.get(filePathStr);
        File file = new File(filePathStr);
        //FILE EXISTS?
        if (file.exists()) {
            return Files.readAllLines(path);
        } else {
            throw new IOException();
        }
    }

    //metoda care scrie un text intr-un fisier.
    public static Path writeFile(String filePathStr, String content) throws IOException {
        Path path = Paths.get(filePathStr);
        return Files.write(path, content.getBytes());
    }
}