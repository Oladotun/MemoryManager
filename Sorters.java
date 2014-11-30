import java.util.ArrayList;


/** Class Sorter, contains all the sorting methods.
 * such as bucketSort and quickSort
 * @author Connie Chang 
 * @author Lara Gatehouse
 * @author Oladotun Opasina
 * @author Whitney Kim
 * Project P4
 * @param <T> generic type for the sorter class
 */
public class Sorters<T extends Comparable<T>> {
    /** Main method used to test if sorting methods work.
     * @param args
     */

    /** Public constructor for sorter class. */
    public Sorters() {
        
    }
    
    /** Public method quicksort an an ArrayList.
     * 
     * @param values ArrayList of values
     */
    
    public void quickSort(ArrayList<T> values) {
        this.quickSort(values, 0, values.size() - 1);
    }
    /** Method sorts array by divide and conquer method of splitting
     * array into two, then recursively sorting smaller subarrays.
     * @param values an array.
     * @param low index of lowest number in partition of array.
     * @param high index of highest number in partition of array.
     */
    private void quickSort(ArrayList<T> values, int low, int high) {
        //check if empty or null
        if (values == null) {
            return;
        }
        //pick point of pivot
        int midpoint = low + (high - low) / 2;
        T pivot = values.get(midpoint);
        //makes sure right greater than pivot and left less than pivot
        int i = low;
        int j = high;
        while (i <= j) {
            while (pivot.compareTo(values.get(i)) > 0) {
                i++;
            }
            while (pivot.compareTo(values.get(j)) < 0) {
                j--;
            }
            if (i <= j) {
                T temp = values.get(i);
                values.set(i, values.get(j));
                values.set(j, temp);
                i++;
                j--;
            }
        }
        //recursively sort 
        if (low < j) {
            this.quickSort(values, low, j);
        }
        if (high > i) {
            this.quickSort(values, i, high);
        }
    }

    /** Method bucketSort sorts the array by creating partitions of array.
     * into a number of buckets
     * @param values an array. 
     */
    public void bucketSort(ArrayList<GeneralMemory> values) {
        // Checks if array is empty
        if (values.isEmpty()) {
            return;
        }
        GeneralMemory max = this.findMax(values);
        //create new array bucket
        ArrayList<GeneralMemory> buckets =
                new ArrayList<GeneralMemory>(max.getStartAddress() + 1);
        //Initialize the buckets
        for (int i = 0; i <= max.getStartAddress(); i++) {
            buckets.add(null);
        }
        // Update each bucket, Avoids Collision
        for (int i = 0; i < values.size(); i++) {
            GeneralMemory oldData = values.get(i);
            int code = oldData.getStartAddress();
            buckets.set(code, oldData);
            
        }
        int num = 0;
        //empty bucket back into array
        for (int i = 0; i < buckets.size(); i++) {
            GeneralMemory data = buckets.get(i);
            if (data != null) {
                values.set(num, data);
                num++;
            }
        }

    }
    
    
    /** Helper method to find maximum value.
     * 
     * @param values passed in ArrayList
     * @return the maximum item in list
     */
    private GeneralMemory findMax(ArrayList<GeneralMemory>  values) {
        if (values == null) {
            return null;
        }
        GeneralMemory max = values.get(0);
        GeneralMemory curr;
        for (int i = 1; i < values.size(); i++) {
            curr = values.get(i);
            if (curr.getStartAddress() > max.getStartAddress()) {
                max = curr;
            }
        }
        return  max;
    }


}