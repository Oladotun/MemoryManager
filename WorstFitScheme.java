import java.util.ArrayList;

/**Class to model worst-fit scheme for memory allocation.
 * Uses BBST to store used memory.
 * Uses a max heap to store freeMem memory.
 * 
 * @author Connie Chang 
 * @author Lara Gatehouse
 * @author Oladotun Opasina
 * @author Whitney Kim
 *
 */
public class WorstFitScheme extends BaseManager {
    /**A BBST to store the used memory.**/
    private AvlTree<UsedMemory> used;
    /**A max heap to store freeMem memory.**/
    private MaxHeap<GeneralMemory> freeMem;

    
    /**Constructor that sets up worst-fit scheme.
     * 
     * @param totalMem The total size of memory
     */
    public WorstFitScheme(int totalMem) {
        super();
        this.freeMem = new MaxHeap<GeneralMemory>();
        this.used = new AvlTree<UsedMemory>();
        GeneralMemory free = new GeneralMemory(totalMem, 0);
        this.freeMem.insert(free);
        //this.used = null;
        this.alloCount = 0;
    }
    
    /**Allocates memory of size s.
     * 
     * @param s The size of memory to allocate
     * @return boolean True if allocated. Otherwise false
     */
    public int allocate(int s) {
        //GeneralMemory g = new GeneralMemory(s); //so we can compareTo
        this.defrag = false;
        this.alloCount++;
        if (this.freeMem.best() == null) {
            this.totalFails++;
            this.totalFailsize += s;
            return -1;
        }
        if (this.freeMem.best().getMemSize() < s) { //too small, then defrag
            ArrayList<GeneralMemory> defragged 
                = this.defrag(this.freeMem.getHeap());
            this.defragCount++;
            this.defrag = true;
            this.freeMem = new MaxHeap<GeneralMemory>();
            //need to put memory blocks back in order
            for (int i = 0; i < defragged.size(); i++) {
                this.freeMem.insert(defragged.get(i));
            }
            if (this.freeMem.best().getMemSize() < s) { //still too small
                this.totalFails++;
                this.totalFailsize += s;
                return -1;
            }
        }
        
        GeneralMemory old = this.freeMem.deleteBest();
        UsedMemory newUsed = new 
                UsedMemory(s, old.getStartAddress(), this.alloCount);
        if (old.getMemSize() - s > 0) {
            GeneralMemory newfreeMem = new 
                GeneralMemory(old.getMemSize() - s, old.getStartAddress() + s);
            //becomes the leftover freeMem mem
            this.freeMem.insert(newfreeMem);
        }
        this.used.insert(newUsed);
        return newUsed.getStartAddress();
    }
    
    /**Deallocates used memory with ID i.
     * 
     * @param i The ID of the memory to deallocate
     * @return boolean True if deleted. Otherwise, false
     */
    public GeneralMemory deallocate(int i) {
        UsedMemory fake = new UsedMemory(-1, -1, i);
        this.used.remove(fake);
        UsedMemory u = this.used.getTheRemove();
        //UsedMemory u = this.used.remove(fake);
        if (u == null) {
            return null;
        }
        GeneralMemory newFree = new 
                GeneralMemory(u.getMemSize(), u.getStartAddress());
        this.freeMem.insert(newFree);
        return newFree;
    }
    
    /*
    public static void main(String args[]) {
        //for testing
        WorstFitScheme scheme = new WorstFitScheme(100);
        System.out.println("free mem:" + scheme.freeMem);
        System.out.println("used mem: " + scheme.used);
        System.out.println("allcoate 50");
        scheme.allocate(50);
        System.out.println("free mem:" + scheme.freeMem);
        System.out.println("used mem: " + scheme.used);
        System.out.println("allcoate 20");
        scheme.allocate(20);
        System.out.println("free mem:" + scheme.freeMem);
        System.out.println("used mem: " + scheme.used);
        System.out.println("allcoate 10");
        scheme.allocate(10);
        System.out.println("free mem:" + scheme.freeMem);
        System.out.println("used mem: " + scheme.used);
        
        System.out.println("deallocate 1");
        scheme.deallocate(1);
        System.out.println("free mem:" + scheme.freeMem);
        System.out.println("used mem: " + scheme.used);
        
        System.out.println("deallocate 2");
        scheme.deallocate(2);
        System.out.println("free mem:" + scheme.freeMem);
        System.out.println("used mem: " + scheme.used);
        System.out.println("allocate 70");
        scheme.allocate(70);
        System.out.println("free mem:" + scheme.freeMem);
        System.out.println("used mem: " + scheme.used);
        
    }
    */
    
}
