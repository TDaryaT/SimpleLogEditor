package ru.tlepbergenova.utils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {
    /**
     * @param path путь до файла
     * @return лист со строками из файла
     */
    public static List<String> addFileLinesToList(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            return reader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            return new LinkedList<>();
        }
    }

    /**
     * функция разделяет общий файл на несколько (по 7 MB)
     * и кладет их в dir с названием nameNewFile
     *
     * @param nameBigFile - название общего файла с логами
     * @param nameNewFile - постоянная часть названий разбитых фалов
     *                    nameNewFile_1
     *                    nameNewFile_2 итд
     */
    public static void splitFile(String nameBigFile, String nameNewFile) {
        String dir = ".\\" + nameNewFile + "_logs\\";
        boolean mkdirs = new File(dir).mkdirs();
        int partCounter = 0;
        int sizeOfFiles = 1024 * 1024 * 7;// 7MB
        byte[] buffer = new byte[sizeOfFiles];
        File bigFile = new File(nameBigFile);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(bigFile))) {
            int bytesAmount;
            while ((bytesAmount = bis.read(buffer)) > 0) {
                String filePartName = String.format("%s_%03d", nameNewFile, partCounter++);
                File newFile = new File(dir + filePartName);
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, bytesAmount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Функция, которая по регулярному выражению находит строку в файле, в которую входит
     * данное выражение и копирует в новый файл
     *
     * @param regularExpression - регулярное выражение, по котором ищем совпадение в логах
     * @param pathToFile        - путь до файла, в которых осуществляется поиск
     * @param nameNewFile       - имя нового файла, в который записывается результат поиска
     */
    public static void findInFile(String regularExpression, String pathToFile, String nameNewFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(nameNewFile, true))) {
            List<String> findLogs = reader
                    .lines()
                    .map(String::valueOf)
                    .filter(s -> s.matches(regularExpression))
                    .collect(Collectors.toList());

            for (String findLog : findLogs) {
                writer.write(findLog + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Функция, которая по регулярному выражению находит строку в файле/фалах, в которую входит
     * данное выражение и копирует в новый файл. Если в аргументе передан файл до директории,
     * будет осуществлен поиск оп каждому файлу внутри директории (рекурсивно)
     *
     * @param regularExpression - регулярное выражение, по котором ищем совпадение в логах
     * @param pathToFiles       - путь до файла/директории с файлами, в которых осуществляется поиск
     * @param nameNewFile       - имя нового файла, в который записывается результат поиска
     */
    public static void findInFiles(String regularExpression, String pathToFiles, String nameNewFile) {
        File files = new File(pathToFiles);
        boolean isDir = files.isDirectory();
        if (!isDir) {
            findInFile(regularExpression, pathToFiles, nameNewFile);
        } else {
            String[] nameFiles = files.list();
            assert nameFiles != null;
            for (String nameFile : nameFiles) {
                isDir = new File(pathToFiles + nameFile).isDirectory();
                if (isDir) {
                    findInFiles(regularExpression, pathToFiles + nameFile, nameNewFile);
                } else {
                    findInFile(regularExpression, pathToFiles + nameFile, nameNewFile);
                }
            }
        }
    }
}