package PresentiveShell;

import DataStructure.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

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
        index = getLeastIndex();// get least available index from PCB
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
        // call release function to release all resources this process owned.
        Map<Integer, Integer> resourceOwned = pcbs[index].releaseAllResources();
        Iterator<Map.Entry<Integer, Integer>> it = resourceOwned.entrySet().iterator();
        while (((Iterator<?>) it).hasNext()) {
            Map.Entry<Integer, Integer> pair = it.next();
            releaseResource(pair.getKey(), pair.getValue(), index);
        }
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

    void requestResource(int resource, int units) {
        if(rcbs[resource].getInventory() >= units){
        if (rcbs[resource].allocateResource(units)) {
            pcbs[running].requestResource(resource, units);
        } else {// get blocked
            pcbs[running].setState(0);
            rl.removeProcess(running);
            rcbs[resource].add2WaitingList(running, units);
            scheduleProcess();
        }
        }else{
            throw new NullPointerException();
        }
    }

    void releaseResource(int resource, int units, int index) {
        // only allow the process releases less greater than resource they own
        if (pcbs[index].getOwnedResource(resource) >= units && units <= rcbs[resource].getInventory() ) {
            LinkedList<RequestInfo> info = rcbs[resource].releaseResource(units);
            for (RequestInfo item : info
            ) {
                int process = item.getProcess();
                int unit = item.getUnits();
                pcbs[process].setState(1);
                pcbs[process].requestResource(resource, unit);
                rl.add2ReadyList(process, pcbs[process].getPriority());

            }
            pcbs[index].releaseResource(resource);
            scheduleProcess();
        } else {
            throw new NullPointerException();
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
