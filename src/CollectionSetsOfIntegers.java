//Created by Dibblz
//7/14/25
//COSC 381



import java.util.Arrays;
import java.util.ArrayList;

/* */
public class CollectionSetsOfIntegers {
	static /*variable for the array of int arrays */
	ArrayList<int[]> sets = new ArrayList<>();
	
	
	/* Lists all the sets(int arrays) in the list of sets(array of int array)
	 * */	
	public static void listAll(int[][] x) {
		System.out.println("You have called the listAll function!");
		for(int i = 0; i  < x.length; i++) {
			char letter = (char) ('A'+i);
				System.out.println(letter + ":" + Arrays.toString(x[i]));
			}
			
		}
	
	/* Creates an array and adds it to the array  of arrays
	 * */	
	public static void createSet(int[] x) {
		System.out.println("You have called the createNewSet function!");
	}
	
	
	/* Deletes an array from the array  of arrays
	 * */
	public void deleteSet() {
		System.out.println("You have called the deleteSet function!");
	}
	
	
	/* Restores a save file, saved using CollectionSetsOfIntegers.save().
	 * This will restore the saved array  of arrays	 * */
	public void restore() {
		System.out.println("You have called the restore function!");
	}
	
	
	/* Save function that stores your array of arrays into a text file. 
	 * */
	public static Integer[][] save() {
		System.out.println("You have called the save function!");
		Integer[][] sets = {{}};
		return sets;
	}
	
	/*public static void main(String[] args) {
		int[][] array1 = {
				{1,2,3,4},
				{1},
				{1,2,3,4,5},
				{00000000000}}; */
	
	//	listAll(array1);
	//}
		
}

