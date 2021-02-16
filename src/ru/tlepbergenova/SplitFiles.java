package ru.tlepbergenova;

import static ru.tlepbergenova.utils.FileUtils.splitFile;

/**
 * Консольное приложение, позволяющее разделить один файл на несколько
 * новые файлы помещаются в отдельную папку (\\ + args[1] + _logs\\),
 * которая находится в той же директории, что и основной
 *
 * arg[0] - путь до файла, который мы хотим разделить
 * arg[1] - строка с основным именем новых файлов (name_001, name_002 итд)
 *        - (опциональный) если отсутствует, берется arg[0]
 */
public class SplitFiles {
    public static void main(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Не хватает аргументов");
        }
        String nameBigFile = args[0];
        String nameNewFile = args.length == 1 ? args[0] : args[1];

        splitFile(nameBigFile, nameNewFile);
    }
}
