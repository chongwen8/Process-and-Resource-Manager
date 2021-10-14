package DataStructure;

/**
 * Information requested by the process including process's index and units
 * @author chong
 */
public class RequestInfo {
    private final int process;
    private final int units;

    RequestInfo(int process, int units) {
        this.process = process;
        this.units = units;
    }

    public int getProcess() {
        return process;
    }

    public int getUnits() {
        return units;
    }
}
