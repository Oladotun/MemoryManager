import java.util.ArrayList;


/** Models memory allocation by close-fit.
 * 
 * @author Connie Chang 
 * @author Lara Gatehouse
 * @author Oladotun Opasina
 * @author Whitney Kim
 *
 */
public class CloseFitScheme extends BaseManager  {
    
    /** The starting address. */
    private final int start = 0;
    /** Free Memory Block.*/
    private HashTable<GeneralMemory> freeMem;
    /** Current Block of memory. */
    private GeneralMemory currBlock;
    /**Tree of used memory.*/
    private AvlTree<UsedMemory> used;
    
    /** General Constructor. */
    public CloseFitScheme() {
        
    }
    /** Constructor for close fit. 
     * 
     * @param totalMem maximum size block for memory
     */
    public CloseFitScheme(int totalMem) {
        this.currBlock = new GeneralMemory(totalMem, this.start);
        this.freeMem = new HashTable<GeneralMemory>(this.currBlock);
        this.used = new AvlTree<UsedMemory>();
        this.alloCount = 0;
        
        
    }

    /** Allocate memory for the Close fit scheme.
     * @param s the size of block to be allocated
     * @return a true or false if memory was allocated
     */
    public int allocate(int s) {
        this.defrag = false;
        GeneralMemory newBlock = new GeneralMemory(s, -1);
        
        if (s > this.freeMem.getMaxData().getMemSize()) {
              
            ArrayList<GeneralMemory> defragData = 
                        super.defrag(this.freeMem.allData());
            this.defragCount++;
            this.defrag = true;
            if (defragData == null) {  
                this.totalFails++;
                this.totalFailsize += s;
                return -1;
            }
                
            if (defragData.size() == 1) {
                this.freeMem = new HashTable<GeneralMemory>(defragData.get(0)); 
            } else {
                this.freeMem = new HashTable<GeneralMemory>(defragData.size());
                for (int i = 0; i < defragData.size(); i++) {
                    this.freeMem.insert(defragData.get(i));

                }
            } 
            

            if (!this.freeMem.getCloseFit(newBlock)) {
                super.totalFailsize += s;
                super.totalFails++;
                return -1;
            }
            
        }
        

        this.currBlock = this.freeMem.getData();
        this.freeMem.remove(this.currBlock);
        int mem = this.currBlock.getMemSize() - s;
        int addr = this.currBlock.getStartAddress();
      
        if (mem > 0) {
            this.currBlock.setMemSize(mem);
            this.currBlock.setStartAddress(
                    this.currBlock.getStartAddress() + s);
            this.freeMem.insert(this.currBlock);

        }
        this.alloCount++;
        this.used.insert(new UsedMemory(s, addr, this.alloCount));
        
        
        return addr;
    }

    /** Deallocation for a Close fit.
     * @param i id to be deallocated
     * @return whether deallocation was successful or not
     */
    public GeneralMemory deallocate(int i) {
        UsedMemory curr = new UsedMemory(-1, -1, i);
        this.used.remove(curr);
        UsedMemory u = this.used.getTheRemove();
      
        
        if (u == null) {
            return null;
        }
        
       
        GeneralMemory newFree = new 
                GeneralMemory(u.getMemSize(), u.getStartAddress());
        this.freeMem.insert(newFree);
        return newFree;
        
    }
    
    /** String representation of memory block.
     * @return a string representation of the memory block
     */
    public String toString() {
        return this.freeMem.toString();
    }
    
    /**Prints the tree of used memory.*/
    public void printUsed() {
        this.used.printTree();
    }


}