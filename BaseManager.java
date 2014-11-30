import java.util.ArrayList;

/**BaseManager contains data and methods common to all three schemes.
 * 
 * @author Connie Chang 
 * @author Lara Gatehouse
 * @author Oladotun Opasina
 * @author Whitney Kim
 *
 */
public class BaseManager implements MemoryManager {
    
    /**Conversion from nanoseconds to microseconds.*/
    public static final double UNIT_CONVERT = 1000;
    
    /**Sorter class to do quicksort and bucketsort.**/
    protected Sorters<GeneralMemory> sortingThing;
    /** Keeps track of amount of time it defrags. */
    protected int defragCount;
    /** Keeps track of how many times allocation fails. */
    protected int totalFails;
    /** Keeps track of total size of failed allocations. */
    protected double totalFailsize;
    /** Keeps track of total time to process allocation. */
    protected double totalTime;
    
    /**The number of times we have allocated.**/
    protected int alloCount; 
    
    
    /**The number of times we sorted.**/
    protected int numSort;
    
    /**The total time spent on quicksort.**/ 
    long totalTime1;
    /** The total time spent on bucketsort. */
    long totalTime2;
    /**The sum of sizes that were sorted.*/
    double totalSize1;
    /** Boolean value that shows if defragged or not. */
    boolean defrag;
    
    /**Constructor that creates variables and sets all numbers to 0.*/
    public BaseManager() {
        this.sortingThing = new Sorters<GeneralMemory>();
        this.defragCount = 0;
        this.totalFails = 0;
        //this.totalTimeSort1 = 0;
        //this.totalTimeSort2 = 0;
        this.totalTime1 = 0;
        this.totalTime2 = 0;
        this.totalSize1 = 0;
        this.totalFailsize = 0;
    }
    
    /**Allocates memory of a certain size.
     * @param size The size of memory to be allocated
     * @return boolean True if allocated. Otherwise false
     */
    public int allocate(int size) {
        return -1;
    }
    
    /**Deallocates memory with a certain ID.
     * @param i The ID to delete.
     * @return boolean True if deallocated. Otherwise false
     */
    public GeneralMemory deallocate(int i) {
        return null;
    }
    
    /** Checks if defragged or not and returns true if yes, or false.
     *@return boolean True if defrag happened. Otherwise false
     */
    public boolean didDefrag() {
        return this.defrag;
    }
    
    /**Defragments adjacent free memory blocks.
     * @param list The list of GeneralMemory to defragment.
     * @return ArrayList<GeneralMemory> The new defragmented memory list
     */
    public ArrayList<GeneralMemory> defrag(ArrayList<GeneralMemory> list) {
        ArrayList<GeneralMemory> copy = new ArrayList<GeneralMemory>();
        long time;
        if (list == null) {
            return null;
        }
        for (int i = 0; i < list.size(); i++) {
            copy.add(list.get(i));
        }
        
        long startTime1 = System.nanoTime();
        this.sortingThing.quickSort(list);
        long endTime1 = System.nanoTime();
        time = endTime1 - startTime1;
        this.totalTime1 += time / UNIT_CONVERT;
        this.totalSize1 += list.size();
     
        long startTime2 = System.nanoTime();
        this.sortingThing.bucketSort(copy);
        long endTime2 = System.nanoTime();
        time = endTime2 - startTime2;
        this.totalTime2 += time / UNIT_CONVERT;
        this.numSort++;
        
        int i = 0;
        while (i < list.size() - 1) {
            GeneralMemory first = list.get(i);
            GeneralMemory second = list.get(i + 1);
            if (second.getStartAddress() - first.getStartAddress() 
                    == first.getMemSize()) {
                GeneralMemory combined = new GeneralMemory(first.getMemSize() 
                        + second.getMemSize(), first.getStartAddress());
                list.remove(first);
                list.remove(second);
                list.add(i, combined);
                i--;
                this.defragCount++;
            }
            i++;
        } 
        return list;
    }
    
    /**Gives the number of times a memory has been defragmented.
     * 
     * @return int The number of times defrag has occurred
     */
    public int defragCount() {
        return this.defragCount;
    }
    
    /**Gives the number of times allocating failed.
     * 
     * @return int The number of times allocate failed
     */
    public int totalFails() {
        return this.totalFails;
    }
    
    /**Gives the average size of allocating failures.
     * 
     * @return double The avg size of allocated failures
     */
    public int avgFailsize() {
        if (this.totalFails == 0) {
            return 0;
        }
        return (int) (this.totalFailsize / this.totalFails);
    }
    
    /**Gives the average time of allocating.
     * 
     * @return double The average time of allocating
     */
    public double avgTime() {
        if (this.alloCount == 0) {
            return 0;
        }
        return this.totalTime / this.alloCount;
    }
    
    /**Add time to the totalTime.
     * 
     * @param time The time to add
     */
    public void addTime(double time) {
        this.totalTime += time;
    }
    
    /**Gives the average sorting ratio for quicksort.
     * 
     * @return The average sorting ratio
     */
    public double avgSortRatio1() {
        if (this.totalSize1 == 0) {
            return 0;
        }
        return this.totalTime1 / this.totalSize1;
    }
    
    /**Gives the average sorting ratio for bucketsort.
     * 
     * @return The average sorting ratio
     */
    public double avgSortRatio2() {
        if (this.totalSize1 == 0) {
            return 0;
        }
        return this.totalTime2 / this.totalSize1;
    }

}
