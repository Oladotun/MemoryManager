/**Generic interface for a Priority Queue.
 * 
 * @author Connie Chang 
 * @author Lara Gatehouse
 * @author Oladotun Opasina
 * @author Whitney Kim
 *
 * @param <T> The generic type that will be stored
 */
public interface PriorityQueue<T> {
    
    /**Gives how many elements are in the heap.
     * 
     * @return The size of the heap
     */
    int size();
    
    /**Checks if the heap is empty.
     * 
     * @return True if empty. Otherwise false
     */
    boolean isEmpty();
    
    /**Gives the maximum in the heap.
     * @return The object with the highest value
     */
    T best();
    
    /**Deletes the maximum in this heap.
     * 
     * @return The data that was deleted
     */
    T deleteBest();
    
    /**Inserts a piece of data into heap without duplicates.
     * 
     * @param data The data to be entered
     * @return boolean True if inserted. Otherwise, false
     */
    boolean insert(T data);

}
