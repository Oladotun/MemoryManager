/**Base class for all memory types.
 * 
 * @author Connie Chang 
 * @author Lara Gatehouse
 * @author Oladotun Opasina
 * @author Whitney Kim
 */
public class GeneralMemory implements Comparable<GeneralMemory> {
    /**The size of this block of memory.**/
    Integer memSize;
    
    /**The starting address for this block.**/
    Integer startAddress;
    
    /** General Memory. */
    
    public GeneralMemory() {
        
    }
    /**Constructor that takes size and starting address as parameters.
     * 
     * @param s The size for this block
     * @param a The starting address for this block
     */
    public GeneralMemory(int s, int a) {
        this.memSize = s;
        this.startAddress = a;
    }

    /**Returns the size of memory block.
     * 
     * @return The size of memory block
     */
    public int getMemSize() {
        return this.memSize;
    }

    /**Set the size of this block.
     * 
     * @param s The new size of this memory chunk
     */
    public void setMemSize(int s) {
        this.memSize = s;
    }

    /**Returns the starting address.
     * 
     * @return The address
     */
    public int getStartAddress() {
        return this.startAddress;
    }

    /**Set the starting address.
     * 
     * @param s The new starting address
     */
    public void setStartAddress(int s) {
        this.startAddress = s;
    }
    
    /** Overrides the compareTo method.
     * @param o generic object passed
     * @return a value comparing the data
     */

    public int compareTo(GeneralMemory o) {
        
        return this.startAddress.compareTo(o.startAddress);
        //SHOULD THAT BE SIZE?
    }
    
    /** hashCode of GeneralMemory.
     * @return the starting address
     */
    
    public int hashCode() {
        return this.memSize;
        //ADDRESS OR SIZE?
    }
    
    /**Gives a string representing the memory.
     * 
     * @return The string
    **/
    public String toString() {
        return "Size: " + this.getMemSize() 
                + " Address: " + this.getStartAddress();
    }
    
    /**Tests if GeneralMemory is equal to another object.
     * @param o The other object
     * @return boolean True if equal. Otherwise false
     */
    public boolean equals(Object o) {
        if (!(o instanceof GeneralMemory)) {
            return false;
        }
        GeneralMemory other = (GeneralMemory) o;
        if (this.getStartAddress() == other.getStartAddress()
                && this.getMemSize() == other.getMemSize()) {
            return true;
        }
        return false;
    }
}