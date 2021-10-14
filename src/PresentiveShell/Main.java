package PresentiveShell;

import DataStructure.*;

import java.util.LinkedList;

/***
 * @author chong
 */

public class Main {
    private final PCB[] pcbs = new PCB[16];
    private final RCB[] rcbs = new RCB[4];
    //PCB's status.
    private final boolean[] pcbUsed = new boolean[16];
    private final ReadyList rl = new ReadyList();
    //index process input from terminal
    private int index;
    //process which is running
    private int running;


    Main() {
        index = 0;
        running = 0;
        rcbs[0] = new RCB(1);
        rcbs[1] = new RCB(1);
        rcbs[2] = new RCB(2);
        rcbs[3] = new RCB(3);
        creatProcess(Priority.LOW);
    }

    /***
     * find least slot's index in PCB
     */
    public int getLeastIndex() {
        int i;
        for (i = 0; i < pcbs.length; i++) {
            if (!pcbUsed[i]) {
                return i;
            }
        }
        //cause ArrayIndexOutOfBoundsException;
        return -1;
    }

    /***
     *
     * @param priority process's priority which get from terminal
     */
    void creatProcess(Priority priority) {
        index = getLeastIndex();
        pcbUsed[index] = true;
        if (running != index) {
            pcbs[index] = new PCB(running, priority);
            pcbs[running].addChild(index);
        } else {
            pcbs[index] = new PCB(priority);
        }
        // 1 is ready, 0 is blocked
        pcbs[index].setState(1);
        rl.add2ReadyList(index, priority);
        scheduleProcess();//schedule
    }

    /***
     * using recursion to delete all chidren
     * @param index process's index
     */
    void destroyProcess(int index) {
        while (pcbs[index].getChildrenNum() != 0) {
            destroyProcess(pcbs[index].getChild());
        }
        pcbs[pcbs[index].getParent()].deleteChildren(); //delete itself from parent's children list
        if (rl.existInRl(index)) {
            rl.removeProcess(index);
        } else {
            for (RCB rcb : rcbs
            ) {
                rcb.removeFromWaitingList(index);
            }
        }
        pcbs[index] = null; //clean array
        pcbUsed[index] = false;
    }

    void requestResource(int units, int resource) {
        if (rcbs[resource].allocateResource(units)) {
            pcbs[running].requestResource(resource, units);
        } else {
            pcbs[running].setState(0);
            rl.removeProcess(running);
            /**
             * code using for allocate resource to other resources
             * after one process request first time successfully
             * then fail to request secondly
             */
//            Map<Integer, Integer> allOwnedResource = pcbs[running].releaseAllResources();
//            Iterator<Map.Entry<Integer, Integer>> it = allOwnedResource.entrySet().iterator();
//            while (it.hasNext()) {
//                Map.Entry<Integer, Integer> pair = it.next();
//                releaseResource(pair.getKey());
//            }
            rcbs[resource].add2WaitingList(running, units);
            scheduleProcess();
        }

    }

    void releaseResource(int resource, int units) {
        // only allow the process releases less greater than resource they own
        if (pcbs[running].getOwnedResource(resource) >= units) {
            LinkedList<RequestInfo> info = rcbs[resource].releaseResource(units);
            for (RequestInfo item : info
            ) {
                int process = item.getProcess();
                int unit = item.getUnits();
                pcbs[process].setState(1);
                pcbs[process].requestResource(resource, unit);
                rl.add2ReadyList(process, pcbs[process].getPriority());

            }
            pcbs[running].releaseResource(resource);
            scheduleProcess();
            System.out.println(running);
        } else {
            System.out.println(-1);
        }

    }

    void scheduleProcess() {
        running = rl.getRunningProcess();
    }

    void timeOut() {

        rl.timeOut(pcbs[running].getPriority());
        scheduleProcess();
    }

    public int getRunning() {
        return running;
    }

}
