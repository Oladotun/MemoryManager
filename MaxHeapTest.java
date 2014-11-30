import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/* Full description of test suite for MaxHeap
 ***************************************************************
Testing a MaxHeap:

preEmptiness()-
        Check size is 0.
        Check heap is empty.

size(), isEmpty() -
         Check size is 0.
         Insert the integer 1.
         Check that MaxHeap is not empty.
         Check that the size is 1.
         Insert numbers 4, 5, 6.
         Check that size is 4.

insert() -
        Test size is 0.
        Check heap is empty.
        Insert number 1. Check it was successfully inserted.
        Check size is 1.
        Insert number 1. Make sure it was unsuccessful since 1 is already there.
        Check the highest value in heap is 1.
        Insert 55, 8, 42, 6.
        Check size is 5.
        Check highest value is 55.
        
equals()  -
        Insert the numbers 3,55,20,40,25,49 into mh1,mh2
        Check that both tables contain equal items
        Insert number 5 and 9 in different orders into mh1, mh2
        Check mh1 and mh2 are not equal
        Delete best in mh1
        Check that both items are still not equal
        Check the size of mh1 is 1 less than mh2
        
toString() - Checks to if an empty max heap prints correctly
             check to see if all elements get printed out
             check formatting with multiple values
 remove() - 
             Checks to make sure an empty table removal returns false

 **************************************************************/

public class MaxHeapTest {
    
    MaxHeap<Integer> test = new MaxHeap<Integer>();

    @Test
    public void preEmptiness() {
        assertTrue(this.test.isEmpty());
        assertEquals(0, this.test.size());
    }
    
    @Test
    public void sizeTest() {
        assertTrue(this.test.isEmpty());
        assertEquals(0, this.test.size());
        this.test.insert(1);
        assertFalse(this.test.isEmpty());
        assertEquals(1, this.test.size());
        this.test.insert(4);
        this.test.insert(5);
        this.test.insert(6);
        assertEquals(4, this.test.size());
    }
    
    @Test
    public void insertTest() {
        assertEquals(0, this.test.size());
        assertTrue(this.test.isEmpty());
        assertTrue(this.test.insert(1));
        assertEquals(1, this.test.size());
        assertFalse(this.test.insert(1));
        assertTrue(this.test.best().equals(1));
        this.test.insert(8);
        this.test.insert(55);
        this.test.insert(42);
        this.test.insert(6);
        assertEquals(5, this.test.size());
        assertTrue(this.test.best().equals(55));
    }
    
    @Test
    public void deleteTest() {
        assertEquals(0, this.test.size());
        assertTrue(this.test.isEmpty());
        assertTrue(this.test.insert(1));
        this.test.insert(4);
        assertTrue(this.test.deleteBest().equals(4));
        assertEquals(1, this.test.size());
        this.test.deleteBest();
        assertEquals(0, this.test.size());
        assertEquals(null, this.test.deleteBest());
        this.test.insert(5);
        //System.out.println(this.test);
        this.test.insert(10);
        //System.out.println(this.test);
        this.test.insert(13);
        //System.out.println(this.test);
        this.test.insert(15);
        //System.out.println(this.test);
        assertTrue(this.test.deleteBest().equals(15));
        //System.out.println(this.test);
        assertTrue(this.test.deleteBest().equals(13));
        //System.out.println(this.test);
        //System.out.println(this.test.best());
        assertTrue(this.test.deleteBest().equals(10));
    }
    
    @Test
    public void stringTest() {
        assertEquals("[]", this.test.toString());
        this.test.insert(1);
        this.test.insert(4);
        this.test.insert(5);
        assertEquals("[5, 1, 4]", this.test.toString());
        this.test.deleteBest();
        //System.out.println(this.test.toString());
        assertEquals("[4, 1]", this.test.toString());
    }
    
    @Test
    public void equalsTest() {
        MaxHeap<Integer> mh1 = new MaxHeap<Integer>();
        MaxHeap<Integer> mh2 = new MaxHeap<Integer>();
        mh1.insert(3);
        mh2.insert(3);
        mh1.insert(55);
        mh2.insert(55);
        mh1.insert(20);
        mh2.insert(20);
        mh1.insert(40);
        mh2.insert(40);
        mh1.insert(25);
        mh2.insert(25);
        mh1.insert(49);
        mh2.insert(49);
        
        assertTrue(mh1.equals(mh2));
        assertTrue(mh2.equals(mh1)); //other direction
        
        mh1.insert(5);
        mh2.insert(9);
        mh1.insert(9);
        mh2.insert(5); //insert in different order
        
        assertFalse(mh1.equals(mh2));
        assertFalse(mh2.equals(mh1));
        
        mh1.deleteBest();
        //Check that both items are no longer equals
        assertFalse(mh1.equals(mh2));
        assertFalse(mh2.equals(mh1));
        assertEquals(mh1.size(), mh2.size() - 1);
    }

}
