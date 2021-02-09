package ru.tlepbergenova;

import static ru.tlepbergenova.utils.FileUtils.splitFile;

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
