package ru.tlepbergenova;

import ru.tlepbergenova.utils.FileUtils;

public class FindInLogs {
    public static void main(String[] args) {
        if (args.length <= 1) {
            throw new IllegalArgumentException("Не хватает аргументов");
        }
        String regExp = args[0];
        String path = args[1];
        String newName = args[2];
        FileUtils.findInFile(regExp, path, newName);
    }
}
