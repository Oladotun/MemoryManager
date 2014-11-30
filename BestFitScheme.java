import java.util.ArrayList;

/**
 * Class to model best-fit scheme for memory allocation. 
 * 
 * @author Connie Chang 
 * @author Lara Gatehouse
 * @author Oladotun Opasina
 * @author Whitney Kim
 *
 */
public class BestFitScheme extends BaseManager {
  
    
    /** A BBST to store the used memory. **/
    private AvlTree<UsedMemory> used; 
    
    /**A BBST to store the free memory.**/
    private AvlTree<GeneralMemory> freeMem; 
    
    
    
    /**
     * Constructor that sets up the best-fit scheme. 
     * @param totalMem The total size of memory. 
     */
    public BestFitScheme(int totalMem) {
        super(); 
        this.freeMem = new AvlTree<GeneralMemory>(); 
        this.used = new AvlTree<UsedMemory>(); 
        GeneralMemory free = new GeneralMemory(totalMem, 0); 
        this.freeMem.insert(free); 
        this.alloCount = 0; 
    }
    
    /**Gives the tree of Used Memory.
     * 
     * @return AvlTree<UsedMemory> The avl tree of unavailable memory
     */
    public AvlTree<UsedMemory> getTree() {
        return this.used; 
    }
    
    /**
     * Allocates memory of size s. 
     * 
     * @param s The size of memory to allocate. 
     * @return int The address of new memory 
     */
    public int allocate(int s) {
        this.defrag = false;
        this.alloCount++;
        return this.alloHelp(s);
    }
    
    /**
     * Helps allocate memory recursively. 
     * 
     * @param s The size of memory to allocate. 
     * @return int The address of new memory 
     */
    public int alloHelp(int s) {
        GeneralMemory alloc = new GeneralMemory(s, 0);

        GeneralMemory toBeRemoved = 
                this.freeMem.findNextLargest(alloc); 
        if (toBeRemoved != null) {
            
            Integer newFreeSize = toBeRemoved.getMemSize() - s; 
            if (newFreeSize > 0) {
                GeneralMemory newFreeBlock = new GeneralMemory(newFreeSize, 
                        toBeRemoved.getStartAddress() + s);
            
                this.freeMem.insert(newFreeBlock); 
            }
            this.freeMem.remove(toBeRemoved);
            
            UsedMemory newUsed = new UsedMemory(s, 
                    toBeRemoved.getStartAddress(), this.alloCount);
            this.used.insert(newUsed);

            return newUsed.getStartAddress(); 
            
        } else if (!(this.defrag)) {
            this.defrag = true;
            this.defragCount++;
            ArrayList<GeneralMemory> array = this.defrag(this.freeMem.array());
            if (array == null) {
                this.totalFails++;
                this.totalFailsize += s;
                return -1;
            }
            this.freeMem = new AvlTree<GeneralMemory>();
            for (int i = 0; i < array.size(); i++) {
                this.freeMem.insert(array.get(i));
            }
            return this.alloHelp(s); 
        } else {  
            this.totalFails++;
            this.totalFailsize += s;
            return -1;
        }
       
    }
    
    /**Deallocates memory of a certain ID.
     * @param id The integer id
     * @return GeneralMemory The memory block with the ID
     */
    public GeneralMemory deallocate(int id) {
        
        UsedMemory fake = new UsedMemory(-1, -1, id); 
        this.used.remove(fake);
        UsedMemory u = this.used.getTheRemove();
        //System.out.println(u);
        
        if (u == null) {
            return null; 
        }
        
        GeneralMemory newFree = 
                new GeneralMemory(u.getMemSize(), u.getStartAddress()); 
        //System.out.println(u.getMemSize());
        this.freeMem.insert(newFree); 
        
        return newFree;  
    }
}