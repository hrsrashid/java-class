import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Task5 {

  public static void main(String[] args) {
    int[] array = (new Random()).ints(10, 0, 100).toArray();
    printArray(array);
    System.out.println("Sorting...");
    quicksort(array);
    printArray(array);
  }

  public static void printArray(int[] array) {
    for (int i : array) {
      System.out.print(i + " ");
    }
    System.out.println();
  }

  public static void quicksort(int[] array) {
    Queue<int[]> q = new LinkedList<>();
    BlockingQueue<Thread> pool = new ArrayBlockingQueue<>(Math.max(2, Runtime.getRuntime().availableProcessors()));

    q.offer(new int[] {0, array.length - 1});

    try {
      do {
        while (!q.isEmpty() && pool.remainingCapacity() > 0) {
          int[] bounds = q.poll();

          if (bounds[0] < bounds[1]) {
            Thread t = new Thread(() -> {
              int boundary = partition(array, bounds[0], bounds[1]);
              q.offer(new int[] {bounds[0], boundary});
              q.offer(new int[] {boundary + 1, bounds[1]});
            });

            t.start();
            pool.put(t);
          }
        }

        while (!pool.isEmpty()) {
          pool.take().join();
        }

      } while (!q.isEmpty());

    } catch (InterruptedException e) {
      System.out.println("Error: sort interrupted");
    }
  }

  public static int partition(int[] array, int low, int high) {
    int pivot = array[low];
    int i = low - 1;
    int j = high + 1;

    while (true) {
      do {
          i++;
      } while (array[i] < pivot);

      do {
          j--;
      } while (array[j] > pivot);

      if (i >= j) {
        return j;
      }

      swap(array, i, j);
    }
  }

  public static void swap(int[] array, int i, int j) {
    int t = array[i];
    array[i] = array[j];
    array[j] = t;
  }
}
