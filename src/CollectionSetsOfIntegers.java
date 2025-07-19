    //Created by Dibblz


import java.util.Arrays;
import java.util.ArrayList;

public class CollectionSetsOfIntegers {
	static /*variable for the array of int arrays */
	ArrayList<int[]> sets = new ArrayList<>();  //THE VARIABLE FOR THE ARRAY OF ARRYS!!
	
	
	/* Lists all the sets in the 'sets' list, one per line with a letter label (A, B, C...). 
	 * */	
	public static void listAll() {
	    System.out.println("You have called the listAll function!");
	    
	    for (int i = 0; i < sets.size(); i++) {
	        char letter = (char) ('A' + i);
	        System.out.println(letter + ": " + Arrays.toString(sets.get(i)));
	    }
	}
	
	
	/* Adds a new int[] set to the list of sets.
	 * */	
	public static void createSet(int[] x) {
	    System.out.println("You have called the createSet function!");
	    
	    sets.add(x);
	}	
	
	
	/* Deletes a set at the specified index from the list of sets.
	 * */
	public static void deleteSet(int x) {
		System.out.println("You have called the deleteSet function!");
		
		sets.remove(x);
	}
	
	
	/*   Restores the list of sets from a file saved by the 'save' method.
 * Each line of the file should represent a set, with comma-separated integers.
 * Clears any existing sets before loading new ones. */
	public static void restore(String filename) throws java.io.FileNotFoundException {  //takes argument of filename
		System.out.println("You have called the restore function!");
		
		  sets.clear();
		    java.util.Scanner scanner = new java.util.Scanner(new java.io.File(filename));
		    while (scanner.hasNextLine()) {
		        String line = scanner.nextLine().trim();
		        if (!line.isEmpty()) {
		            String[] parts = line.split(",");
		            int[] set = new int[parts.length];
		            for (int i = 0; i < parts.length; i++) {
		                set[i] = Integer.parseInt(parts[i]);
		            }
		            sets.add(set);
		        }
		    }
		    scanner.close();

		    System.out.println("Restored from " + filename);
	}
	
	
	/* * Saves all sets in the 'sets' list to a text file.
 * Each int[] set is written on its own line, with values comma-separated (e.g., 1,2,3).
 * The file is saved relative to the current working directory 
 * typically the project root unless a full path is specified.
	 * */
	public static void save(String filename) throws java.io.FileNotFoundException { //takes argument of filename
	    System.out.println("You have called the save function!");

	    java.io.PrintWriter writer = new java.io.PrintWriter(filename);
	    for (int i = 0; i < sets.size(); i++) {
	        int[] set = sets.get(i);
	        StringBuilder line = new StringBuilder();
	        for (int j = 0; j < set.length; j++) {
	            line.append(set[j]);
	            if (j < set.length - 1) {
	                line.append(",");
	            }
	        }
	        writer.println(line.toString());
	    }
	    writer.close();

	    System.out.println("Saved to " + filename);
	    System.out.println("Absolute path: " + new java.io.File(filename).getAbsolutePath());
	}
	
	
}
	
