import java.util.ArrayList;

/**MemScheme interface with allocate methods that are used for all schemes.
 * 
 * @author Connie Chang 
 * @author Lara Gatehouse
 * @author Oladotun Opasina
 * @author Whitney Kim
 *
 */
public interface MemoryManager {

    /**Allocates memory from the free memory.
     * @param s The size of memory
     * @return boolean True if allocated. Otherwise, false
     */
    int allocate(int s);
    
    /**Deallocates memory from the used memory.
     * 
     * @param i The ID of the memory to delete
     * @return boolean True if deallocated. Otherwise, false
     */
    GeneralMemory deallocate(int i);

    /** Defrags memory, combines any free memory blocks next to each other.
     *
     * @param list an Arraylist of free data
     * @return an Arraylist that has been defraged
     */
    ArrayList<GeneralMemory> defrag(ArrayList<GeneralMemory> list); 
    
    /**Gives the number of times a memory has been defragmented.
     * 
     * @return int The number of times defrag has occurred
     */
    int defragCount();
    
    /**Gives the number of times allocating failed.
     * 
     * @return int The number of times allocate failed
     */
    int totalFails();
    
    /**Gives the average size of allocating failures.
     * 
     * @return double The avg size of allocated failures
     */
    int avgFailsize();
    
    /**Gives the average time of allocating.
     * 
     * @return double The average time of allocating
     */
    double avgTime();
    
    /**Gives the average sorting ratio for quicksort.
     * 
     * @return The average sorting ratio
     */
    double avgSortRatio1();
    
    /**Gives the average sorting ratio for bucketsort.
     * 
     * @return The average sorting ratio
     */
    double avgSortRatio2();
    
    /** Returns boolean of if defragged or not.
     * 
     * @return defrag, true if defragged false if not
     */
    boolean didDefrag();
    
    /**Add time to the totalTime.
     * 
     * @param time The time to add
     */
    void addTime(double time);
    
}