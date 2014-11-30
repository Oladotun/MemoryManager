import java.util.ArrayList;

/** Class for a generic hash table.
 * 
 * @author Connie Chang 
 * @author Lara Gatehouse
 * @author Oladotun Opasina
 * @author Whitney Kim
 * @param <T> generic object extends comparable
 *
 */
public class HashTable<T extends Comparable<? super T>> {
    
    /** Load Factor. */
    private final double loadFactor = 0.75;
    
    /** number of elements in hashtable. */
    private int size;
    /** Number of rows in hashtable.*/
    private int rowNumber;
    /** Item in the hashtable. */
    private ArrayList<T> []newItem;
    /** Keeps track of the used Buckets. */
    private int numberUsedBucket;
    
    /** the current data. */
    private T currData;
    
    /** the max data. */
    private T maxData;
   
    
    /** public constructor. */
    public HashTable() {
        
    }
    /** Constructor to add only one data to the Hash table.
     * 
     * @param data data to be added to the hash table
     */
    public HashTable(T data) {
        this.newItem = (ArrayList<T>[]) (new ArrayList[1]);
        this.newItem[0] = new ArrayList<T>();
        this.newItem[0].add(data);
        this.size = 1;
        this.rowNumber = 1;
        this.numberUsedBucket = 1;
        this.maxData = data;
        this.currData = data;
        
    }
    /**
     * 
     * @param length of bucket for the hash table
     */
    public HashTable(int length) {
        
        if (length <= 0) {
            throw new IllegalArgumentException("Memory Cant be zero");
        }
                
        if (!this.isPrime(length) && length != 1) {            
            length = this.getNextPrime(length);
        }
        
        this.newItem = (ArrayList<T>[]) (new ArrayList[length]);
        this.size = 0;
        this.rowNumber = length;
        this.numberUsedBucket = 0;
        
        
    }
    /** Keeps track of the maximum data in table.
     * 
     * @return the maximum data in hash table
     */
    public T getMaxData() {
        return this.maxData;
        
    }
    /**
     * 
     * @return the number of rows in the table
     */
    public int hashRows() {
        return this.rowNumber;
    }
    /**
     * 
     * @return the number of items in hash table
     */
    public int size() {
        return this.size;
        
    }
    
    /** checks if the hash table is empty.
     * 
     * @return a boolean if the hash table is empty
     */
    
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    /** Checks the number of filled buckets.
     * 
     * @return the number of used bucket
     */
    public int usedBuckets() {
        return this.numberUsedBucket;
    }
    /** Insert a new item.
     * 
     * @param data insert into the hash table
     * @return when insertion occurs
     */
    public boolean insert(T data) {
                
        int hash = this.hashFunc(data); 
        
        if (hash == -1) {
            throw new IllegalArgumentException("No Rows");
        }
             
        if (this.newItem[hash] == null) {
            this.numberUsedBucket++;
            this.newItem[hash] = new ArrayList<T>();
        }
        
        if (this.maxData == null || this.maxData.hashCode() < data.hashCode()) {
            this.maxData = data;
        }
        
        this.currData = this.maxData;
        this.newItem[hash].add(data);
        this.size++;
        
        double load = ((double) this.numberUsedBucket)
                / ((double) this.rowNumber);
        if (load >= this.loadFactor) {
            this.reHash();

        }
        return true;
    }
    /** reHash method which calls hash function.
     * 
     * @return whether we re hashed or not successfully
     */
    
    private boolean reHash() {
        if (this.isEmpty()) {
            return false;
        }
        
        ArrayList<T> temp;
        T currNode;
        temp = this.allData();
        int newsize = this.getNextPrime(this.rowNumber);
        int curr = 0;
        this.newItem = (ArrayList<T>[]) (new ArrayList[newsize]);
        this.numberUsedBucket = 0;

        for (int i = 0; i < temp.size(); i++) {
            currNode = temp.get(i);
            curr = this.hashFunc(currNode);

            if (this.newItem[curr] == null) {
                this.newItem[curr] = new ArrayList<T>();
                this.numberUsedBucket++;
            }
            this.newItem[curr].add(currNode);

        }
        this.rowNumber = this.newItem.length;
        return true;

    }
    
    
    /** checks if the hash table contains a particular data.
     * 
     * @param data item to be checked for
     * @return whether a data is present or not
     */
    
