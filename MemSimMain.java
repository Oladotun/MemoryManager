/**
 * Memory Simulation for 600.226 - partial main driver for I/O handling.
 */

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Driver for MemSimulator project - partial skeleton for I/O handling.
 */

final class MemSimMain {

    /**
     * Empty Constructor for Checkstyle.
     */
    private MemSimMain() {
    }

    /**
     * Main program.
     * @param args for Checkstyle
     * @throws FileNotFoundException if file not found
     * @throws IOException for bad filename
     */
    public static void main(String[] args) throws FileNotFoundException,
    IOException {

        MemoryManager[] sims = new BaseManager[3];
        
        // read input filename from keyboard, or get from command-line args
        String filename = "";
        if (args.length == 0) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter filename: ");
            filename = sc.nextLine();
        } else {
            filename = args[0];
        }

        Scanner fromFile = new Scanner(new File(filename));
        int memSize = 0;

        try {
            memSize = Integer.parseInt(fromFile.nextLine());
        } catch (NumberFormatException e) {
            memSize = -1;
        }


        PrintWriter outPut1 = new PrintWriter("translog.txt");
        outPut1.println("Memory Transaction Log");
        outPut1.println("Memory Size:" + memSize);
        outPut1.println("Input file: " + filename);
        outPut1.println("Memory Address begins at location 0");
        outPut1.println();

        outPut1.println("------------------------------------------------------"
            + "----------------------------------------------------------");
        outPut1.println("           Best Fit                                "
            + "Worst Fit                               Close Fit");
        outPut1.println("------------------------------------------------------"
            + "----------------------------------------------------------");
        outPut1.println("   ID  DF? Success  Addr.  Size           ID  DF? "
            + "Success  Addr.  Size           ID  DF? Success  Addr.  Size");
        outPut1.println();

        ArrayList<String> lines = new ArrayList<String>();
        String output = "";

        // insert lots of stuff here to fill lines, calling methods below
        
        sims[0] = new BestFitScheme(memSize);
        sims[1] = new WorstFitScheme(memSize);
        sims[2] = new CloseFitScheme(memSize);
        
        //index of where to add in ArrayList called lines
        int i = 0;
        boolean defragged = false;
        int id = 1;
        outPut1.print(" ");
        while (fromFile.hasNextLine()) {
            // Gets the letter part of file input, which is A or D
            String letter = fromFile.next();
            // Gets num portion following letter, 
            //which is size after A, and id after D


            letter = parseLine(letter);
            int num = Integer.parseInt(letter.substring(1).trim());
            letter = letter.substring(0,1);
            if (letter.equals("A")) {
                for (int k = 0; k < sims.length; k++) {
                    long startTime1 = System.nanoTime();
                    int address = sims[k].allocate(num); 
                    long endTime1 = System.nanoTime();
                    sims[k].addTime((endTime1 - startTime1) 
                            / BaseManager.UNIT_CONVERT);
                    if (sims[k].didDefrag()) {
                        defragged = true;
                    } else {
                        defragged = false;
                    }
                    output = formatAlloc(output , address, defragged, id, num);
                }
                id++;
            } 
            if (letter.equals("D")) {
                for (int k = 0; k < sims.length; k++) {
                    GeneralMemory hello = sims[k].deallocate(num);
                    output = formatDealloc(output, hello, num);
                }
            }
            lines.add(i, output);
            output = " ";
            i++;
        }
        
