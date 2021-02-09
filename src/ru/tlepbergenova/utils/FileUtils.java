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
     *
     * @param nameBigFile - название общего файла с логами
     * @param nameNewFile - постоянная часть названий разбитых фалов
     *                    nameNewFile_1
     *                    nameNewFile_2 итд
     */
    public static void splitFile(String nameBigFile, String nameNewFile) {
        String dir = ".\\" + nameBigFile + "_logs\\";
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
     * @param regularExpression - регулярное выражение, по котором ищем совпадение в логах
     * @param pathToFiles       - путь до файла(ов) в которых осуществляется поиск
     * @param nameNewFile       - имя нового файла, в который записывается результат поиска
     */
    public static void findInFile(String regularExpression, String pathToFiles, String nameNewFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(pathToFiles));
             BufferedWriter writer = new BufferedWriter(new FileWriter(nameNewFile))) {
            List<String> findLogs = reader
                    .lines()
                    .map(String::valueOf)
                    .filter(s -> s.matches(regularExpression))
                    .collect(Collectors.toList());

            for (String findLog : findLogs) {
                writer.write(findLog);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}