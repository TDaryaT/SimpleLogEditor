package ru.tlepbergenova;

import static ru.tlepbergenova.utils.FileUtils.splitFile;

public class SplitFiles {

    public static void main(String[] args) {
	    if (args.length == 0 || args.length == 1) {
	        throw new IllegalArgumentException("Не хватает аргументов");
        }

        splitFile(args[0], args[1]);
    }
}
