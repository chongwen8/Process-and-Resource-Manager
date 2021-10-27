package PresentiveShell;

import DataStructure.Priority;

import java.io.File;
import java.util.LinkedList;

/**
 * @author chong
 */
public class Decision {
    final Priority[] priorities = Priority.values();
    Main in = null;
    LinkedList<String> outputList = new LinkedList<String>();

    void decision(String command){
        String[] commands = command.split(" ");
        try {
            if ("cr".equals(commands[0])) {
                if(commands[1].equals("1") || commands[1].equals("2")){
                in.creatProcess(priorities[Integer.parseInt(commands[1])]);
                System.out.println(in.getRunning());
                outputList.add(Integer.toString(in.getRunning()));
                }else{
                    System.out.println(-1);
                    outputList.add("-1");
                }

            } else if ("de".equals(commands[0])) {
                int process = Integer.parseInt(commands[1]);
                in.destroyProcess(process);
                in.scheduleProcess();//release all resource then schedule once.
                System.out.println(in.getRunning());
                outputList.add(Integer.toString(in.getRunning()));

            } else if ("rq".equals(commands[0])) {
                if (in.getRunning() != 0){
                int process = Integer.parseInt(commands[1]);
                int units = Integer.parseInt(commands[2]);
                in.requestResource(units, process);
                outputList.add(Integer.toString(in.getRunning()));
                }else{
                    System.out.println(-1);
                    outputList.add("-1");
                }

            } else if ("rl".equals(commands[0])) {
                int resource = Integer.parseInt(commands[1]);
                int units = Integer.parseInt(commands[2]);
                in.releaseResource(resource, units);
                System.out.println(in.getRunning());
                outputList.add(Integer.toString(in.getRunning()));
            } else if ("to".equals(commands[0])) {
                in.timeOut();
                System.out.println(in.getRunning());
                outputList.add(Integer.toString(in.getRunning()));

            } else if ("in".equals(commands[0])) {
                in = new Main();
                System.out.println(in.getRunning());
                outputList.add(Integer.toString(in.getRunning()));

            } else if (command.isEmpty()) {
                outputList.add("\n");
            }
            else {
                System.out.println(-1);
                outputList.add("-1");
            }
        } catch (Exception e) {
//            e.printStackTrace(); //find where wrong
            System.out.println(-1);
            outputList.add("-1");
        }
    }
}
