package ru.tlepbergenova;

import ru.tlepbergenova.utils.Logs;

/**
 * Консольное приложение, с помощью которого можно найти по регулярному выражению
 * находит строки в файле/файлах и заносит их в отдельный файл
 * <p>
 * args[0] - регулярное выражение (пробелы в регулярном выражении не допускаются)
 * args[1] - путь до файла/файлов (если директория - то в конце обязательно \\)
 * args[2] - название нового файла
 */
public class FindInLogs {
    public static void main(String[] args) {
        if (args.length < 3) {
            throw new IllegalArgumentException("Не хватает аргументов");
        } else if (args.length > 3) {
            throw new IllegalArgumentException("Аргументов слишком много " +
                    "(пробелы в регулярном выражении не допускаются, только как \\s)");
        }
        String regExp = args[0];
        String path = args[1];
        String newName = args[2];

        Logs logs = new Logs(path);
        logs.findInFiles(regExp, newName);
    }
}