        printTrans(outPut1, lines);
        outPut1.close();
        
        
        PrintWriter outPut = new PrintWriter("analysis.txt");
        printOutput(outPut, sims, memSize, filename);

    }

        /** Check the line is a correct input.
     * 
     * @param line input string line
     * @throws IllegalArgumentException for string
     * @return a String if the input is legal
     */
    public static String parseLine(String line) throws IllegalArgumentException {

        final int input = 2;
        

        String []curr = line.split(" ");
                
        if (curr.length != input) {
            throw new IllegalArgumentException("invalid number of input");
        } 
        int num;
        
        
        if(!curr[0].equals("A") && !curr[0].equals("D")) {
            throw new IllegalArgumentException("invalid letter input");
        }
        try {
            num = Integer.parseInt(curr[1]);

        } catch (NumberFormatException ne) {
            throw new IllegalArgumentException("non-number in second");
        }
        return line;
    }


    /**
     * Formats allocation attempt for transaction log.
     * @param output String with allocation request info.
     * @param address Current address of allocation
     * @param defragged True if defrag occurred, false otherwise
     * @param id Current id number
     * @param size Allocation size
     * @return String representation of the allocation
     */
    public static String formatAlloc(String output, int address,
        boolean defragged, int id, int size) {

        String defrag = "";
        String sucString = "SUCCESS";

        if (defragged) {
            defrag = "DF";
        }
        if (address == -1) {
            sucString = "FAILED";
        }
        output += String.format("A%4d%4s  %-7s%6s%7s        ", 
             id, defrag, sucString, address, size);

        return output;
    }

     /**
     * Formats lines from deallocations for transaction log.
     * @param output String with deallocation info.
     * @param temp Block from deallocation
     * @param id Current id number
     * @return String representation of the deallocation
     */
    public static String formatDealloc(String output, GeneralMemory temp,
        int id) {  //Block temp

        String sucString = "SUCCESS";
        int deallocSize = 0;
        int address = 0;

        if (temp == null) {
            sucString = "FAILED";
            address = -1;
        } else {
            address = temp.getStartAddress();
            deallocSize = temp.getMemSize();
        }
        output += String.format("D%4s%4s  %-7s%6s%7s        ",
            id, "", sucString, address, deallocSize);
        return output;
    }

    /**
    * Print lines to transaction log.
    * @param outPut PrintWriter for transaction log
    * @param lines Arraylist of lines to be printed
    */
    public static void printTrans(PrintWriter outPut,
        ArrayList<String> lines) {
        for (String item : lines) {
            outPut.println(item);
        }
    }

    /**
    * Print lines to analysis log.
    * @param outPut PrintWriter for analysis log
    * @param sims Array of MemoryManagers
    * @param memSize Amount of memory in this simulation
    * @param filename Input file name
    */
    public static void printOutput(PrintWriter outPut, MemoryManager[] sims,
        int memSize, String filename) {
        outPut.println("Performance Analysis Chart");
        outPut.println("Memory Size " + memSize);
        outPut.println("Input File Used: " + filename);
        outPut.println("---------------------------------------------------"
            + "-------------------------");
        outPut.println("Statistics:                        Best Fit    "
            + "Worst Fit    Close Fit");
        outPut.println("---------------------------------------------------"
            + "-------------------------");
        outPut.println("");
        outPut.printf("%-35s%8s%13s%13s", "Number of Defragmentations:",
            sims[0].defragCount(), sims[1].defragCount(),
            sims[2].defragCount());
        outPut.println("");
        outPut.printf("%-35s%8s%13s%13s", "# of failed allocation requests:",
            sims[0].totalFails(), sims[1].totalFails(),
            sims[2].totalFails());
        outPut.println("");
        outPut.printf("%-35s%8s%13s%13s", "Average size failed allocs:",
            sims[0].avgFailsize(), sims[1].avgFailsize(),
            sims[2].avgFailsize());
        outPut.println("");
        outPut.printf("%-35s%8.2f%13.2f%13.2f",
                "Average time to process alloc*:",
            sims[0].avgTime(), sims[1].avgTime(),
            sims[2].avgTime());
        outPut.println("");
        outPut.printf("%-35s%8.2f%13.2f%13.2f",
                "Average time/size quicksort*:",
            sims[0].avgSortRatio1(), sims[1].avgSortRatio1(),
            sims[2].avgSortRatio1());
        outPut.println("");
        outPut.printf("%-35s%8.2f%13.2f%13.2f",
                "Average time/size bucketsort*:",
            sims[0].avgSortRatio2(), sims[1].avgSortRatio2(),
            sims[2].avgSortRatio2());
        outPut.println("");
        outPut.println("");
        outPut.println("*All times in microseconds.");
        outPut.close();
    }
}