    public boolean contains(T data) {
        int hash = this.hashFunc(data);
        
        if (this.isEmpty()) {
            return false;
        }

        if (this.newItem[hash] != null) {
            return this.newItem[hash].contains(data);
        }
        return false;
    }
    
    /** remove an item from the hash table if present.
     * 
     * @param data to be removed
     * @return if the data was removed or not
     */
    
    public boolean remove(T data) {
        int hash = this.hashFunc(data);
        if (this.isEmpty()) {
            return false;
        }
        if (this.contains(data)) {
            this.newItem[hash].remove(data);
            
            if (this.newItem[hash].isEmpty()) {
                this.newItem[hash] = null;
            }
            
            this.size--;
            return true;
        }
        
        return false;
    }
    /** A Array list of all items.
     * 
     * @return the a Array list of all items in the HashTable
     */
    public ArrayList<T> allData() {
        
        ArrayList<T> items = new ArrayList<T>();
        if (this.isEmpty()) {
            return null;
        }
        
        for (int i = 0; i < this.rowNumber; i++) {
            if (this.newItem[i] != null) {
                items.addAll(this.newItem[i]);
            }
        }
        return items;
    }
    
    /** Helper method to get the closest fit in a bucket.
     * 
     * @param data to find a close fit for
     * @return a boolean if a close fit was found
     */
    public boolean getCloseFit(T data) {
        
        int hash = this.hashFunc(data);        
        if (this.newItem[hash] == null) {
            return false;
        }
        
        for (int i = 0; i < this.newItem[hash].size(); i++) {
            if (data.hashCode() <= (this.newItem[hash].get(i).hashCode())) {
                this.currData = this.newItem[hash].get(i);
                return true;
            }
        }
        
        return false;
        
    }
    /** Helper method to get data.
     * 
     * @return the current data in a particular bucket
     */
    public T getData() {
        
        return this.currData;

        
    }
    
    /**
     * 
     * @param input generic type input
     * @return an integer representing the index to be inputed
     */
    
    public int hashFunc(T input) {
        if (this.rowNumber == 0) {
            return -1;
        }
        return input.hashCode() % this.rowNumber;

    }
    
    /** String representation of HashTable elements.
     * @return String representation of the elements in Hash Table
     */
    
    public String toString() {
        String elements = "Bucket \t Data\n";
        for (int i = 0; i < this.rowNumber; i++) {
            if (this.newItem[i] != null) {
                elements += i;
                elements += " \t ";
                elements +=  this.newItem[i].toString();
                elements += "\n";
            }
        }
        return elements;
    }
    /** equals method compares two hash tables.
     * 
     * @param o generic object to be compared
     * @return a boolean if both tables are equal
     */
    public boolean equals(Object o) {
        int curr = 0;
        if (o instanceof HashTable) {
            HashTable<T> oth = (HashTable<T>) o;
            if (oth.size() != this.size()) {
                return false;
            }
            for (int i = 0; i < this.rowNumber; i++) {
                if (this.newItem[i] != null && oth.newItem[i] != null) {
                    if (!this.newItem[i].equals(oth.newItem[i])) {
                        return false;
                    }
                } else if (this.newItem[i] == null && oth.newItem[i] == null) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
            
        }
        
        return false;

    }
    /** Calculate the hash code of the table.
     * 
     * @return the hash code for the table
     */
    
    public int hashCode() {
        return this.newItem.hashCode();
    }
    /**
     * 
     * @param input number to be checked
     * @return if a number if prime or not
     */
    public boolean isPrime(int input) {
        final int value = 3; // 1 is not a prime
        
        if (input % 2 == 0) {
            return false;
        }
        
        for (int i = value; i * i <= input; i += 2) {
            if (input % i == 0) {
                return false;             
            }   
        }
        return true;
       
    }
    /**
     * @param input the input number
     * @return the next prime number
     */
    public int getNextPrime(int input) {
        do {
            input++;            
        } while(!this.isPrime(input));
        return input;
        
    }
}