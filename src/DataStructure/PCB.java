package DataStructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/***
 * state = 1 means ready , 0 means allocated
 * three priorities low, medium, high
 */
public class PCB {

    int state;
    int parent;
    LinkedList<Integer> children;
    Map<Integer, Integer> resources;
    Priority priority;

    public PCB(int parent, Priority priority) {
        this.parent = parent;
        allocatePcb(priority);
    }

    public PCB(Priority priority) {
        allocatePcb(priority);
    }

    private void allocatePcb(Priority priority) {
        this.state = 1;
        this.priority = priority;
        this.children = new LinkedList<>();
        this.resources = new HashMap<>(4);

    }

    public int getChild() {
        return this.children.getFirst();
    }

    public int getParent() {
        return parent;
    }

    public void deleteChildren() {
        this.children.pop();
    }

    public int getChildrenNum() {
        return this.children.size();
    }

    public void addChild(int child) {
        children.add(child);
    }

    public void requestResource(int resource, int units) {
        // request resources might from same Resource
        this.resources.put(resource, resources.getOrDefault(resource, 0) + units);
    }

    public void releaseResource(int resource) {
        this.resources.remove(resource);
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getOwnedResource(int resource) {
        return resources.get(resource);
    }

    public Priority getPriority() {
        return priority;
    }
//     return all resource owned by the process
    public Map<Integer, Integer> releaseAllResources(){
        return resources;
    }

}
