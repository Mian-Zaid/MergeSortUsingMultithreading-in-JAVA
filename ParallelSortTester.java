package mergeSort;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
public class ParallelSortTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//runSortTester();
		int arr[] = {12, 11, 13, 5, 6, 7}; 
        int i=0; 
        System.out.println("Given array is"); 
  
        for(i=0; i<arr.length; i++) 
            System.out.print(arr[i]+" "); 
  
        mergeSort(arr); 
  
        System.out.println("\n"); 
        System.out.println("Sorted array is"); 
  
        for(i=0; i<arr.length; i++) 
            System.out.print(arr[i]+" "); 
	}
	
	public static void mergeSort(int[] array) 
    { 
        if(array == null) 
        { 
            return; 
        } 
  
        if(array.length > 1) 
        { 
            int mid = array.length / 2; 
  
            // Split left part 
            int[] left = new int[mid]; 
            for(int i = 0; i < mid; i++) 
            { 
                left[i] = array[i]; 
            } 
              
            // Split right part 
            int[] right = new int[array.length - mid]; 
            for(int i = mid; i < array.length; i++) 
            { 
                right[i - mid] = array[i]; 
            } 
            mergeSort(left); 
            mergeSort(right); 
  
            int i = 0; 
            int j = 0; 
            int k = 0; 
  
            // Merge left and right arrays 
            while(i < left.length && j < right.length) 
            { 
                if(left[i] < right[j]) 
                { 
                    array[k] = left[i]; 
                    i++; 
                } 
                else
                { 
                    array[k] = right[j]; 
                    j++; 
                } 
                k++; 
            } 
            // Collect remaining elements 
            while(i < left.length) 
            { 
                array[k] = left[i]; 
                i++; 
                k++; 
            } 
            while(j < right.length) 
            { 
                array[k] = right[j]; 
                j++; 
                k++; 
            } 
        } 
    } 
	
	public static void runSortTester() {
        int SIZE = 10,   // initial length of array to sort
            ROUNDS = 1,
            availableThreads = (Runtime.getRuntime().availableProcessors())*2;

        Integer[] a;

        Comparator<Integer> comp = new Comparator<Integer>() {
            public int compare(Integer d1, Integer d2) {
                return d1.compareTo(d2);
            }
        };

        System.out.printf("\nMax number of threads == %d\n\n", availableThreads);
        for (int i = 1; i <= availableThreads; i*=2) {
            if (i == 1) {
                System.out.printf("%d Thread:\n", i);
            }
            else {
                System.out.printf("%d Threads:\n", i);
            }
            for (int j = 0, k = SIZE; j < ROUNDS; ++j, k*=2) {
                a = createRandomArray(k);
                
                // run the algorithm and time how long it takes to sort the elements
                long startTime = System.currentTimeMillis();
                ParallelMergeSorter.sort(a, comp, availableThreads);
                long endTime = System.currentTimeMillis();

                if (!isSorted(a, comp)) {
                    throw new RuntimeException("not sorted afterward: " + Arrays.toString(a));
                }else {
                	System.out.println("Sorted    : "+Arrays.toString(a));
                	java.lang.System.exit(0);
                }

                System.out.printf("%10d elements  =>  %6d ms \n", k, endTime - startTime);
            }
            System.out.print("\n");
        }
    }

    /**
     * Returns true if the given array is in sorted ascending order.
     *
     * @param a the array to examine
     * @param comp the comparator to compare array elements
     * @return true if the given array is sorted, false otherwise
     */
    public static <E> boolean isSorted(E[] a, Comparator<? super E> comp) {
        for (int i = 0; i < a.length - 1; i++) {
            if (comp.compare(a[i], a[i + 1]) > 0) {
                System.out.println(a[i] + " > " + a[i + 1]);
                return false;
            }
        }
        return true;
    }

    // Randomly rearranges the elements of the given array.
    public static <E> void shuffle(E[] a) {
        for (int i = 0; i < a.length; i++) {
            // move element i to a random index in [i .. length-1]
            int randomIndex = (int) (Math.random() * a.length - i);
            swap(a, i, i + randomIndex);
        }
    }

    // Swaps the values at the two given indexes in the given array.
    public static final <E> void swap(E[] a, int i, int j) {
        if (i != j) {
            E temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }

    // Creates an array of the given length, fills it with random
    // non-negative integers, and returns it.
    public static Integer[] createRandomArray(int length) {
        Integer[] a = new Integer[length];
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < a.length; i++) {
            a[i] = rand.nextInt(100);
        }
        System.out.println("Un Sorted : "+Arrays.toString(a));
        return a;
    }

}
