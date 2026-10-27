
import java.util.Arrays;
import java.util.Random;

public class SortingBenchmark {

    static final int N = 100000;
    static Random random = new Random();

    public static void main(String[] args) {

        int[][] arrays = {
            createRandomArray(),
            createReversedArray(),
            createPartiallySortedArray(),
            createSortedArray()
        };

        String[] caseNames = {
            "مصفوفة عشوائية",
            "مصفوفة معكوسة",
            "مصفوفة مرتبة جزئيا",
            "مصفوفة مرتبة بالكامل"
        };

        String[] algorithms = {
            "الترتيب بالاختيار",
            "الترتيب بالإدراج",
            "الترتيب الفقاعي",
            "الترتيب بالدمج",
            "الترتيب السريع"
        };

        for (int c = 0; c < arrays.length; c++) {

            System.out.println("\nنتائج " + caseNames[c] + ":");

            for (int a = 0; a < algorithms.length; a++) {

                int[] testArray = Arrays.copyOf(
                    arrays[c], arrays[c].length
                );

                long startTime = System.nanoTime();

                switch (a) {
                    case 0:
                        selectionSort(testArray);
                        break;
                    case 1:
                        insertionSort(testArray);
                        break;
                    case 2:
                        bubbleSort(testArray);
                        break;
                    case 3:
                        mergeSort(testArray, 0, testArray.length - 1);
                        break;
                    case 4:
                        quickSort(testArray, 0, testArray.length - 1);
                        break;
                }

                long endTime = System.nanoTime();
                double time = (endTime - startTime) / 1000.0;

                System.out.println(
                    algorithms[a] + ": استغرق "
                    + time + " ميكروثانية"
                );
            }
        }

        System.out.println("\nانتهت جميع الاختبارات.");
    }

    public static int[] createRandomArray() {
        int[] array = new int[N];

        for (int i = 0; i < N; i++) {
            array[i] = random.nextInt(1000000);
        }

        return array;
    }

    public static int[] createSortedArray() {
        int[] array = new int[N];

        for (int i = 0; i < N; i++) {
            array[i] = i;
        }

        return array;
    }

    public static int[] createReversedArray() {
        int[] array = createSortedArray();

        for (int i = 0; i < N / 2; i++) {
            int temp = array[i];
            array[i] = array[N - 1 - i];
            array[N - 1 - i] = temp;
        }

        return array;
    }

    public static int[] createPartiallySortedArray() {
        int[] array = createSortedArray();

        for (int i = (int) (N * 0.8); i < N; i++) {
            array[i] = random.nextInt(1000000);
        }

        return array;
    }

    public static void selectionSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            int min = i;

            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[min]) {
                    min = j;
                }
            }

            int temp = array[i];
            array[i] = array[min];
            array[min] = temp;
        }
    }

    public static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }

            array[j + 1] = key;
        }
    }

    public static void bubbleSort(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
        }
    }

    public static void mergeSort(int[] array, int left, int right) {
        if (left < right) {
            int middle = left + (right - left) / 2;

            mergeSort(array, left, middle);
            mergeSort(array, middle + 1, right);

            merge(array, left, middle, right);
        }
    }

    public static void merge(int[] array, int left, int middle, int right) {
        int[] temp = new int[right - left + 1];

        int i = left;
        int j = middle + 1;
        int k = 0;

        while (i <= middle && j <= right) {
            if (array[i] <= array[j]) {
                temp[k++] = array[i++];
            } else {
                temp[k++] = array[j++];
            }
        }

        while (i <= middle) {
            temp[k++] = array[i++];
        }

        while (j <= right) {
            temp[k++] = array[j++];
        }

        System.arraycopy(temp, 0, array, left, temp.length);
    }

    public static void quickSort(int[] array, int low, int high) {
        if (low >= high) {
            return;
        }

        int pivot = array[low + (high - low) / 2];

        int i = low;
        int j = high;

        while (i <= j) {
            while (array[i] < pivot) {
                i++;
            }

            while (array[j] > pivot) {
                j--;
            }

            if (i <= j) {
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;

                i++;
                j--;
            }
        }

        if (low < j) {
            quickSort(array, low, j);
        }

        if (i < high) {
            quickSort(array, i, high);
        }
    }
}
