import java.util.*;

public class SetOfIntegers {

//Method to sort the sets in increasing order. First, the set is cloned so that the original is intact. 
//For loop to execute the insertion sort algorithm. Swaps acts as a counter for the number of passes in the algorithm.
    public static int[] sortIncreasing(int[] set, int[] swapCountHolder) {
        int[] sorted = set.clone();
        int swaps = 0;

        for (int i = 1; i < sorted.length; i++) {
            int key = sorted[i];
            int j = i - 1;

            while (j >= 0 && sorted[j] > key) {
                sorted[j + 1] = sorted[j];
                j--;
                swaps++;
            }
            sorted[j + 1] = key;
        }

        if (swapCountHolder != null && swapCountHolder.length > 0) {
            swapCountHolder[0] = swaps;
        }

        return sorted;
    }

//Method to reverse the order of the sets. First the set is cloned so that the original is intact.
//For loop swaps the element in the front with the element at the end. The length/2 indicates that half the set needs to be looped through.
    public static int[] reverse(int[] set) {
        int[] reversed = set.clone();
        for (int i = 0; i < reversed.length / 2; i++) {
            int temp = reversed[i];
            reversed[i] = reversed[reversed.length - 1 - i];
            reversed[reversed.length - 1 - i] = temp;
        }
        return reversed;
    }

//Returns a new array with the elements randomized by running Random().
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

// Adds unique values to a set and returns a new array. First, it includes all of the original values, then adds additional values
//indicated by the user. Returns the added values as integers and places them in the modified set.
    public static int[] addValue(int[] set, int... valuesToAdd) {
        Set<Integer> uniqueValues = new LinkedHashSet<>();
        for (int val : set) uniqueValues.add(val);
        for (int val : valuesToAdd) uniqueValues.add(val);

        return uniqueValues.stream().mapToInt(Integer::intValue).toArray();
    }

// Removes values from a set and returns a new array. For loop iterates through the set and keeps values not indicated by the user
//with the If statement.
    public static int[] removeValue(int[] set, int... valuesToRemove) {
        Set<Integer> valuesToRemoveSet = new HashSet<>();
        for (int val : valuesToRemove) valuesToRemoveSet.add(val);

        List<Integer> resultList = new ArrayList<>();
        for (int val : set) {
            if (!valuesToRemoveSet.contains(val)) {
                resultList.add(val);
            }
        }

        return resultList.stream().mapToInt(Integer::intValue).toArray();
    }
}
