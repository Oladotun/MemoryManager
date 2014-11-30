import java.util.ArrayList;

/**Adapted from AvlTree.java by Mark Allen Weiss.
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * 
 * @author Connie Chang 
 * @author Lara Gatehouse
 * @author Oladotun Opasina
 * @author Whitney Kim
 * @param <T> The generic type of the AvlTree
 */
public class AvlTree<T extends Comparable<? super T>> {
    
    /**The largest balance factor allowed in the tree.*/
    private static final int ALLOWED_IMBALANCE = 1;
    
    /** The tree root. */
    private AvlNode<T> root;
    /**The last removed item. */
    private AvlNode<T> theRemove = new AvlNode<T>();
    /**
     * Construct the tree.
     */
    public AvlTree() {
        this.root = null;
        this.theRemove = new AvlNode<T>();
    }

    /**Gives the root of the tree.
     * 
     * @return AvlNode<T> The root
     */
    public AvlNode<T> getRoot() {
        return this.root;
    }
    
    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert(T x) {
        this.root = this.insert(x, this.root);
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove(T x) {
        this.theRemove.setData(null);
        this.root = this.remove(x, this.root);
    }


    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<T> remove(T x, AvlNode<T> t) {
        //this.theRemove.setData(null);
        if (t == null) {
            return t;   // Item not found; do nothing
        }

        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = this.remove(x, t.left);
        } else if (compareResult > 0) {
            t.right = this.remove(x, t.right);
        } else if (t.left != null && t.right != null) { // Two children
            this.theRemove.setData(t.getData());
            t.element = this.findMin(t.right).element;
            t.right =  this.remove(t.element, t.right);
                
        }  else {
            if (this.theRemove.getData() == null) {
                this.theRemove.setData(t.getData());  
            }
            if (t.left != null) {
                t = t.left;
            } else {
                t = t.right;
            }
        }
        return this.balance(t);       
    }
    
    /**Finds the smallest element that is larger than the input.
     * 
     * @param x The lower bound
     * @return AvlNode<T> The node with the element
     */
    public T findNextLargest(T x) {
        
        if (this.find(x) != null) {
            return this.find(x).getData(); 
        } else {
            
            AvlNode<T> temp = this.findNextLargest(x, this.root); 
            if (temp == null) {
                return null;
            } 
            return temp.getData();
        }
    }
    
    /**Finds the largest element that is still larger than input under subtree.
     * 
     * @param x The lower bound
     * @param n The root of subtree
     * @return AvlNode<T> The node with element 
     */
    public AvlNode<T> findNextLargest(T x, AvlNode<T> n) {
        
        if (n == null) {
            return null;
        }
        //If the size we're looking for is less than the 
        //data in the current node but greater than the data 
        //in the left child of the current node, then
        //the current node is the "BEST FIT"
        if (x.hashCode() < ((T) n.element).hashCode() && n.left != null 
                && x.hashCode() < ((T) n.left.element).hashCode()) {
            return n; 
        
        //If the size we're looking for is less than both the 
        //current node data and the data of the 
        //left child of the current node, then "move left" 
        //by recursively calling the method
        //with n.left as the new root. 
        
        } else if (x.hashCode() < ((T) n.element).hashCode() 
                && n.left == null) {
            return n;
        
        /*
        if (this.nextLargestHelper(x, n)) {
            return n;
        }
        */
        } else if (x.hashCode() < ((T) n.element).hashCode() && n.left != null 
                && x.hashCode() < ((T) n.left.element).hashCode()) {
            return this.findNextLargest(x, n.left);
            
        //If the size we're looking for is larger than 
        //the data in the current node 
        //but smaller than the data in the right child of the current node, then
        //the right child node is the "BEST FIT"
        } else if (x.hashCode() < ((T) n.element).hashCode() && n.right != null 
                && x.hashCode() < ((T) n.right.element).hashCode()) {
            return n.right; 
        
        //If the size we're looking for is bigger 
        //than both the current node's data and the data
        //of the right child of the current node, then 
        //"move" to the right and recursively
        //call the method with the right child as the new root. 
        } else if (x.hashCode() < ((T) n.element).hashCode() && n.right != null 
                && x.hashCode() < ((T) n.right.element).hashCode()) {
            return this.findNextLargest(x, n.right); 
        
        //If the above conditions aren't met, 
        //then assume a larger value doesn't exist in the tree
        }
        return null; 
    }

