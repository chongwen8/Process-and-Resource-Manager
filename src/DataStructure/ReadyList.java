package DataStructure;

import java.util.LinkedList;

/**
 * @author chong
 */
public class ReadyList {
    private final LinkedList<Integer> readyListLow = new LinkedList<>();
    private final LinkedList<Integer> readyListMedium = new LinkedList<>();
    private final LinkedList<Integer> readyListHigh = new LinkedList<>();

    public void removeLowProcess(int index) {
        readyListLow.remove((Integer) index);
    }

    public void removeMediumProcess(int index) {
        readyListMedium.remove((Integer) index);
    }

    public void removeHighProcess(int index) {
        readyListHigh.remove((Integer) index);
    }

    public boolean existInRl(int index) {
        return (readyListLow.contains(index)
                || readyListMedium.contains(index)
                || readyListHigh.contains(index));
    }

    public int getRunningProcess() {
        if (!readyListHigh.isEmpty()) {
            return readyListHigh.getFirst();
        } else if (!readyListMedium.isEmpty()) {
            return readyListMedium.getFirst();
        } else {
            return readyListLow.getFirst();
        }

    }

    /**
     * remove process from RL
     */
    public void removeProcess(int index) {
        if (readyListHigh.contains(index)) {
            removeHighProcess(index);
        } else if (readyListMedium.contains(index)) {
            removeMediumProcess(index);
        } else {
            removeLowProcess(index);
        }
    }

    public void add2ReadyList(int index, Priority priority) {
        switch (priority) {
            case LOW:
                readyListLow.add(index);
                break;
            case MEDIUM:
                readyListMedium.add(index);
                break;
            case HiGH:
                readyListHigh.add(index);
                break;
            default:
                break;
        }

    }

    /**
     * move process in RL from the head to the tail
     */
    public void timeOut(Priority priority) {
        switch (priority) {
            case LOW:
                readyListLow.add(readyListLow.pop());
                break;
            case MEDIUM:
                readyListMedium.add(readyListMedium.pop());
                break;
            case HiGH:
                readyListHigh.add(readyListHigh.pop());
                break;
            default:
                break;
        }
    }

}
