package ru.tlepbergenova.utils;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

public class FileUtils {
    /**
     * @param path путь до файла
     * @return лист со строками из файла
     * @throws IOException - ошибки, связанные с чтением файла (например, файл не существует)
     */
    public static List<String> addFileLinesToList(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        return reader.lines().collect(Collectors.toList());
    }

    /**
     * функция разделяет общий файл на несколько (по 50 000 строк)
     *
     * @param pathBigFile  - название общего файла с логами
     * @param pathNewFiles - постоянная часть названий разбитых фалов
     *                     nameNewFiles_1
     *                     nameNewFiles_2 итд
     */
    public static void splitFile(String pathBigFile, String pathNewFiles) {
        int partCounter = 0;
        int sizeOfFiles = 1024 * 1024 * 7;// 7MB
        byte[] buffer = new byte[sizeOfFiles];
        File bigFile = new File(pathBigFile);

        try (FileInputStream fis = new FileInputStream(bigFile);
             BufferedInputStream bis = new BufferedInputStream(fis)) {

            int bytesAmount;
            while ((bytesAmount = bis.read(buffer)) > 0) {
                String filePartName = String.format("%s_%03d", pathNewFiles, partCounter++);
                File newFile = new File(bigFile.getParent(), filePartName);
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, bytesAmount);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}