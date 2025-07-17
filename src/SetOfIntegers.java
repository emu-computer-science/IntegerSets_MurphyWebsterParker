import java.util.*;

public class SetOfIntegers {

    // First the array is cloned then, java.util uses the sort method to sort the sets in order. Still have to update how many sorts it takes.
    public static int[] sortIncreasing(int[] set) {
        int[] sorted = set.clone(); 
        Arrays.sort(sorted);         
        return sorted;
    }

    // First the array is cloned then, java.util uses the sort method again to sort sets in order but, for loop reverses the order.Still have to update how many sorts it takes.
    public static int[] sortDecreasing(int[] set) {
        int[] sorted = set.clone();
        Arrays.sort(sorted);         // sort ascending

        //For loop to reverse the sorted sets
        for (int i = 0; i < sorted.length / 2; i++) {
            int temp = sorted[i];
            sorted[i] = sorted[sorted.length - 1 - i];
            sorted[sorted.length - 1 - i] = temp;
        }
        return sorted;
    }

    // java.util uses the random method to randomly order the numbers in the set. Still have to update how many sorts it takes.
    public static int[] randomSort(int[] set) {
        int[] shuffled = set.clone();
        Random rand = new Random();

        for (int i = shuffled.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            int temp = shuffled[i];
            shuffled[i] = shuffled[j];
            shuffled[j] = temp;
        }

        return shuffled;
    }

    // Adds user input to the set and returns the new array
    public static int[] addValue(int[] set, int... valuesToAdd) {
        int[] result = new int[set.length + valuesToAdd.length];
        System.arraycopy(set, 0, result, 0, set.length);
        System.arraycopy(valuesToAdd, 0, result, set.length, valuesToAdd.length);
        return result;
    }

    // Removes value indicated by the user input
    public static int[] removeValue(int[] set, int... valuesToRemove) {
        boolean[] toRemove = new boolean[set.length];
        int removeCount = 0;

        for (int i = 0; i < set.length; i++) {
            for (int val : valuesToRemove) {
                if (set[i] == val) {
                    toRemove[i] = true;
                    removeCount++;
                    break;
                }
            }
        }

        int[] result = new int[set.length - removeCount];
        int idx = 0;
        for (int i = 0; i < set.length; i++) {
            if (!toRemove[i]) {
                result[idx++] = set[i];
            }
        }

        return result;
    }
}
