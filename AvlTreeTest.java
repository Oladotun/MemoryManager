import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class AvlTreeTest {

    AvlTree<Integer> test = new AvlTree<Integer>(); 
    
    @Before
    public void setUp() throws Exception {
        
    }

    @Test
    public void preEmptiness() {
        assertTrue(this.test.getRoot() == null); 
    }
    
    @Test
    public void insertTest() {
        this.test.insert(10); 
        this.test.insert(9); 
        this.test.insert(1); 
        this.test.insert(2); 
        this.test.insert(3); 
        
        assertTrue(!this.test.isEmpty()); 
        assertTrue(this.test.height(test.getRoot()) == 2); 
    }
    
    @Test
    public void removeTest() { 
        this.test.insert(20); 
        this.test.insert(100); 
        this.test.insert(200); 
        this.test.remove(1); 
        this.test.remove(3); 
        
        assertTrue(this.test.height(test.getRoot()) == 1); 
    }
    
    @Test
    public void testContains() {
        this.test.insert(10); 
        this.test.insert(100); 
        this.test.insert(200);
        assertTrue(this.test.contains(10));
        assertTrue(this.test.contains(100));
        assertTrue(this.test.contains(200));
        assertFalse(this.test.contains(99)); 
    }
}

