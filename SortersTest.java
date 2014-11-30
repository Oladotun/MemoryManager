import static org.junit.Assert.*;


import java.util.ArrayList;
import org.junit.Before;
import org.junit.Test;

/* Full description of test suite for SortersClass
 ***************************************************************
Testing a Sorters Class for an ArrayList that contains a bucketsort and quicksort

quickSort(), bucketSort() - Insert 10 integers in unsorted order into one arrayList 
           - Insert 10 integers in sorted order into another arrayList
           - Pass the unsorted arrayList into the sort method from the sorter class
           - Make sure the result is now sorted by comparing the two array lists
           - remove a value from one of the array list and make sure they are no 
           longer equal
*/

public class SortersTest {
    
    private ArrayList<Integer> s;
    private ArrayList<Integer> s1;
    private Sorters<Integer> t;
    
    private ArrayList<GeneralMemory> v;
    private ArrayList<GeneralMemory> v1;
    private Sorters<GeneralMemory> w;
    
    private int []values = {9,12,2,1,8,7,10,5,3,4};
    private int []sortedV = {1,2,3,4,5,7,8,9,10,12};
    
    private GeneralMemory []mValues = {new GeneralMemory(1,0), new GeneralMemory(9,2), new GeneralMemory(4,5), new GeneralMemory(3,1)};
    private GeneralMemory []sortedM = {new GeneralMemory(4,4), new GeneralMemory(1,1), new GeneralMemory(3,3), new GeneralMemory(2,2)};
    
    @Before
    public void setUp() {
        s = new ArrayList<Integer>(10);
        s1 = new ArrayList<Integer>(10);
        t = new Sorters<Integer>();
        
        v = new ArrayList<GeneralMemory>(4);
        v1 = new ArrayList<GeneralMemory>(4);
        w = new Sorters<GeneralMemory>();
         
        for (int idx = 0; idx < 10; ++idx){
            s.add(values[idx]);
            s1.add(sortedV[idx]);
          }
        
        for (int idx = 0; idx < 4; ++idx) {
            v.add(mValues[idx]);
            v1.add(sortedM[idx]);
          }
    }
    

    @Test
    public void testQuickSort() {
        t.quickSort(s);
        System.out.print(s);
        assertTrue(s.equals(s1));
        s.remove(4);
        assertFalse(s.equals(s1));
    }
    
   
    @Test
    public void testBucketSort() {
        System.out.println(v1);
        w.bucketSort(v1);
        System.out.println(v1);
        
        System.out.println(v);
        w.bucketSort(v);
        System.out.print(v);
        //assertTrue(v.equals(v1));
        v.remove(3);
        assertFalse(v.equals(v1));
    }
    

}