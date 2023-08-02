package HW2;

// Пирамидальная сортировка
public class App {
    public static void main(String[] args) {

        int[] array = new int[] { 1, 3, 7, 9, 7, 2, 8, 5 };
        System.out.println(print(array));
        heapSort(array);
        System.out.println(print(array));

    }

    private static String print(int[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        for (int i = 0; i < array.length; i++) {
            stringBuilder.append(array[i]).append(',');

        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    private static void heapSort(int[] array) {
        for (int i = array.length / 2 - 1; i >= 0; i--) {
            heapify(array, array.length, i);
        }
        for (int i = array.length - 1; i >= 0; i--) {
            int temp = array[0];
            array[0] = array[i];
            array[i] = temp;
            heapify(array, i, 0);
        }
    }

    private static void heapify(int[] array, int size, int rootIndex) {
        int largest = rootIndex;
        int firstChildIndex = 2 * rootIndex + 1;
        int secondChildIndex = 2 * rootIndex + 2;

        if (firstChildIndex < size && array[largest] < array[firstChildIndex]) {
            largest = firstChildIndex;
        }
        if (secondChildIndex < size && array[largest] < array[secondChildIndex]) {
            largest = secondChildIndex;
        }

        if (largest != rootIndex) {
            int temp = array[largest];
            array[largest] = array[rootIndex];
            array[rootIndex] = temp;
            heapify(array, size, largest);
        }

    }
}

