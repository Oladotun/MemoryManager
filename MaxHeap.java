import java.util.ArrayList;

/** 
 * Implementation of PriorityQueue with max as root.
 * @author Connie Chang 
 * @author Lara Gatehouse
 * @author Oladotun Opasina
 * @author Whitney Kim
 * @param <T> the base type of data in a node
 */

public class MaxHeap<T extends Comparable<? super T>>
    implements PriorityQueue<T> {

    /** Inner node class.  Do not make this static because you want
        the T to be the same T as in the BST header.
    */
    

    private ArrayList<T> heap;

    /**Constructor for heap that sets up arraylist.**/
    public MaxHeap() {
        this.heap = new ArrayList<T>();
        this.heap.add(null); //the wasted first position of array
    }

    /**Gives how many elements are in the heap.
     * 
     * @return The size of the heap
     */
    public int size() {
        return this.heap.size() - 1;
    }
    
    /**Gives the ArrayList version of the heap.
     * 
     * @return ArrayList<T> The heap
     */
    public ArrayList<T> getHeap() {
        ArrayList<T> temp = new ArrayList<T>();
        for (int i = 1; i <= this.size(); i++) {
            temp.add(this.heap.get(i));
        }
        return temp;
    }
    
    /**Gives the maximum in the heap.
     * @return The object with the highest value
     */
    public T best() {
        if (this.isEmpty()) {
            return null;
        }
        return this.heap.get(1);
    }

    /**Checks if the heap is empty.
     * 
     * @return True if empty. Otherwise false
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**Inserts a piece of data into heap without duplicates.
     * 
     * @param val The data to be entered
     * @return boolean True if inserted. Otherwise, false
     */
    public boolean insert(T val) {
        if (this.heap.contains(val)) {
            return false;
        }
        
        this.heap.add(val);
        int num = this.heap.size() - 1; //new node should be in last position
        int parent = num / 2;
        while (parent > 0) {
            T me = this.heap.get(num);
            T rent = this.heap.get(parent);
            if (me.hashCode() > rent.hashCode()) { 
                //our hashCode returns memSize
                this.heap.set(num, rent);
                this.heap.set(parent, me);
                num = parent;
                parent = num / 2;
            } else {
                break;
            }
        }
        return true;
    }

    /**Deletes the maximum in this heap.
     * 
     * @return The data that was deleted
     */
    public T deleteBest() {
        T toRemove;
        if (this.isEmpty()) {
            return null;
        } else if (this.size() == 1) { 
            //root is only element
            toRemove = this.heap.remove(1);
            return toRemove;
        }

        int temp = this.size(); //last index
        T curr = this.heap.get(temp); //last index
        toRemove = this.heap.get(1); //keep track of old max
        this.heap.set(1, curr); //set last item to be new first
        this.heap.remove(temp); //remove the last space of array
        
        int max = 1;
        int temp1 = 2 * max;
        int temp2 = (2 * max) + 1;
        int temp3 = -1;
        T dat = this.heap.get(max);
        int compare1, compare2;
        T right, left;
        
        while (temp1 <= this.size()) {
            compare1 = dat.compareTo(this.heap.get(temp1));
            if (!(temp2 < this.size())) {
                compare2 = 0;
                temp2 = temp1;
            } else {
                compare2 = dat.compareTo(this.heap.get(temp2));
            }
            if (compare1 < 0 || compare2 < 0) { //at least one child is larger
                left = this.heap.get(temp1);
                right = this.heap.get(temp2);
                if (left.compareTo(right) < 0) {
                    temp3 = temp2;
                } else {
                    temp3 = temp1;
                }
                curr = this.heap.get(temp3);
                this.heap.set(temp3, this.heap.get(max));
                this.heap.set(max, curr);
                max = temp3;
                temp1 = 2 * max;
                temp2 = (2 * max) + 1;
                dat = this.heap.get(max);
            } else { //neither children are larger
                break;
            }   
        }
        return toRemove;
    }
    
    /**Checks if the heap contains a certain object.
     * 
     * @param o The object to look for
     * @return boolean True if there. Otherwise false
     */
    public boolean contains(Object o) {
        for (int i = 1; i < this.size(); i++) {
            if (this.heap.get(i).equals(o)) {
                return true;
            }
        }
        return false;
    }
    
    /**Creates toString for heap.
     * @return The string
     */
    public String toString() {
        String s = "";
        if (this.isEmpty()) {
            return "[]";
        }
        for (int i = 1; i < this.size(); i++) {
            s += this.heap.get(i) + ", ";
        }
        //to get commas right, last element is outside loop
        s += "" + this.heap.get(this.size());
        return "[" + s + "]";
    }
    
    /**Checks if this heap is equal to another object.
     * @param o The other object
     * @return boolean True if equal. Otherwise false
     */
    public boolean equals(Object o) {
        if (!(o instanceof MaxHeap)) {
            return false;
        }
        MaxHeap<T> other = (MaxHeap<T>) o;
        if (this.size() != other.size()) {
            return false;
        }
        return this.toString().equals(other.toString());
    }
    
    /**Gives the hash code of the heap.
     * @return int The hash code
     */
    public int hashCode() {
        return this.toString().hashCode();
    }
    
    /**Resets the heap to a new arraylist.
     * 
     * @param list The arraylist representation of the new heap
     */
    public void setHeap(ArrayList<T> list) {
        this.heap = new ArrayList<T>();
        for (int i = 0; i < list.size(); i++) {
            this.insert(list.get(i));
        }
        this.heap.add(0, null);
    }
    
}