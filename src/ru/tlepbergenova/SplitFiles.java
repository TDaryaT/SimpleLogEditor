package ru.tlepbergenova;

import ru.tlepbergenova.utils.Logs;

/**
 * Консольное приложение, позволяющее разделить один файл на несколько
 * новые файлы помещаются в отдельную папку,
 * которая находится в той же директории, что и программа
 * <p>
 * arg[0] - путь до файла, который мы хотим разделить
 * arg[1] - строка с основным именем новых файлов (name_001, name_002 итд)
 * оно же и является именем папки (\\ + args[1] + \\)
 */
public class SplitFiles {
    public static void main(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException("Не хватает аргументов");
        }
        String nameBigFile = args[0];
        String nameNewFile = args[1];

        Logs logs = new Logs(nameBigFile, nameNewFile);
    }
}
