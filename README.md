## Условие
### 1) Формулировка задания

- [x] Задание 1: Разбить имеющийся файл на несколько файлов (5-10 файлов) и залить в одну папку.

- [x] Задание 2: Написать парсер логов, который выполняет следующие действия над логами в п.1:

    - [x] а) С помощью регулярного выражения задать фразу поиска и все найденные строки записать в новый файл

    - [x] б) Вставить разделитель ";"  между значениями в строке во всем файле, например, 17.11.2014;11:43:51.347;TRACE:
     ;9.6.25.83:54650;Count is 1157, и записать в новый файл с расширением csv

В результате должно быть 3 jar файла, которые будут запускаться из командной строки при помощи команды "java -jar <
название jar-файла> <входные параметры>.

### 2) Входные параметры

#### Задание 1: 
2 параметра:    ` <имя_лог_файла>,<постоянная_часть_имени_полученных_файлов>` 

#### Задание 2 а):
3 параметра:    `<фраза_поиска>,<путь_до_лог_файла(ов)>,<имя_нового_файла>`
 
 #### Задание 2 б):
3 параметра:    `<тип_разделителя>,<путь_до_лог_файла(ов)>,<имя_нового_файла>`

## Примеры запуска
Приведены примеры запуска из папки artifacts на тестовом файле `main.log2014-11-17`

#### Задание 1:
Файл `main.log2014-11-17` в той же папке (artifacts)
```
java -jar SplitFiles.jar main.log.2014-11-17 main.log.2014-11-17_logs
```
Полный путь до файла
```
java -jar SplitFiles.jar <path-to-file>\\main.log.2014-11-17 main.log.2014-11-17_logs
```

#### Задание 2 а):

Пример поиска в папке (если второй аргумент - путь до директории, в конце обязательно \\\\):
```
.*Command.=.[A-Z].* .\\main.log.2014-11-17_logs\\ main.log.2014-11-17_find_dir
```
Пример поиска в файле:
```
.*Command.=.[A-Z].* .\\main.log.2014-11-17_logs\\main.log.2014-11-17_000 main.log.2014-11-17_000_find
```

#### Задание 2 б):

```
java -jar AddSeparator.jar ; main.log.2014-11-17_000_find main.log.2014-11-17_separator
```
