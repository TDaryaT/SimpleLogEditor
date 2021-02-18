package ru.tlepbergenova;

import ru.tlepbergenova.utils.Logs;

/**
 * Консольное приложение, с помощью которого можно создать новый файл с разделителями
 * из файла(ов) с логами
 * <p>
 * * args[0] - разделитель (например ;)
 * * args[1] - путь до файла(ов) (если директория - то в конце обязательно \\)
 * * args[2] - название нового файла с разделителями
 */
public class AddSeparator {
    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Не хватает аргументов");
        }

        String separator = args[0];
        String path = args[1];
        String newName = args[2];

        Logs logs = new Logs(path);
        logs.addSeparatorInFiles(separator, newName);
    }
}
