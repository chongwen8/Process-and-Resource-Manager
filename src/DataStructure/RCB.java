package DataStructure;

import java.util.LinkedList;

/**
 * inventory: the initial number of units of the resource
 * state: the number of currently available units.
 */
public class RCB {
    int state;
    int inventory;
    LinkedList<RequestInfo> waitingList;

    public RCB(int inventory) {
        this.inventory = inventory;
        this.state = inventory;
        waitingList = new LinkedList<>();
    }

    public boolean allocateResource(int units) {
            if (units > state) {
                return false;
            } else {
                state -= units;
                return true;
            }
    }


    public LinkedList<RequestInfo> releaseResource(int units) {
        // mistakenly release more resource than Resource own
        if((state  + units) > inventory){
            return null; // cause NullPointerException
        }
        LinkedList<RequestInfo> info = new LinkedList<>();
        state += units;
        // unblock other process in waiting List
        while (!waitingList.isEmpty() && state > 0) {
            RequestInfo temp = waitingList.getFirst();
            if (state >= temp.getUnits()) {
                waitingList.pop();
                state -= temp.getUnits();
                info.add(temp);
            } else {
                break;
            }
        }
        return info;
    }

    public void add2WaitingList(int process, int units) {
        waitingList.add(new RequestInfo(process, units));
    }

    public void removeFromWaitingList(int process) {
        LinkedList<RequestInfo> removeItems = new LinkedList<>();
        for (RequestInfo info :
                waitingList) {
            if (info.getProcess() == process) {
                removeItems.add(info);
            }
        }
        if (!waitingList.isEmpty()) {
            waitingList.removeAll(removeItems);
        }
    }

    public int getInventory() {
        return inventory;
    }
}
