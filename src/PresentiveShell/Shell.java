package PresentiveShell;

import DataStructure.Priority;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * @author chong
 */
public class Shell {
    public static void main(String[] args) throws IOException {
        final Priority[] priorities = Priority.values();
        /**
         * initialize in as null, if input other commands other than in will throw NullPointerException
         *  then catch sentence will catch this exception and print -1
         */
        Decision main = new Decision();
        while (true) {
            // read commands from terminal
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            if ("quit".equals(command)) {
                break;
            } else if (command.startsWith("auto")) {
                String[] commands = command.split(" ");
                ReadWrite rw = new ReadWrite();
                File cwd = new File("data");
                File inputFile = new File(cwd.getPath() + "/" + commands[1] +".txt");
                LinkedList<String> inputList = rw.readFile(inputFile);
                File outputFile = new File(cwd.getPath() + "/output.txt");
                for (String str : inputList) {
                    System.out.println(str);
                    main.decision(str);
                }
                rw.writeFile(outputFile, main.outputList);
                break;}
            else {
                main.decision(command);
            }
        }
    }

}