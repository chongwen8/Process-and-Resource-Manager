package PresentiveShell;

import DataStructure.Priority;

import java.util.Scanner;

/**
 * @author chong
 */
public class Shell {
    public static void main(String[] args) {
        final Priority[] priorities = Priority.values();
        /**
         * initialize in as null, if input other commands other than in will throw NullPointerException
         *  then catch sentence will catch this exception and print -1
         */
        Main in = null;
        while (true) {
            // read commands from terminal
//            System.out.print("> ");
            Scanner sc = new Scanner(System.in);
            String command = sc.nextLine();
            String[] commands = command.split(" ");
            try {
                if ("quit".equals(command)) {
                    break;
                } else if ("cr".equals(commands[0])) {
                    in.creatProcess(priorities[Integer.parseInt(commands[1])]);
                    System.out.println(in.getRunning());

                } else if ("de".equals(commands[0])) {
                    int process = Integer.parseInt(commands[1]);
                    in.destroyProcess(process);
                    in.scheduleProcess();//release all resource then schedule once.
                    System.out.println(in.getRunning());


                } else if ("rq".equals(commands[0])) {
                    int process = Integer.parseInt(commands[1]);
                    int units = Integer.parseInt(commands[2]);
                    in.requestResource(units, process);
                    System.out.println(in.getRunning());

                } else if ("rl".equals(commands[0])) {
                    int resource = Integer.parseInt(commands[1]);
                    int units = Integer.parseInt(commands[2]);
                    in.releaseResource(resource, units);
                } else if ("to".equals(commands[0])) {
                    in.timeOut();
                    System.out.println(in.getRunning());

                } else if ("in".equals(commands[0])) {
                    in = new Main();
                    System.out.println(in.getRunning());

                } else if (command.isEmpty()) {
                } else {
                    System.out.println(-1);
                }
            } catch (Exception e) {
//                e.printStackTrace(); find where wrong
                System.out.println(-1);
            }

        }
    }
}