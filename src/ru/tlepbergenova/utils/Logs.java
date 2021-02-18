package ru.tlepbergenova.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Класс для работы с логами
 * Логи представляют из себя лист с файлами логов
 */
public class Logs {
    private static final int SIZE_OF_FILES = 1024 * 1024 * 7; // 7MB
    private List<File> splitFiles = new ArrayList<>();

    /**
     * Конструктор создания файлов с логами из одного большого файла в папку с
     * файлами с логами размера SIZE_OF_FILES.
     * Директория с логами создается в директории программы.
     *
     * @param pathToFile  - путь до основного файла с логами (большой файл)
     *                    пример (файл в директории программы): main.log.2014-11-17
     *                    пример (абсолютный путь): C:\\Users\\tdaryat\\Desktop\\main.log.2014-11-17
     * @param nameNewFile - основная часть названия новых файлов с папкой (и название папки)
     *                    пример: main.log.2014-11-17_logs
     */
    public Logs(String pathToFile, String nameNewFile) {
        String dir = ".\\" + nameNewFile + "\\";
        boolean mkdirs = new File(dir).mkdirs();
        if (!mkdirs) {
            throw new IllegalArgumentException("Невозможно создать директорию: " + nameNewFile);
        }
        splitFiles = splitFile(pathToFile, nameNewFile);
    }

    /**
     * Конструктор создания файлов с логами из папки, в которой они хранятся.
     *
     * @param pathToFiles - путь до папки с логами
     *                    пример(файл в директории программы): .\\main.log.2014-11-17_logs\\
     *                    пример(абсолютный путь): C:\\Users\\tdaryat\\Desktop\\main.log.2014-11-17_logs\\
     */
    public Logs(String pathToFiles) {
        File dir = new File(pathToFiles);
        File[] files = dir.listFiles();
        if (files == null) {
            splitFiles.add(dir);
        } else {
            splitFiles = Arrays.asList(files);
        }
    }

    /**
     * Функция, которая по регулярному выражению находит строку в файле/фалах, в которую входит
     * данное выражение и копирует в новый файл. Если в аргументе передан файл до директории,
     * будет осуществлен поиск оп каждому файлу внутри директории
     * Новый файл создается в директории программы.
     *
     * @param regularExpression - регулярное выражение, по котором ищем совпадение в логах
     * @param nameNewFile       - имя нового файла, в который записывается результат поиска
     */
    public void findInFiles(String regularExpression, String nameNewFile) {
        for (File file : splitFiles) {
            findInFile(regularExpression, file, nameNewFile);
        }
    }

    /**
     * Функция, которая добавляет сепаратор в файл(ы) из списка с логами
     * Новый файл с результатом создается в директории программы.
     *
     * @param separator   - разделитель, который добавляется
     * @param nameNewFile - имя нового файла
     */
    public void addSeparatorInFiles(String separator, String nameNewFile) {
        for (File file : splitFiles) {
            addSeparatorInFile(separator, file, nameNewFile);
        }
    }

    /**
     * Статическая функция, для создания файлов с логами из одного большого
     * файла в папку с файлами с логами размера SIZE_OF_FILES.
     * Директория с логами создается в директории программы.
     *
     * @param nameBigFile - путь до основного файла с логами (большой файл)
     *                    пример (файл в директории программы): main.log.2014-11-17
     *                    пример (абсолютный путь): C:\\Users\\tdaryat\\Desktop\\main.log.2014-11-17
     * @param nameNewFile - основная часть названия новых файлов с папкой (и название папки)
     *                    пример: main.log.2014-11-17_logs
     */
    public static List<File> splitFile(String nameBigFile, String nameNewFile) {
        List<File> splitFileNames = new ArrayList<>();
        String dir = ".\\" + nameNewFile + "\\";
        int partCounter = 0;
        byte[] buffer = new byte[SIZE_OF_FILES];
        File bigFile = new File(nameBigFile);

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(bigFile))) {
            int bytesAmount;
            while ((bytesAmount = bis.read(buffer)) > 0) {
                String filePartName = String.format("%s_%03d", nameNewFile, partCounter++);
                File newFile = new File(dir + filePartName);
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, bytesAmount);
                }
                splitFileNames.add(newFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return splitFileNames;
    }

    /**
     * Функция, которая по регулярному выражению находит строку в файле, в которую входит
     * данное выражение и копирует в новый файл.
     * Новый файл создается в директории программы, запись в файл производится на продолжение.
     *
     * @param regularExpression - регулярное выражение, по котором ищем совпадение в логах
     * @param file              - файл в котором осуществляется поиск
     * @param nameNewFile       - имя нового файла, в который записывается результат поиска
     */
    public static void findInFile(String regularExpression, File file, String nameNewFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
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
     * Функция, которая добавляет разделитель в файл из списка с логами
     * Новый файл с результатом создается в директории программы.
     * Пример: 17.11.2014;11:43:51.347;TRACE:;9.6.25.83:54650;Count is 1157
     * !При изменении структуры логов изменить расположение добавления разделителя!
     *
     * @param separator   - разделитель, который добавляется
     * @param nameNewFile - имя нового файла с результатом
     * @param file        - файл в который хотим добавить разделитель
     */
    public static void addSeparatorInFile(String separator, File file, String nameNewFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(nameNewFile, true))) {
            List<String> findLogs = reader
                    .lines()
                    .map(String::valueOf)
                    .map(s -> s.replaceAll("\\t", separator))
                    .map(s -> s.replaceAll("(?<=\\d{2}\\.\\d{2}\\.\\d{4})\\s" +
                            "(?=\\d{2}:\\d{2}:\\d{2}\\.\\d{3})", separator))
                    .map(s -> s.replaceAll("(?<=\\d{2}:\\d{2}:\\d{2}\\.\\d{3})\\s" +
                            "(?=.+?:)", separator))
                    .map(s -> s.replaceAll("(?<=[A-Z])\\s" +
                            "(?=.+?)", separator))
                    .map(s -> s + separator)
                    .collect(Collectors.toList());

            for (String findLog : findLogs) {
                writer.write(findLog + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}