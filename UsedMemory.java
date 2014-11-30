/**Class for used memory that derives from GeneralMemory.
 * 
 * @author Connie Chang 
 * @author Lara Gatehouse
 * @author Oladotun Opasina
 * @author Whitney Kim
 *
 */
public class UsedMemory extends GeneralMemory  {
    
    /**The unique ID for this block of memory.**/
    Integer id;
    
    /**Constructor that sets up variables.
     * 
     * @param s The size of this memory
     * @param a The starting address
     * @param i The ID
     */
    public UsedMemory(int s, int a, int i) {
        super(s, a);
        this.id = i;
    }
    
    /**Gives a string representation of UsedMemory.
     * @return String The string representation
     */
    public String toString() {
        return super.toString() + "\tID : " + this.id; 
    }
    
    /**Compares the ID of two Used Memory objects.
     * 
     * @param other The other memory block
     * @return int 0 if equal. positive if this is smaller. 
     * negative if this is larger.
     */
    public int compareTo(GeneralMemory other) {
        if (other instanceof UsedMemory) {
            UsedMemory o = (UsedMemory) other;
            return this.id.compareTo(o.id);
        }
        return super.compareTo(other);
    }

}