    /**
     * This method finds a particular value in a tree. 
     * @param x The element to search for
     * @return AvlNode<T> The node with element x
     */
    public AvlNode<T> find(T x) {
        return this.find(x, this.root); 
    }
    
    /**
     * Find the node that contains the data x. 
     * @param x the data we're looking for. 
     * @param n the node from which we start looking. 
     * @return the node with the particular data. 
     */
    public AvlNode<T> find(T x, AvlNode<T> n) {
        
        if (n == null) {
            return null; 
        }
        
        if ((n.element).compareTo(x) == 0) {
            return n; 
        
        } else {
            if (this.find(x, n.left) != null) {
                return this.find(x, n.left);
            }
            
            if (this.find(x, n.right) != null) {
                return this.find(x, n.right);
            }
            
            return null; 
        }
    }

    
    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public T findMin() {
        if (this.isEmpty()) {
            return null;
        }
        //throw new UnderflowException( );
        return this.findMin(this.root).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public T findMax() {
        if (this.isEmpty()) {
            return null;
        }
        //        if( isEmpty( ) )
        //            throw new UnderflowException( );
        return this.findMax(this.root).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if x is found.
     */
    public boolean contains(T x) {
        return this.contains(x, this.root);
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        this.root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree() {
        if (this.isEmpty()) {
            System.out.println("Empty tree");
        } else {
            this.printTree(this.root);
        }
    }

    /**Balances the a subtree.
     * 
     * @param t The root of the subtree
     * @return The new root of the subtree
     */
    private AvlNode<T> balance(AvlNode<T> t) {
        if (t == null) {
            return t;
        }

        if (this.height(t.left) - this.height(t.right) > ALLOWED_IMBALANCE) {
            if (this.height(t.left.left) >= this.height(t.left.right)) {
                t = this.rotateWithLeftChild(t);
            } else {
                t = this.doubleWithLeftChild(t);
            }
        } else {
            if (this.height(t.right) - this.height(t.left) 
                    > ALLOWED_IMBALANCE) {
                if (this.height(t.right.right) >= this.height(t.right.left)) {
                    t = this.rotateWithRightChild(t);
                } else {
                    t = this.doubleWithRightChild(t);
                }
            }
        }
        t.height = Math.max(this.height(t.left), this.height(t.right)) + 1;
        return t;
    }

    /**Calls the other chekBalance method which calculates the balance factor.
     * 
     */
    public void checkBalance() {
        this.checkBalance(this.root);
    }

    /**Prints a statement if tree is imbalanced.
     * 
     * @param t The root of subtree
     * @return int The height of the subtree
     */
    private int checkBalance(AvlNode<T> t) {
        if (t == null) {
            return -1;
        }

        int hl = this.checkBalance(t.left);
        int hr = this.checkBalance(t.right);
        if (Math.abs(this.height(t.left) - this.height(t.right)) > 1 
                || this.height(t.left) != hl || this.height(t.right) != hr) {
            System.out.println("OOPS!!");
        }
        return this.height(t);
    }


    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<T> insert(T x, AvlNode<T> t) {
        if (t == null) {
            return new AvlNode<T>(x, null, null);
        }
        int compareResult = x.compareTo(t.element);

        if (compareResult < 0) {
            t.left = this.insert(x, t.left);
        } else if (compareResult > 0) {
            t.right = this.insert(x, t.right);
        }
        return this.balance(t);
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<T> findMin(AvlNode<T> t) {
        if (t == null) {
            return t;
        }

        while (t.left != null) {
            t = t.left;
        }
        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<T> findMax(AvlNode<T> t) {
        if (t == null) {
            return t;
        }

        while (t.right != null) {
            t = t.right;
        }
        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains(T x, AvlNode<T> t) {
        while (t != null) {
            int compareResult = x.compareTo(t.element);

            if (compareResult < 0) {
                t = t.left;
            } else if (compareResult > 0) {
                t = t.right;
            } else {
                return true;    // Match
            }
        }

        return false;   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the tree.
     */
    private void printTree(AvlNode<T> t) {
        if (t != null) {
            this.printTree(t.left);
            System.out.println(t.element);
            this.printTree(t.right);
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     * @param t The node we are checking
     * @return int The height
     */
    public int height(AvlNode<T> t) {
        if (t == null) {
            return -1;
        }
        return t.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     * @param k2 The root of the subtree to rotate.
     * @return AvlNode<T> The new root of the subtree
     * 
     */
    private AvlNode<T> rotateWithLeftChild(AvlNode<T> k2) {
        AvlNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(this.height(k2.left), this.height(k2.right)) + 1;
        k1.height = Math.max(this.height(k1.left), k2.height) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     * 
     * @param k1 The root of subtree to rotate
     * @return AvlNode<T> The new root of the rotated subtree
     */
    private AvlNode<T> rotateWithRightChild(AvlNode<T> k1) {
        AvlNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(this.height(k1.left), this.height(k1.right)) + 1;
        k2.height = Math.max(this.height(k2.right), k1.height) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     * 
     * @param k3 The root of the subtree to rotate
     * @return AvlNode<T> The new root of rotated tree
     */
    private AvlNode<T> doubleWithLeftChild(AvlNode<T> k3) {
        k3.left = this.rotateWithRightChild(k3.left);
        return this.rotateWithLeftChild(k3);
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     * 
     * @param k1 The root of subtree to rotate
     * @return AvlNode<T> The new root of rotated subtree
     */
    private AvlNode<T> doubleWithRightChild(AvlNode<T> k1) {
        k1.right = this.rotateWithLeftChild(k1.right);
        return this.rotateWithRightChild(k1);
    }
    
    /**Gives a string representation of the tree.
     * @return String the string representation
     */
    public String toString() {
        return this.inOrder(this.root);
    }
    
    /**Converts AvlTree to an ArrayList.
     * 
     * @return ArrayList<T> The arraylist of elements
     */
    public ArrayList<T> array() {
        if (this.isEmpty()) {
            return null; 
        }
        return this.inOrderArray(this.root);
    }
    
    /**Gives a string representation of tree in-order.
     * 
     * @param in The starting node
     * @return String The in-order string
     */
    private ArrayList<T> inOrderArray(AvlNode<T> in) {
        
        if (in == null) {
            return null;
        }
        ArrayList<T> list = new ArrayList<T>();
        //String s = "";
        if(in.left!= null) {
            list.addAll(this.inOrderArray(in.left));  
        }
       
        //s = this.inOrder(in.left) + s;
        list.add(in.element);
        //s += in.element.toString() + ", ";
        if(in.right != null) {
            list.addAll(this.inOrderArray(in.right));   
        }
        
        //s += this.inOrder(in.right);
        return list;
    }
    
    private String inOrder(AvlNode<T> in) {
        if (in == null) {
            return "";
        }
        String s = "";
        s = this.inOrder(in.left) + s;
        s += in.element.toString() + ", ";
        s += this.inOrder(in.right);
        return s;
    }
    
    /**Gives theRemove variable.
     * theRemove is the last item to be removed from tree
     * @return T The last item that was deleted
     */
    public T getTheRemove() {
        return this.theRemove.element;
    }

    /**The Node class for the AvlTree.
     * 
     * @author Dotun, Lara, Connie, Whitney
     *
     * @param <T> The generic type of the node
     */
    public static class AvlNode<T> {
        
        /**The data.*/
        T element;
        /**The left child.*/
        AvlNode<T> left;
        /**The right child.*/
        AvlNode<T> right;
        /**The height.*/
        int height;
        
        /**Empty constructor.*/
        AvlNode() {
            
        }
        
        /**Constructor with just an element.
         * 
         * @param theElement The element to put in node
         */
        AvlNode(T theElement) {
            this(theElement, null, null);
        }

        /**Constructor that takes the element and children parameters.
         * 
         * @param theElement The element to be stored
         * @param lt The left child
         * @param rt The right child
         */
        AvlNode(T theElement, AvlNode<T> lt, AvlNode<T> rt) {
            this.element  = theElement;
            this.left     = lt;
            this.right    = rt;
            this.height   = 0;
        }
    
        /**Gives the data in the node.
         * 
         * @return T The element stored in the node
         */
        public T getData() {
            return this.element; 
        }
        
        /**Sets the data in the node.
         * 
         * @param theElement The new element for the node
         */
        public void setData(T theElement) {
            this.element = theElement;
        }       
    }

  


    // Test program
    public static void main(String [ ] args) {
        AvlTree<Integer> t = new AvlTree<Integer>();

        t.insert(45); 
        t.insert(99); 
        t.insert(5); 
        
        t.printTree(); 
        
        int test = t.findNextLargest(87); 
        
        System.out.println("This is the next after : " + test); 
        
    }
}