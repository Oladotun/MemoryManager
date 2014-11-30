import static org.junit.Assert.*;

import java.util.ArrayList;


import org.junit.Before;
import org.junit.Test;


/* Full description of test suite for HashTable
 ***************************************************************
Testing a HashTable:

getNextPrime(), isPrime() -
         check that if an integer is prime or not
         check that the value 11 is prime
         check that the next Prime after 10 is 11
         checks that the number of rows is always a prime 
         checks that the number of rows is changed if it is not a prime number

hashFunc()
        Check that the hashFunc(100) hashes to value 10
        Check that the hashFunc(12) does not hash to 2
insert() -
        Insert the numbers 3,55,20,40,25,49 into s
        Check that the size of the hash table is no longer 0
        Check that the size of the hash table is contains 6 items
        Check that that the number of bucket used is correct based on the hash function
equals()  -
        Insert the numbers 3,55,20,40,25,49 into s,s1
        Check that both tables contain equal items
        Remove an item 3 from s1
        Check that both items are no longer equals
        Check the size of Hash Table s1 is 1 less than Hash Table s
toString() - Checks to if an Empty Hash Table prints correctly
             check to see if all elements get printed out
             check formatting with multiple values
 remove() - 
             Checks to make sure an empty table removal returns false,
             Checks to make sure an item does not contain a data after removed
allData() -
            Checks to make sure that the number of items in HashTable is the same 
            size as that of the ArrayList item.
            Checks that the items we added to the hash table are the same in the ArrayList
removeAll() -
            

 **************************************************************/


public class HashTableTest {
    
    private HashTable<Integer> s;
    private HashTable<Integer> s1;
    
    @Before
    public void setup() {
        s = new HashTable<Integer>(10);
        s1 = new HashTable<Integer>(10);

    }
    @Test
    public void testPreEmptiness() {
        assertTrue("Result", s.isEmpty());
        assertEquals("Result", 0, s.size());
    }
    
    @Test
    public void testNonEmptiness() {
        /*
        check that after adding something, the Hash table isn't empty anymore
        check that after removing that element, the hash table is empty again
        */
        s.insert(10);
        assertFalse("Is no longer empty",s.isEmpty());
        s.remove(10);
        assertTrue("Is empty",s.isEmpty());
        s.insert(15);
        s.remove(15);
        assertTrue("Is still empty",s.isEmpty());
    }
    
    @Test
    public void testPrime() {
        assertFalse(s.isPrime(0));
        assertTrue(s.isPrime(11));
        assertSame(s.getNextPrime(10),11);
        assertTrue(s.isPrime(s.hashRows()));
        assertTrue(s.hashRows()!=10);
    }
    
    @Test
    public void testHashFunc() {
        //System.out.println(s.hashFunc(100));
        assertSame(s.hashFunc(100),1);
        assertTrue(s.hashFunc(12) == 1);
    }
    @Test
    public void testInsert() {
        s.insert(3);
        s.insert(55);
        s.insert(20);
      
        
        //System.out.println(s.toString());
        assertTrue(s.size() != 0);
        assertTrue(s.size() == 3);
//        System.out.println(s.usedBuckets());
//        System.out.println(s);
        
        
        assertTrue(s.usedBuckets() == 3);
    }
    
    @Test 
    public void testEquals() {
        s.insert(3);
        s.insert(55);
        s.insert(20);
        s.insert(40);
        s.insert(25);
        s.insert(49);
        
        //System.out.println(s.toString());
        s1.insert(3);
        s1.insert(55);
        s1.insert(20);
        s1.insert(40);
        s1.insert(25);
        s1.insert(49);
        
        assertTrue(s.equals(s1));
        s1.remove(3);
        assertFalse(s.equals(s1));
        assertTrue(s.size() - 1== s1.size());
        

    }
    @Test 
    public void testRemoveFromEmpty() {
        /* remove()
           test to make sure an empty set removal returns false
        */
        assertFalse(s.remove(12));
    }
    @Test
    public void testRemove() {
        /* remove()
           test to make sure that removing an element puts it back to 0
        */
        assertEquals(0,s.size());
        s.insert(25);
        assertEquals(1,s.size());
        s.remove(25);
        assertEquals(0,s.size());
        assertFalse(s.contains(25));
        assertFalse(s.remove(25));
        assertEquals(0,s.size());

        assertTrue(s.insert(17));
        assertTrue(s.insert(5));
        assertEquals(2,s.size());
        s.remove(17);
        assertEquals(1,s.size());
        assertFalse(s.contains(25));
        

    }    
    
    @Test
    public void testToString() {

        assertEquals("Bucket \t Data\n",s.toString());
        s.insert(3);
        assertEquals("Bucket \t Data\n3 \t [3]\n", s.toString());
        s.insert(9);
        s1.insert(3);
        s1.insert(9);
              
        assertTrue(s.toString().equals(s1.toString()));
        
        
        
    }
//    
    @Test
    public void testContains() {
        /*
         check along with contains: check that an empty hashTable says "No" to containing something
         check that if you remove a thing, it no longer contains it
         check that if you remove everything, the size is 0
         check that contains doesn't affect the size
        */ 
        assertFalse("Empty container",s.contains(1215));
        assertEquals(0,s.size());
        s.insert(20);
        assertTrue("Containing something",s.contains(20));
        assertFalse("Not Containing something", s.contains(15));
        assertEquals(1,s.size());
        s.remove(20);

        s.insert(12);
        s.remove(12);
        assertFalse("12",s.contains(12));
    }
    
    
    
    @Test
    public void testAllData() {
        
        /* Test to make sure that all the data in the hash table are in the Array lists
         * 
         */
        assertTrue(s.allData() == null);
        s.insert(3);
        s.insert(55);
        s.insert(20);
        s.insert(40);
        s.insert(25);
        s.insert(49);
        
        
        ArrayList<Integer> items = s.allData();
        assertEquals(s.size(),items.size());
        assertTrue(items.toString().equals("[55, 3, 25, 49, 40, 20]"));

    }

}